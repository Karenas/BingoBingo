#include <jni.h>
#include <transmission.h>




void l3_torrentStart();

JNIEXPORT jint JNICALL
l3_addTorrentBySeedURL(char * seedurl, jint pid, jint eid);

JNIEXPORT jint JNICALL
torrentInfoToJava(
	int id,
	uint64_t totalsize,
	uint64_t downloadedsize,
	char * name,
	int activity,
	float downloadspeed
);

JNIEXPORT jint JNICALL
addToMappingInJava(
	int id,
	jint pid,
	jint eid
);