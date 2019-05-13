LOCAL_PATH:= $(call my-dir)

#
# libnatpmp
#

include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \
    utp.cpp \
    utp_utils.cpp

LOCAL_CFLAGS:= \
	-DENABLE_STRNATPMPERR \

LOCAL_C_INCLUDES:= \
	\

LOCAL_SHARED_LIBRARIES := \
	\

LOCAL_STATIC_LIBRARIES := \
	\


LOCAL_MODULE:=libutp

include $(BUILD_STATIC_LIBRARY)
