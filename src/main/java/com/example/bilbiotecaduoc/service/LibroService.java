package com.example.bilbiotecaduoc.service;

import com.example.bilbiotecaduoc.model.Libro;
import com.example.bilbiotecaduoc.model.Author;
import com.example.bilbiotecaduoc.repository.LibroRepository;
import com.example.bilbiotecaduoc.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LibroService {
    
    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private AuthorRepository authorRepository;
    
    // cu3: ver la informacion de todos los libros
    public List<Libro> getAllLibros() {
        return libroRepository.findAll();
    }
    
    // cu1: consultar datos de un libro por id
    public Optional<Libro> getLibroById(Long id) {
        return libroRepository.findById(id);
    }
    
    // cu1: consultar datos de un libro por isbn
    public Optional<Libro> getLibroByIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn);
    }
    
    // cu2: crear un libro
    public Libro createLibro(Libro libro) {
        // regla de negocio: no existen dos libros con el mismo isbn
        if (libroRepository.existsByIsbn(libro.getIsbn())) {
            throw new RuntimeException("Ya existe un libro con el ISBN: " + libro.getIsbn());
        }
        
        // establecer fecha de ingreso automaticamente
        libro.setFechaIngreso(LocalDate.now());
        
        return libroRepository.save(libro);
    }
    
    // actualizar libro
    public Libro updateLibro(Long id, Libro libroDetails) {
        Optional<Libro> libroOpt = libroRepository.findById(id);
        if (libroOpt.isPresent()) {
            Libro libro = libroOpt.get();
            
            // verificar si el isbn cambio y si ya existe
            if (!libro.getIsbn().equals(libroDetails.getIsbn()) && 
                libroRepository.existsByIsbn(libroDetails.getIsbn())) {
                throw new RuntimeException("Ya existe un libro con el ISBN: " + libroDetails.getIsbn());
            }
            
            // actualizar campos
            libro.setIsbn(libroDetails.getIsbn());
            libro.setTitulo(libroDetails.getTitulo());
            libro.setDescripcion(libroDetails.getDescripcion());
            libro.setAnioPublicacion(libroDetails.getAnioPublicacion());
            libro.setIdioma(libroDetails.getIdioma());
            libro.setNumeroPaginas(libroDetails.getNumeroPaginas());
            libro.setPrecio(libroDetails.getPrecio());
            libro.setStockDisponible(libroDetails.getStockDisponible());
            libro.setAuthor(libroDetails.getAuthor());
            
            return libroRepository.save(libro);
        } else {
            throw new RuntimeException("Libro no encontrado con ID: " + id);
        }
    }
    
    // eliminar libro
    public void deleteLibro(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("Libro no encontrado con ID: " + id);
        }
        libroRepository.deleteById(id);
    }
    
    // busquedas por criterios
    public List<Libro> searchByTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }
    
    public List<Libro> searchByAuthor(String nombre, String apellido) {
        return libroRepository.findByAuthorNombreContainingIgnoreCaseOrAuthorApellidoContainingIgnoreCase(nombre, apellido);
    }
    
    public List<Libro> searchByAnio(Integer anio) {
        return libroRepository.findByAnioPublicacion(anio);
    }
    
    public List<Libro> searchByAnioRange(Integer anioInicio, Integer anioFin) {
        return libroRepository.findByAnioPublicacionBetween(anioInicio, anioFin);
    }
    
    public List<Libro> searchByPrecioRange(Double precioMin, Double precioMax) {
        return libroRepository.findByPrecioBetween(precioMin, precioMax);
    }
    
    public List<Libro> searchByStock(Integer stockMinimo) {
        return libroRepository.findByStockDisponibleGreaterThan(stockMinimo);
    }
    
    // busqueda avanzada por multiples criterios
    public List<Libro> searchByCriterios(String titulo, Long authorId, 
                                       Integer anioMin, Integer anioMax, 
                                       Double precioMin, Double precioMax) {
        return libroRepository.findByCriterios(titulo, authorId, 
                                             anioMin, anioMax, precioMin, precioMax);
    }
    
    // metodos para manejar autores
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }
    
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }
    
    public void deleteAuthor(Long id) {
        // regla de negocio: no se puede eliminar un autor si tiene algun libro registrado
        if (authorRepository.hasBooks(id)) {
            throw new RuntimeException("No se puede eliminar el autor porque tiene libros registrados");
        }
        authorRepository.deleteById(id);
    }
}
