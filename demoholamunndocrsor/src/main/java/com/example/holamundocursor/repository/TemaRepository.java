package com.example.holamundocursor.repository;

import com.example.holamundocursor.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {
    
    // Buscar temas por fase ordenados por orden
    List<Tema> findByFaseIdAndActivoTrueOrderByOrdenAsc(Long faseId);
    
    // Buscar temas activos ordenados por orden
    List<Tema> findByActivoTrueOrderByOrdenAsc();
    
    // Buscar temas por título (búsqueda parcial)
    @Query("SELECT t FROM Tema t WHERE t.titulo LIKE %:titulo% AND t.activo = true ORDER BY t.orden ASC")
    List<Tema> findByTituloContainingIgnoreCaseAndActivoTrue(@Param("titulo") String titulo);
    
    // Buscar temas por fase y título
    @Query("SELECT t FROM Tema t WHERE t.fase.id = :faseId AND t.titulo LIKE %:titulo% AND t.activo = true ORDER BY t.orden ASC")
    List<Tema> findByFaseIdAndTituloContainingIgnoreCaseAndActivoTrue(@Param("faseId") Long faseId, @Param("titulo") String titulo);
    
    // Buscar tema por título exacto en una fase
    Optional<Tema> findByTituloAndFaseIdAndActivoTrue(String titulo, Long faseId);
    
    // Verificar si existe un tema con el mismo orden en una fase
    boolean existsByOrdenAndFaseIdAndActivoTrue(Integer orden, Long faseId);
    
    // Buscar tema por orden en una fase
    Optional<Tema> findByOrdenAndFaseIdAndActivoTrue(Integer orden, Long faseId);
    
    // Contar temas activos por fase
    long countByFaseIdAndActivoTrue(Long faseId);
    
    // Contar temas completados por fase
    long countByFaseIdAndCompletadoTrueAndActivoTrue(Long faseId);
    
    // Buscar todos los temas con sus fases (para búsqueda global)
    @Query("SELECT t FROM Tema t JOIN FETCH t.fase f WHERE t.activo = true ORDER BY f.orden ASC, t.orden ASC")
    List<Tema> findAllWithFaseOrderByOrden();
}



