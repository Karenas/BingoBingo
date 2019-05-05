cmake_minimum_required(VERSION 3.4.1)

set(CARES_PATH .)

include_directories(${CARES_PATH})

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -DHAVE_CONFIG_H -DCARES_STATICLIB -DCARES_BUILDING_LIBRARY -DCARES_SYMBOL_HIDING -fvisibility=hidden -g0 -O2 -Wno-system-headers")

add_library(
            # Sets the name of the library.
             cares
            # Sets the library as a shared library.
             STATIC
            # Provides a relative path to your source file(s).
            ${CARES_PATH}/ares__close_sockets.c
              ${CARES_PATH}/ares__get_hostent.c
              ${CARES_PATH}/ares__read_line.c
              ${CARES_PATH}/ares__timeval.c
              ${CARES_PATH}/ares_cancel.c
              ${CARES_PATH}/ares_data.c
              ${CARES_PATH}/ares_destroy.c
              ${CARES_PATH}/ares_expand_name.c
              ${CARES_PATH}/ares_expand_string.c
              ${CARES_PATH}/ares_fds.c
              ${CARES_PATH}/ares_free_hostent.c
              ${CARES_PATH}/ares_free_string.c
              ${CARES_PATH}/ares_getenv.c
              ${CARES_PATH}/ares_gethostbyaddr.c
              ${CARES_PATH}/ares_gethostbyname.c
              ${CARES_PATH}/ares_getnameinfo.c
              ${CARES_PATH}/ares_getsock.c
              ${CARES_PATH}/ares_init.c
              ${CARES_PATH}/ares_library_init.c
              ${CARES_PATH}/ares_llist.c
              ${CARES_PATH}/ares_mkquery.c
              ${CARES_PATH}/ares_create_query.c
              ${CARES_PATH}/ares_nowarn.c
              ${CARES_PATH}/ares_options.c
              ${CARES_PATH}/ares_parse_a_reply.c
              ${CARES_PATH}/ares_parse_aaaa_reply.c
              ${CARES_PATH}/ares_parse_mx_reply.c
              ${CARES_PATH}/ares_parse_naptr_reply.c
              ${CARES_PATH}/ares_parse_ns_reply.c
              ${CARES_PATH}/ares_parse_ptr_reply.c
              ${CARES_PATH}/ares_parse_soa_reply.c
              ${CARES_PATH}/ares_parse_srv_reply.c
              ${CARES_PATH}/ares_parse_txt_reply.c
              ${CARES_PATH}/ares_platform.c
              ${CARES_PATH}/ares_process.c
              ${CARES_PATH}/ares_query.c
              ${CARES_PATH}/ares_search.c
              ${CARES_PATH}/ares_send.c
              ${CARES_PATH}/ares_strcasecmp.c
              ${CARES_PATH}/ares_strdup.c
              ${CARES_PATH}/ares_strerror.c
              ${CARES_PATH}/ares_timeout.c
              ${CARES_PATH}/ares_version.c
              ${CARES_PATH}/ares_writev.c
              ${CARES_PATH}/bitncmp.c
              ${CARES_PATH}/inet_net_pton.c
              ${CARES_PATH}/inet_ntop.c
              ${CARES_PATH}/indows_port.c

             )

find_library(
            # Sets the name of the path variable.
              log-lib
            # Specifies the name of the NDK library that
            # you want CMake to locate.
              log )

target_link_libraries(
                  # Specifies the target library.
                     cares
                  # Links the target library to the log library
                  # included in the NDK.
                 ${log-lib}
                 )