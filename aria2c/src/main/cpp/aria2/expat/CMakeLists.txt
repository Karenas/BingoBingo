cmake_minimum_required(VERSION 3.4.1)

set(EXPAT_PATH .)

include_directories(${EXPAT_PATH})

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -std=gnu99 -g -O2 -Wall -Wmissing-prototypes -Wstrict-prototypes -fexceptions -DHAVE_EXPAT_CONFIG_H")

add_library(
            # Sets the name of the library.
             expat
            # Sets the library as a shared library.
             STATIC
            # Provides a relative path to your source file(s).
             ${EXPAT_PATH}/xmlparse.c
             ${EXPAT_PATH}/xmlrole.c
             ${EXPAT_PATH}/xmltok.c
             ${EXPAT_PATH}/xmltok_impl.c
             ${EXPAT_PATH}/xmltok_ns.c
             ${EXPAT_PATH}/codepage.c
             ${EXPAT_PATH}/unixfilemap.c
             ${EXPAT_PATH}/xmlfile.c
             ${EXPAT_PATH}/xmlwf.c
             )

find_library(
            # Sets the name of the path variable.
              log-lib
            # Specifies the name of the NDK library that
            # you want CMake to locate.
              log )

target_link_libraries(
                  # Specifies the target library.
                     expat
                  # Links the target library to the log library
                  # included in the NDK.
                 ${log-lib}
                 )