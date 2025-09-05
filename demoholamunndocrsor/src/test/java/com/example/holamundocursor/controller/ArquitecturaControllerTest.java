package com.example.holamundocursor.controller;

import com.example.holamundocursor.model.Fase;
import com.example.holamundocursor.model.Tema;
import com.example.holamundocursor.service.FaseService;
import com.example.holamundocursor.service.TemaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArquitecturaControllerTest {

    @Mock
    private FaseService faseService;

    @Mock
    private TemaService temaService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private ArquitecturaController controller;

    private Fase fase;
    private Tema tema;

    @BeforeEach
    void setUp() {
        fase = new Fase();
        fase.setId(1L);
        fase.setNombre("Fundamentos de Arquitectura");
        fase.setDescripcion("Conceptos básicos y principios de diseño de software.");
        fase.setOrden(1);
        fase.setActiva(true);

        tema = new Tema();
        tema.setId(1L);
        tema.setTitulo("Introducción a la Arquitectura");
        tema.setDescripcion("Definiciones y la importancia de la arquitectura de software.");
        tema.setContenido("Contenido detallado sobre la introducción...");
        tema.setOrden(1);
        tema.setCompletado(false);
        tema.setActivo(true);
        tema.setFase(fase);
    }

    @Test
    void testMostrarFormularioNuevaFase() {
        // When
        String resultado = controller.mostrarFormularioNuevaFase(model);

        // Then
        assertEquals("form-fase", resultado);
        verify(model).addAttribute(eq("fase"), any(Fase.class));
    }

    @Test
    void testCrearFase() {
        // Given
        when(faseService.crearFase(any(Fase.class))).thenReturn(fase);

        // When
        String resultado = controller.crearFase(fase, redirectAttributes);

        // Then
        assertEquals("redirect:/", resultado);
        verify(faseService, times(1)).crearFase(fase);
    }

    @Test
    void testMostrarFormularioNuevoTema() {
        // Given
        List<Fase> fases = Arrays.asList(fase);
        when(faseService.obtenerTodasLasFases()).thenReturn(fases);

        // When
        String resultado = controller.mostrarFormularioNuevoTema(model);

        // Then
        assertEquals("form-tema", resultado);
        verify(model).addAttribute("fases", fases);
        verify(model).addAttribute(eq("tema"), any(Tema.class));
        verify(model).addAttribute("siguienteOrden", 1);
    }

    @Test
    void testCrearTema() {
        // Given
        when(temaService.crearTema(any(Tema.class))).thenReturn(tema);

        // When
        String resultado = controller.crearTema(tema, redirectAttributes);

        // Then
        assertEquals("redirect:/fase/1/temas", resultado);
        verify(temaService, times(1)).crearTema(tema);
    }

    @Test
    void testBuscarFases() {
        // Given
        String nombre = "Fundamentos";
        List<Fase> fasesEsperadas = Arrays.asList(fase);
        when(faseService.buscarFasesPorNombre(nombre)).thenReturn(fasesEsperadas);

        // When
        String resultado = controller.buscarFases(nombre, model);

        // Then
        assertEquals("buscar-fases", resultado);
        verify(model).addAttribute("fases", fasesEsperadas);
        verify(model).addAttribute("terminoBusqueda", nombre);
    }

    @Test
    void testBuscarTemas() {
        // Given
        String titulo = "Introducción";
        List<Tema> temasEsperados = Arrays.asList(tema);
        when(temaService.buscarTemasPorTitulo(titulo)).thenReturn(temasEsperados);

        // When
        String resultado = controller.buscarTemas(titulo, model);

        // Then
        assertEquals("buscar-temas", resultado);
        verify(model).addAttribute("temas", temasEsperados);
        verify(model).addAttribute("terminoBusqueda", titulo);
    }

    @Test
    void testBuscarTemasPorFase() {
        // Given
        Long faseId = 1L;
        String titulo = "Introducción";
        List<Tema> temasEsperados = Arrays.asList(tema);
        when(faseService.obtenerFasePorId(faseId)).thenReturn(Optional.of(fase));
        when(temaService.buscarTemasPorFaseYTitulo(faseId, titulo)).thenReturn(temasEsperados);

        // When
        String resultado = controller.buscarTemasPorFase(faseId, titulo, model);

        // Then
        assertEquals("buscar-temas-fase", resultado);
        verify(model).addAttribute("fase", fase);
        verify(model).addAttribute("temas", temasEsperados);
    }

    @Test
    void testBuscarTemasPorFase_FaseNoEncontrada() {
        // Given
        Long faseId = 999L;
        String titulo = "Introducción";
        when(faseService.obtenerFasePorId(faseId)).thenReturn(Optional.empty());

        // When
        String resultado = controller.buscarTemasPorFase(faseId, titulo, model);

        // Then
        assertEquals("redirect:/?error=fase_no_encontrada", resultado);
        verify(temaService, never()).buscarTemasPorFaseYTitulo(anyLong(), anyString());
    }

    @Test
    void testMostrarFormularioEditarFase() {
        // Given
        Long id = 1L;
        when(faseService.obtenerFasePorId(id)).thenReturn(Optional.of(fase));

        // When
        String resultado = controller.mostrarFormularioEditarFase(id, model);

        // Then
        assertEquals("form-fase", resultado);
        verify(model).addAttribute("fase", fase);
    }

    @Test
    void testMostrarFormularioEditarFase_FaseNoEncontrada() {
        // Given
        Long id = 999L;
        when(faseService.obtenerFasePorId(id)).thenReturn(Optional.empty());

        // When
        String resultado = controller.mostrarFormularioEditarFase(id, model);

        // Then
        assertEquals("redirect:/?error=fase_no_encontrada", resultado);
    }

    @Test
    void testActualizarFase() {
        // Given
        Long id = 1L;
        when(faseService.actualizarFase(eq(id), any(Fase.class))).thenReturn(fase);

        // When
        String resultado = controller.actualizarFase(id, fase, redirectAttributes);

        // Then
        assertEquals("redirect:/", resultado);
        verify(faseService, times(1)).actualizarFase(id, fase);
    }

    @Test
    void testMostrarFormularioEditarTema() {
        // Given
        Long id = 1L;
        List<Fase> fases = Arrays.asList(fase);
        when(temaService.obtenerTemaPorId(id)).thenReturn(Optional.of(tema));
        when(faseService.obtenerTodasLasFases()).thenReturn(fases);

        // When
        String resultado = controller.mostrarFormularioEditarTema(id, model);

        // Then
        assertEquals("form-tema", resultado);
        verify(model).addAttribute("tema", tema);
        verify(model).addAttribute("fases", fases);
    }

    @Test
    void testMostrarFormularioEditarTema_TemaNoEncontrado() {
        // Given
        Long id = 999L;
        when(temaService.obtenerTemaPorId(id)).thenReturn(Optional.empty());

        // When
        String resultado = controller.mostrarFormularioEditarTema(id, model);

        // Then
        assertEquals("redirect:/?error=tema_no_encontrado", resultado);
    }

    @Test
    void testActualizarTema() {
        // Given
        Long id = 1L;
        when(temaService.actualizarTema(eq(id), any(Tema.class))).thenReturn(tema);

        // When
        String resultado = controller.actualizarTema(id, tema, redirectAttributes);

        // Then
        assertEquals("redirect:/fase/1/temas", resultado);
        verify(temaService, times(1)).actualizarTema(id, tema);
    }

    @Test
    void testMarcarTemaCompletado() {
        // Given
        Long id = 1L;
        boolean completado = true;
        when(temaService.marcarTemaCompletado(id, completado)).thenReturn(tema);

        // When
        String resultado = controller.marcarTemaCompletado(id, completado, redirectAttributes);

        // Then
        assertEquals("redirect:/fase/1/temas", resultado);
        verify(temaService, times(1)).marcarTemaCompletado(id, completado);
    }

    @Test
    void testEliminarFase() {
        // Given
        Long id = 1L;

        // When
        String resultado = controller.eliminarFase(id, redirectAttributes);

        // Then
        assertEquals("redirect:/", resultado);
        verify(faseService, times(1)).eliminarFase(id);
    }

    @Test
    void testEliminarTema() {
        // Given
        Long id = 1L;
        when(temaService.obtenerTemaPorId(id)).thenReturn(Optional.of(tema));

        // When
        String resultado = controller.eliminarTema(id, redirectAttributes);

        // Then
        assertEquals("redirect:/fase/1/temas", resultado);
        verify(temaService, times(1)).obtenerTemaPorId(id);
        verify(temaService, times(1)).eliminarTema(id);
    }
}
