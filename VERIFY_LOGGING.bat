@echo off
REM Script de Verificación de Logging
REM Ejecuta: VERIFY_LOGGING.bat

echo.
echo ========================================
echo  VERIFICACION DE LOGGING
echo ========================================
echo.

setlocal enabledelayedexpansion

REM Verificar que exista pom.xml padre
echo [1/4] Verificando dependencia en pom.xml...
findstr /M "logstash-logback-encoder" pom.xml > nul
if %errorlevel% equ 0 (
    echo       ✓ Dependencia Logstash Logback Encoder encontrada
) else (
    echo       ✗ ERROR: Dependencia NO encontrada
)

echo.

REM Verificar logback-spring.xml en cada servicio
echo [2/4] Verificando archivos logback-spring.xml...

set servicios=config-server platos-service pedidos-service clientes-service pagos-service inventario-service mesas-service empleados-service notificaciones-service reportes-service

for %%s in (%servicios%) do (
    if exist "%%s\src\main\resources\logback-spring.xml" (
        echo       ✓ %%s - logback-spring.xml encontrado
    ) else (
        echo       ✗ %%s - ERROR: logback-spring.xml NO encontrado
    )
)

echo.

REM Verificar application.properties con logging.file.name
echo [3/4] Verificando configuración en application.properties...

for %%s in (%servicios%) do (
    findstr "logging.file.name" "%%s\src\main\resources\application.properties" > nul
    if !errorlevel! equ 0 (
        echo       ✓ %%s - Configuración de logs encontrada
    ) else (
        echo       ✗ %%s - ERROR: Configuración NO encontrada
    )
)

echo.

REM Verificar documentación
echo [4/4] Verificando documentación...

if exist "GUIA_LOGGING.md" (
    echo       ✓ GUIA_LOGGING.md encontrado
) else (
    echo       ✗ ERROR: GUIA_LOGGING.md NO encontrado
)

if exist "EJEMPLO_LOGGING_IMPLEMENTADO.md" (
    echo       ✓ EJEMPLO_LOGGING_IMPLEMENTADO.md encontrado
) else (
    echo       ✗ ERROR: EJEMPLO_LOGGING_IMPLEMENTADO.md NO encontrado
)

if exist "RESUMEN_IMPLEMENTACION_LOGGING.md" (
    echo       ✓ RESUMEN_IMPLEMENTACION_LOGGING.md encontrado
) else (
    echo       ✗ ERROR: RESUMEN_IMPLEMENTACION_LOGGING.md NO encontrado
)

echo.
echo ========================================
echo.
echo Para compilar y verificar logs en tiempo real:
echo.
echo 1. Compila el proyecto:
echo    mvn clean install
echo.
echo 2. Ejecuta un servicio:
echo    cd platos-service
echo    mvn spring-boot:run
echo.
echo 3. En otra terminal, ve los logs:
echo    tail -f logs/platos-service.json
echo.
echo 4. En otra terminal, haz una petición:
echo    curl -X GET http://localhost:8001/api/v1/platos
echo.
echo Los logs aparecerán en JSON en la terminal y en los archivos.
echo ========================================
echo.

pause

