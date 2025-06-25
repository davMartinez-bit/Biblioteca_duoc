package com.example.bilbiotecaduoc.controller;

import com.example.bilbiotecaduoc.model.Libro;
import com.example.bilbiotecaduoc.model.Author;
import com.example.bilbiotecaduoc.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class LibroController {

    @Autowired
    private LibroService libroService;

    // ========== casos de uso principales ==========
    
    // cu1: consultar datos de un libro por id
    @GetMapping("/libros/{id}")
    public ResponseEntity<?> getLibroById(@PathVariable Long id) {
        try {
            Optional<Libro> libro = libroService.getLibroById(id);
            if (libro.isPresent()) {
                return ResponseEntity.ok(libro.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Libro no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al consultar el libro: " + e.getMessage());
        }
    }

    // cu1: consultar datos de un libro por isbn
    @GetMapping("/libros/isbn/{isbn}")
    public ResponseEntity<?> getLibroByIsbn(@PathVariable String isbn) {
        try {
            Optional<Libro> libro = libroService.getLibroByIsbn(isbn);
            if (libro.isPresent()) {
                return ResponseEntity.ok(libro.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Libro no encontrado con ISBN: " + isbn);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al consultar el libro: " + e.getMessage());
        }
    }

    // cu2: crear un libro
    @PostMapping("/libros")
    public ResponseEntity<?> createLibro(@Valid @RequestBody Libro libro) {
        try {
            Libro nuevoLibro = libroService.createLibro(libro);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoLibro);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error al crear el libro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());
        }
    }

    // cu3: ver la informacion de todos los libros
    @GetMapping("/libros")
    public ResponseEntity<?> getAllLibros() {
        try {
            List<Libro> libros = libroService.getAllLibros();
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener los libros: " + e.getMessage());
        }
    }

    // ========== operaciones crud adicionales ==========
    
    @PutMapping("/libros/{id}")
    public ResponseEntity<?> updateLibro(@PathVariable Long id, @Valid @RequestBody Libro libro) {
        try {
            Libro libroActualizado = libroService.updateLibro(id, libro);
            return ResponseEntity.ok(libroActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error al actualizar el libro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());
        }
    }

    @DeleteMapping("/libros/{id}")
    public ResponseEntity<?> deleteLibro(@PathVariable Long id) {
        try {
            libroService.deleteLibro(id);
            return ResponseEntity.ok("Libro eliminado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error al eliminar el libro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());
        }
    }

    // ========== busquedas por criterios ==========
    
    @GetMapping("/libros/buscar/titulo")
    public ResponseEntity<?> searchByTitulo(@RequestParam String titulo) {
        try {
            List<Libro> libros = libroService.searchByTitulo(titulo);
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error en la búsqueda: " + e.getMessage());
        }
    }

    @GetMapping("/libros/buscar/autor")
    public ResponseEntity<?> searchByAuthor(@RequestParam String nombre, @RequestParam String apellido) {
        try {
            List<Libro> libros = libroService.searchByAuthor(nombre, apellido);
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error en la búsqueda: " + e.getMessage());
        }
    }

    @GetMapping("/libros/buscar/anio")
    public ResponseEntity<?> searchByAnio(@RequestParam Integer anio) {
        try {
            List<Libro> libros = libroService.searchByAnio(anio);
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error en la búsqueda: " + e.getMessage());
        }
    }

    @GetMapping("/libros/buscar/anio-rango")
    public ResponseEntity<?> searchByAnioRange(@RequestParam Integer anioInicio, @RequestParam Integer anioFin) {
        try {
            List<Libro> libros = libroService.searchByAnioRange(anioInicio, anioFin);
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error en la búsqueda: " + e.getMessage());
        }
    }

    @GetMapping("/libros/buscar/precio")
    public ResponseEntity<?> searchByPrecioRange(@RequestParam Double precioMin, @RequestParam Double precioMax) {
        try {
            List<Libro> libros = libroService.searchByPrecioRange(precioMin, precioMax);
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error en la búsqueda: " + e.getMessage());
        }
    }

    @GetMapping("/libros/buscar/stock")
    public ResponseEntity<?> searchByStock(@RequestParam Integer stockMinimo) {
        try {
            List<Libro> libros = libroService.searchByStock(stockMinimo);
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error en la búsqueda: " + e.getMessage());
        }
    }

    // busqueda avanzada por multiples criterios
    @GetMapping("/libros/buscar/avanzada")
    public ResponseEntity<?> searchByCriterios(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Integer anioMin,
            @RequestParam(required = false) Integer anioMax,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax) {
        try {
            List<Libro> libros = libroService.searchByCriterios(titulo, authorId, anioMin, anioMax, precioMin, precioMax);
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error en la búsqueda avanzada: " + e.getMessage());
        }
    }

    // ========== gestion de autores ==========
    
    @GetMapping("/authors")
    public ResponseEntity<?> getAllAuthors() {
        try {
            List<Author> authors = libroService.getAllAuthors();
            return ResponseEntity.ok(authors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener los autores: " + e.getMessage());
        }
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        try {
            Optional<Author> author = libroService.getAuthorById(id);
            if (author.isPresent()) {
                return ResponseEntity.ok(author.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Autor no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al consultar el autor: " + e.getMessage());
        }
    }

    @PostMapping("/authors")
    public ResponseEntity<?> createAuthor(@Valid @RequestBody Author author) {
        try {
            Author nuevoAuthor = libroService.createAuthor(author);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAuthor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al crear el autor: " + e.getMessage());
        }
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        try {
            libroService.deleteAuthor(id);
            return ResponseEntity.ok("Autor eliminado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error al eliminar el autor: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());
        }
    }
}