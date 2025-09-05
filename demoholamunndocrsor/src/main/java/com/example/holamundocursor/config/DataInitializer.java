package com.example.holamundocursor.config;

import com.example.holamundocursor.model.Fase;
import com.example.holamundocursor.model.Tema;
import com.example.holamundocursor.service.FaseService;
import com.example.holamundocursor.service.TemaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

// @Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class DataInitializer implements CommandLineRunner {
    
    private final FaseService faseService;
    private final TemaService temaService;
    
    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya hay datos
        if (!faseService.obtenerTodasLasFases().isEmpty()) {
            log.info("Los datos ya están inicializados, saltando la carga inicial");
            return;
        }
        
        log.info("Inicializando datos de ejemplo...");
        
        // Crear fases
        Fase fase1 = crearFase("Fundamentos de Arquitectura", "Conceptos básicos y principios fundamentales de la arquitectura de software", 1);
        Fase fase2 = crearFase("Patrones de Diseño", "Patrones de diseño clásicos y modernos para arquitecturas robustas", 2);
        Fase fase3 = crearFase("Microservicios", "Arquitectura de microservicios, contenedores y orquestación", 3);
        Fase fase4 = crearFase("DevOps y CI/CD", "Integración continua, despliegue continuo y prácticas DevOps", 4);
        Fase fase5 = crearFase("Seguridad en Arquitectura", "Principios de seguridad aplicados a la arquitectura de software", 5);
        Fase fase6 = crearFase("Escalabilidad y Performance", "Técnicas para diseñar sistemas escalables y de alto rendimiento", 6);
        
        // Crear temas para cada fase
        crearTemasFase1(fase1);
        crearTemasFase2(fase2);
        crearTemasFase3(fase3);
        crearTemasFase4(fase4);
        crearTemasFase5(fase5);
        crearTemasFase6(fase6);
        
        log.info("Datos de ejemplo inicializados correctamente");
    }
    
    private Fase crearFase(String nombre, String descripcion, int orden) {
        Fase fase = new Fase(nombre, descripcion, orden);
        return faseService.crearFase(fase);
    }
    
    private void crearTemasFase1(Fase fase) {
        List<Tema> temas = List.of(
            new Tema("Introducción a la Arquitectura de Software", "Conceptos básicos y definiciones", 
                "La arquitectura de software es la estructura de alto nivel de un sistema de software. Define los componentes principales, sus responsabilidades y las relaciones entre ellos.", 1, fase),
            new Tema("Principios SOLID", "Los cinco principios fundamentales del diseño orientado a objetos", 
                "Los principios SOLID son: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, y Dependency Inversion.", 2, fase),
            new Tema("Arquitecturas Monolíticas vs Distribuidas", "Comparación entre diferentes tipos de arquitecturas", 
                "Las arquitecturas monolíticas son más simples pero menos escalables, mientras que las distribuidas son más complejas pero más flexibles.", 3, fase),
            new Tema("Clean Architecture", "Principios de la arquitectura limpia de Robert C. Martin", 
                "Clean Architecture separa las preocupaciones en capas, con reglas de dependencia que apuntan hacia adentro, hacia las entidades de negocio.", 4, fase)
        );
        
        temas.forEach(tema -> {
            temaService.crearTema(tema);
            // Marcar algunos como completados
            if (tema.getTitulo().contains("Introducción") || tema.getTitulo().contains("SOLID")) {
                temaService.marcarTemaCompletado(tema.getId(), true);
            }
        });
    }
    
    private void crearTemasFase2(Fase fase) {
        List<Tema> temas = List.of(
            new Tema("Patrón Singleton", "Garantizar una única instancia de una clase", 
                "El patrón Singleton asegura que una clase tenga solo una instancia y proporciona un punto de acceso global a ella.", 1, fase),
            new Tema("Patrón Factory", "Creación de objetos sin especificar sus clases concretas", 
                "El patrón Factory proporciona una interfaz para crear objetos sin especificar sus clases exactas.", 2, fase),
            new Tema("Patrón Observer", "Notificación automática de cambios de estado", 
                "El patrón Observer define una dependencia uno-a-muchos entre objetos, donde un cambio en un objeto notifica automáticamente a todos sus dependientes.", 3, fase),
            new Tema("Patrón Strategy", "Intercambiar algoritmos en tiempo de ejecución", 
                "El patrón Strategy define una familia de algoritmos, los encapsula y los hace intercambiables.", 4, fase)
        );
        
        temas.forEach(tema -> {
            temaService.crearTema(tema);
            if (tema.getTitulo().contains("Singleton") || tema.getTitulo().contains("Factory")) {
                temaService.marcarTemaCompletado(tema.getId(), true);
            }
        });
    }
    
    private void crearTemasFase3(Fase fase) {
        List<Tema> temas = List.of(
            new Tema("Introducción a Microservicios", "Conceptos básicos de arquitectura de microservicios", 
                "Los microservicios son un enfoque arquitectónico donde una aplicación se construye como una colección de servicios débilmente acoplados.", 1, fase),
            new Tema("Docker y Contenedores", "Containerización de aplicaciones", 
                "Docker permite empaquetar aplicaciones y sus dependencias en contenedores ligeros y portátiles.", 2, fase),
            new Tema("Kubernetes", "Orquestación de contenedores", 
                "Kubernetes es una plataforma de orquestación de contenedores que automatiza el despliegue, escalado y gestión de aplicaciones.", 3, fase),
            new Tema("API Gateway", "Punto de entrada único para microservicios", 
                "Un API Gateway actúa como punto de entrada único para todos los clientes, manejando autenticación, autorización y enrutamiento.", 4, fase)
        );
        
        temas.forEach(tema -> {
            temaService.crearTema(tema);
            if (tema.getTitulo().contains("Introducción") || tema.getTitulo().contains("Docker")) {
                temaService.marcarTemaCompletado(tema.getId(), true);
            }
        });
    }
    
    private void crearTemasFase4(Fase fase) {
        List<Tema> temas = List.of(
            new Tema("Integración Continua (CI)", "Automatización de la integración de código", 
                "CI es la práctica de integrar cambios de código frecuentemente, con verificaciones automáticas de build y test.", 1, fase),
            new Tema("Despliegue Continuo (CD)", "Automatización del despliegue a producción", 
                "CD extiende CI automatizando el despliegue de aplicaciones a diferentes entornos.", 2, fase),
            new Tema("Infrastructure as Code", "Gestión de infraestructura mediante código", 
                "IaC permite gestionar y aprovisionar infraestructura de computación mediante archivos de configuración.", 3, fase),
            new Tema("Monitoreo y Logging", "Observabilidad de aplicaciones en producción", 
                "El monitoreo y logging son esenciales para mantener aplicaciones saludables en producción.", 4, fase)
        );
        
        temas.forEach(tema -> {
            temaService.crearTema(tema);
            if (tema.getTitulo().contains("Integración") || tema.getTitulo().contains("Despliegue")) {
                temaService.marcarTemaCompletado(tema.getId(), true);
            }
        });
    }
    
    private void crearTemasFase5(Fase fase) {
        List<Tema> temas = List.of(
            new Tema("Autenticación y Autorización", "Control de acceso a sistemas", 
                "La autenticación verifica la identidad, mientras que la autorización determina qué puede hacer un usuario autenticado.", 1, fase),
            new Tema("HTTPS y Certificados SSL", "Comunicación segura en la web", 
                "HTTPS protege la comunicación entre cliente y servidor mediante cifrado SSL/TLS.", 2, fase),
            new Tema("OWASP Top 10", "Vulnerabilidades de seguridad más comunes", 
                "OWASP Top 10 es una lista de las vulnerabilidades de seguridad más críticas en aplicaciones web.", 3, fase),
            new Tema("Secrets Management", "Gestión segura de credenciales", 
                "La gestión de secretos implica almacenar y acceder de forma segura a credenciales y claves de API.", 4, fase)
        );
        
        temas.forEach(tema -> {
            temaService.crearTema(tema);
            if (tema.getTitulo().contains("Autenticación") || tema.getTitulo().contains("HTTPS")) {
                temaService.marcarTemaCompletado(tema.getId(), true);
            }
        });
    }
    
    private void crearTemasFase6(Fase fase) {
        List<Tema> temas = List.of(
            new Tema("Load Balancing", "Distribución de carga entre servidores", 
                "El balanceador de carga distribuye el tráfico entrante entre múltiples servidores para mejorar el rendimiento.", 1, fase),
            new Tema("Caching Strategies", "Estrategias de caché para mejorar performance", 
                "El caching almacena datos frecuentemente accedidos en memoria para reducir latencia y carga en la base de datos.", 2, fase),
            new Tema("Database Sharding", "Particionamiento horizontal de bases de datos", 
                "El sharding divide una base de datos en múltiples fragmentos más pequeños y manejables.", 3, fase),
            new Tema("CDN y Edge Computing", "Distribución de contenido global", 
                "Las CDN distribuyen contenido estático desde ubicaciones geográficamente cercanas a los usuarios.", 4, fase)
        );
        
        temas.forEach(tema -> {
            temaService.crearTema(tema);
            if (tema.getTitulo().contains("Load Balancing") || tema.getTitulo().contains("Caching")) {
                temaService.marcarTemaCompletado(tema.getId(), true);
            }
        });
    }
}
