package com.example.bilbiotecaduoc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "authors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 100)
    private String apellido;
    
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    @Column(length = 500)
    private String biografia;
    
    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<Libro> libros;
    
    // constructor para crear autor sin libros
    public Author(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }
} 