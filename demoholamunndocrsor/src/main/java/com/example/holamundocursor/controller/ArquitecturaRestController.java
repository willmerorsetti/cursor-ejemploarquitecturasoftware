package com.example.holamundocursor.controller;

import com.example.holamundocursor.model.Fase;
import com.example.holamundocursor.model.Tema;
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
    public List<Map<String, Object>> obtenerTodasLasFases() {
        List<Fase> fases = faseService.obtenerTodasLasFases();
        
        // Crear fases sin relaciones para evitar StackOverflowError
        return fases.stream()
                .map(fase -> {
                    Map<String, Object> faseMap = new HashMap<>();
                    faseMap.put("id", fase.getId());
                    faseMap.put("nombre", fase.getNombre());
                    faseMap.put("descripcion", fase.getDescripcion());
                    faseMap.put("orden", fase.getOrden());
                    faseMap.put("activa", fase.getActiva());
                    faseMap.put("fechaCreacion", fase.getFechaCreacion());
                    faseMap.put("fechaActualizacion", fase.getFechaActualizacion());
                    return faseMap;
                })
                .collect(Collectors.toList());
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
        
        // Crear fases sin relaciones para evitar StackOverflowError
        List<Map<String, Object>> fasesSinRelaciones = fases.stream()
                .map(fase -> {
                    Map<String, Object> faseMap = new HashMap<>();
                    faseMap.put("id", fase.getId());
                    faseMap.put("nombre", fase.getNombre());
                    faseMap.put("descripcion", fase.getDescripcion());
                    faseMap.put("orden", fase.getOrden());
                    faseMap.put("activa", fase.getActiva());
                    faseMap.put("fechaCreacion", fase.getFechaCreacion());
                    faseMap.put("fechaActualizacion", fase.getFechaActualizacion());
                    return faseMap;
                })
                .collect(Collectors.toList());
        
        Map<String, Object> estadisticas = new HashMap<>();
        estadisticas.put("totalFases", fases.size());
        estadisticas.put("totalTemas", totalTemas);
        estadisticas.put("temasCompletados", temasCompletados);
        estadisticas.put("progresoGeneral", Math.round(progresoGeneral * 10.0) / 10.0);
        estadisticas.put("fases", fasesSinRelaciones);
        
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
        return faseService.obtenerFasePorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Fase no encontrada con ID: " + id));
    }

    // Obtener temas de una fase específica
    @GetMapping("/fases/{id}/temas")
    public List<Tema> obtenerTemasPorFase(@PathVariable Long id) {
        return temaService.obtenerTemasPorFase(id);
    }
    
}