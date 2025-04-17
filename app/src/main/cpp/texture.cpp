#include "texture.h"
#include <android/log.h>

#define LOG_TAG "Texture"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

Texture::Texture(GLenum internalFormat, int width, int height, GLenum filterMode)
    : width(width), 
      height(height), 
      internalFormat(internalFormat),
      filterMode(filterMode) {
    
    // Gera o ID da textura
    glGenTextures(1, &textureId);
    
    // Vincula a textura
    glBindTexture(GL_TEXTURE_2D, textureId);
    
    // Configura os parâmetros de filtragem
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filterMode);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filterMode);
    
    // Configura os parâmetros de wrapping
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    
    // Aloca espaço para a textura
    GLenum format = GL_RGBA;
    GLenum type = GL_UNSIGNED_BYTE;
    
    // Ajusta o formato com base no formato interno
    if (internalFormat == GL_R32F || internalFormat == GL_R16F) {
        format = GL_RED;
        type = GL_FLOAT;
    } else if (internalFormat == GL_RG16F) {
        format = GL_RG;
        type = GL_FLOAT;
    }
    
    // Aloca espaço para a textura sem dados iniciais
    glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, type, nullptr);
    
    // Desvincula a textura
    glBindTexture(GL_TEXTURE_2D, 0);
    
    LOGI("Textura criada: id=%u, formato=%d, dimensões=%dx%d", textureId, internalFormat, width, height);
}

Texture::~Texture() {
    if (textureId != 0) {
        glDeleteTextures(1, &textureId);
        textureId = 0;
    }
}

void Texture::bind(GLuint unit) const {
    glActiveTexture(GL_TEXTURE0 + unit);
    glBindTexture(GL_TEXTURE_2D, textureId);
}

void Texture::unbind() const {
    glBindTexture(GL_TEXTURE_2D, 0);
}
