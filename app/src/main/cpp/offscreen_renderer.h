#pragma once

#include <memory>
#include <string>
#include <GLES3/gl3.h>
#include "compute_shader.h"
#include "texture.h"

class OffscreenRenderer {
public:
    OffscreenRenderer();
    
    void execute();
    
    // Configurações
    bool enableSuperResolution;
    bool enableInterpolation;
    int generatedFramesCount;
    float upsampleScale;
    int renderWidth;
    int renderHeight;
    
private:
    // Dimensões calculadas
    int presentationWidth;
    int presentationHeight;
    
    // Grupos de trabalho para shaders
    GLuint groupX_LR;
    GLuint groupY_LR;
    GLuint groupZ_LR;
    GLuint groupX_HR;
    GLuint groupY_HR;
    GLuint groupZ_HR;
    
    // Métodos privados
    void initializeResources();
    void processFrames();
};
