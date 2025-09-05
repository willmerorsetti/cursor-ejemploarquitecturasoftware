package com.example.holamundocursor.service;

import com.example.holamundocursor.model.Fase;
import com.example.holamundocursor.repository.FaseRepository;
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
class FaseServiceTest {

    @Mock
    private FaseRepository faseRepository;

    @InjectMocks
    private FaseService faseService;

    private Fase fase;

    @BeforeEach
    void setUp() {
        fase = new Fase();
        fase.setId(1L);
        fase.setNombre("Fundamentos de Arquitectura");
        fase.setDescripcion("Conceptos básicos y principios de diseño de software.");
        fase.setOrden(1);
        fase.setActiva(true);
    }

    @Test
    void testCrearFase() {
        // Given
        when(faseRepository.save(any(Fase.class))).thenReturn(fase);

        // When
        Fase resultado = faseService.crearFase(fase);

        // Then
        assertNotNull(resultado);
        assertEquals("Fundamentos de Arquitectura", resultado.getNombre());
        assertEquals("Conceptos básicos y principios de diseño de software.", resultado.getDescripcion());
        assertEquals(1, resultado.getOrden());
        assertTrue(resultado.getActiva());
        
        verify(faseRepository, times(1)).save(fase);
    }

    @Test
    void testBuscarFasesPorNombre() {
        // Given
        String nombreBusqueda = "Fundamentos";
        List<Fase> fasesEsperadas = Arrays.asList(fase);
        when(faseRepository.findByNombreContainingIgnoreCaseAndActivaTrue(nombreBusqueda))
                .thenReturn(fasesEsperadas);

        // When
        List<Fase> resultado = faseService.buscarFasesPorNombre(nombreBusqueda);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Fundamentos de Arquitectura", resultado.get(0).getNombre());
        
        verify(faseRepository, times(1)).findByNombreContainingIgnoreCaseAndActivaTrue(nombreBusqueda);
    }

    @Test
    void testBuscarFases() {
        // Given
        String nombreBusqueda = "Arquitectura";
        List<Fase> fasesEsperadas = Arrays.asList(fase);
        when(faseRepository.findByNombreContainingIgnoreCaseAndActivaTrue(nombreBusqueda))
                .thenReturn(fasesEsperadas);

        // When
        List<Fase> resultado = faseService.buscarFases(nombreBusqueda);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Fundamentos de Arquitectura", resultado.get(0).getNombre());
        
        verify(faseRepository, times(1)).findByNombreContainingIgnoreCaseAndActivaTrue(nombreBusqueda);
    }

    @Test
    void testObtenerTodasLasFases() {
        // Given
        List<Fase> fasesEsperadas = Arrays.asList(fase);
        when(faseRepository.findByActivaTrueOrderByOrdenAsc()).thenReturn(fasesEsperadas);

        // When
        List<Fase> resultado = faseService.obtenerTodasLasFases();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Fundamentos de Arquitectura", resultado.get(0).getNombre());
        
        verify(faseRepository, times(1)).findByActivaTrueOrderByOrdenAsc();
    }

    @Test
    void testObtenerFasePorId() {
        // Given
        Long id = 1L;
        when(faseRepository.findById(id)).thenReturn(Optional.of(fase));

        // When
        Optional<Fase> resultado = faseService.obtenerFasePorId(id);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Fundamentos de Arquitectura", resultado.get().getNombre());
        
        verify(faseRepository, times(1)).findById(id);
    }

    @Test
    void testActualizarFase() {
        // Given
        Long id = 1L;
        when(faseRepository.findById(id)).thenReturn(Optional.of(fase));
        when(faseRepository.save(any(Fase.class))).thenReturn(fase);

        // When
        Fase resultado = faseService.actualizarFase(id, fase);

        // Then
        assertNotNull(resultado);
        assertEquals("Fundamentos de Arquitectura", resultado.getNombre());
        
        verify(faseRepository, times(1)).findById(id);
        verify(faseRepository, times(1)).save(fase);
    }

    @Test
    void testEliminarFase() {
        // Given
        Long id = 1L;
        when(faseRepository.findById(id)).thenReturn(Optional.of(fase));
        when(faseRepository.save(any(Fase.class))).thenReturn(fase);

        // When
        faseService.eliminarFase(id);

        // Then
        assertFalse(fase.getActiva());
        
        verify(faseRepository, times(1)).findById(id);
        verify(faseRepository, times(1)).save(fase);
    }
}
