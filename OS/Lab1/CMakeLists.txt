cmake_minimum_required(VERSION 3.13)
project(compfunc CXX)


set(CMAKE_CXX_STANDARD 20)
#set(CMAKE_CXX_STANDARD_REQUIRED ON)
#add_executable(test compfunc.cpp test.cpp)
add_executable(test)

#set(CMAKE_VERBOSE_MAKEFILE ON)
#target_sources(test PRIVATE compfunc.cpp 
target_sources(test PUBLIC compfunc.cpp 
	test.cpp)

target_include_directories(test PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/include/)


