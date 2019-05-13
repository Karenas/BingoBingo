#include <stdio.h>
#include <string.h>
#include <dirent.h>
#include <unistd.h>
#include <fcntl.h>
#include "transmission.h"
#define  BUFFER_LENGTH 2048
#define  XML_LINE_LENGTH 1024
const char *getHomeDir(void);

#define dbgmsg(...) \
  do \
    { \
      if (tr_logGetDeepEnabled ()) \
        tr_logAddDeep (__FILE__, __LINE__, NULL, __VA_ARGS__); \
    } \
  while (0)

char *RadixChart1="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
char *RadixChart2="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

/*#########################################################
 * 实现setmntent()和endmntent()函数
 *#######################################################*/
#include "lalala.h"
FILE *setmntent(const char *filename, const char *type) {
    return fopen(filename, type);
}

int endmntent(FILE *filep) {
    fclose(filep);
    return 1;
}



/*##########################################################
* Digital2C的反向，把一个编码字符转换为进制数值(十进制表示)
* 调用方式：	C2Digital(param1 param2)
* 输入参数：	param1为一个RadixChart码表中的一个字符
*			param2为进制类型，如16表示16进制，64表示64进制
* 输出参数：编码字符转换成十进制后的值
##########################################################*/
int C2Digit(char RadixChar, unsigned int radix)
{
	int i;
	char *RadixChart;

	if (radix <= 36)
		RadixChart = RadixChart1;
	else
		RadixChart = RadixChart2;

	for (i = 0; i < radix; i ++)
		if (RadixChar == RadixChart[i])
			return i;
}

/*##############################################################
* 把字符串表示的其他进制格式转换成十进制格式数值（无符号型），例如 Radix2Dec B7 64 得到 123
* 调用方式：	Radix2Dec(param1 params)
* 输入参数：	param1为字符串表示的其他进制格式
*			param2为进制类型，如16表示16进制，64表示64进制
* 输出参数： 十进制数值(无符号型)
##############################################################*/
long long Radix2Dec(char const *RadixStr, unsigned int Radix)
{
	int	RadixStrLen, i, j, isNegative = 0;
	char *TmpStr;
	long long DecValue = 0, TempValue;

	if (RadixStr[0] == '-') {	/* 字符串是负数 */
		isNegative = 1;
		RadixStr ++;	/* 指向实际的数值字符串开始 */
	}
	RadixStrLen = strlen(RadixStr);


	if (RadixStrLen == 1)
		return C2Digit(RadixStr[0], 64);



	for (i = 0; i < RadixStrLen; i ++) {
		TempValue = 1;
		for (j = 1; j < RadixStrLen - i; j++)
			TempValue *= Radix;
		DecValue += (long long) C2Digit(RadixStr[i], Radix) * TempValue;
	}

	if (isNegative)
		return 0 - DecValue;
	else
		return DecValue;

}

int GetXmlLine(FILE *fp, char *buff, char *XmlLine, int *Position, int BuffLength)
/***********************************************
* 从buff的Position位置开始获取一行xml数据
* 若读到BuffLength后xml数据仍然没有结束，则从文件fp中读取新的buff数据
* 若读取的xmlLine长度达到XML_LINE_LENGTH，则只读取这些个字符。（必须确保能包含enkey部分）
* 读到的xml数据存在XmlLine中
* 返回值： 0表示文件已经读完，其它值表示本次读取的buff长度
************************************************/
{
    int i = 0;
	char DisplayStr[10];

    if (*Position == BuffLength) {	//buff数据已经处理完，从文件中读出下一部分
		BuffLength = fread(buff, sizeof(char), BuffLength, fp);
		*Position = 0;
    }
    while (1) {
		if (*Position == BuffLength) {	//buff数据已经处理完，从文件中读出下一部分
	    	BuffLength = fread(buff, sizeof(char), BuffLength, fp);
	    	if (BuffLength == 0) return 0;	//文件已经读完
	    	*Position = 0;
		}
		if (buff[*Position] == '<') break;	//找到xml开始的 "<" 字符
		(*Position) ++;
    }

	i = 0;
    while (1) {
		if (( i == XML_LINE_LENGTH ) || ( XmlLine[i-1] == '>' )) {
			XmlLine[i-1] = '\0';
			return BuffLength;      //已经读完一行xml数据或达到xml在本程序中的最大长度，返回
		}

		if (*Position == BuffLength) {		//buff数据已经处理完，从文件中读出下一部分
	    	BuffLength = fread(buff, sizeof(char), BuffLength, fp);
	    	if (BuffLength == 0) return 0;  //文件已经读完
            	*Position = 0;
        }	

		XmlLine[i] = buff[*Position];
		i ++;
		(*Position) ++;	
    }
}

/************************************************
* This function is to get the Enkey from ini file
* Input		char * //torrent name
* Output	int Enkey (0 if not found)
************************************************/
int lalala_getEnkey(char *tr_name) {
    FILE    *fp;
    int     i = 0, j = 0, buffLength, Position;
    char    buff[BUFFER_LENGTH], xmlstr[XML_LINE_LENGTH], enkeystr[30];		//要定义xmlstr长度足够包含到sk的值
    char    IdValue[20], IdInXml[20];
    char    *substr;

    for (i = 0; i < strlen(tr_name); i ++) {
		if (tr_name[i] == '_')		//获取PID赋给IdValue，若tr_name中没有EID和MFN部分（即种子是一个目录），则Id=tr_name
			break;	
		IdValue[i] = tr_name[i];
    }
    IdValue[i] = '\0';

    strcpy(xmlstr, getHomeDir());	//Get Lalala Home Dir
    strcat(xmlstr, "/");
    strcat(xmlstr, IdValue);
    strcat(xmlstr, ".lll");

    fp = fopen (xmlstr, "r");
    if (fp == NULL) {
		//pid.lll 文件不存在，是一个计次产品，要从SFile.xml中根据PID获取enkey
		strcpy(xmlstr, getHomeDir());
		strcat(xmlstr, "/SFile.xml");
		fp = fopen (xmlstr, "r");
		if (fp == NULL)
	    	return 0;
    } else {
			//存在 pid.lll 文件，从pid.lll文件中根据EID获取enkey
		i = 0;
		j = 0;
		while (1) {	//从 tr_name中移到EID部分
			i ++;
	   		if (tr_name[i] == '_')
				break;
		}
		i ++;	//移到EID部分
		while (i < strlen(tr_name)) {		//获取EID赋给IdValue
	   		if (tr_name[i] == '_')
				break;
			IdValue[j] = tr_name[i];
	   		j ++;
			i ++;
		}
		IdValue[j] = '\0';
	}

	//开始在SFile.xml或lll文件中寻找id域的值等于IdValue的行，并取其sk域的值
	buffLength = Position = BUFFER_LENGTH;		//初始值两个变量相同，可以确保进入子程序后直接从文件读取buff
    buffLength = GetXmlLine(fp, buff, xmlstr, &Position, buffLength);	//跳过xml文件的头两行
    buffLength = GetXmlLine(fp, buff, xmlstr, &Position, buffLength);
    while (1) {
		buffLength = GetXmlLine(fp, buff, xmlstr, &Position, buffLength);
//dbgmsg("####################################### xml line is: %s", xmlstr);
		if ( buffLength == 0 ) return 0;		//文件已经处理完

		i = 0;
		while ((xmlstr[i] != ' ') && (xmlstr[i] != '\0')) i ++;		//移到第一项
		if (xmlstr[i] == '\0') continue;		//处理到行末尾，继续处理下一行
		
		i ++;
		if (strncmp(&xmlstr[i], "id=", 3))
			continue;		//第一项名称不是id=，继续处理下一行

		i += 4;				//将指针移到id的值处
		j = 0;
		while ((xmlstr[i] != '"')  && (xmlstr[i] != '\0')) {	//Get IdValue
	    	IdInXml[j] = xmlstr[i];
	    	j ++;
	    	i ++;
		}
		IdInXml[j] = '\0';

		if (strcmp(IdValue, IdInXml)) continue;				//Id值不相同，继续处理下一行

		substr = strstr(&xmlstr[i], " sk=");
		if (substr == NULL)	return 0;		//未找到" sk="字符串，返回0
		i = 5;
		j = 0;

		//Get Enkey string
		while ((substr[i] != '"') && (substr[i] != '\0')) {
	    	enkeystr[j] = substr[i];
	    	j ++;
	    	i ++;
		}
		enkeystr[j] = '\0';
//dbgmsg("################################ Enkey is: %s", enkeystr);
		fclose(fp);
		return (int) (Radix2Dec(enkeystr, 64));
    }

    fclose(fp);
    return 0;	//Product id or enkey not found
}

/**********************************
* This function is to check the pid of lalala-daemon
* Note: can not use fopen in this function
*       May cause core dump
* Input:	char *
* Output:	pid or "null"
**********************************/
const char daemonPath[] = "/proc/";
const char daemonName[] = "lalala-daemon";
const int daemonNameLength = 13; 	//strlen(daemonName)

//void lalala_getDaemonPID(char * DaemonPID)
pid_t lalala_getDaemonPID(void)
{
    DIR			*pDir ;
    struct dirent	*ent  ;
    char                filename[512], procname[512], localPID[20];
    long		procNameLength;
    int			fp, readLength;
    pid_t		Pid = 0;

    pDir=opendir(daemonPath);
    sprintf(localPID, "%d", getpid());
    while((ent=readdir(pDir))!=NULL) {
        if(ent->d_type & DT_DIR) {
	    strcpy(filename, "/proc/");
	    strcat(filename, ent->d_name);
	    strcat(filename, "/cmdline");
	    fp = open(filename, O_RDONLY);
	    if (fp < 0)
		continue;	//Open failed
	    readLength = read(fp, procname, sizeof(procname));
	    close(fp);		//We only read first 512 characters
	    if (readLength <= 0)
		continue;	//EOF or read failed

	    procNameLength = strlen(procname);
	    if (procNameLength < daemonNameLength)
		continue;		//Not lalala Daemon program
	    if (!strcmp(localPID, ent->d_name))
		continue;		//PID for this process
	    if (!strcmp(procname + (procNameLength - daemonNameLength), daemonName)) {
		//strcpy(DaemonPID, ent->d_name);
		Pid = (pid_t) Radix2Dec(ent->d_name, 10);
		//closedir(pDir);
		//return Pid;
		break;
	    }
        }
    }
    closedir(pDir);
//    strcpy(DaemonPID, "null");
    return Pid;
}

