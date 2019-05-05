https://blog.csdn.net/qq_22494029/article/details/80972274
ifneq ($(TARGET_SIMULATOR),true)
 include $(all-subdir-makefiles)
# 如果用下述引入，提示 expat/Android.mk wslay/Android.mk aria2c-1.18.10/Android.mk 找不到。暂留作记录，仍使用上面的 include $(all-subdir-makefiles)
#  LOCAL_PATH := $(call my-dir)
#  include $(LOCAL_PATH)/cares/Android.mk
#  include $(LOCAL_PATH)/expat/Android.mk
#  include $(LOCAL_PATH)/wslay/Android.mk
#  include $(LOCAL_PATH)/aria2c-1.18.10/Android.mk
endif

