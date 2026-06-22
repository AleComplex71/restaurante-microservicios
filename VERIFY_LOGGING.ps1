# Script de Verificación de Logging
# Ejecuta en PowerShell: .\VERIFY_LOGGING.ps1

Write-Host ""
Write-Host "========================================"
Write-Host "  VERIFICACION DE LOGGING" -ForegroundColor Cyan
Write-Host "========================================"
Write-Host ""

# Verificar que exista pom.xml padre
Write-Host "[1/4] Verificando dependencia en pom.xml..." -ForegroundColor Yellow

$pomContent = Get-Content -Path "pom.xml" -Raw
if ($pomContent -match "logstash-logback-encoder") {
    Write-Host "      ✓ Dependencia Logstash Logback Encoder encontrada" -ForegroundColor Green
} else {
    Write-Host "      ✗ ERROR: Dependencia NO encontrada" -ForegroundColor Red
}

Write-Host ""

# Verificar logback-spring.xml en cada servicio
Write-Host "[2/4] Verificando archivos logback-spring.xml..." -ForegroundColor Yellow

$servicios = @(
    "config-server",
    "platos-service",
    "pedidos-service",
    "clientes-service",
    "pagos-service",
    "inventario-service",
    "mesas-service",
    "empleados-service",
    "notificaciones-service",
    "reportes-service"
)

foreach ($servicio in $servicios) {
    $logbackPath = "$servicio\src\main\resources\logback-spring.xml"
    if (Test-Path -Path $logbackPath) {
        Write-Host "      ✓ $servicio - logback-spring.xml encontrado" -ForegroundColor Green
    } else {
        Write-Host "      ✗ $servicio - ERROR: logback-spring.xml NO encontrado" -ForegroundColor Red
    }
}

Write-Host ""

# Verificar application.properties con logging.file.name
Write-Host "[3/4] Verificando configuración en application.properties..." -ForegroundColor Yellow

foreach ($servicio in $servicios) {
    $propsPath = "$servicio\src\main\resources\application.properties"
    if (Test-Path -Path $propsPath) {
        $propsContent = Get-Content -Path $propsPath -Raw
        if ($propsContent -match "logging.file.name") {
            Write-Host "      ✓ $servicio - Configuración de logs encontrada" -ForegroundColor Green
        } else {
            Write-Host "      ✗ $servicio - ERROR: Configuración NO encontrada" -ForegroundColor Red
        }
    } else {
        Write-Host "      ✗ $servicio - ERROR: application.properties NO encontrado" -ForegroundColor Red
    }
}

Write-Host ""

# Verificar documentación
Write-Host "[4/4] Verificando documentación..." -ForegroundColor Yellow

$docFiles = @(
    "GUIA_LOGGING.md",
    "EJEMPLO_LOGGING_IMPLEMENTADO.md",
    "RESUMEN_IMPLEMENTACION_LOGGING.md"
)

foreach ($doc in $docFiles) {
    if (Test-Path -Path $doc) {
        Write-Host "      ✓ $doc encontrado" -ForegroundColor Green
    } else {
        Write-Host "      ✗ ERROR: $doc NO encontrado" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "========================================"
Write-Host ""
Write-Host "Para compilar y verificar logs en tiempo real:" -ForegroundColor Cyan
Write-Host ""
Write-Host "1. Compila el proyecto:"
Write-Host "   mvn clean install"
Write-Host ""
Write-Host "2. Ejecuta un servicio:"
Write-Host "   cd platos-service"
Write-Host "   mvn spring-boot:run"
Write-Host ""
Write-Host "3. En otra terminal, ve los logs:"
Write-Host "   Get-Content -Path logs/platos-service.json -Wait"
Write-Host ""
Write-Host "4. En otra terminal, haz una petición:"
Write-Host "   curl -X GET http://localhost:8001/api/v1/platos"
Write-Host ""
Write-Host "Los logs aparecerán en JSON en la terminal y en los archivos."
Write-Host "========================================"
Write-Host ""

Read-Host "Presiona Enter para salir"

