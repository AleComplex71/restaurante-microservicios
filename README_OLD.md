## Restaurante - Arquitectura de Microservicios
Sistema de gestión de restaurante basado en microservicios independientes con persistencia en MySQL, validaciones exhaustivas, manejo centralizado de errores y logging estructurado.
1. **Config Server** (Puerto 8888) - Servidor centralizado de configuración
2. **Platos Service** (Puerto 8001) - Gestión de menú
3. **Pedidos Service** (Puerto 8002) - Gestión de órdenes
4. **Clientes Service** (Puerto 8003) - Administración de clientes
5. **Pagos Service** (Puerto 8004) - Procesamiento de transacciones
6. **Inventario Service** (Puerto 8005) - Control de stock
7. **Mesas Service** (Puerto 8006) - Gestión de mesas y reservas
8. **Empleados Service** (Puerto 8007) - Administración de personal
9. **Notificaciones Service** (Puerto 8008) - Sistema de notificaciones
10. **Reportes Service** (Puerto 8009) - Generación de reportes

### Configuración Inicial
#### 1. Crear Bases de Datos
Ejecutar en MySQL:
```bash
#### 2. Compilar
cd restaurante
mvn clean install
```
#### 3. Ejecutar Servicios
Cada servicio rl independiente:
```bash
# Terminal 1 - Config Server
cd config-server
mvn spring-boot:run

# Terminal 2 - Platos Service
cd platos-service
mvn spring-boot:run

# Terminal 3 - Pedidos Service
cd pedidos-service
mvn spring-boot:run
### Estructura de Proyecto
Cada microservicio contiene:
```
[servicio-service]/
├── pom.xml
└── src/main/
    ├── java/com/restaurante/[servicio]/
    │   ├── [Servicio]Application.java
    │   ├── config/
    │   ├── controller/
    │   ├── dto/
    │   ├── entity/
    │   ├── exception/
    │   ├── repository/
    │   └── service/
    └── resources/
        ├── application.properties
        └── logback-spring.xml
```

### Patrón CSR
- **Controller**: Manejo de solicitudes HTTP
- **Service**: Lógica de negocio
- **Repository**: Acceso a datos mediante JPA

### Validaciones

Implementadas mediante Bean Validation (JSR 380):
- Anotaciones en DTOs (@NotBlank, @Positive, @Email, etc.)
- Validación automática en controladores
- Manejo centralizado de errores de validación

### Manejo de Excepciones

- GlobalExceptionHandler con @ControllerAdvice
- Response entity con códigos HTTP adecuados
- Mensajes de error estructurados

### Logging

Implementado con SLF4J + Logback + Logstash Encoder:
- Logs en formato JSON estructurado
- Rotación automática de archivos
- Un archivo log por servicio en directorio `logs/`

### Comunicación Entre Microservicios

Implementada con WebClient:
- Validación de datos recibidos
- Manejo de timeouts
- Try-catch para errores de conectividad

### Testing

Colección Postman incluida: `POSTMAN_COLLECTION.json`

Importar en Postman e incluye todos los endpoints:
- GET, POST, PUT, DELETE
- Validación de respuestas
- Pruebas de comunicación inter-servicios

### Dependencias Principales

- Spring Boot 3.3.0
- Spring Data JPA
- MySQL Connector
- Hibernate Validator
- Logstash Logback Encoder
- Lombok

### Respuestas Estructuradas

Todos los endpoints retornan ResponseEntity con estructura:

```json
{
  "status": "200",
  "message": "Operación exitosa",
  "data": {}
}
```

En caso de error:

```json
{
  "status": "400",
  "message": "Error de validación",
  "details": {
    "campo": "valor requerido"
  }
}
```
### Propiedades Configurables

Cada servicio tiene su propio `application.properties`:Puerto independiente Base de datos específica
  Niveles de logging- Configuración de Spring Cloud Config Archivos Importantes
- `DATABASE_SETUP.sql` - Script de creación de esquemas y datos iniciales
- `POSTMAN_COLLECTION.json` - Colección de endpoints para testing
- `pom.xml` - POM padre agregador
- `mvnw` / `mvnw.cmd` - Maven wrapper

1. Cliente realiza petición HTTP a endpoint
2. Controller valida entrada mediante DTOs
3. Service ejecuta lógica de negocio
4. Repository accede a base de datos
5. Response se envuelve en ResponseEntity
6. Logs se registran en formato JSON
7. En caso de error, GlobalExceptionHandler maneja excepción



