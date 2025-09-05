package com.example.holamundocursor.dto;

import com.example.holamundocursor.model.Tema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer orden;
    private Boolean activa;
    private List<TemaDTO> temas;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}

