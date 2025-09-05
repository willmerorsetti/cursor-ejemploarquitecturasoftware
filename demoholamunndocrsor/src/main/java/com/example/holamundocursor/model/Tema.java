package com.example.holamundocursor.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

@Entity
@Table(name = "temas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tema {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "contenido", columnDefinition = "LONGTEXT")
    private String contenido;
    
    @Column(name = "orden", nullable = false)
    private Integer orden;
    
    @Column(name = "completado", nullable = false)
    private Boolean completado = false;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fase_id", nullable = false)
    @JsonIgnore
    private Fase fase;
    
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Constructor para crear un tema
    public Tema(String titulo, String descripcion, String contenido, Integer orden, Fase fase) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.contenido = contenido;
        this.orden = orden;
        this.fase = fase;
        this.completado = false;
        this.activo = true;
    }
}
