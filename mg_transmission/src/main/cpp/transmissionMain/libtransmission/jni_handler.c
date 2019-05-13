

#include <jni_handler.h>
//#include "transmission.h" 
#include "session.h"
#include "torrent.h"
static tr_session * mySession = NULL;
typedef struct l3_torrentInfo l3_torrentInfo;


//ժȡ��Ϣ���ص�java����
void
l3_callbackJava(tr_torrent * tor)
{


	int l3tor_id = tor->uniqueId;
	uint64_t totalsize = tor->info.totalSize;
	uint64_t downloadedsize = tor->completion.sizeNow;
	char * name = tor->info.originalName;//ȡ��matainfo����ͬ������
	float downloadspeed = tor->stats.pieceDownloadSpeed_KBps;
	
	//if (downloadedsize >= totalsize) {
	//	tor->completeness = tr_cpGetStatus(&tor->completion);
	//	tor->stats.activity = tr_torrentGetActivity(tor);
	//}

	int ta = tor->stats.activity;

	torrentInfoToJava(l3tor_id, totalsize, downloadedsize, name, ta, downloadspeed);
}




struct l3_add_torrent_idle_data
{
	tr_ctor * ctor;
	jint pid;
	jint eid;
};

//�޸���rpcimpl.c�е�addTorrentImpl����
static void
l3_addTorrentImpl(tr_ctor * ctor, jint pid, jint eid)
{
	int err;
	int duplicate_id;
	const char * result;
	tr_torrent * tor;
	
	err = 0;
	duplicate_id = 0;
	tor = tr_torrentNew(ctor, err, &duplicate_id); //�˴����ص�tor �����ܿ�����Ϊ������غ�ĳ�ʼ����Դ
	tr_ctorFree(ctor);

	if (!err) //����������native�������Ҫһ��ģ�鷴����java��
	{
		result = NULL;
		if (tor)//�������������غõ���ͬ�ļ�ʱ��torΪ��
		{
			addToMappingInJava(tor->uniqueId, pid, eid);
			l3_callbackJava(tor); //�����ݷ��ظ�Java��
		}
	}
	else if (err == TR_PARSE_ERR)
	{
		result = "invalid or corrupt torrent file";
	}
	else if (err == TR_PARSE_DUPLICATE)
	{
		result = "duplicate torrent";
	}

	
}

static void
l3_gotMetadataFromURL(tr_session       * session,
	bool               did_connect,
	bool               did_timeout,
	long               response_code,
	const void       * response,
	size_t             response_byte_count,
	void             * user_data)
{
	struct l3_add_torrent_idle_data * data = user_data;
	

	//dbgmsg("torrentAdd: HTTP response code was %ld (%s); response length was %zu bytes",
		//response_code, tr_webGetResponseStr(response_code), response_byte_count);

	if (response_code == 200 || response_code == 221) /* http or ftp success.. */
	{
		tr_ctorSetMetainfo(data->ctor, response, response_byte_count);
		l3_addTorrentImpl(data->ctor, data->pid, data->eid);
	}
	else
	{
		char result[1024];
		tr_snprintf(result, sizeof(result), "gotMetadataFromURL: http error %ld: %s",
			response_code, tr_webGetResponseStr(response_code));
	}
}

static bool
l3_isCurlURL(const char * url)
{
	if (url == NULL)
		return false;

	return !strncmp(url, "ftp://", 6) ||
		!strncmp(url, "http://", 7) ||
		!strncmp(url, "https://", 8);
}

JNIEXPORT jint JNICALL
l3_addTorrentBySeedURL(char * seedurl, jint pid, jint eid) {
	if (mySession == NULL)
	{
		mySession = get_tr_session();
	}

	if (l3_isCurlURL(seedurl))
	{
		struct l3_add_torrent_idle_data * d = tr_new0(struct l3_add_torrent_idle_data, 1);
		tr_ctor * ctor = tr_ctorNew(mySession);
		
		d->ctor = ctor;
		d->pid = pid;
		d->eid = eid;
		tr_webRun(mySession, seedurl, l3_gotMetadataFromURL, d);
	}
}






/*�����������������ʱ����Ҫ�˺�����tor->isRunning��ǩ��Ϊtrue*/
void
l3_torrentStart()
{
	if (mySession == NULL)
	{
		mySession = get_tr_session();
	}

	int i;
	int torrentCount;
	tr_torrent ** torrents;

	torrents = tr_sessionGetTorrents(mySession, &torrentCount);
	for (i = 0; i < torrentCount; ++i)
	{
		tr_torrent * tor = torrents[i];
		if (!tor->isRunning)
		{
			tr_torrentStart(tor);
		}
	}

	tr_free(torrents);
}
