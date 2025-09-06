package com.example.holamundocursor.controller;

import com.example.holamundocursor.model.Fase;
import com.example.holamundocursor.model.Tema;
import com.example.holamundocursor.service.FaseService;
import com.example.holamundocursor.service.TemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ArquitecturaController {
    
    private final FaseService faseService;
    private final TemaService temaService;
    
    // Página principal - redirigir automáticamente a ver-todo-working
    @GetMapping
    public String index() {
        return "redirect:/ver-todo-working";
    }
    
    // Ver todo - vista completa de todas las fases y temas
    @GetMapping("/ver-todo")
    public String verTodo(Model model) {
        List<Fase> fases = faseService.obtenerTodasLasFases();
        List<Tema> todosLosTemas = temaService.obtenerTodosLosTemas();
        
        // Calcular estadísticas
        long totalTemas = todosLosTemas.size();
        long temasCompletados = todosLosTemas.stream().filter(Tema::getCompletado).count();
        double progresoGeneral = totalTemas > 0 ? (double) temasCompletados / totalTemas * 100 : 0;
        
        model.addAttribute("fases", fases);
        model.addAttribute("totalTemas", totalTemas);
        model.addAttribute("temasCompletados", temasCompletados);
        model.addAttribute("progresoGeneral", Math.round(progresoGeneral * 10.0) / 10.0);
        
        return "ver-todo-working";
    }
    
    // Endpoint de debug para verificar datos
    @GetMapping("/debug-data")
    @ResponseBody
    public String debugData() {
        List<Fase> fases = faseService.obtenerTodasLasFases();
        List<Tema> todosLosTemas = temaService.obtenerTodosLosTemas();
        
        StringBuilder sb = new StringBuilder();
        sb.append("DEBUG DATA:\n");
        sb.append("Total fases: ").append(fases.size()).append("\n");
        sb.append("Total temas: ").append(todosLosTemas.size()).append("\n\n");
        
        for (Fase fase : fases) {
            sb.append("Fase: ").append(fase.getNombre()).append(" (ID: ").append(fase.getId()).append(")\n");
            if (fase.getTemas() != null) {
                sb.append("  Temas: ").append(fase.getTemas().size()).append("\n");
                for (Tema tema : fase.getTemas()) {
                    sb.append("    - ").append(tema.getTitulo()).append(" (ID: ").append(tema.getId()).append(")\n");
                }
            } else {
                sb.append("  Temas: null\n");
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    // Test simple - sin layout
    @GetMapping("/ver-todo-simple")
    public String verTodoSimple(Model model) {
        List<Fase> fases = faseService.obtenerTodasLasFases();
        List<Tema> todosLosTemas = temaService.obtenerTodosLosTemas();
        
        // Calcular estadísticas
        long totalTemas = todosLosTemas.size();
        long temasCompletados = todosLosTemas.stream().filter(Tema::getCompletado).count();
        double progresoGeneral = totalTemas > 0 ? (double) temasCompletados / totalTemas * 100 : 0;
        
        model.addAttribute("fases", fases);
        model.addAttribute("totalTemas", totalTemas);
        model.addAttribute("temasCompletados", temasCompletados);
        model.addAttribute("progresoGeneral", Math.round(progresoGeneral * 10.0) / 10.0);
        
        return "ver-todo-simple";
    }
    
    // Test template arreglado
    @GetMapping("/ver-todo-fixed")
    public String verTodoFixed(Model model) {
        List<Fase> fases = faseService.obtenerTodasLasFases();
        List<Tema> todosLosTemas = temaService.obtenerTodosLosTemas();
        
        // Calcular estadísticas
        long totalTemas = todosLosTemas.size();
        long temasCompletados = todosLosTemas.stream().filter(Tema::getCompletado).count();
        double progresoGeneral = totalTemas > 0 ? (double) temasCompletados / totalTemas * 100 : 0;
        
        model.addAttribute("fases", fases);
        model.addAttribute("totalTemas", totalTemas);
        model.addAttribute("temasCompletados", temasCompletados);
        model.addAttribute("progresoGeneral", Math.round(progresoGeneral * 10.0) / 10.0);
        
        return "ver-todo-fixed";
    }
    
    // Template que funciona - sin fragmentos complejos
    @GetMapping("/ver-todo-working")
    public String verTodoWorking(Model model) {
        List<Fase> fases = faseService.obtenerTodasLasFases();
        List<Tema> todosLosTemas = temaService.obtenerTodosLosTemas();
        
        // Calcular estadísticas
        long totalTemas = todosLosTemas.size();
        long temasCompletados = todosLosTemas.stream().filter(Tema::getCompletado).count();
        double progresoGeneral = totalTemas > 0 ? (double) temasCompletados / totalTemas * 100 : 0;
        
        model.addAttribute("fases", fases);
        model.addAttribute("totalTemas", totalTemas);
        model.addAttribute("temasCompletados", temasCompletados);
        model.addAttribute("progresoGeneral", Math.round(progresoGeneral * 10.0) / 10.0);
        
        return "ver-todo-working";
    }
    
    // Búsqueda de fases
    @GetMapping("/buscar-fases")
    public String buscarFases(@RequestParam(required = false) String nombre, Model model) {
        List<Fase> fases;
        if (nombre != null && !nombre.trim().isEmpty()) {
            fases = faseService.buscarFasesPorNombre(nombre);
            model.addAttribute("terminoBusqueda", nombre);
        } else {
            fases = faseService.obtenerTodasLasFases();
        }
        model.addAttribute("fases", fases);
        return "buscar-fases";
    }
    
    // Ver temas de una fase específica
    @GetMapping("/fase/{id}/temas")
    public String verTemasDeFase(@PathVariable Long id, Model model) {
        Optional<Fase> faseOpt = faseService.obtenerFasePorId(id);
        if (faseOpt.isEmpty()) {
            return "redirect:/?error=fase_no_encontrada";
        }
        
        Fase fase = faseOpt.get();
        List<Tema> temas = temaService.obtenerTemasPorFase(id);
        
        model.addAttribute("fase", fase);
        model.addAttribute("temas", temas);
        return "temas-fase";
    }
    
    // Búsqueda de temas
    @GetMapping("/buscar-temas")
    public String buscarTemas(@RequestParam(required = false) String titulo, Model model) {
        List<Tema> temas;
        if (titulo != null && !titulo.trim().isEmpty()) {
            temas = temaService.buscarTemasPorTitulo(titulo);
            model.addAttribute("terminoBusqueda", titulo);
        } else {
            temas = temaService.obtenerTodosLosTemas();
        }
        model.addAttribute("temas", temas);
        return "buscar-temas";
    }
    
    // Búsqueda de temas por fase
    @GetMapping("/fase/{faseId}/buscar-temas")
    public String buscarTemasPorFase(@PathVariable Long faseId, 
                                   @RequestParam(required = false) String titulo, 
                                   Model model) {
        Optional<Fase> faseOpt = faseService.obtenerFasePorId(faseId);
        if (faseOpt.isEmpty()) {
            return "redirect:/?error=fase_no_encontrada";
        }
        
        Fase fase = faseOpt.get();
        List<Tema> temas;
        
        if (titulo != null && !titulo.trim().isEmpty()) {
            temas = temaService.buscarTemasPorFaseYTitulo(faseId, titulo);
            model.addAttribute("terminoBusqueda", titulo);
        } else {
            temas = temaService.obtenerTemasPorFase(faseId);
        }
        
        model.addAttribute("fase", fase);
        model.addAttribute("temas", temas);
        return "buscar-temas-fase";
    }
    
    // Formulario para crear nueva fase
    @GetMapping("/fase/nueva")
    public String mostrarFormularioNuevaFase(Model model) {
        model.addAttribute("fase", new Fase());
        model.addAttribute("siguienteOrden", faseService.obtenerSiguienteOrden());
        return "form-fase";
    }
    
    // Procesar creación de nueva fase
    @PostMapping("/fase/crear")
    public String crearFase(@ModelAttribute Fase fase, RedirectAttributes redirectAttributes) {
        try {
            faseService.crearFase(fase);
            redirectAttributes.addFlashAttribute("mensaje", "Fase creada exitosamente");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/fase/nueva";
        }
    }
    
    // Formulario para editar fase
    @GetMapping("/fase/{id}/editar")
    public String mostrarFormularioEditarFase(@PathVariable Long id, Model model) {
        Optional<Fase> faseOpt = faseService.obtenerFasePorId(id);
        if (faseOpt.isEmpty()) {
            return "redirect:/?error=fase_no_encontrada";
        }
        
        model.addAttribute("fase", faseOpt.get());
        return "form-fase";
    }
    
    // Procesar actualización de fase
    @PostMapping("/fase/{id}/actualizar")
    public String actualizarFase(@PathVariable Long id, @ModelAttribute Fase fase, RedirectAttributes redirectAttributes) {
        try {
            faseService.actualizarFase(id, fase);
            redirectAttributes.addFlashAttribute("mensaje", "Fase actualizada exitosamente");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/fase/" + id + "/editar";
        }
    }
    
    // Eliminar fase
    @PostMapping("/fase/{id}/eliminar")
    public String eliminarFase(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            faseService.eliminarFase(id);
            redirectAttributes.addFlashAttribute("mensaje", "Fase eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }
    
    // Formulario para crear nuevo tema
    @GetMapping("/tema/nuevo")
    public String mostrarFormularioNuevoTema(Model model) {
        List<Fase> fases = faseService.obtenerTodasLasFases();
        model.addAttribute("tema", new Tema());
        model.addAttribute("fases", fases);
        model.addAttribute("siguienteOrden", 1); // Orden por defecto
        return "form-tema";
    }
    
    // Procesar creación de nuevo tema
    @PostMapping("/tema/crear")
    public String crearTema(@ModelAttribute Tema tema, RedirectAttributes redirectAttributes) {
        try {
            Tema temaCreado = temaService.crearTema(tema);
            redirectAttributes.addFlashAttribute("mensaje", "Tema creado exitosamente");
            return "redirect:/fase/" + temaCreado.getFase().getId() + "/temas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/tema/nuevo";
        }
    }
    
    // Formulario para editar tema
    @GetMapping("/tema/{id}/editar")
    public String mostrarFormularioEditarTema(@PathVariable Long id, Model model) {
        Optional<Tema> temaOpt = temaService.obtenerTemaPorId(id);
        if (temaOpt.isEmpty()) {
            return "redirect:/?error=tema_no_encontrado";
        }
        
        List<Fase> fases = faseService.obtenerTodasLasFases();
        model.addAttribute("tema", temaOpt.get());
        model.addAttribute("fases", fases);
        return "form-tema";
    }
    
    // Procesar actualización de tema
    @PostMapping("/tema/{id}/actualizar")
    public String actualizarTema(@PathVariable Long id, @ModelAttribute Tema tema, RedirectAttributes redirectAttributes) {
        try {
            Tema temaActualizado = temaService.actualizarTema(id, tema);
            redirectAttributes.addFlashAttribute("mensaje", "Tema actualizado exitosamente");
            return "redirect:/fase/" + temaActualizado.getFase().getId() + "/temas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/tema/" + id + "/editar";
        }
    }
    
    // Eliminar tema
    @PostMapping("/tema/{id}/eliminar")
    public String eliminarTema(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Tema> temaOpt = temaService.obtenerTemaPorId(id);
            if (temaOpt.isPresent()) {
                Long faseId = temaOpt.get().getFase().getId();
                temaService.eliminarTema(id);
                redirectAttributes.addFlashAttribute("mensaje", "Tema eliminado exitosamente");
                return "redirect:/fase/" + faseId + "/temas";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }
    
    // Marcar tema como completado
    @PostMapping("/tema/{id}/completar")
    public String marcarTemaCompletado(@PathVariable Long id, @RequestParam boolean completado, RedirectAttributes redirectAttributes) {
        try {
            Tema temaActualizado = temaService.marcarTemaCompletado(id, completado);
            redirectAttributes.addFlashAttribute("mensaje", completado ? "Tema marcado como completado" : "Tema marcado como no completado");
            return "redirect:/fase/" + temaActualizado.getFase().getId() + "/temas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }
    }
}
