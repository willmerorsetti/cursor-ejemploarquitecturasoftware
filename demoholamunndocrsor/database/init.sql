-- Script de inicialización de la base de datos
-- Sistema de Gestión de Fases y Temas de Arquitectura de Software

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS arquitectura_software;
USE arquitectura_software;

-- Crear tabla de fases
CREATE TABLE IF NOT EXISTS fases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    orden INT NOT NULL,
    activa BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY unique_orden_activa (orden, activa),
    UNIQUE KEY unique_nombre_activa (nombre, activa)
);

-- Crear tabla de temas
CREATE TABLE IF NOT EXISTS temas (
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
    FOREIGN KEY (fase_id) REFERENCES fases(id) ON DELETE CASCADE,
    UNIQUE KEY unique_orden_fase_activa (orden, fase_id, activo),
    UNIQUE KEY unique_titulo_fase_activa (titulo, fase_id, activo)
);

-- Insertar datos de ejemplo - Fases
INSERT INTO fases (nombre, descripcion, orden) VALUES
('Fundamentos de Arquitectura', 'Conceptos básicos y principios fundamentales de la arquitectura de software', 1),
('Patrones de Diseño', 'Patrones de diseño clásicos y modernos para arquitecturas robustas', 2),
('Microservicios', 'Arquitectura de microservicios, contenedores y orquestación', 3),
('DevOps y CI/CD', 'Integración continua, despliegue continuo y prácticas DevOps', 4),
('Seguridad en Arquitectura', 'Principios de seguridad aplicados a la arquitectura de software', 5),
('Escalabilidad y Performance', 'Técnicas para diseñar sistemas escalables y de alto rendimiento', 6);

-- Insertar datos de ejemplo - Temas para Fundamentos de Arquitectura
INSERT INTO temas (titulo, descripcion, contenido, orden, fase_id) VALUES
('Introducción a la Arquitectura de Software', 'Conceptos básicos y definiciones', 'La arquitectura de software es la estructura de alto nivel de un sistema de software. Define los componentes principales, sus responsabilidades y las relaciones entre ellos.', 1, 1),
('Principios SOLID', 'Los cinco principios fundamentales del diseño orientado a objetos', 'Los principios SOLID son: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, y Dependency Inversion.', 2, 1),
('Arquitecturas Monolíticas vs Distribuidas', 'Comparación entre diferentes tipos de arquitecturas', 'Las arquitecturas monolíticas son más simples pero menos escalables, mientras que las distribuidas son más complejas pero más flexibles.', 3, 1),
('Clean Architecture', 'Principios de la arquitectura limpia de Robert C. Martin', 'Clean Architecture separa las preocupaciones en capas, con reglas de dependencia que apuntan hacia adentro, hacia las entidades de negocio.', 4, 1);

-- Insertar datos de ejemplo - Temas para Patrones de Diseño
INSERT INTO temas (titulo, descripcion, contenido, orden, fase_id) VALUES
('Patrón Singleton', 'Garantizar una única instancia de una clase', 'El patrón Singleton asegura que una clase tenga solo una instancia y proporciona un punto de acceso global a ella.', 1, 2),
('Patrón Factory', 'Creación de objetos sin especificar sus clases concretas', 'El patrón Factory proporciona una interfaz para crear objetos sin especificar sus clases exactas.', 2, 2),
('Patrón Observer', 'Notificación automática de cambios de estado', 'El patrón Observer define una dependencia uno-a-muchos entre objetos, donde un cambio en un objeto notifica automáticamente a todos sus dependientes.', 3, 2),
('Patrón Strategy', 'Intercambiar algoritmos en tiempo de ejecución', 'El patrón Strategy define una familia de algoritmos, los encapsula y los hace intercambiables.', 4, 2);

-- Insertar datos de ejemplo - Temas para Microservicios
INSERT INTO temas (titulo, descripcion, contenido, orden, fase_id) VALUES
('Introducción a Microservicios', 'Conceptos básicos de arquitectura de microservicios', 'Los microservicios son un enfoque arquitectónico donde una aplicación se construye como una colección de servicios débilmente acoplados.', 1, 3),
('Docker y Contenedores', 'Containerización de aplicaciones', 'Docker permite empaquetar aplicaciones y sus dependencias en contenedores ligeros y portátiles.', 2, 3),
('Kubernetes', 'Orquestación de contenedores', 'Kubernetes es una plataforma de orquestación de contenedores que automatiza el despliegue, escalado y gestión de aplicaciones.', 3, 3),
('API Gateway', 'Punto de entrada único para microservicios', 'Un API Gateway actúa como punto de entrada único para todos los clientes, manejando autenticación, autorización y enrutamiento.', 4, 3);

-- Insertar datos de ejemplo - Temas para DevOps y CI/CD
INSERT INTO temas (titulo, descripcion, contenido, orden, fase_id) VALUES
('Integración Continua (CI)', 'Automatización de la integración de código', 'CI es la práctica de integrar cambios de código frecuentemente, con verificaciones automáticas de build y test.', 1, 4),
('Despliegue Continuo (CD)', 'Automatización del despliegue a producción', 'CD extiende CI automatizando el despliegue de aplicaciones a diferentes entornos.', 2, 4),
('Infrastructure as Code', 'Gestión de infraestructura mediante código', 'IaC permite gestionar y aprovisionar infraestructura de computación mediante archivos de configuración.', 3, 4),
('Monitoreo y Logging', 'Observabilidad de aplicaciones en producción', 'El monitoreo y logging son esenciales para mantener aplicaciones saludables en producción.', 4, 4);

-- Insertar datos de ejemplo - Temas para Seguridad en Arquitectura
INSERT INTO temas (titulo, descripcion, contenido, orden, fase_id) VALUES
('Autenticación y Autorización', 'Control de acceso a sistemas', 'La autenticación verifica la identidad, mientras que la autorización determina qué puede hacer un usuario autenticado.', 1, 5),
('HTTPS y Certificados SSL', 'Comunicación segura en la web', 'HTTPS protege la comunicación entre cliente y servidor mediante cifrado SSL/TLS.', 2, 5),
('OWASP Top 10', 'Vulnerabilidades de seguridad más comunes', 'OWASP Top 10 es una lista de las vulnerabilidades de seguridad más críticas en aplicaciones web.', 3, 5),
('Secrets Management', 'Gestión segura de credenciales', 'La gestión de secretos implica almacenar y acceder de forma segura a credenciales y claves de API.', 4, 5);

-- Insertar datos de ejemplo - Temas para Escalabilidad y Performance
INSERT INTO temas (titulo, descripcion, contenido, orden, fase_id) VALUES
('Load Balancing', 'Distribución de carga entre servidores', 'El balanceador de carga distribuye el tráfico entrante entre múltiples servidores para mejorar el rendimiento.', 1, 6),
('Caching Strategies', 'Estrategias de caché para mejorar performance', 'El caching almacena datos frecuentemente accedidos en memoria para reducir latencia y carga en la base de datos.', 2, 6),
('Database Sharding', 'Particionamiento horizontal de bases de datos', 'El sharding divide una base de datos en múltiples fragmentos más pequeños y manejables.', 3, 6),
('CDN y Edge Computing', 'Distribución de contenido global', 'Las CDN distribuyen contenido estático desde ubicaciones geográficamente cercanas a los usuarios.', 4, 6);

-- Marcar algunos temas como completados para mostrar el progreso
UPDATE temas SET completado = TRUE WHERE id IN (1, 2, 5, 6, 9, 10, 13, 14, 17, 18, 21, 22);

-- Crear índices para mejorar el rendimiento
CREATE INDEX idx_fases_activa_orden ON fases(activa, orden);
CREATE INDEX idx_temas_activo_orden ON temas(activo, orden);
CREATE INDEX idx_temas_fase_activo ON temas(fase_id, activo);
CREATE INDEX idx_temas_completado ON temas(completado);

-- Mostrar estadísticas
SELECT 
    'Fases creadas' as tipo,
    COUNT(*) as cantidad
FROM fases 
WHERE activa = TRUE
UNION ALL
SELECT 
    'Temas creados' as tipo,
    COUNT(*) as cantidad
FROM temas 
WHERE activo = TRUE
UNION ALL
SELECT 
    'Temas completados' as tipo,
    COUNT(*) as cantidad
FROM temas 
WHERE activo = TRUE AND completado = TRUE;



