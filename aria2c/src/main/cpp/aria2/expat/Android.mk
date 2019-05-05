#
# libexpat， 根据以下编译输出 expat1.log 的 gcc 命令行信息编写
#lalala@vm8ubuntu:~/workspace/expat-2.1.0$ make 1>>~/workspace/expat1.log 2>>~/workspace/expat2.log
#gcc -std=gnu99 -I./lib -I. -g -O2 -Wall -Wmissing-prototypes -Wstrict-prototypes -fexceptions  -DHAVE_EXPAT_CONFIG_H -o lib/xmlparse.lo -c lib/xmlparse.c
#

LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \
  xmlparse.c \
  xmlrole.c \
  xmltok.c \
  xmltok_impl.c \
  xmltok_ns.c \
	codepage.c \
	unixfilemap.c \
	xmlfile.c \
	xmlwf.c

LOCAL_CFLAGS := -std=gnu99 -g -O2 -Wall -Wmissing-prototypes -Wstrict-prototypes -fexceptions -DHAVE_EXPAT_CONFIG_H

LOCAL_C_INCLUDES:= $(LOCAL_PATH)

LOCAL_SHARED_LIBRARIES := \

LOCAL_STATIC_LIBRARIES := \

LOCAL_MODULE:=expat

include $(BUILD_STATIC_LIBRARY)
