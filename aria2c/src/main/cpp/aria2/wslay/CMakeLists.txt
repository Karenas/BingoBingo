cmake_minimum_required(VERSION 3.4.1)

set(WSLAY_PATH .)

include_directories(${WSLAY_PATH}/lib/includes )

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -DHAVE_CONFIG_H")

add_library(
            # Sets the name of the library.
             wslay
            # Sets the library as a shared library.
             STATIC
            # Provides a relative path to your source file(s).
            ${WSLAY_PATH}/lib/wslay_event.c
            ${WSLAY_PATH}/lib/wslay_frame.c
            ${WSLAY_PATH}/lib/wslay_net.c
            ${WSLAY_PATH}/lib/wslay_queue.c

             )

find_library(
            # Sets the name of the path variable.
              log-lib
            # Specifies the name of the NDK library that
            # you want CMake to locate.
              log )

target_link_libraries(
                  # Specifies the target library.
                     wslay
                  # Links the target library to the log library
                  # included in the NDK.
                 ${log-lib}
                 )