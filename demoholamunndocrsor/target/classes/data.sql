-- Script de datos para H2
-- Las tablas se crean automáticamente por Hibernate

-- Insertar datos de ejemplo
INSERT INTO fases (nombre, descripcion, orden) VALUES
('Fundamentos de Arquitectura', 'Conceptos básicos y principios fundamentales de la arquitectura de software', 1),
('Patrones de Diseño', 'Patrones de diseño clásicos y modernos para arquitecturas robustas', 2),
('Microservicios', 'Arquitectura de microservicios, contenedores y orquestación', 3),
('DevOps y CI/CD', 'Integración continua, despliegue continuo y prácticas DevOps', 4),
('Seguridad en Arquitectura', 'Principios de seguridad aplicados a la arquitectura de software', 5),
('Escalabilidad y Performance', 'Técnicas para diseñar sistemas escalables y de alto rendimiento', 6);

-- Insertar temas de ejemplo
INSERT INTO temas (titulo, descripcion, contenido, orden, fase_id) VALUES
('Introducción a la Arquitectura de Software', 'Conceptos básicos y definiciones', 'La arquitectura de software es la estructura de alto nivel de un sistema de software.', 1, 1),
('Principios SOLID', 'Los cinco principios fundamentales del diseño orientado a objetos', 'Los principios SOLID son: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, y Dependency Inversion.', 2, 1),
('Patrón Singleton', 'Garantizar una única instancia de una clase', 'El patrón Singleton asegura que una clase tenga solo una instancia y proporciona un punto de acceso global a ella.', 1, 2),
('Patrón Factory', 'Creación de objetos sin especificar sus clases concretas', 'El patrón Factory proporciona una interfaz para crear objetos sin especificar sus clases exactas.', 2, 2),
('Introducción a Microservicios', 'Conceptos básicos de arquitectura de microservicios', 'Los microservicios son un enfoque arquitectónico donde una aplicación se construye como una colección de servicios débilmente acoplados.', 1, 3),
('Docker y Contenedores', 'Containerización de aplicaciones', 'Docker permite empaquetar aplicaciones y sus dependencias en contenedores ligeros y portátiles.', 2, 3);

-- Marcar algunos temas como completados
UPDATE temas SET completado = TRUE WHERE id IN (1, 2, 3, 4);
