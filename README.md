# 🍽️ Sistema de Gestión de Restaurante - Microservicios

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Sistema de gestión completo para restaurantes basado en arquitectura de microservicios con Spring Boot, H2 Database, Swagger/OpenAPI y API Gateway.

## 📋 Tabla de Contenidos

- [Características](#características)
- [Arquitectura](#arquitectura)
- [Tecnologías](#tecnologías)
- [Instalación](#instalación)
- [Uso](#uso)
- [API Documentation](#api-documentation)
- [Estructura del Proyecto](#estructura-del-proyecto)

## ✨ Características

- 🏗️ **Arquitectura de Microservicios**: 10 servicios independientes
- 🚪 **API Gateway**: Punto de entrada único con Spring Cloud Gateway
- 📚 **Documentación Swagger/OpenAPI**: En todos los servicios
- 💾 **Base de Datos H2**: En memoria, sin configuración externa
- 🔄 **Circuit Breaker**: Resiliencia y fallback implementados
- 🔗 **WebClient**: Comunicación asíncrona entre servicios
- ✅ **Validaciones**: Bean Validation en todos los endpoints
- 📊 **Logging Estructurado**: Logs en formato JSON con Logstash
- 🧪 **Tests Unitarios**: JUnit 5 + Mockito
- 🎨 **Patrón CSR**: Controller-Service-Repository bien definido

## 🏛️ Arquitectura

```
                            ┌─────────────────┐
                            │   API Gateway   │
                            │   Port: 8080    │
                            └────────┬────────┘
                                     │
        ┌────────────────────────────┼────────────────────────────┐
        │                            │                            │
   ┌────▼────┐               ┌──────▼──────┐            ┌───────▼──────┐
   │ Platos  │               │   Pedidos   │            │   Clientes   │
   │  :8001  │               │    :8002    │            │    :8003     │
   └─────────┘               └─────────────┘            └──────────────┘
        │                            │                            │
   ┌────▼────┐               ┌──────▼──────┐            ┌───────▼──────┐
   │  Pagos  │               │ Inventario  │            │    Mesas     │
   │  :8004  │               │    :8005    │            │    :8006     │
   └─────────┘               └─────────────┘            └──────────────┘
        │                            │                            │
   ┌────▼────────┐           ┌──────▼──────────┐       ┌────────▼─────┐
   │  Empleados  │           │ Notificaciones  │       │   Reportes   │
   │    :8007    │           │     :8008       │       │    :8009     │
   └─────────────┘           └─────────────────┘       └──────────────┘
```

## 🛠️ Tecnologías

### Backend
- **Java 21**
- **Spring Boot 3.3.0**
- **Spring Cloud Gateway 2024.0.1**
- **Spring Data JPA**
- **Hibernate Validator**

### Base de Datos
- **H2 Database** (en memoria)

### Documentación
- **SpringDoc OpenAPI 2.5.0**
- **Swagger UI**

### Testing
- **JUnit 5**
- **Mockito**
- **Spring Boot Test**

### Herramientas
- **Maven**
- **Lombok**
- **Logback + Logstash Encoder**

## 🚀 Instalación

### Requisitos Previos
- Java 21 o superior
- Maven 3.8+ (o usar el wrapper incluido)
- Git

### Clonar el Repositorio
```bash
git clone https://github.com/AleComplex71/restaurante-microservicios.git
cd restaurante-microservicios
```

### Compilar el Proyecto
```bash
./mvnw clean install -DskipTests
```

## ▶️ Uso

### Opción 1: Ejecutar desde IntelliJ IDEA
1. Abrir el proyecto en IntelliJ
2. Esperar a que Maven descargue las dependencias
3. Ejecutar cada servicio desde su clase `Application.java`

### Opción 2: Ejecutar con Maven
```bash
# API Gateway (ejecutar primero)
cd api-gateway
../mvnw spring-boot:run

# En terminales separadas, ejecutar los demás servicios:
cd clientes-service
../mvnw spring-boot:run

cd platos-service
../mvnw spring-boot:run

# ... etc
```

### Orden Recomendado de Inicio
1. **api-gateway** (puerto 8080) ← Primero
2. platos-service (puerto 8001)
3. pedidos-service (puerto 8002)
4. clientes-service (puerto 8003)
5. pagos-service (puerto 8004)
6. inventario-service (puerto 8005)
7. mesas-service (puerto 8006)
8. empleados-service (puerto 8007)
9. notificaciones-service (puerto 8008)
10. reportes-service (puerto 8009)

## 📖 API Documentation

Cada microservicio tiene su propia documentación Swagger:

| Servicio | Swagger UI | Puerto |
|----------|-----------|--------|
| **API Gateway** | http://localhost:8080 | 8080 |
| **Platos** | http://localhost:8001/swagger-ui.html | 8001 |
| **Pedidos** | http://localhost:8002/swagger-ui.html | 8002 |
| **Clientes** | http://localhost:8003/swagger-ui.html | 8003 |
| **Pagos** | http://localhost:8004/swagger-ui.html | 8004 |
| **Inventario** | http://localhost:8005/swagger-ui.html | 8005 |
| **Mesas** | http://localhost:8006/swagger-ui.html | 8006 |
| **Empleados** | http://localhost:8007/swagger-ui.html | 8007 |
| **Notificaciones** | http://localhost:8008/swagger-ui.html | 8008 |
| **Reportes** | http://localhost:8009/swagger-ui.html | 8009 |

### Consola H2 Database
Acceder a la base de datos en memoria de cada servicio:

- URL: `http://localhost:800X/h2-console`
- JDBC URL: `jdbc:h2:mem:{servicio}db`
- Usuario: `sa`
- Password: (vacío)

Ejemplo para clientes-service:
```
URL: http://localhost:8003/h2-console
JDBC URL: jdbc:h2:mem:clientesdb
```

## 📁 Estructura del Proyecto

```
restaurante-microservicios/
├── api-gateway/                 # API Gateway con Spring Cloud Gateway
├── config-server/              # Servidor de configuración (opcional)
├── platos-service/             # Gestión de menú y platos
├── pedidos-service/            # Gestión de pedidos
├── clientes-service/           # Gestión de clientes
├── pagos-service/              # Procesamiento de pagos
├── inventario-service/         # Control de inventario
├── mesas-service/              # Gestión de mesas y reservas
├── empleados-service/          # Gestión de empleados
├── notificaciones-service/     # Sistema de notificaciones
├── reportes-service/           # Generación de reportes
└── pom.xml                     # POM padre agregador
```

### Estructura de cada microservicio:
```
[servicio]-service/
├── src/
│   ├── main/
│   │   ├── java/com/restaurante/[servicio]/
│   │   │   ├── controller/     # Endpoints REST
│   │   │   ├── service/        # Lógica de negocio
│   │   │   ├── repository/     # Acceso a datos
│   │   │   ├── entity/         # Entidades JPA
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── exception/      # Manejo de excepciones
│   │   │   └── config/         # Configuraciones (Swagger, etc.)
│   │   └── resources/
│   │       ├── application.properties
│   │       └── logback-spring.xml
│   └── test/                   # Tests unitarios
└── pom.xml
```

## 🧪 Testing

### Ejecutar Tests
```bash
# Todos los tests
./mvnw test

# Test de un servicio específico
cd clientes-service
../mvnw test
```

### Ejemplo de Test
```java
@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    void crearCliente_Success() {
        // Given
        ClienteDTO dto = new ClienteDTO(...);

        // When
        when(clienteRepository.save(any())).thenReturn(cliente);
        ClienteDTO result = clienteService.crearCliente(dto);

        // Then
        assertNotNull(result);
        assertEquals(dto.getEmail(), result.getEmail());
    }
}
```

## 📊 Ejemplos de Uso

### Crear un Cliente
```bash
curl -X POST http://localhost:8080/api/v1/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan Pérez",
    "email": "juan@example.com",
    "telefono": "555-1234",
    "direccion": "Calle Falsa 123",
    "documento": "12345678"
  }'
```

### Listar Platos
```bash
curl http://localhost:8080/api/v1/platos
```

### Crear Pedido
```bash
curl -X POST http://localhost:8080/api/v1/pedidos \
  -H "Content-Type: application/json" \
  -d '{
    "mesaId": 1,
    "items": [
      {
        "platoId": 1,
        "cantidad": 2
      }
    ]
  }'
```

## 🔧 Configuración

### API Gateway
El Gateway está configurado con:
- Circuit Breaker en cada ruta
- Fallback controllers para resiliencia
- CORS habilitado globalmente
- Timeout de 5 segundos por defecto

Ver configuración completa en: `api-gateway/src/main/resources/application.yml`

### Base de Datos
Cada servicio usa H2 en memoria con:
- Generación automática de esquema (create-drop)
- Consola web habilitada
- Datos se pierden al reiniciar (ideal para desarrollo)

## 📝 Características Técnicas

### Validaciones
```java
@NotBlank(message = "El nombre es requerido")
private String nombre;

@Email(message = "Email inválido")
private String email;

@Positive(message = "El precio debe ser positivo")
private BigDecimal precio;
```

### Manejo de Excepciones
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        // Manejo centralizado
    }
}
```

### Logging
```json
{
  "@timestamp": "2026-06-22T16:07:59.370",
  "level": "INFO",
  "message": "Cliente creado exitosamente",
  "service": "clientes-service"
}
```

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:
1. Fork el proyecto
2. Crea una rama (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 👤 Autor

**AleComplex71**
- GitHub: [@AleComplex71](https://github.com/AleComplex71)

## 🙏 Agradecimientos

- Spring Boot Team
- Spring Cloud Team
- Comunidad de desarrolladores Java

---

⭐ Si este proyecto te fue útil, dale una estrella en GitHub!
