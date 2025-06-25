package com.example.bilbiotecaduoc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "libros")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Libro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;
    
    @Column(nullable = false, unique = true, length = 20)
    @NotBlank(message = "El ISBN es obligatorio")
    @Pattern(regexp = "^(?:[0-9]{10}|[0-9]{13})$", message = "El ISBN debe tener 10 o 13 dígitos")
    private String isbn;
    
    @Column(nullable = false, length = 200)
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    
    @Column(length = 500)
    private String descripcion;
    
    @Column(name = "anio_publicacion")
    @NotNull(message = "El año de publicación es obligatorio")
    @Min(value = 1900, message = "El año debe ser mayor a 1900")
    private Integer anioPublicacion;
    
    @Column(length = 50)
    private String idioma;
    
    @Column(name = "numero_paginas")
    private Integer numeroPaginas;
    
    @Column(precision = 10)
    private Double precio;
    
    @Column(name = "stock_disponible")
    private Integer stockDisponible;
    
    // relacion con author (muchos a uno)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull(message = "El autor es obligatorio")
    private Author author;
    
    // constructor para crear libro con datos basicos
    public Libro(String isbn, String titulo, Integer anioPublicacion, Author author) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        this.author = author;
        this.fechaIngreso = LocalDate.now();
    }
}
