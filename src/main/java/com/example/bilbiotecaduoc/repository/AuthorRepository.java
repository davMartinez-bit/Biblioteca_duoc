package com.example.bilbiotecaduoc.repository;

import com.example.bilbiotecaduoc.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    
    // Buscar autor por nombre y apellido
    Optional<Author> findByNombreAndApellido(String nombre, String apellido);
    
    // Buscar autores por nombre (búsqueda parcial)
    List<Author> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar autores por apellido (búsqueda parcial)
    List<Author> findByApellidoContainingIgnoreCase(String apellido);
    
    // Verificar si un autor tiene libros
    @Query("SELECT COUNT(l) > 0 FROM Libro l WHERE l.author.id = :authorId")
    boolean hasBooks(@Param("authorId") Long authorId);
    
    // Contar libros de un autor
    @Query("SELECT COUNT(l) FROM Libro l WHERE l.author.id = :authorId")
    long countBooksByAuthor(@Param("authorId") Long authorId);
} 