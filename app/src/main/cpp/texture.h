#pragma once

#include <GLES3/gl3.h>
#include <string>

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
