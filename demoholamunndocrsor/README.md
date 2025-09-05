# Sistema de Gestión de Fases y Temas de Arquitectura de Software

Un sistema completo desarrollado en Java 17 con Spring Boot para gestionar fases y temas de aprendizaje en arquitectura de software.

## 🚀 Características

- **Gestión de Fases**: Crear, editar, eliminar y buscar fases de aprendizaje
- **Gestión de Temas**: Crear, editar, eliminar y marcar como completados los temas
- **Búsqueda Avanzada**: Buscar fases por nombre y temas por título
- **Interfaz Web Moderna**: Interfaz responsive con Bootstrap 5
- **API REST**: Endpoints para integración con otras aplicaciones
- **Base de Datos MySQL**: Persistencia de datos con JPA/Hibernate
- **Progreso Visual**: Barras de progreso y estadísticas

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **Spring Web MVC**
- **Thymeleaf** (Motor de plantillas)
- **MySQL 8.0**
- **Bootstrap 5** (Frontend)
- **Lombok** (Reducción de código boilerplate)

## 📋 Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🗄️ Estructura de Base de Datos

### Tabla: `fases`
```sql
CREATE TABLE fases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    orden INT NOT NULL,
    activa BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Tabla: `temas`
```sql
CREATE TABLE temas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT,
    contenido LONGTEXT,
    orden INT NOT NULL,
    completado BOOLEAN NOT NULL DEFAULT FALSE,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fase_id BIGINT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (fase_id) REFERENCES fases(id)
);
```

## ⚙️ Configuración

### 1. Configurar Base de Datos

1. Crear una base de datos MySQL:
```sql
CREATE DATABASE arquitectura_software;
```

2. Actualizar las credenciales en `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/arquitectura_software?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### 2. Ejecutar la Aplicación

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar la aplicación
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 📱 Funcionalidades

### Gestión de Fases
- ✅ Crear nuevas fases
- ✅ Editar fases existentes
- ✅ Eliminar fases (soft delete)
- ✅ Buscar fases por nombre
- ✅ Ver estadísticas de progreso

### Gestión de Temas
- ✅ Crear nuevos temas
- ✅ Editar temas existentes
- ✅ Eliminar temas (soft delete)
- ✅ Marcar temas como completados
- ✅ Buscar temas por título
- ✅ Organizar temas por orden

### Interfaz Web
- ✅ Página principal con resumen
- ✅ Búsqueda de fases y temas
- ✅ Vista detallada de fases con sus temas
- ✅ Formularios para crear/editar
- ✅ Interfaz responsive

### API REST
- ✅ Endpoints para fases (`/api/fases`)
- ✅ Endpoints para temas (`/api/temas`)
- ✅ Endpoints de búsqueda
- ✅ Endpoints de estadísticas

## 🔗 Endpoints de la API

### Fases
- `GET /api/fases` - Obtener todas las fases
- `GET /api/fases/{id}` - Obtener fase por ID
- `GET /api/fases/buscar?nombre={nombre}` - Buscar fases por nombre
- `POST /api/fases` - Crear nueva fase
- `PUT /api/fases/{id}` - Actualizar fase
- `DELETE /api/fases/{id}` - Eliminar fase

### Temas
- `GET /api/temas` - Obtener todos los temas
- `GET /api/temas/{id}` - Obtener tema por ID
- `GET /api/fases/{faseId}/temas` - Obtener temas de una fase
- `GET /api/temas/buscar?titulo={titulo}` - Buscar temas por título
- `POST /api/temas` - Crear nuevo tema
- `PUT /api/temas/{id}` - Actualizar tema
- `DELETE /api/temas/{id}` - Eliminar tema
- `PATCH /api/temas/{id}/completar?completado={boolean}` - Marcar tema como completado

### Estadísticas
- `GET /api/fases/{faseId}/estadisticas` - Obtener estadísticas de una fase

## 🎯 Ejemplos de Uso

### Crear una Fase
```json
POST /api/fases
{
    "nombre": "Fundamentos de Arquitectura",
    "descripcion": "Conceptos básicos de arquitectura de software",
    "orden": 1
}
```

### Crear un Tema
```json
POST /api/temas
{
    "titulo": "Patrón Singleton",
    "descripcion": "Implementación del patrón Singleton",
    "contenido": "El patrón Singleton garantiza que una clase tenga solo una instancia...",
    "orden": 1,
    "fase": {
        "id": 1
    }
}
```

## 🚀 Despliegue

### Desarrollo
```bash
mvn spring-boot:run
```

### Producción
```bash
mvn clean package
java -jar target/demoholamunndocrsor-0.0.1-SNAPSHOT.jar
```

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/example/holamundocursor/
│   │   ├── controller/          # Controladores MVC y REST
│   │   ├── model/              # Entidades JPA
│   │   ├── repository/         # Repositorios JPA
│   │   ├── service/            # Lógica de negocio
│   │   └── DemoholamunndocrsorApplication.java
│   └── resources/
│       ├── templates/          # Plantillas Thymeleaf
│       └── application.properties
└── test/                       # Pruebas unitarias
```

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 📞 Soporte

Si tienes preguntas o necesitas ayuda, puedes:
- Abrir un issue en GitHub
- Contactar al desarrollador

---

**¡Disfruta aprendiendo arquitectura de software! 🎓**



