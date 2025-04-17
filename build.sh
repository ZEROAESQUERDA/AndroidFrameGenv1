#!/bin/bash

# Script para compilar o aplicativo Android Frame Generation

echo "Compilando o aplicativo Android Frame Generation..."

# Navegar para o diretório do projeto
cd /home/ubuntu/AndroidFrameGen

# Verificar se o diretório gradle existe, se não, criar
if [ ! -d "gradle" ]; then
  echo "Criando wrapper gradle..."
  mkdir -p gradle/wrapper
  
  # Criar arquivo gradle-wrapper.properties
  cat > gradle/wrapper/gradle-wrapper.properties << EOF
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-7.5-bin.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
EOF

  # Criar arquivo gradlew
  cat > gradlew << EOF
#!/usr/bin/env sh
exec gradle "\$@"
EOF
  chmod +x gradlew
  
  # Criar arquivo settings.gradle
  cat > settings.gradle << EOF
include ':app'
EOF
fi

# Criar arquivo gradle.properties se não existir
if [ ! -f "gradle.properties" ]; then
  cat > gradle.properties << EOF
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
android.useAndroidX=true
android.enableJetifier=true
kotlin.code.style=official
EOF
fi

# Criar arquivo proguard-rules.pro se não existir
if [ ! -f "app/proguard-rules.pro" ]; then
  mkdir -p app
  cat > app/proguard-rules.pro << EOF
# Add project specific ProGuard rules here.
EOF
fi

# Compilar o projeto
echo "Executando build..."
./gradlew assembleDebug

# Verificar se a compilação foi bem-sucedida
if [ $? -eq 0 ]; then
  echo "Compilação concluída com sucesso!"
  echo "APK gerado em: app/build/outputs/apk/debug/app-debug.apk"
else
  echo "Erro durante a compilação."
fi
