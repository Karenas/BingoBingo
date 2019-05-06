# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html
# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

project(aria2c)

set(LOCAL_PATH src/main/cpp/aria2)

include_directories(${LOCAL_PATH}/wslay)
include_directories(${LOCAL_PATH}/expat)
include_directories(${LOCAL_PATH}/cares)

add_subdirectory(${LOCAL_PATH}/wslay)
add_subdirectory(${LOCAL_PATH}/expat)
add_subdirectory(${LOCAL_PATH}/cares)

add_library(aria2c )#上述方法有问题，这种方法才有效
target_link_libraries(aria2c hello world)#将多目录下的hello库和world库链接上可执行文件helloworld.o