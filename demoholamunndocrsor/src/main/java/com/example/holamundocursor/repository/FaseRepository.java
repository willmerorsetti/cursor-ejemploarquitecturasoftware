package com.example.holamundocursor.repository;

import com.example.holamundocursor.model.Fase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FaseRepository extends JpaRepository<Fase, Long> {
    
    // Buscar fases activas ordenadas por orden
    List<Fase> findByActivaTrueOrderByOrdenAsc();
    
    // Buscar fases por nombre (búsqueda parcial)
    @Query("SELECT f FROM Fase f WHERE f.nombre LIKE %:nombre% AND f.activa = true ORDER BY f.orden ASC")
    List<Fase> findByNombreContainingIgnoreCaseAndActivaTrue(@Param("nombre") String nombre);
    
    // Buscar fase por nombre exacto
    Optional<Fase> findByNombreAndActivaTrue(String nombre);
    
    // Verificar si existe una fase con el mismo orden
    boolean existsByOrdenAndActivaTrue(Integer orden);
    
    // Buscar fase por orden
    Optional<Fase> findByOrdenAndActivaTrue(Integer orden);
    
    // Contar fases activas
    long countByActivaTrue();
    
    // Buscar todas las fases con sus temas cargados
    @Query("SELECT f FROM Fase f WHERE f.activa = true ORDER BY f.orden ASC")
    List<Fase> findAllWithTemasOrderByOrden();
}
