#pragma once

#include <string>
#include <GLES3/gl3.h>

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
