package com.example.holamundocursor.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemaDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String contenido;
    private Integer orden;
    private Boolean completado;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}

