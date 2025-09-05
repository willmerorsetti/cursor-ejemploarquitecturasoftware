package com.example.holamundocursor.controller;

import com.example.holamundocursor.model.Fase;
import com.example.holamundocursor.model.Tema;
import com.example.holamundocursor.dto.FaseDTO;
import com.example.holamundocursor.dto.TemaDTO;
import com.example.holamundocursor.service.FaseService;
import com.example.holamundocursor.service.TemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ArquitecturaRestController {

    @Autowired
    private FaseService faseService;

    @Autowired
    private TemaService temaService;

    // Obtener todas las fases con sus temas
    @GetMapping("/fases")
    public List<Fase> obtenerTodasLasFases() {
        return faseService.obtenerTodasLasFases();
    }

    // Obtener todas las fases con estadísticas
    @GetMapping("/fases/estadisticas")
    public Map<String, Object> obtenerEstadisticas() {
        List<Fase> fases = faseService.obtenerTodasLasFases();
        List<Tema> todosLosTemas = temaService.obtenerTodosLosTemas();
        
        // Calcular estadísticas
        long totalTemas = todosLosTemas.size();
        long temasCompletados = todosLosTemas.stream().filter(Tema::getCompletado).count();
        double progresoGeneral = totalTemas > 0 ? (double) temasCompletados / totalTemas * 100 : 0;
        
        // Convertir a DTOs
        List<FaseDTO> fasesDTO = fases.stream()
                .map(this::convertirAFaseDTO)
                .collect(Collectors.toList());
        
        Map<String, Object> estadisticas = new HashMap<>();
        estadisticas.put("totalFases", fases.size());
        estadisticas.put("totalTemas", totalTemas);
        estadisticas.put("temasCompletados", temasCompletados);
        estadisticas.put("progresoGeneral", Math.round(progresoGeneral * 10.0) / 10.0);
        estadisticas.put("fases", fasesDTO);
        
        return estadisticas;
    }

    // Obtener todos los temas
    @GetMapping("/temas")
    public List<Tema> obtenerTodosLosTemas() {
        return temaService.obtenerTodosLosTemas();
    }

    // Obtener una fase específica por ID
    @GetMapping("/fases/{id}")
    public Fase obtenerFasePorId(@PathVariable Long id) {
        return faseService.obtenerFasePorId(id);
    }

    // Obtener temas de una fase específica
    @GetMapping("/fases/{id}/temas")
    public List<Tema> obtenerTemasPorFase(@PathVariable Long id) {
        return temaService.obtenerTemasPorFase(id);
    }
    
    // Métodos de conversión
    private FaseDTO convertirAFaseDTO(Fase fase) {
        FaseDTO dto = new FaseDTO();
        dto.setId(fase.getId());
        dto.setNombre(fase.getNombre());
        dto.setDescripcion(fase.getDescripcion());
        dto.setOrden(fase.getOrden());
        dto.setActiva(fase.getActiva());
        dto.setFechaCreacion(fase.getFechaCreacion());
        dto.setFechaActualizacion(fase.getFechaActualizacion());
        
        // Convertir temas a DTOs
        if (fase.getTemas() != null) {
            List<TemaDTO> temasDTO = fase.getTemas().stream()
                    .map(this::convertirATemaDTO)
                    .collect(Collectors.toList());
            dto.setTemas(temasDTO);
        }
        
        return dto;
    }
    
    private TemaDTO convertirATemaDTO(Tema tema) {
        TemaDTO dto = new TemaDTO();
        dto.setId(tema.getId());
        dto.setTitulo(tema.getTitulo());
        dto.setDescripcion(tema.getDescripcion());
        dto.setContenido(tema.getContenido());
        dto.setOrden(tema.getOrden());
        dto.setCompletado(tema.getCompletado());
        dto.setActivo(tema.getActivo());
        dto.setFechaCreacion(tema.getFechaCreacion());
        dto.setFechaActualizacion(tema.getFechaActualizacion());
        return dto;
    }
}