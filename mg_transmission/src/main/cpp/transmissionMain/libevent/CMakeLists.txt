
cmake_minimum_required(VERSION 3.4.1)



set(LIB_EVENT_PATH .)

include_directories(${LIB_EVENT_PATH}
                    ${LIB_EVENT_PATH}/include
                    ${LIB_EVENT_PATH}/android
                    ${LIB_EVENT_PATH}/WIN32-Code
                    )

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -fstack-protector -fpic -fno-strict-aliasing -fno-short-enums -g2 -gdwarf-2 -O0 -x c++-header -DHAVE_CONFIG_H -fno-rtti -fno-omit-frame-pointer -mthumb -fno-exceptions -mfloat-abi=softfp -Wpointer-arith -Wwrite-strings -Wunused -Winline -Wnested-externs -Wmissing-declarations -Wmissing-prototypes -Wno-long-long -Wfloat-equal -Wno-multichar -Wsign-compare -Wno-format-nonliteral -Wendif-labels -Wstrict-prototypes -Wdeclaration-after-statement -Wno-system-headers -Wreturn-type -Wimplicit-function-declaration -Wmacro-redefined")
# set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -DANDROID_NDK -fexceptions -DNDEBUG -D_REENTRANT -DENV_UNIX -DEXTERNAL_CODECS -DUNICODE -D_UNICODE -DUNIX_USE_WIN_FILE -Wno-c++11-narrowing")

file(GLOB event_c_path ${LIB_EVENT_PATH}/*.c)

add_library(
 event

 STATIC

 ${event_c_path}
 )


find_library(
            # Sets the name of the path variable.
              log-lib
            # Specifies the name of the NDK library that
            # you want CMake to locate.
              log )


target_link_libraries(
                    # Specifies the target library.
                    event
                    # Links the target library to the log library
                    # included in the NDK.
                     ${log-lib}
                    )