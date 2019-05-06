
#include <jni.h>
//#include <jni_handler.h>
#include "assert.h"
#include "deamon.h" //TODO 20161107
#include "quark.h"
//#include "transmission.h" //addTr



static tr_session * mySession = NULL;
static JavaVM * jvm = NULL;
static jobject callbackObject = NULL;
static jobject infoObj = NULL;

//��������������Կ��Ǵ�java�����native��transmain����ʱ���룬����������
#define JNIREG_CALLBACK_CLASS "transmission/deamon/TransHandler"
#define JNIREG_TASKINFO_CLASS "data/objectType/DownloadTaskFile"

char * javaStringTochar(JNIEnv *env, jstring str);

void makeSession()
{
	mySession = get_tr_session();
}



JNIEXPORT jstring JNICALL Java_transmission_deamon_TransHandler_transmain(JNIEnv* jnv, jobject caller, jstring homeDir)
{	
	char * customizeHomeDir = javaStringTochar(jnv, homeDir);
	setHomeDir(customizeHomeDir);
	main(1, 0);		
}

void initClassHelper(JNIEnv *env_, const char *path, jobject *objptr) {
	jclass cls = (*env_)->FindClass(env_, path);
	if (!cls) {
		//LOGE("initClassHelper: failed to get %s class reference", path);
		return;
	}
	jmethodID constr = (*env_)->GetMethodID(env_, cls, "<init>", "()V");
	if (!constr) {
		//LOGE("initClassHelper: failed to get %s constructor", path);
		return;
	}
	jobject obj = (*env_)->NewObject(env_, cls, constr);
	if (!obj) {
		//LOGE("initClassHelper: failed to create a %s object", path);
		return;
	}
	(*objptr) = (*env_)->NewGlobalRef(env_, obj);
}


JNIEXPORT jint JNICALL 
JNI_OnLoad(JavaVM* vm, void* reserved)
{
	jvm = vm;

	JNIEnv* env;
	if ((*jvm)->GetEnv(jvm, (void**)&env, JNI_VERSION_1_6) != JNI_OK) {
		return -1;
	}
	initClassHelper(env, JNIREG_CALLBACK_CLASS, &callbackObject);
	initClassHelper(env, JNIREG_TASKINFO_CLASS, &infoObj);

	return JNI_VERSION_1_6;
}



//��ȡjava��String�����ַ�����C�е�char����������
char * javaStringTochar(JNIEnv *env, jstring str)
{
	char * str_c = NULL;
	if (str != NULL) {
		str_c = (*env)->GetStringUTFChars(env, str, NULL);
	}
	return str_c;
}

//�ⲿ��char*תString���ں�������»����롣
jstring  CharTojstring(JNIEnv*   env, char*   str)
{
	jsize   len = strlen(str);

	jclass   clsstring = (*env)->FindClass(env, "java/lang/String");
	jstring   strencode = (*env)->NewStringUTF(env, "GB2312");

	jmethodID   mid = (*env)->GetMethodID(env, clsstring, "<init>", "([BLjava/lang/String;)V");
	jbyteArray   barr = (*env)->NewByteArray(env, len);

	(*env)->SetByteArrayRegion(env, barr, 0, len, (jbyte*)str);
	return (jstring)(*env)->NewObject(env, clsstring, mid, barr, strencode);
}


/*
if (!err)
{
key = TR_KEY_torrent_added;
result = NULL;
}
else if (err == TR_PARSE_ERR)
{
key = 0;
result = "invalid or corrupt torrent file";
}
else if (err == TR_PARSE_DUPLICATE)
{
//tor = tr_torrentFindFromId(data->session, duplicate_id);
key = 0;		//TR_KEY_torrent_duplicate;
result = "duplicate torrent";
}
*/
int torrentNew(tr_ctor * ctor)
{
	int err;
	int duplicate_id;

	err = 0;
	duplicate_id = 0;

	tr_torrentNew(ctor, &err, &duplicate_id);
	tr_ctorFree(ctor);
	return err;
}

//��base64�����ʽ��������Ϣ�����н���󱣴浽ctor��
void ctorSetMetainfo(char * metainfo_base64, int * len, tr_ctor * ctor)
{
	char * metainfo = tr_base64_decode(metainfo_base64, -1, &len);
	tr_ctorSetMetainfo(ctor, (uint8_t*)metainfo, len);
	tr_free(metainfo);
}

//ͨ�������ļ��������һ������
JNIEXPORT void JNICALL Java_transmission_deamon_TransHandler_torrentAdd(JNIEnv *env, jobject j, jstring tr_content)
{
	if (mySession == NULL)
	{
		makeSession();
	}
	
	tr_ctor * ctor = NULL;
	const char * metainfo_base64 = NULL;
	int len = NULL;

	ctor = tr_ctorNew(mySession);
	metainfo_base64 = javaStringTochar(env, tr_content);

	ctorSetMetainfo(metainfo_base64, &len, ctor);//SetMetainfo
	torrentNew(ctor);

	l3_torrentStart();
	
}





//TODO 20161129 ͨ��url�������
JNIEXPORT void JNICALL Java_transmission_deamon_TransHandler_torrentAddByURL(JNIEnv *env, jobject j, jstring seed_url, jintArray fileID)
{
	if (mySession == NULL)
	{
		makeSession();
	}

	jint* fielId = (*env)->GetIntArrayElements(env, fileID, NULL);
	jint pid = NULL;
	jint eid = NULL;

	if (fielId)
	{
		pid = fielId[0];
		eid = fielId[1];
	}

	char * seedUrl = NULL;
	seedUrl = javaStringTochar(env, seed_url);

	l3_addTorrentBySeedURL(seedUrl, pid, eid);
}


//TODO 20161129 ɾ��һ������
JNIEXPORT void JNICALL Java_transmission_deamon_TransHandler_deleteAtask(JNIEnv *env, jobject j, jint tor_id)
{
	if (mySession == NULL)
	{
		makeSession();
	}
	tr_torrent * tor = NULL;
	//int tor_id = NULL;
	bool deleteFlag = true;

	tor = tr_torrentFindFromId(mySession, tor_id);

	tr_torrentRemove(tor, deleteFlag, NULL);

}

//TODO 20161129 ��ͣһ�����������
JNIEXPORT void JNICALL Java_transmission_deamon_TransHandler_pauseAtask(JNIEnv *env, jobject j, jint tor_id)
{
	if (mySession == NULL)
	{
		makeSession();
	}

	tr_torrent * tor = NULL;
	
	tor = tr_torrentFindFromId(mySession, tor_id);

	tr_torrentStop(tor);
}

//TODO 20161129 �ָ�һ�����������
JNIEXPORT void JNICALL Java_transmission_deamon_TransHandler_reStartAtask(JNIEnv *env, jobject j, jint tor_id)
{
	if (mySession == NULL)
	{
		makeSession();
	}

	tr_torrent * tor = NULL;

	tor = tr_torrentFindFromId(mySession, tor_id);

	tr_torrentStart(tor);

}

//TODO 20161129 ���������ٶ�
JNIEXPORT void JNICALL Java_transmission_deamon_TransHandler_sessionDownloadSpeedLimit(JNIEnv *env, jobject j, jint v_kb)
{
	if (mySession == NULL)
	{
		makeSession();
	}
	tr_sessionSetSpeedLimit_KBps(mySession, TR_DOWN, v_kb);
}

//���ڵ����������ٵĹ��ܲ���δ�ɹ���û��Ч��
JNIEXPORT void JNICALL Java_transmission_deamon_TransHandler_torrentDownloadSpeedLimit(JNIEnv *env, jobject j, jint tor_id, jint v_kb)
{
	if (mySession == NULL)
	{
		makeSession();
	}
	tr_torrent * tor;
	tor = tr_torrentFindFromId(mySession, tor_id);
	tr_torrentSetSpeedLimit_KBps(tor, TR_DOWN, v_kb);
}


JNIEXPORT void JNICALL Java_transmission_deamon_TransHandler_transClose()
{
	closeTransmission();
}





//--------------------------------native callback  java----��-------------------------------------

JNIEnv* env = NULL;
bool isAttach = false;



JNIEXPORT jint JNICALL
l3_getJniCurrentThreadEnv() 
{
	int r = 0;

	r = (*jvm)->GetEnv(jvm, (void**)&env, JNI_VERSION_1_6);
	if (r < 0)
	{
		r = (*jvm)->AttachCurrentThread(jvm, &env, NULL);
		if (r < 0)
		{
			return JNI_FALSE;
		}
		isAttach = true;
	}
	return JNI_OK;
}

JNIEXPORT jint JNICALL
l3_getJniClassFromObj(jclass * setme, jobject * obj)
{
	(*setme) = (*env)->GetObjectClass(env, (*obj));
	if (!(*setme)) return JNI_FALSE;
	return JNI_OK;
}



JNIEXPORT jint JNICALL
l3_newTaskInfoObj(
	jobject * setme,
	jclass * clz, 
	jmethodID * initmeht, 
	int id_,
	uint64_t totalsize_,
	uint64_t downloadedsize_,
	char * name_,
	int activity_,
	float downloadspeed_)
{
	jint id = id_;
	jlong totalsize = totalsize_;
	jlong downloadedsize = downloadedsize_;
	jstring name = CharTojstring(env, name_);
	jint activity = activity_;
	jfloat downloadspeed = downloadspeed_;


	(*setme) = (*env)->NewObject(env, (*clz), (*initmeht), id, totalsize, downloadedsize, name, activity, downloadspeed);
	if (!(*setme)) return JNI_FALSE;
	return JNI_OK;
}


JNIEXPORT jint JNICALL
l3_getJniMethID(jmethodID * setme, jclass * clz, const char * id, const char * sig)
{
	(*setme) = (*env)->GetMethodID(env, (*clz), id, sig);
	if (!(*setme)) return JNI_FALSE;
	return JNI_OK;
}


/*
	int l3tor_id = tor->uniqueId;
	uint64_t totalsize = tor->info.totalSize;
	uint64_t downloadedsize = tor->completion.sizeNow;
	char * name = tor->info.originalName;//ȡ��matainfo����ͬ������
	int ta =  tor->stats.activity;
	float downloadspeed = tor->stats.pieceDownloadSpeed_KBps;
*/
JNIEXPORT jint JNICALL 
torrentInfoToJava(
	int id, 
	uint64_t totalsize, 
	uint64_t downloadedsize,
	char * name,
	int activity,
	float downloadspeed
	) 
{

	if (l3_getJniCurrentThreadEnv()!=JNI_OK)
	{
		return JNI_FALSE;
	}

	jclass callbackClz = NULL;
	jclass infoClz = NULL;

	if (l3_getJniClassFromObj(&callbackClz, &callbackObject)!=JNI_OK)
	{
		return JNI_FALSE;
	}

	if (l3_getJniClassFromObj(&infoClz, &infoObj) != JNI_OK)
	{
		return JNI_FALSE;
	}

	jmethodID callbackMeth = NULL;
	jmethodID initInfoMeth = NULL;

	if (l3_getJniMethID(&callbackMeth, &callbackClz, "torrentInfo", "(Ldata/objectType/DownloadTaskFile;)V"))
	{
		return JNI_FALSE;
	}

	if (l3_getJniMethID(&initInfoMeth, &infoClz, "<init>", "(IJJLjava/lang/String;IF)V"))
	{
		return JNI_FALSE;
	}
	
	jobject taskinfoObj = NULL;

	if (l3_newTaskInfoObj(&taskinfoObj, &infoClz, &initInfoMeth, id, totalsize, downloadedsize, name, activity, downloadspeed) != JNI_OK)
	{
		return JNI_FALSE;
	}
	

	(*env)->CallVoidMethod(env, callbackObject, callbackMeth, taskinfoObj);

	if (isAttach)
	{
		(*jvm)->DetachCurrentThread(jvm);//Ӧ���߳̽���֮ǰ���á�
	}
	return JNI_OK;
}


JNIEXPORT jint JNICALL
addToMappingInJava(
	int id,
	jint pid,
	jint eid
)
{

	if (l3_getJniCurrentThreadEnv() != JNI_OK)
	{
		return JNI_FALSE;
	}

	jclass callbackClz = NULL;
	

	if (l3_getJniClassFromObj(&callbackClz, &callbackObject) != JNI_OK)
	{
		return JNI_FALSE;
	}

	jmethodID callbackMeth = NULL;	

	if (l3_getJniMethID(&callbackMeth, &callbackClz, "addToMapping", "(III)V"))
	{
		return JNI_FALSE;
	}

	jint tor_id = id;
	
	(*env)->CallVoidMethod(env, callbackObject, callbackMeth, tor_id, pid, eid);
	

	if (isAttach)
	{
		(*jvm)->DetachCurrentThread(jvm);//Ӧ���߳̽���֮ǰ���á�
	}
	return JNI_OK;
}


JNIEXPORT jint JNICALL
transIsClosedNoticToJava() {
	if (l3_getJniCurrentThreadEnv() != JNI_OK)
	{
		return JNI_FALSE;
	}

	jclass callbackClz = NULL;

	if (l3_getJniClassFromObj(&callbackClz, &callbackObject) != JNI_OK)
	{
		return JNI_FALSE;
	}

	jmethodID callbackMeth = NULL;

	if (l3_getJniMethID(&callbackMeth, &callbackClz, "transIsClosed", "()V"))
	{
		return JNI_FALSE;
	}

	(*env)->CallVoidMethod(env, callbackObject, callbackMeth);

	if (isAttach)
	{
		(*jvm)->DetachCurrentThread(jvm);//Ӧ���߳̽���֮ǰ���á�
	}
	return JNI_OK;
}





/*
// ��C�������JAVA���룬������
JNIEXPORT void JNICALL Java_transmission_deamon_TransHandler_test(JNIEnv *env, jobject j)
{
	
	jstring str = 0;

	jclass clz = (*env)->FindClass(env, "transmission/deamon/TransHandler");
	//��ȡclz�Ĺ��캯��������һ������  
	jmethodID ctor = (*env)->GetMethodID(env, clz, "<init>", "()V");
	jobject obj = (*env)->NewObject(env, clz, ctor);

	// ������������ͣ���������ǰ��[,����������int[] intArray,���Ӧ����Ϊ[I,��������String[] strArray��ӦΪ[Ljava/lang/String;  
	jmethodID mid = (*env)->GetMethodID(env, clz, "torrentInfo", "()V");
	if (mid)
	{
		
		(*env)->CallVoidMethod(env, obj, mid);
		
	}
}
*/

