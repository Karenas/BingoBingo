package com.gmself.studio.mg.basemodule.utils.dirUtil;

import android.content.Context;
import android.os.Environment;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.gmself.studio.mg.basemodule.log_tool.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by guomeng on 2018/7/4.
 */

public class DirsTools {
//不依赖于 Context 的公共路径：
    /** 大容量存储卡的 mount 路径。有可能是内置盘模拟大容量卡，也有可能是外置SD卡。
     * 考虑到 Google及手机厂商对 External 有混淆的含义，因此选用 Storage 词。	 */
    private static String StorageRoot = null;
    /** 最可用存储卡的 mount 路径。TF卡优先，内置盘次之，/data/data 再次之 */
    private static String MaxsizeRoot = null;

    /** （可插拔式）外置 SD 卡的 mount 路径。规则：如果 SdCardRoot 存在，则 StorageRoot 必然存在	 */
//	private static String SdCardRoot = null;

    //依赖于 Context 的 package_name 私有的路径：
    /** /data 下的程序目录，应为 /data/data/<package_name>/files ，在安装APP时即已创建好 */
    private static String HomeProgramData = null;

    /** 大容量存储卡下的程序专用空间目录，应为 (StorageRoot)/Android/data/(package_name)/files */
    private static String HomeStorage = null;
    /** 最可用存储卡下的程序专用空间目录，应为 (MaxsizeRoot)/Android/data/(package_name)/files */
    private static String HomeMaxsize = null;

    private static String ImageCachePath = null;
    private static String FILE_CACHE_PATH = null;
//	private static String LocalIP = "http://127.0.0.1:8080";

    private static boolean isInitialized = false;


	private static boolean RUN_RD = true;
	private static String rdPath = "rd";
    private static String tempImagePath = "tempPic";
    private static String IMAGE_CACHE_DIR_NAME = "cacheImage";
    private static String RES_FILE_CACHE_DIR_NAME = "resFile";
    private static String ANALYSIS_FILE_CACHE_DIR_NAME = "analysisFile";
    private static String KPathSeparated = "/";
//	private static String NetRoot = null;
//	private static String ServletURL = null;

    public static boolean init(Context ctx, boolean isRunDebug) {
//		isRunRd();
        RUN_RD = isRunDebug;
        if( isInitialized )
            return true;

        return initMain(ctx);

    }

    private static boolean initMain(Context ctx){
        isInitialized = true;

        //程序目录，既然已经安装运行，则必然存在
        HomeProgramData = ctx.getFilesDir().getPath();	//Got /data/data/<package name>/files

        ImageCachePath = getDiskCachePath(ctx);
        FILE_CACHE_PATH = getDiskCachePath(ctx);

        if (getHomeStorage(ctx)) {
            //存在大容量存储，则继续分析有否（可插拔式）外置 SD 卡
            if (! isSdAsStorage()
                    && !getSdFromDirs(ctx)
                    && !getSdFromSecondaryStorage()
                    && !getFirstExternalSDCardDirectory()
                    && !getSdFromMount() ) {
                Logger.log(Logger.Type.FILE, new String[]{"Oh..... no Removable SD card in this device, so try the internal disk " + StorageRoot});
                isAnValidSdRoot(StorageRoot );
            }
        }
        if ( HomeMaxsize == null ) {
            Logger.log(Logger.Type.FILE, new String[]{"Oh..... no Storage in this device, turn to " + HomeProgramData});
            HomeMaxsize = HomeProgramData;	//假定手机的 /data 分区空间也有一定的使用价值
        }
        AnalyzeMaxsizeRoot();
        return true;
    }

    /**
     * 获取cache路径
     *
     * @param context
     * @return
     */
    public static String getDiskCachePath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }

    /**
     * 根据 HomeMaxsize 计算 MaxsizeRoot
     */
    private static boolean AnalyzeMaxsizeRoot()	{
        if(HomeMaxsize == null )
            return false;

        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                String columns[] = line.split(" ");
                if (columns.length > 1
                        && HomeMaxsize.startsWith(columns[1])
                        && (MaxsizeRoot == null || columns[1].length() > MaxsizeRoot.length() )
                        ) {
                    MaxsizeRoot = columns[1];
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        if (MaxsizeRoot==null || MaxsizeRoot.length()<=1) {
            //是 mount 表中的 / ，故无效。当手机的存储卡  mount 路径和 API 给出的路径不一致的时候会发生。
            //此时须  GetStorageRoot() 的调用者自己处理 null 崩溃风险或者逻辑错误
            Logger.log(Logger.Type.FILE, new String[]{"Warning: can not locate the storage root directory from "+HomeMaxsize});
            MaxsizeRoot = null;
        }
        return MaxsizeRoot != null;
    }


/*
		Environment.getDataDirectory().getPath();						// /data the user data directory.
		Environment.getDownloadCacheDirectory().getPath();				// /cache the download/cache content directory.
		Environment.getExternalStorageDirectory().getPath();			// /storage/sdcard0 the primary external storage directory.
		Environment.getExternalStoragePublicDirectory(null).getPath();	// /storage/sdcard0/Pictures
		Environment.getExternalStorageState();							//"mounted"  the current state of the primary "external" storage device.
		Environment.getRootDirectory().getPath();						// /system, the Android root directory.
		Environment.getStorageState(null);		//Returns the current state of the storage device that provides the given path.
		//Returns one of MEDIA_UNKNOWN, MEDIA_REMOVED, MEDIA_UNMOUNTED, MEDIA_CHECKING, MEDIA_NOFS, MEDIA_MOUNTED, MEDIA_MOUNTED_READ_ONLY, MEDIA_SHARED, MEDIA_BAD_REMOVAL, or MEDIA_UNMOUNTABLE
此外，http://developer.android.com/reference/android/os/Environment.html 中 getExternalStorageDirectory() 中记载了：
	 Note: don't be confused by the word "external" here. This directory can better be thought as media/shared storage.
 It is a filesystem that can hold a relatively large amount of data and that is shared across all applications.
 Traditionally this is an SD card, but it may also be implemented as built-in storage in a device
 即所谓 "external"实际指共享的大容量媒体存储器，既可能是可插拔的 SD（TF） 卡，也可能是内置盘
*/
    /**
     * 利用 Environment.getExternalStorageDirectory()获取大容量存储卡的路径，保存在 StorageRoot 中；
     * 利用 Context.getExternalFilesDir() 获取本app在大容量存储卡中的 HOME 路径，保存在 HomeStorage 中。
     * 如果没有本app可用的大容量存储卡设备，则返回 false
     */
    private static boolean getHomeStorage(Context ctx) {
        final String state = Environment.getExternalStorageState();
        Logger.log(Logger.Type.FILE, new String[]{"Environment.getExternalStorageState()="+state});
        if ( Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state) ) {  // we can read the External Storage...
            StorageRoot = Environment.getExternalStorageDirectory().getPath();
            File f = ctx.getExternalFilesDir(null);	//Got /storage/sdcard0/Android/data/<package name>/files 。需要 SD 访问权限（但是从Android4.4/API21开始私有路径可以不需权限）
            //20150413注：首次调用之后，内置盘、外置盘上均会创建 Android/data/<package name>/files 路径，同时只返回其中之一到 f
            assert(f != null);		// ExternalStorage 存在， 且有 SD 访问权限
            HomeStorage = f.getPath();
            Logger.log(Logger.Type.FILE, new String[]{"StorageRoot="+StorageRoot+", HomeStorage="+HomeStorage});
        }
        return HomeStorage != null;
    }

    /** 是否外置SD卡即为大容量存储卡？
     * API level 9 */
    private static boolean isSdAsStorage() {
        boolean bRemovable = Environment.isExternalStorageRemovable();	//“外存”卡是否是可插拔的？ need API level 9
        //boolean bEmulated = false; //Environment.isExternalStorageEmulated();	//“外存”卡是否是内置盘模拟的？为android 3.0 API level 11 以上版本可用
/* 红米手机（2014.05时的最新固件），
* 		在有插入SD卡时，虽 Environment.getExternalStorageDirectory() 返回SD卡路径，但是却报告为不可插拔的；
* 		在未插入SD卡时，虽 Environment.getExternalStorageDirectory() 返回内置盘路径，但是却报告为不是模拟外置盘
* 		上述2个情况与 Android 官方的 API 描述相左。不过没影响优先使用SD卡的原则
* 	其它手机待观测记录
*/
        if( bRemovable ) {	// SdCard是可插拔的
            if(isDirWriteable(HomeStorage))	//且可写，则主用存储即为 SD 卡，则忽略内置盘。红米新ROM应属于此种情况（其内置存储盘只有700M，故以 TF 卡为大容量存储区）
                HomeMaxsize = HomeStorage;
        }
        return HomeMaxsize != null;
    }

    /**
     *  利用 android.support.v4.content.ContextCompat 扩展包分析外置 SD 卡;
     *
     *	较广泛地适用于 Android 2-4
     */
    private static boolean getSdFromDirs(Context ctx) {
        File[] dirs= ContextCompat.getExternalFilesDirs(ctx, null);	//效果等于 Android 4.4/API21 的 Context.getExternalFilesDirs()
        int dirNum = dirs.length;
        if (dirNum>0 && dirs[dirNum-1]==null) dirNum --;	//getExternalFilesDirs()返回数组的最后一项有可能是 null，因此先扣减
        if( dirNum > 0 ) {
            if( dirNum == 1 ) {	//只给出一个大容量分区，暂不使用
/*Samsung I9300 4.0 虽然插了 tf 卡但只给出内置盘，故本方法无效
 * 				if(isDirWriteable(HomeStorage)) {
					HomeMaxsize = HomeStorage;
					Logger.log(Logger.Type.FILE, "HomeMaxsize set to "+HomeMaxsize+"since there is only this storage device.");
				}*/
                Logger.log(Logger.Type.FILE, new String[]{"ContextCompat.getExternalFilesDirs() got only 1 storage device."});
            } else {
                for(int i=1;i<dirNum;i++) {	//测试各个外置设备
                    String dir = dirs[i].getAbsolutePath();
                    if(isDirWriteable(dir)) {	//只要可写，即采用。通常dirs[1]即为可插拔SD卡上的。 TODO::须考虑 USB 等设备是否一定排在 dirs[2]以上从而不干扰本代码的强壮性
                        HomeMaxsize = dir;
                        String strDirs = "";
                        for(int j=0;j<dirNum;j++) {
                            if(strDirs.length()>0) strDirs += ", ";
                            strDirs += dirs[j].getAbsolutePath();
                        }
                        i++;	//0->1, etc
                        Logger.log(Logger.Type.FILE, new String[]{"HomeMaxsize set to the No."+i+" of "+ dirNum + ": " + strDirs});
                        break;
                    }
                }
            }
        }
        return HomeMaxsize != null;
    }

    /**
     * 通过 System.getenv("SECONDARY_STORAGE"); 获得可插拔存储卡。
     *
     * 兼容性 强壮性待评估！
     * @return
     */
    private static boolean getSdFromSecondaryStorage() {
        String primary_sd = System.getenv("EXTERNAL_STORAGE");
        String secondary_sd = System.getenv("SECONDARY_STORAGE");	//Huawei 3xPro 4.4 下得到： /storage/sdcard1
		/* 20150331 ：在 3xPro 4.4 下没插入tf卡时仍得到和插卡时一样的 /storage/sdcard1，因并未 mount，而须弃用
		 * Samsung I9300 4.0 上则得到： /mnt/extSdCard:/mnt/UsbDriveA:/mnt/UsbDriveB:/mnt/UsbDriveC:/mnt/UsbDriveD:/mnt/UsbDriveE:/mnt/UsbDriveF,
		 * 	需要以 : 分拆到数组
		 * Huawei X1 4.4 下得到 /storage/sdcard1， 4.4 下此路径不可读
 */
        if (!TextUtils.isEmpty(secondary_sd)) {
            String[] sds = secondary_sd.split(":");
            for (String sd : sds) {
                File f = new File(sd);
                final boolean bDir = f.isDirectory();
//	            final boolean bCanRead = f.canRead();
//	            final int nFiles = f.listFiles() == null ? 0 : f.listFiles().length;
                if ( bDir /*&& bCanRead && nFiles > 0 */ // it is a real directory (not a USB drive)...
                        && f.getAbsolutePath().compareTo(primary_sd) != 0
                        && isAnValidSdRoot(f.getAbsolutePath())
                        ) {
                    Logger.log(Logger.Type.FILE, new String[]{"SECONDARY_STORAGE("+f+") is an removable SD"});
                    break;
                }
            }
        }

        return HomeMaxsize != null;
    }

    /**
     * to find the first listed external SD card
     * See: http://stackoverflow.com/questions/11281010/how-can-i-get-external-sd-card-path-for-android-4-0
     * 依赖于：内置存储盘和可插拔sd卡的mount路径位于同一父目录下，比如 /mnt/sdcard0 和 /mnt/sdcard1
     *
     * 可能仅适用于 Android 2.x ，因 4.4 下一般会拒绝访问 app 私有路径之外的目录
     */
    private static boolean getFirstExternalSDCardDirectory() {
        final String state = Environment.getExternalStorageState();

        if ( Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state) ) {  // we can read the External Storage...
            //Retrieve the primary External Storage:
            final File externalStorageRoot = Environment.getExternalStorageDirectory().getParentFile();// 4.4 下是 /storage/emulated/0 ，因此不适用
            final File[] files;
            if(externalStorageRoot != null && (files=externalStorageRoot.listFiles())!=null) {
				/* 是否也返回目录？ Huawei 3xPro 4.4 下跟踪得到 null ，其下有 0 和 legacy 两个子目录。很可能是 4.4 权限拒绝
				Samsung I9300 4.0 上的  ls - /mnt 则得到：
d---------    2 system   system           0 Apr  7 09:00 UsbDriveA
d---------    2 system   system           0 Apr  7 09:00 UsbDriveB
d---------    2 system   system           0 Apr  7 09:00 UsbDriveC
d---------    2 system   system           0 Apr  7 09:00 UsbDriveD
d---------    2 system   system           0 Apr  7 09:00 UsbDriveE
d---------    2 system   system           0 Apr  7 09:00 UsbDriveF
drwxr-xr-x    2 root     system          40 Apr  7 09:00 asec
drwxrwxr-x   17 system   1023         32768 Apr  9 13:11 extSdCard
drwxr-xr-x    2 root     system          40 Apr  7 09:00 obb
drwxrwxr-x  141 root     sdcard_r      8192 Apr  9 10:05 sdcard
drwx------    4 root     root             0 Apr  7 09:00 secure
*/
                for ( final File file : files ) {
                    if ( file.isDirectory()		// it is a real directory (not a USB drive)...
                            && file.compareTo(Environment.getExternalStorageDirectory()) != 0
                            && isAnValidSdRoot(file.getAbsolutePath())) {
                        Logger.log(Logger.Type.FILE, new String[]{"Yes, I think this is a removable External Storage: " + file + "\n"});
                        break;
                    }
                }
            }
            else {
                Logger.log(Logger.Type.FILE, new String[]{"Can not locate External Storage by: " + externalStorageRoot});
            }
        }

        return HomeMaxsize != null;
    }


    /** 利用系统 mount 分析外置 SD 卡
     //20150301 增加 getSecondaryStorageDirectory() 和 getFirstExternalSDCardDirectory() 方法以后， 以下情况只对 4.4 之前的插入tf卡但仍未能找到时适用
     * 只简单调试和用过。疑虑点包括：
     *	各厂商存储方案及其固件的API实现方案是否统一符合 android.com 的规范？
     *	mount 命令结果的格式统一性，比如 busybox mount 的路径在第3列而不是第2列
     *
     *	主要适用于 Android 2.x
     */
    private static boolean getSdFromMount()	{
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
//判断规则：fat格式且不含 secure 和 asec，且不是 Environment.getExternalStorageDirectory() 的第一个结果，即是 SD 卡。
// 需要适配不同机型修改。或者利用 Android 4.4 的 Context.getExternalFilesDirs() 方法
//huawei 4.4 的tf卡mount路径有2项，其中 /mnt/media_rw/sdcard1 不可写；/storage/sdcard1 可写：
//	/dev/block/vold/179:100 on /mnt/media_rw/sdcard1 type vfat (rw,dirsync,nosuid,nodev,noexec,relatime,uid=1023,gid=1023,fmask=0007,dmask=0007,allow_utime=0020,codepage=cp437,iocharset=iso8859-1,shortname=mixed,utf8,errors=remount-ro)
//	/dev/fuse on /storage/sdcard1 type fuse (rw,nosuid,nodev,relatime,user_id=1023,group_id=1023,default_permissions,allow_other)
                if ( !line.contains("secure")
                        && !line.contains("asec")
                        && line.contains("fat")
                        ) {
                    Logger.log(Logger.Type.FILE, new String[]{"Testing a mount line :" + line});
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1
                            && ! StorageRoot.equalsIgnoreCase(columns[1])
                            && isAnValidSdRoot(columns[1])
                            ) {
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return HomeMaxsize != null;
    }

    /**
     * 测试一个 SD 路径是否为可用的。如果可用，将生成 HomeMaxsize 变量  */
    private static boolean isAnValidSdRoot(String strSdRoot) {
        String strHomeSd = HomeStorage.replace(StorageRoot, strSdRoot);
        if( isDirWriteable(strHomeSd))
            HomeMaxsize = strHomeSd;
        return HomeMaxsize != null;
    }

    /**
     * 判断目录是否可写  */
    public static boolean isDirWriteable(final String strPath) {
        boolean ret = false;
        File f = new File(strPath);
        if(f != null) {
            final boolean bDir = f.isDirectory();
            final boolean bExists = f.exists();
            final boolean bCanWrite = f.canWrite();
            if( bDir && bExists && bCanWrite )
                ret = true;
        }
        return ret;
    }

    /**
     * 获取程序数据文件目录，在安装APP时即已创建好，即：  /data/data/<package name>/files
     * @return
     */
    public static String GetHomeProgramData() {
        if (RUN_RD){
            return HomeProgramData+ KPathSeparated +rdPath;
        }
        return HomeProgramData;
    }

    public static String GetLoggerPath(){
        if (RUN_RD){
            return HomeStorage + KPathSeparated + rdPath + KPathSeparated + "kq_logger.txt";
        }
        return HomeProgramData + KPathSeparated + "kq_logger.txt";
    }

    /**
     * db文件路径，不含文件名
     * */
    public static String GetDBPath(){
        if (RUN_RD){
            return HomeStorage + KPathSeparated + rdPath + KPathSeparated;
        }
        return HomeProgramData + KPathSeparated;
    }

    public static String GetImageCachePath(){
        return ImageCachePath+KPathSeparated + IMAGE_CACHE_DIR_NAME;
    }

    public static String GetFileCachePath(){
        return FILE_CACHE_PATH+KPathSeparated + RES_FILE_CACHE_DIR_NAME;
    }

    public static String GetAnalysisFileCachePath(){
        return FILE_CACHE_PATH+KPathSeparated + ANALYSIS_FILE_CACHE_DIR_NAME;
    }

    public static String GetCacheImage(String fullName){
        return GetImageCachePath() + KPathSeparated + fullName;
    }

    /** 检查是否已初始化，未初始化时将在 Log 中提示*/
    private static void checkInit (final String strCaller) {
        if(!isInitialized)
            Logger.log(Logger.Type.FILE, new String[]{"Dirs.init(Context) must be called before use " + strCaller + "()."});
    }

    /**
     * 获取大容量存储设备 mount 目录。多为 ：/storage/sdcard0 。如果设备即没有内置盘也没有外置盘，则返回 null
     * @return
     */
    public static String GetStorageRoot() {
        checkInit("GetStorageRoot");
        return StorageRoot;
    }

    /**
     * 获取分析出的最可用目录所在的分区 Root
     * @return
     *	Warning : 当 mount 表中的存储卡  mount 路径和 API 给出的路径不一致的时候会返回 null ，此时须调用者自己处理 null 崩溃风险或者逻辑错误
     */
    public static String GetMaxsizeRoot() {
        checkInit("GetMaxsizeRoot");
        if (RUN_RD){
            return MaxsizeRoot + KPathSeparated + rdPath;
        }
        return MaxsizeRoot;
    }


    /**
     * 获取大硬盘上的app HOME 目录。多为 ：/storage/sdcard0/Android/data/<package name>/files
     * 注：如果程序没有SD卡访问权限，则会返回 null
     * @return
     */
    public static String GetHomeStorage() {
        checkInit("GetStorageRoot");
        if (RUN_RD){
            return HomeStorage + KPathSeparated + rdPath;
        }
        return HomeStorage;
    }

    /**
     * 返回程序的可用目录中容量最大的那个（依次是： 外置SD卡、内置存储盘、/data 。假定SD卡空间大于内置盘）
     * @return
     */
    public static String GetHomeMaxsize() {
        checkInit("GetHomeMaxsize");
        if (RUN_RD){
            return HomeMaxsize + KPathSeparated + rdPath;
        }
        return HomeMaxsize;
    }




}
