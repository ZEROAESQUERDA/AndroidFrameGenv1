cmake_minimum_required(VERSION 3.22.1)
project(mobfgsr-android)

# Configuração para OpenGL ES 3.0
find_library(GLES3-lib GLESv3)
find_library(log-lib log)
find_library(android-lib android)

# Inclui diretórios de cabeçalho
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/include)

# Adiciona os arquivos fonte
add_library(mobfgsr SHARED
            mobfgsr_jni.cpp
            frame_generator.cpp
            texture.cpp
            compute_shader.cpp
            offscreen_renderer.cpp)

# Vincula as bibliotecas necessárias
target_link_libraries(mobfgsr
                      ${GLES3-lib}
                      ${log-lib}
                      ${android-lib})

# Configura flags de compilação
set_target_properties(mobfgsr PROPERTIES
                     CXX_STANDARD 17
                     CXX_STANDARD_REQUIRED ON)

# Adiciona definições de compilação
target_compile_definitions(mobfgsr PRIVATE
                          ANDROID_PLATFORM
                          GL_GLEXT_PROTOTYPES)
