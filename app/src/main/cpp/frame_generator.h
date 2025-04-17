#pragma once

#include <string>
#include <memory>
#include <GLES3/gl3.h>

// Classe para gerenciar texturas OpenGL ES
class Texture {
public:
    Texture(GLenum internalFormat, int width, int height, GLenum filterMode);
    ~Texture();

    // Métodos para manipulação de texturas
    void bind(GLuint unit) const;
    void unbind() const;
    GLuint getId() const { return textureId; }
    int getWidth() const { return width; }
    int getHeight() const { return height; }

private:
    GLuint textureId;
    int width;
    int height;
    GLenum internalFormat;
    GLenum filterMode;
};

// Classe para gerenciar shaders de computação
class ComputeShader {
public:
    ComputeShader(const std::string& shaderSource);
    ~ComputeShader();

    void use() const;
    void dispatch(GLuint groupX, GLuint groupY, GLuint groupZ) const;
    GLuint getProgramId() const { return programId; }

private:
    GLuint programId;
};

// Classe principal para Frame Generation
class FrameGenerator {
public:
    FrameGenerator();
    ~FrameGenerator();

    // Inicializa o Frame Generator com as configurações especificadas
    bool initialize(bool enableFrameGeneration, 
                   bool enableSuperResolution,
                   int generatedFramesCount,
                   const std::string& gameName);

    // Aplica o Frame Generation ao frame atual
    bool processFrame(void* inputFrameData, int width, int height, void* outputFrameData);

    // Configura parâmetros
    void setFrameGenerationEnabled(bool enabled);
    void setSuperResolutionEnabled(bool enabled);
    void setGeneratedFramesCount(int count);

    // Obtém o estado atual
    bool isFrameGenerationEnabled() const { return enableFrameGeneration; }
    bool isSuperResolutionEnabled() const { return enableSuperResolution; }
    int getGeneratedFramesCount() const { return generatedFramesCount; }

private:
    // Configurações
    bool enableFrameGeneration;
    bool enableSuperResolution;
    int generatedFramesCount;
    float upsampleScale;
    std::string gameName;

    // Dimensões
    int renderWidth;
    int renderHeight;
    int presentationWidth;
    int presentationHeight;

    // Grupos de trabalho para shaders de computação
    GLuint groupX_LR;
    GLuint groupY_LR;
    GLuint groupZ_LR;
    GLuint groupX_HR;
    GLuint groupY_HR;
    GLuint groupZ_HR;

    // Texturas e shaders
    std::shared_ptr<Texture> inputTexture;
    std::shared_ptr<Texture> outputTexture;
    std::shared_ptr<Texture> previousFrameTexture;
    std::shared_ptr<Texture> motionVectorTexture;
    std::shared_ptr<Texture> depthTexture;

    // Métodos privados para processamento
    void setupTextures(int width, int height);
    void setupShaders();
    void processInputFrame(void* frameData);
    void generateIntermediateFrames();
    void applyFrameGeneration();
    void applySuperResolution();
    void extractOutputFrame(void* outputData);
};
