# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html
# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

set(LOCAL_PATH src/main/cpp/transmissionMain)

include_directories(${LOCAL_PATH}
                    ${LOCAL_PATH}/libevent/include/include
                    ${LOCAL_PATH}/libevent/include/android
                    ${LOCAL_PATH}/libevent/include/WIN32-Code
                    )

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -v -g2 -gdwarf-2 -O0 -x c++-header -DEMBEDDED -fno-rtti -fno-omit-frame-pointer -mthumb -fno-exceptions -mfloat-abi=softfp -fno-short-enums")
# set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -DANDROID_NDK -fexceptions -DNDEBUG -D_REENTRANT -DENV_UNIX -DEXTERNAL_CODECS -DUNICODE -D_UNICODE -DUNIX_USE_WIN_FILE -Wno-c++11-narrowing")

add_library(
# Sets the name of the library.
 mg_transmission
# Sets the library as a shared library.
 SHARED
# Provides a relative path to your source file(s).
 ${LOCAL_PATH}/daemon.c
 ${LOCAL_PATH}/transInterface.c
 ${LOCAL_PATH}/watch.c
 )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library(
            # Sets the name of the path variable.
              log-lib
            # Specifies the name of the NDK library that
            # you want CMake to locate.
              log )
# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

ADD_SUBDIRECTORY(${LOCAL_PATH}/libevent)

target_link_libraries(
                    # Specifies the target library.
                    mg_transmission
                    # Links the target library to the log library
                    # included in the NDK.
                     ${log-lib}
                     event
                     )