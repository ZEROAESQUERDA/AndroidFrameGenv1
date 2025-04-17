#include <jni.h>
#include <string>
#include <android/log.h>

#define LOG_TAG "MobFGSR-JNI"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

// Estrutura para armazenar as configurações do Frame Generation
struct FrameGenConfig {
    bool enableFrameGeneration;
    bool enableSuperResolution;
    int generatedFramesCount;
    float upsampleScale;
    std::string gamePackage;
    
    // Configurações avançadas
    float depthDiffThresholdSR;
    float colorDiffThresholdFG;
    float depthDiffThresholdFG;
    float depthScale;
    float depthBias;
    int renderWidth;
    int renderHeight;
};

// Configuração global
static FrameGenConfig currentConfig;

extern "C" {

// Método JNI para aplicar as configurações de Frame Generation
JNIEXPORT jboolean JNICALL
Java_com_mobfgsr_framegeneration_MainActivity_applyFrameGenerationSettings(
        JNIEnv *env,
        jobject /* this */,
        jstring gamePackage,
        jboolean enableFrameGeneration,
        jboolean enableSuperResolution,
        jint generatedFramesCount,
        jfloat depthDiffThresholdSR,
        jfloat colorDiffThresholdFG,
        jfloat depthDiffThresholdFG,
        jfloat depthScale,
        jfloat depthBias,
        jint renderWidth,
        jint renderHeight,
        jfloat upsampleScale) {
    
    try {
        // Converte jstring para std::string
        const char *gamePackageChars = env->GetStringUTFChars(gamePackage, nullptr);
        std::string gamePackageStr(gamePackageChars);
        env->ReleaseStringUTFChars(gamePackage, gamePackageChars);
        
        // Atualiza a configuração global
        currentConfig.gamePackage = gamePackageStr;
        currentConfig.enableFrameGeneration = enableFrameGeneration;
        currentConfig.enableSuperResolution = enableSuperResolution;
        currentConfig.generatedFramesCount = generatedFramesCount;
        currentConfig.depthDiffThresholdSR = depthDiffThresholdSR;
        currentConfig.colorDiffThresholdFG = colorDiffThresholdFG;
        currentConfig.depthDiffThresholdFG = depthDiffThresholdFG;
        currentConfig.depthScale = depthScale;
        currentConfig.depthBias = depthBias;
        currentConfig.renderWidth = renderWidth;
        currentConfig.renderHeight = renderHeight;
        currentConfig.upsampleScale = upsampleScale;
        
        LOGI("Configurações aplicadas: jogo=%s, frameGen=%d, superRes=%d, framesCount=%d",
             currentConfig.gamePackage.c_str(),
             currentConfig.enableFrameGeneration,
             currentConfig.enableSuperResolution,
             currentConfig.generatedFramesCount);
        
        LOGI("Configurações avançadas: depthDiffSR=%.3f, colorDiffFG=%.3f, depthDiffFG=%.3f, depthScale=%.2f, depthBias=%.2f",
             currentConfig.depthDiffThresholdSR,
             currentConfig.colorDiffThresholdFG,
             currentConfig.depthDiffThresholdFG,
             currentConfig.depthScale,
             currentConfig.depthBias);
        
        LOGI("Configurações de resolução: renderWidth=%d, renderHeight=%d, upsampleScale=%.1f",
             currentConfig.renderWidth,
             currentConfig.renderHeight,
             currentConfig.upsampleScale);
        
        // Aqui seria implementada a lógica para aplicar as configurações ao jogo selecionado
        // Por enquanto, apenas retornamos true para indicar sucesso
        
        return JNI_TRUE;
    } catch (std::exception &e) {
        LOGE("Erro ao aplicar configurações: %s", e.what());
        return JNI_FALSE;
    }
}

} // extern "C"
