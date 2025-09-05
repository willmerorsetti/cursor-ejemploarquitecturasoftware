package com.example.holamundocursor.service;

import com.example.holamundocursor.model.Fase;
import com.example.holamundocursor.model.Tema;
import com.example.holamundocursor.repository.TemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TemaService {
    
    private final TemaRepository temaRepository;
    private final FaseService faseService;
    
    // Obtener todos los temas activos ordenados
    @Transactional(readOnly = true)
    public List<Tema> obtenerTodosLosTemas() {
        return temaRepository.findByActivoTrueOrderByOrdenAsc();
    }
    
    // Obtener temas por fase
    @Transactional(readOnly = true)
    public List<Tema> obtenerTemasPorFase(Long faseId) {
        return temaRepository.findByFaseIdAndActivoTrueOrderByOrdenAsc(faseId);
    }
    
    // Obtener un tema por ID
    @Transactional(readOnly = true)
    public Optional<Tema> obtenerTemaPorId(Long id) {
        return temaRepository.findById(id);
    }
    
    // Buscar temas por título
    @Transactional(readOnly = true)
    public List<Tema> buscarTemasPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return obtenerTodosLosTemas();
        }
        return temaRepository.findByTituloContainingIgnoreCaseAndActivoTrue(titulo.trim());
    }
    
    // Buscar temas por fase y título
    @Transactional(readOnly = true)
    public List<Tema> buscarTemasPorFaseYTitulo(Long faseId, String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return obtenerTemasPorFase(faseId);
        }
        return temaRepository.findByFaseIdAndTituloContainingIgnoreCaseAndActivoTrue(faseId, titulo.trim());
    }
    
    // Crear un nuevo tema
    public Tema crearTema(Tema tema) {
        // Validar que la fase existe
        Fase fase = faseService.obtenerFasePorId(tema.getFase().getId())
                .orElseThrow(() -> new IllegalArgumentException("Fase no encontrada con ID: " + tema.getFase().getId()));
        
        // Validar que no exista otro tema con el mismo orden en la fase
        if (temaRepository.existsByOrdenAndFaseIdAndActivoTrue(tema.getOrden(), fase.getId())) {
            throw new IllegalArgumentException("Ya existe un tema con el orden " + tema.getOrden() + " en esta fase");
        }
        
        // Validar que no exista otro tema con el mismo título en la fase
        if (temaRepository.findByTituloAndFaseIdAndActivoTrue(tema.getTitulo(), fase.getId()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un tema con el título " + tema.getTitulo() + " en esta fase");
        }
        
        tema.setFase(fase);
        tema.setActivo(true);
        tema.setCompletado(false);
        return temaRepository.save(tema);
    }
    
    // Actualizar un tema existente
    public Tema actualizarTema(Long id, Tema temaActualizado) {
        Tema temaExistente = temaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tema no encontrado con ID: " + id));
        
        // Validar que la fase existe si ha cambiado
        if (!temaExistente.getFase().getId().equals(temaActualizado.getFase().getId())) {
            Fase fase = faseService.obtenerFasePorId(temaActualizado.getFase().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Fase no encontrada con ID: " + temaActualizado.getFase().getId()));
            temaActualizado.setFase(fase);
        }
        
        // Validar orden si ha cambiado
        if (!temaExistente.getOrden().equals(temaActualizado.getOrden())) {
            if (temaRepository.existsByOrdenAndFaseIdAndActivoTrue(temaActualizado.getOrden(), temaActualizado.getFase().getId())) {
                throw new IllegalArgumentException("Ya existe un tema con el orden " + temaActualizado.getOrden() + " en esta fase");
            }
        }
        
        // Validar título si ha cambiado
        if (!temaExistente.getTitulo().equals(temaActualizado.getTitulo())) {
            if (temaRepository.findByTituloAndFaseIdAndActivoTrue(temaActualizado.getTitulo(), temaActualizado.getFase().getId()).isPresent()) {
                throw new IllegalArgumentException("Ya existe un tema con el título " + temaActualizado.getTitulo() + " en esta fase");
            }
        }
        
        // Actualizar campos
        temaExistente.setTitulo(temaActualizado.getTitulo());
        temaExistente.setDescripcion(temaActualizado.getDescripcion());
        temaExistente.setContenido(temaActualizado.getContenido());
        temaExistente.setOrden(temaActualizado.getOrden());
        temaExistente.setFase(temaActualizado.getFase());
        temaExistente.setCompletado(temaActualizado.getCompletado());
        
        return temaRepository.save(temaExistente);
    }
    
    // Eliminar un tema (soft delete)
    public void eliminarTema(Long id) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tema no encontrado con ID: " + id));
        
        tema.setActivo(false);
        temaRepository.save(tema);
    }
    
    // Marcar tema como completado
    public Tema marcarTemaCompletado(Long id, boolean completado) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tema no encontrado con ID: " + id));
        
        tema.setCompletado(completado);
        return temaRepository.save(tema);
    }
    
    // Obtener el siguiente orden disponible para una fase
    @Transactional(readOnly = true)
    public Integer obtenerSiguienteOrdenParaFase(Long faseId) {
        long count = temaRepository.countByFaseIdAndActivoTrue(faseId);
        return (int) (count + 1);
    }
    
    // Obtener estadísticas de una fase
    @Transactional(readOnly = true)
    public long contarTemasPorFase(Long faseId) {
        return temaRepository.countByFaseIdAndActivoTrue(faseId);
    }
    
    @Transactional(readOnly = true)
    public long contarTemasCompletadosPorFase(Long faseId) {
        return temaRepository.countByFaseIdAndCompletadoTrueAndActivoTrue(faseId);
    }
    
    // Verificar si un tema existe
    @Transactional(readOnly = true)
    public boolean existeTema(Long id) {
        return temaRepository.findById(id).isPresent();
    }
    
    // Buscar temas por fase (alias para compatibilidad)
    @Transactional(readOnly = true)
    public List<Tema> buscarTemasPorFase(Long faseId) {
        return obtenerTemasPorFase(faseId);
    }
    
    // Buscar temas por título (alias para compatibilidad)
    @Transactional(readOnly = true)
    public List<Tema> buscarTemas(String titulo) {
        return buscarTemasPorTitulo(titulo);
    }
}
