#include "compute_shader.h"
#include <android/log.h>

#define LOG_TAG "ComputeShader"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

ComputeShader::ComputeShader(const std::string& shaderSource) {
    // Cria o shader de computação
    GLuint computeShader = glCreateShader(GL_COMPUTE_SHADER);
    
    // Define o código fonte do shader
    const char* source = shaderSource.c_str();
    glShaderSource(computeShader, 1, &source, nullptr);
    
    // Compila o shader
    glCompileShader(computeShader);
    
    // Verifica se a compilação foi bem-sucedida
    GLint success;
    glGetShaderiv(computeShader, GL_COMPILE_STATUS, &success);
    
    if (!success) {
        GLchar infoLog[512];
        glGetShaderInfoLog(computeShader, sizeof(infoLog), nullptr, infoLog);
        LOGE("Erro na compilação do shader: %s", infoLog);
        glDeleteShader(computeShader);
        throw std::runtime_error("Falha na compilação do shader de computação");
    }
    
    // Cria o programa
    programId = glCreateProgram();
    
    // Anexa o shader ao programa
    glAttachShader(programId, computeShader);
    
    // Linka o programa
    glLinkProgram(programId);
    
    // Verifica se a linkagem foi bem-sucedida
    glGetProgramiv(programId, GL_LINK_STATUS, &success);
    
    if (!success) {
        GLchar infoLog[512];
        glGetProgramInfoLog(programId, sizeof(infoLog), nullptr, infoLog);
        LOGE("Erro na linkagem do programa: %s", infoLog);
        glDeleteProgram(programId);
        glDeleteShader(computeShader);
        throw std::runtime_error("Falha na linkagem do programa de shader");
    }
    
    // Libera o shader, pois ele já está linkado ao programa
    glDeleteShader(computeShader);
    
    LOGI("Shader de computação criado com sucesso: id=%u", programId);
}

ComputeShader::~ComputeShader() {
    if (programId != 0) {
        glDeleteProgram(programId);
        programId = 0;
    }
}

void ComputeShader::use() const {
    glUseProgram(programId);
}

void ComputeShader::dispatch(GLuint groupX, GLuint groupY, GLuint groupZ) const {
    glDispatchCompute(groupX, groupY, groupZ);
    // Garante que a computação termine antes de usar os resultados
    glMemoryBarrier(GL_SHADER_IMAGE_ACCESS_BARRIER_BIT);
}
