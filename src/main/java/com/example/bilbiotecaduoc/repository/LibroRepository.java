package com.example.bilbiotecaduoc.repository;

import com.example.bilbiotecaduoc.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    // cu1: consultar datos de un libro por id
    Optional<Libro> findById(Long id);
    
    // cu1: consultar datos de un libro por isbn
    Optional<Libro> findByIsbn(String isbn);
    
    // cu3: ver la informacion de todos los libros
    List<Libro> findAll();
    
    // busquedas adicionales por criterios
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    
    List<Libro> findByAuthorNombreContainingIgnoreCaseOrAuthorApellidoContainingIgnoreCase(
        String nombre, String apellido);
    
    List<Libro> findByAnioPublicacion(Integer anioPublicacion);
    
    List<Libro> findByAnioPublicacionBetween(Integer anioInicio, Integer anioFin);
    
    List<Libro> findByPrecioBetween(Double precioMin, Double precioMax);
    
    List<Libro> findByStockDisponibleGreaterThan(Integer stock);
    
    // busqueda por multiples criterios (sin editorial)
    @Query("SELECT l FROM Libro l WHERE " +
           "(:titulo IS NULL OR l.titulo LIKE %:titulo%) AND " +
           "(:authorId IS NULL OR l.author.id = :authorId) AND " +
           "(:anioMin IS NULL OR l.anioPublicacion >= :anioMin) AND " +
           "(:anioMax IS NULL OR l.anioPublicacion <= :anioMax) AND " +
           "(:precioMin IS NULL OR l.precio >= :precioMin) AND " +
           "(:precioMax IS NULL OR l.precio <= :precioMax)")
    List<Libro> findByCriterios(
        @Param("titulo") String titulo,
        @Param("authorId") Long authorId,
        @Param("anioMin") Integer anioMin,
        @Param("anioMax") Integer anioMax,
        @Param("precioMin") Double precioMin,
        @Param("precioMax") Double precioMax
    );
    
    // verificar si existe un libro con el mismo isbn (regla de negocio)
    boolean existsByIsbn(String isbn);
}



