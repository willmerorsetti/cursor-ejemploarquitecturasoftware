package com.example.holamundocursor.service;

import com.example.holamundocursor.model.Fase;
import com.example.holamundocursor.model.Tema;
import com.example.holamundocursor.repository.TemaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TemaServiceTest {

    @Mock
    private TemaRepository temaRepository;

    @Mock
    private FaseService faseService;

    @InjectMocks
    private TemaService temaService;

    private Tema tema;
    private Fase fase;

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
    void testCrearTema() {
        // Given
        when(faseService.obtenerFasePorId(fase.getId())).thenReturn(Optional.of(fase));
        when(temaRepository.save(any(Tema.class))).thenReturn(tema);

        // When
        Tema resultado = temaService.crearTema(tema);

        // Then
        assertNotNull(resultado);
        assertEquals("Introducción a la Arquitectura", resultado.getTitulo());
        assertEquals("Definiciones y la importancia de la arquitectura de software.", resultado.getDescripcion());
        assertEquals("Contenido detallado sobre la introducción...", resultado.getContenido());
        assertEquals(1, resultado.getOrden());
        assertFalse(resultado.getCompletado());
        assertTrue(resultado.getActivo());
        assertEquals(fase, resultado.getFase());
        
        verify(faseService, times(1)).obtenerFasePorId(fase.getId());
        verify(temaRepository, times(1)).save(tema);
    }

    @Test
    void testBuscarTemasPorTitulo() {
        // Given
        String tituloBusqueda = "Introducción";
        List<Tema> temasEsperados = Arrays.asList(tema);
        when(temaRepository.findByTituloContainingIgnoreCaseAndActivoTrue(tituloBusqueda))
                .thenReturn(temasEsperados);

        // When
        List<Tema> resultado = temaService.buscarTemasPorTitulo(tituloBusqueda);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Introducción a la Arquitectura", resultado.get(0).getTitulo());
        
        verify(temaRepository, times(1)).findByTituloContainingIgnoreCaseAndActivoTrue(tituloBusqueda);
    }

    @Test
    void testBuscarTemas() {
        // Given
        String tituloBusqueda = "Arquitectura";
        List<Tema> temasEsperados = Arrays.asList(tema);
        when(temaRepository.findByTituloContainingIgnoreCaseAndActivoTrue(tituloBusqueda))
                .thenReturn(temasEsperados);

        // When
        List<Tema> resultado = temaService.buscarTemas(tituloBusqueda);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Introducción a la Arquitectura", resultado.get(0).getTitulo());
        
        verify(temaRepository, times(1)).findByTituloContainingIgnoreCaseAndActivoTrue(tituloBusqueda);
    }

    @Test
    void testObtenerTemasPorFase() {
        // Given
        Long faseId = 1L;
        List<Tema> temasEsperados = Arrays.asList(tema);
        when(temaRepository.findByFaseIdAndActivoTrueOrderByOrdenAsc(faseId))
                .thenReturn(temasEsperados);

        // When
        List<Tema> resultado = temaService.obtenerTemasPorFase(faseId);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Introducción a la Arquitectura", resultado.get(0).getTitulo());
        assertEquals(fase, resultado.get(0).getFase());
        
        verify(temaRepository, times(1)).findByFaseIdAndActivoTrueOrderByOrdenAsc(faseId);
    }

    @Test
    void testBuscarTemasPorFase() {
        // Given
        Long faseId = 1L;
        List<Tema> temasEsperados = Arrays.asList(tema);
        when(temaRepository.findByFaseIdAndActivoTrueOrderByOrdenAsc(faseId))
                .thenReturn(temasEsperados);

        // When
        List<Tema> resultado = temaService.buscarTemasPorFase(faseId);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Introducción a la Arquitectura", resultado.get(0).getTitulo());
        assertEquals(fase, resultado.get(0).getFase());
        
        verify(temaRepository, times(1)).findByFaseIdAndActivoTrueOrderByOrdenAsc(faseId);
    }

    @Test
    void testObtenerTodosLosTemas() {
        // Given
        List<Tema> temasEsperados = Arrays.asList(tema);
        when(temaRepository.findByActivoTrueOrderByOrdenAsc())
                .thenReturn(temasEsperados);

        // When
        List<Tema> resultado = temaService.obtenerTodosLosTemas();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Introducción a la Arquitectura", resultado.get(0).getTitulo());
        
        verify(temaRepository, times(1)).findByActivoTrueOrderByOrdenAsc();
    }

    @Test
    void testObtenerTemaPorId() {
        // Given
        Long id = 1L;
        when(temaRepository.findById(id)).thenReturn(Optional.of(tema));

        // When
        Optional<Tema> resultado = temaService.obtenerTemaPorId(id);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Introducción a la Arquitectura", resultado.get().getTitulo());
        
        verify(temaRepository, times(1)).findById(id);
    }

    @Test
    void testActualizarTema() {
        // Given
        Long id = 1L;
        when(temaRepository.findById(id)).thenReturn(Optional.of(tema));
        when(temaRepository.save(any(Tema.class))).thenReturn(tema);

        // When
        Tema resultado = temaService.actualizarTema(id, tema);

        // Then
        assertNotNull(resultado);
        assertEquals("Introducción a la Arquitectura", resultado.getTitulo());
        
        verify(temaRepository, times(1)).findById(id);
        verify(temaRepository, times(1)).save(tema);
    }

    @Test
    void testMarcarTemaCompletado() {
        // Given
        Long id = 1L;
        when(temaRepository.findById(id)).thenReturn(Optional.of(tema));
        when(temaRepository.save(any(Tema.class))).thenReturn(tema);

        // When
        Tema resultado = temaService.marcarTemaCompletado(id, true);

        // Then
        assertNotNull(resultado);
        assertTrue(tema.getCompletado());
        
        verify(temaRepository, times(1)).findById(id);
        verify(temaRepository, times(1)).save(tema);
    }

    @Test
    void testEliminarTema() {
        // Given
        Long id = 1L;
        when(temaRepository.findById(id)).thenReturn(Optional.of(tema));
        when(temaRepository.save(any(Tema.class))).thenReturn(tema);

        // When
        temaService.eliminarTema(id);

        // Then
        assertFalse(tema.getActivo());
        
        verify(temaRepository, times(1)).findById(id);
        verify(temaRepository, times(1)).save(tema);
    }
}
