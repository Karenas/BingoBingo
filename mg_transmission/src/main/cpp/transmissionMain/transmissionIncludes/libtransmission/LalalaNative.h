#ifdef DEBUG
	#include <android/log.h>
#endif

typedef unsigned int uint;
typedef unsigned char byte;

typedef unsigned long ulong;
typedef unsigned char uchar;

typedef struct MD5state
{
	uint len;
	uint state[4];
}MD5state;

void Dec2Radix(long long, unsigned int, char *);
long long Radix2Dec(char const *, unsigned int);
MD5state* md5Calculate(byte*, uint, byte*, MD5state*);
void md5(char *, int, char *);
int Base64Dec(uchar *, char *, int);
int Base64Enc(char *, uchar *, int);

#define MapTableLen	0x4000		//映射表长度，16384字节。须16的整数倍
