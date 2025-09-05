package com.example.holamundocursor.service;

import com.example.holamundocursor.model.Fase;
import com.example.holamundocursor.repository.FaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FaseService {
    
    private final FaseRepository faseRepository;
    
    // Obtener todas las fases activas ordenadas
    @Transactional(readOnly = true)
    public List<Fase> obtenerTodasLasFases() {
        return faseRepository.findByActivaTrueOrderByOrdenAsc();
    }
    
    // Obtener todas las fases con sus temas cargados
    @Transactional(readOnly = true)
    public List<Fase> obtenerTodasLasFasesConTemas() {
        return faseRepository.findAllWithTemasOrderByOrden();
    }
    
    // Obtener una fase por ID
    @Transactional(readOnly = true)
    public Optional<Fase> obtenerFasePorId(Long id) {
        return faseRepository.findById(id);
    }
    
    // Buscar fases por nombre
    @Transactional(readOnly = true)
    public List<Fase> buscarFasesPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return obtenerTodasLasFases();
        }
        return faseRepository.findByNombreContainingIgnoreCaseAndActivaTrue(nombre.trim());
    }
    
    // Crear una nueva fase
    public Fase crearFase(Fase fase) {
        // Validar que no exista otra fase con el mismo orden
        if (faseRepository.existsByOrdenAndActivaTrue(fase.getOrden())) {
            throw new IllegalArgumentException("Ya existe una fase con el orden " + fase.getOrden());
        }
        
        // Validar que no exista otra fase con el mismo nombre
        if (faseRepository.findByNombreAndActivaTrue(fase.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una fase con el nombre " + fase.getNombre());
        }
        
        fase.setActiva(true);
        return faseRepository.save(fase);
    }
    
    // Actualizar una fase existente
    public Fase actualizarFase(Long id, Fase faseActualizada) {
        Fase faseExistente = faseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fase no encontrada con ID: " + id));
        
        // Validar orden si ha cambiado
        if (!faseExistente.getOrden().equals(faseActualizada.getOrden())) {
            if (faseRepository.existsByOrdenAndActivaTrue(faseActualizada.getOrden())) {
                throw new IllegalArgumentException("Ya existe una fase con el orden " + faseActualizada.getOrden());
            }
        }
        
        // Validar nombre si ha cambiado
        if (!faseExistente.getNombre().equals(faseActualizada.getNombre())) {
            if (faseRepository.findByNombreAndActivaTrue(faseActualizada.getNombre()).isPresent()) {
                throw new IllegalArgumentException("Ya existe una fase con el nombre " + faseActualizada.getNombre());
            }
        }
        
        // Actualizar campos
        faseExistente.setNombre(faseActualizada.getNombre());
        faseExistente.setDescripcion(faseActualizada.getDescripcion());
        faseExistente.setOrden(faseActualizada.getOrden());
        
        return faseRepository.save(faseExistente);
    }
    
    // Eliminar una fase (soft delete)
    public void eliminarFase(Long id) {
        Fase fase = faseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fase no encontrada con ID: " + id));
        
        fase.setActiva(false);
        faseRepository.save(fase);
    }
    
    // Obtener el siguiente orden disponible
    @Transactional(readOnly = true)
    public Integer obtenerSiguienteOrden() {
        long count = faseRepository.countByActivaTrue();
        return (int) (count + 1);
    }
    
    // Verificar si una fase existe
    @Transactional(readOnly = true)
    public boolean existeFase(Long id) {
        return faseRepository.findById(id).isPresent();
    }
    
    // Buscar fases por nombre (alias para compatibilidad)
    @Transactional(readOnly = true)
    public List<Fase> buscarFases(String nombre) {
        return buscarFasesPorNombre(nombre);
    }
}
