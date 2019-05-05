#参考：
#libtool: compile:  arm-linux-androideabi-gcc -DHAVE_CONFIG_H -I. -I.. -DHAVE_CONFIG_H -I./includes -I./includes -Wall -Os -g -MT wslay_frame.lo -MD -MP -MF .deps/wslay_frame.Tpo -c wslay_frame.c  -fPIC -DPIC -o .libs/wslay_frame.o

LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := wslay

LOCAL_SRC_FILES := lib/wslay_event.c lib/wslay_frame.c lib/wslay_net.c lib/wslay_queue.c

LOCAL_C_INCLUDES := $(LOCAL_PATH)/lib/includes

LOCAL_CFLAGS := -DHAVE_CONFIG_H

include $(BUILD_STATIC_LIBRARY)
