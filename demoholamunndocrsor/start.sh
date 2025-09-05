#!/bin/bash

# Script de inicio para el Sistema de Arquitectura de Software
# Asegúrate de tener Java 17, Maven y MySQL instalados

echo "🚀 Iniciando Sistema de Gestión de Fases y Temas de Arquitectura de Software"
echo "=================================================================="

# Verificar que Java 17 esté instalado
if ! command -v java &> /dev/null; then
    echo "❌ Java no está instalado. Por favor instala Java 17 o superior."
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Se requiere Java 17 o superior. Versión actual: $JAVA_VERSION"
    exit 1
fi

echo "✅ Java $JAVA_VERSION detectado"

# Verificar que Maven esté instalado
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven no está instalado. Por favor instala Maven 3.6 o superior."
    exit 1
fi

echo "✅ Maven detectado"

# Verificar que MySQL esté ejecutándose
if ! command -v mysql &> /dev/null; then
    echo "❌ MySQL no está instalado. Por favor instala MySQL 8.0 o superior."
    exit 1
fi

echo "✅ MySQL detectado"

# Crear la base de datos si no existe
echo "📊 Configurando base de datos..."
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS arquitectura_software;" 2>/dev/null || {
    echo "⚠️  No se pudo conectar a MySQL. Asegúrate de que esté ejecutándose y las credenciales sean correctas."
    echo "   Puedes ejecutar manualmente: mysql -u root -p < database/init.sql"
}

# Compilar el proyecto
echo "🔨 Compilando el proyecto..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "❌ Error en la compilación. Revisa los errores anteriores."
    exit 1
fi

echo "✅ Compilación exitosa"

# Preguntar por el entorno
echo ""
echo "Selecciona el entorno de ejecución:"
echo "1) Desarrollo (dev) - Con logs detallados y recarga automática"
echo "2) Producción (prod) - Optimizado para producción"
read -p "Ingresa tu opción (1 o 2): " env_choice

case $env_choice in
    1)
        PROFILE="dev"
        echo "🔧 Iniciando en modo desarrollo..."
        ;;
    2)
        PROFILE="prod"
        echo "🏭 Iniciando en modo producción..."
        ;;
    *)
        echo "❌ Opción inválida. Iniciando en modo desarrollo por defecto."
        PROFILE="dev"
        ;;
esac

# Ejecutar la aplicación
echo "🚀 Iniciando la aplicación..."
echo "   Perfil: $PROFILE"
echo "   URL: http://localhost:8080"
echo "   Presiona Ctrl+C para detener"
echo ""

mvn spring-boot:run -Dspring-boot.run.profiles=$PROFILE

echo ""
echo "👋 Aplicación detenida. ¡Hasta luego!"



