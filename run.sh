#!/usr/bin/env bash

# Script para ejecutar auto_javarturs en Linux/Unix
# Soporta compilación con Java si Ant no está disponible

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

# Verifica que Java esté instalado
if ! command -v java >/dev/null 2>&1; then
    echo "Error: Java no está instalado. Por favor, instale Java para continuar." >&2
    exit 1
fi

# Configura variables de entorno para GUI en Linux
export DISPLAY="${DISPLAY:-:0}"

if [[ -z "${XAUTHORITY:-}" ]]; then
    if [[ -f "/run/user/$(id -u)/gdm/Xauthority" ]]; then
        export XAUTHORITY="/run/user/$(id -u)/gdm/Xauthority"
    fi
fi

# Si Ant está disponible, úsalo
if command -v ant >/dev/null 2>&1; then
    echo "Usando Ant para compilar el proyecto..."
    exec ant run
else
    echo "Ant no encontrado. Compilando con javac..."
    
    # Verifica que javac esté instalado
    if ! command -v javac >/dev/null 2>&1; then
        echo "Error: Java Compiler (javac) no está instalado. Por favor, instale el JDK." >&2
        exit 1
    fi
    
    # Prepara el directorio de clases
    mkdir -p build/classes
    find build/classes -mindepth 1 -delete 2>/dev/null || true

    # Busca archivos fuente
    mapfile -d '' sources < <(find src -name '*.java' -print0)

    if [[ ${#sources[@]} -eq 0 ]]; then
        echo "Error: no se encontraron archivos fuente en src/." >&2
        exit 1
    fi

    echo "Compilando ${#sources[@]} archivos..."
    
    # Compila los archivos
    if ! javac --release 23 -encoding UTF-8 -d build/classes -cp "lib/*" "${sources[@]}"; then
        echo "Error: Falló la compilación" >&2
        exit 1
    fi

    echo "Compilación completada. Iniciando la aplicación..."
    
    # Ejecuta la aplicación
    exec java -cp "build/classes:lib/*" Ejecucion.Main
fi