package com.gmself.studio.mg.basemodule.log_tool;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

/**
日期：2012.12.13
作者： Lei Yi, leiyi@suyuanchang.com
修改： Guo Meng, 2002guomeng@163.com
保密性：无须

使用方法：在每一个需要调用的程序文件中如下使用：

	import com.l3androidtools.Logger;
	……
	Logger.log(Logger.LogType.XXX, "log内容字符串");
这将生成一行日志到 LogCat 和 /sdcard/l3log.txt 中，内容为：
	"mm-dd hh:mm:ss.microsecond, type:nnnnnnnn, log内容字符串"

还可替换常见的：e.printStackTrace()：
	try {……}
	 catch (xxxException e) {
	 	Logger.logException(e);
	 	}
在程序退出时，调用一次 ExitLogger()，使得内存及时回收。（否则在下次运行APP时可能触发 Android 对本 APP GC 耗时很长甚至无法完成的 bug）

其它说明：
	默认日志输出为：LogCat 和 /sdcard/l3log.txt，如果要禁用，则调用：
		Logger.setLogCatActive(false);	//禁用 LogCat 输出
		Logger.setLogFileActive(false);	//禁用 SD 文件输出
	默认 SD 文件路径为：/sdcard/l3log.txt，如要修改，则调用：
		Logger.setLogFilePath("/path/to/logfile/filename");
	默认日志类型为全部类型，如果要关闭某些类型，则调用：
		Logger.setLogLevel(0xnnnnnnnn);其中 0xnnnnnnnn 介于  0~ffffffff 之间，采用位操作机制决定32类日志中的哪些类型需要最终输出

Android 权限需求：
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 */
public class Logger {

	/**
	 * 日志类型定义，共64类（即 long 型的位数）
	 * 把所有希望归出的类型描述在此，用于调用 log()时指明日志的类型。
	 * 当调用 setLogLevel(long type)时，type 值与下述  enum 的2幂值进行与操作，来决定某类日志是否最终输出（到文件或 LogCat ）。
	 *	另：java中， enum 变量的默认起始值为0 ，正好符合 bit 位的编号规则，用于进行 2^ 操作
	 */
	public enum Type {
		DEBUG,
		FILE,
		NET_PORT,
		WRONG,
		ERROR

		}

	public	static final long LOG_LEVEL_NONE	= 0;
	public	static final long LOG_LEVEL_ALL	= -1;	//注：如果写成 0xffffffffffffffff; 会有超出界限错误

	private	static	long	logLevel=LOG_LEVEL_ALL;	//默认打开全部日志。建议的使用方式是在用户配置文件中记录日志级别，再调用 setLogLevel() 设置生效

	private	static	int	MAX_LOGFILE_SIZE = 1024*1024;	//设置LOG文件大小回收阀值，默认1MB，每次运行程序时发现超过此大小后将被回收旧的内容，以防运行速度变慢，及占用无谓存储。
	private	static	boolean	isLogCatActive=true;
	private	static	boolean	isLogFileActive=true;

	private	static boolean firstLogFile = true;	//是否是首次打开日志文件

	private	static String strToday = "";
	private	static final String strTodayFormat = "%Y-%m-%d";
	private	static final String TAG = "L3Log";
	private	static final String DEFAULT_FILENAME = "l3logger";
	private	static final String DEFAULT_EXTFILENAME = ".txt";	//".log"; .log 文件在通常Android手机下不便于关联文本编辑器

	private	static String filePathName=null;
	private	static PrintWriter writer = null;
	private	static Timer flushFileTimer = null;
	private	static	int	flushInterval = 3*1000;	//默认最长3秒钟内把日志刷新到文件中。此配置可能使得 tail -f 命令看到的日志数据有3秒钟的时延
	private	static	boolean isFileBuffered=false;
	private	static Context ctx = null;
	
	private	static final String nl = System.getProperty("line.separator");


	public static void init(Context ctx){
		setContext(ctx);

		String logPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/mg_logger.txt";
		setLogFilePathName(logPath);
	};

	public static void init(Context ctx, String path){
		setContext(ctx);

		setLogFilePathName(path);
	};

	/**
	 * 输出日志主函数。
	 */
	private 	static	void	log(Type logType, String logText)
		{
		int typeValue=logType.ordinal();	//返回在 enum 中的顺序，第一项是0
			if((logLevel & (1<<typeValue)) != 0)
				{
				String logTextAll=formatLog(logType, logText);

				if(isLogCatActive)	logLogCat(logTextAll);
				if(isLogFileActive)	logLogFile(logTextAll);
			}
//	return true;
		}

	/**
	 * 输出日志主函数。
	 */
	public	static synchronized	void log(Type logType, String... logTexts)
	{
		log(logType, "----begin ↓----");
		for (String str: logTexts){
			log(logType, str);
		}
		log(logType, "----end   ↑----");
//	return true;
	}

	/**
	 * 输出日志主函数。
	 */
	public	static 	void log_unsync(Type logType, String logTexts)
	{
		log(logType, logTexts);
//	return true;
	}

/**
 * 设置 app 的 Context，以便日志文件尽量存到 App 私有路径中。否则，将尝试存在 SD 卡根路径中，这在 Android 4.x 之后将失败
 * @param ctx
 */
	private static void setContext(Context ctx) {
		Logger.ctx = ctx.getApplicationContext();
	}

/**
 * 输出 Exception 日志的专用函数。替换常见的：e.printStackTrace(); 写法。
 * 将无条件输出日志到文件和 LogCat 中。
 * 调用方法： Logger.logException(e, "地点描述");
 */
	public	static synchronized 	void	logException(Exception e, final String strType)
	{
	String str = e.toString();		//Manual说：toString()=getClass().getName() + '@' + Integer.toHexString(hashCode())，但是实际获得的描述文字为例如：
						//java.io.FileNotFoundException: /sdcard/test.txt: open failed: ENOENT (No such file or directory)
//	String msg = e.getMessage();	//Manual说：Returns the extra information message which was provided when this Throwable was created。实际获得的描述文字为例如：
//				// /sdcard/test.txt: open failed: ENOENT (No such file or directory)

	String logTextAll=getTimeString() + ", an exception occured on :" + strType + nl + str ;
	logLogCat(logTextAll);
	logLogFile(logTextAll);
	}

	/**
	 * 日志文件修改为自定义的路径
	 * 注：目录不必已存在，只要本 APP 有创建目录的权限即可自动生成日志文件
	 * 一般在 APP 入口处调用，或者用户在配置中修改了数据存储位置时调用
	 * 
	 * 如果未调用过，则默认日志文件名为 <DEFAULT_FILENAME><DEFAULT_EXTFILENAME>，通常位于 /sdcard/ 目录下
	 */
	public	static boolean setLogFilePathName(String newPathName) {
		if(!newPathName.equals(filePathName)){
			if( filePathName != null ) {
					log(Type.FILE, "Log file will be moved to "+newPathName);
					flushLogFile();
			}
			filePathName = newPathName;
			return prepareLogFile();
		}
		return true;
	}

	/**
	 * 获得日志文件路径
	 */
	public	static String getLogFilePathName()
		{
		return filePathName;
		}

	/**
     * 设置需要真正输出日志的那些类型，达到运行级日志配置的目的。如果发布的程序在某些用户那儿总是遇到问题，可以请用户在设置界面中全选所有类型，然后在遇到错误以后，发回日志文件供开发人员分析原因
     * logLevelNew 参数是要激活的类型的置1位的位操作组合。例如：setLogLevel(0xfffffff7) 将关闭 0x8 所对应的 enum Type 中第4类日志
     * logLevelNew = LOG_LEVEL_NONE 时，将关闭全部日志； = LOG_LEVEL_ALL 时，将打开全部日志。
     */
	public	static	void	setLogLevel(long logLevelNew)
		{
		logLevel = logLevelNew;
		}

	/**
	 * 设置需要真正输出日志的那些类型（以可变参数形式）
	 * 例如：setLogTypes(Logger.Type.Debug, Logger.Type.中文enum测试); 则将只输出 Debug 和 中文enum测试 这 2 类日志
	 * 返回：所选类型的置1位的位操作组合值。
	 */
	public	static long	setLogTypes(Type... typeArray)
		{
		logLevel=0;
		for(int i=0; i<typeArray.length; i++)
			{
			logLevel |= (1<<typeArray[i].ordinal());
			}
		return logLevel;
		}
	
	/**
	 * 获取当前设定的日志级别。
	 */
	public static long getLogLevel()
		{
		return logLevel;
		}

	/**
	 * 设置是否输出到 LogCat
	 */
	public	static void setLogCatActive(boolean isActive)
		{
		isLogCatActive=isActive;
		}

	/**
	 * 设置是否输出到文件
	 */
	public	static	void	setLogFileActive(boolean isActive)
		{
		isLogFileActive=isActive;
		}

	/**
	 * 修改 flush 间隔。综合考虑手机存储设备性能以及日志观察的及时性需求，建议位置为 1000-3000 毫秒
	 */
    public	static void setLogFileFlushInterval(int interval)
    	{
    	flushInterval = interval;
    	}

	/**
	 * 刷新日志文件。
	 * 程序结束时应该调用一次，确保最后3秒日志的内容能够被刷新到日志文件
	 */
	public	static void flushLogFile()
		{
		if(isFileBuffered)
			{
			isFileBuffered=false;
			writer.flush();
			Log.d(TAG, "L3Log file flushed");
//			isFileBuffered=false;	20131231 提到{最前面。因从 LogCat中经常看到2条同一毫秒时的  Log.d(TAG, "L3Log file flushed"); 动作
			}
		}

	/**
	 * 朝 LogCat 输出日志
	 */
	private	static	void	logLogCat(String logText)
		{
		Log.d(TAG, logText);
		}

	/**
	 * 朝文件输出日志。如果是首次调用，则将初始化日志文件
	 */
	private	static	void	logLogFile(String logText)
		{
		if (firstLogFile == true) {
			firstLogFile = false;
			initLogFile();
		} else {
			Time time=new Time(); time.setToNow();
			if( ! strToday.equals(time.format(strTodayFormat)) ) {	//跨天了，需要插入一段日期分割说明，以免阅读时不知道日期
				strToday = time.format(strTodayFormat);
				writeLogFile(	"----------------------------------------" + nl +
								"|\tToday is: " + strToday + "\t\t|" + nl +
								"----------------------------------------"	);
			}
		}
		writeLogFile(logText);
	}

	/**
	 * 朝文件写日志
	 */
	private	static	void	writeLogFile(String logText)
		{
		if(writer != null)
			{
			writer.println(logText);
			isFileBuffered=true;
			}
		}

/**
 * 初次写日志文件时的初始化动作。正常的话， writer 对象将就绪。如果此前调用者没有设置自定义的 Logfile 路径，则采用默认的 SD 卡分区上的 L3Log.txt 文件
 */
	private	static synchronized boolean initLogFile()
		{
		if(filePathName==null){
			configLogFilePathName();
			}
		return prepareLogFile();
		}

    /**
     * 计算 一个合适的日志文件路径，保存在 filePathName 中，通常为 /mnt/sdcard/l3logger.txt
     */
    private static void configLogFilePathName() {
    	String logPath;
    	if(ctx != null)
    		logPath = ctx.getFilesDir().getPath();
    	else
    		logPath = Environment.getExternalStorageDirectory().getPath();
   		filePathName = logPath + "/" + DEFAULT_FILENAME + DEFAULT_EXTFILENAME;
	}

	/**
	 * 按 filePathName 描述的文件，准备日志文件供输出日志，
	 * 当为新文件时，将新建之；当为旧文件时，将检查大小，抹掉超过 MAX_LOGFILE_SIZE 的旧内容
	 */
    private	static boolean prepareLogFile() {
    	makeFileExist();
    	cutOverloadSize();
		return openFileforLog();
	}

    /**
     * 打开日志文件，以追加模式准备接收日志
     */
    private static boolean openFileforLog(){
		boolean ret;
    	File logFile=new File(filePathName);
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(logFile, true);	//true: use append mode，追加模式
//			firstLogFile = false;
			writer = new PrintWriter(fileOutputStream);
			writeLogFileStart();
			startFlushTimer();
			ret = true;
		} catch (FileNotFoundException e) {
			Log.e(TAG, "Failed to create the log file (no stream)");
			ret = false;
		}
		return ret;
	}

    /**
     * 如果日志文件不存在则创建。
     * 如果创建失败，返回 false
     */
	private	static boolean makeFileExist()
		{
		boolean ret=true;
		File logFile=new File(filePathName);
		if (!logFile.exists())
			{
			try {
				makeDirExist(logFile.getParent());
				if(!logFile.createNewFile())
					{
					Log.e(TAG, "Unable to create new log file");
					ret = false;
					}
				} catch (IOException e) {	//如没有SD卡读写权限，则出此异常
					e.printStackTrace();
				}
			}
		return ret;
		}

/**
 * 检查日志文件大小，如果超过 MAX_LOGFILE_SIZE，则丢弃旧的，以防运行速度变慢，及占用无谓存储
 * 推荐在每次打开旧文件时运行，
 */
	private	static void cutOverloadSize()
		{
		File logFile=new File(filePathName);
		if (logFile != null){
			long length=logFile.length();
			if(length>MAX_LOGFILE_SIZE)	{
				try {
					byte buffer[]=new byte[MAX_LOGFILE_SIZE];
					FileInputStream in=new FileInputStream(logFile);

//					in.read(buffer, (int)length-MAX_LOGFILE_SIZE, MAX_LOGFILE_SIZE); 此写法运行时会崩溃，改为下2行:
					in.skip(length-MAX_LOGFILE_SIZE);
					in.read(buffer, 0, MAX_LOGFILE_SIZE);
					in.close();

					FileOutputStream out=new FileOutputStream(logFile);
					out.write(buffer);
					out.close();
				}catch (FileNotFoundException e) {
				}catch (IOException e) {
				} 
			}
		}
	}

	/**
	 * 为每一轮运行程序的日志设置相应的分界区。其中包含日期、时间
	 */
	private	static void writeLogFileStart()
		{
		Time time=new Time(); time.setToNow();
		strToday = time.format(strTodayFormat);
		String logCatText =	nl + nl +	"========================================" + nl +
						time.format(	"| %Y-%m-%d %H:%M:%S, SYCLog start\t|") + nl +
										"========================================";
		writeLogFile(logCatText);
		logLogCat(logCatText);
		}

	/**
	 * 启动 flush 文件定时器。启用定时机制刷新文件，以免频繁刷新文件导致运行速度变慢且使得  SD 卡折寿
	 */
	private static void	startFlushTimer()
		{
		flushFileTimer = new Timer();
		Log.d(TAG, "L3Log file flush timer started...");
		flushFileTimer.schedule(new TimerTask()
			{
			@Override
			public void run()
				{
				flushLogFile();
				}
			}, flushInterval, flushInterval);
		}

/**格式化日志内容为：
 * 	"mm-dd hh:mm:ss.microsecond, type:nnnnnnnn, log内容字符串"
 */
	private	static String formatLog(Type logType, String logText)
		{
		String logTextAll;

		logTextAll=getTimeString();
		
		logTextAll += ", type: " +	logType.name() + "(0x" + Long.toHexString(1<<logType.ordinal()) + "), ";

		logTextAll += logText;

		return logTextAll;
		}

	/**
	 * 格式化时间，得到 “时:分:秒.3位毫秒” 字样。
	 * 	注：日期信息在本次运行期间所写内容的头部，由 writeLogFileStart() 记录
	 */
	private static String getTimeString()
		{
		String timeString;
		Time time=new Time(); time.setToNow();
		timeString = time.format("%H:%M:%S.");	//"%Y-%m-%d %H:%M:%S.%s" -> 2012-12-13 08:40:10.1355532010
		String second=time.format("%s");
		timeString +=second.substring(0,3);	//注：在Android 下，time.format("%s") 看起来得到高精度的10位 135xxxxxxx ，但实际上前三位总是 135，所以实际上只能准确到秒。暂保留
		return timeString;
		}

	/**
	 * 关闭日志。
	 * 退出 APP 时调用一次，使得内存及时回收。（否则在下次运行APP时可能触发 Android 对本 APP GC 耗时很长甚至无法完成的 bug）
	 */
	public static void ExitLogger(){
		if (writer!=null) {
			writer.flush();
			writer.close();
		}
		if (flushFileTimer!=null) {
			flushFileTimer.cancel();
		}
	}


	/**
	 * 确保目录存在。
	 * 如果目录不存在且创建失败，返回 false
	 */
	private static boolean makeDirExist(String strDir) {
		boolean ret = false;
		File dir;
		if(strDir != null && (dir = new File(strDir)) != null) {
			if(dir.exists()) {
				ret = true;
			}else {	//目录不存在，则需先创建，否则创建文件会失败
				if(dir.mkdirs()) {
					ret = true;
				} else {
					Log.e(TAG, "mkdirs failed on directory: " + strDir);
				}
			}
		}
		return ret;
	}
}
