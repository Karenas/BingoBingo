
cmake_minimum_required(VERSION 3.4.1)



set(TRANSMISSION_PATH .)
set(TRANSMISSION_THIRD_PATH ../transmissionIncludes/third-party)
set(TRANSMISSION_INCLUDE_PATH ../transmissionIncludes/libtransmission)

include_directories(${TRANSMISSION_PATH}
                    # ${TRANSMISSION_THIRD_PATH}
                    # ${TRANSMISSION_THIRD_PATH}/dht
                    # ${TRANSMISSION_THIRD_PATH}/libnatpmp
                    # ${TRANSMISSION_THIRD_PATH}/libutp
                    # ${TRANSMISSION_THIRD_PATH}/miniupmp
                    # ${TRANSMISSION_THIRD_PATH}/curl
                    ${TRANSMISSION_THIRD_PATH}/libevent/android
                    ${TRANSMISSION_THIRD_PATH}/libevent/include
                    ${TRANSMISSION_THIRD_PATH}/libevent/WIN32-Code
                    ${TRANSMISSION_THIRD_PATH}/libevent
                    ${TRANSMISSION_THIRD_PATH}/openssl
                    ${TRANSMISSION_THIRD_PATH}/openssl/crypto
                    ${TRANSMISSION_THIRD_PATH}/openssl/include
                    ${TRANSMISSION_THIRD_PATH}/openssl/ssl
                    ${TRANSMISSION_INCLUDE_PATH}
                    ../transmissionIncludes/third-party/libevent/include
                    )

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -D__TRANSMISSION__ -DANDROID -DNDEBUG -DHAVE_ZLIB -DHAVE_STRLCPY -DHAVE_GETMNTENT -DANDROID_PLATFORM=android-19")
# set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -DANDROID_NDK -fexceptions -DNDEBUG -D_REENTRANT -DENV_UNIX -DEXTERNAL_CODECS -DUNICODE -D_UNICODE -DUNIX_USE_WIN_FILE -Wno-c++11-narrowing")

file(GLOB c_path ${TRANSMISSION_PATH}/*.c)

add_library(
 libtransmission

 STATIC

 ${c_path}
 )


find_library(
            # Sets the name of the path variable.
              log-lib
            # Specifies the name of the NDK library that
            # you want CMake to locate.
              log )


target_link_libraries(
                    # Specifies the target library.
                    libtransmission
                    # Links the target library to the log library
                    # included in the NDK.
                     ${log-lib}
                    )