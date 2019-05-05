#在为编译 aria2c 设置 toolchain 的时候 用的是 android-18 ：
#/home/leiyi/adt/ndk/build/tools/make-standalone-toolchain.sh --platform=android-18 --toolchain=arm-linux-androideabi-4.8 --install-dir=$ANDROID_HOME/toolchain
#此处设为 android-9，具体差异待观察、查阅
APP_PLATFORM := android-9

#APP_ABI := all
APP_ABI := $(ANDROID_ABI)

NDK_TOOLCHAIN_VERSION := 4.8

#APP_STL := stlport_shared
#APP_STL := stlport_static
#APP_STL := gnustl_shared
APP_STL := gnustl_static

APP_CPPFLAGS += -std=c++11 -D__GXX_EXPERIMENTAL_CXX0X__
APP_CPPFLAGS += -fexceptions
APP_CPPFLAGS += -frtti