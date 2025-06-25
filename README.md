# Biblioteca DUOC - Sistema de Gestión de Libros

## Descripción

Sistema de gestión de libros para la librería DUOCUC desarrollado en Spring Boot con Java 17 y MySQL. Permite la gestión completa de libros y autores con búsquedas avanzadas y validaciones de negocio.

## Características

### ✅ Casos de Uso Implementados
- **CU1**: Consultar datos de un libro (por ID o ISBN)
- **CU2**: Crear un libro nuevo
- **CU3**: Ver catálogo completo de libros

### ✅ Reglas de Negocio
- No existen dos libros con el mismo ISBN
- No se puede eliminar un autor si tiene libros registrados

### ✅ Funcionalidades Adicionales
- Búsquedas por múltiples criterios (título, autor, año, precio, stock)
- Gestión completa de autores
- Validaciones de datos

- API REST completa

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.5.0**
- **Spring Data JPA**
- **MySQL 8.0**
- **Maven**
- **Lombok**
- **Jakarta Validation**

## Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+
- Laragon (para gestión de base de datos)

## Instalación y Configuración

### 1. Clonar el Proyecto
```bash
git clone <url-del-repositorio>
cd bilbiotecaduoc
```

### 2. Configurar Base de Datos
1. Abrir Laragon
2. Iniciar MySQL
3. Crear una base de datos llamada `biblioteca_duoc` (opcional, se crea automáticamente)
4. Verificar que el usuario `root` no tenga contraseña (configuración por defecto)

### 3. Configurar Aplicación
El archivo `application.properties` ya está configurado para:
- Puerto: 8080
- Base de datos: `biblioteca_duoc`
- Usuario: `root`
- Contraseña: (vacía)

Si necesitas cambiar la configuración, edita `src/main/resources/application.properties`

### 4. Ejecutar la Aplicación
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## Estructura del Proyecto

```
src/main/java/com/example/bilbiotecaduoc/
├── BilbiotecaduocApplication.java     # Clase principal
├── controller/
│   └── LibroController.java           # Controlador REST
├── model/
│   ├── Libro.java                     # Entidad Libro
│   └── Author.java                    # Entidad Autor
├── repository/
│   ├── LibroRepository.java           # Repositorio Libros
│   └── AuthorRepository.java          # Repositorio Autores
└── service/
    └── LibroService.java              # Lógica de negocio
```

## API Endpoints

### Libros

#### Casos de Uso Principales
- `GET /api/v1/libros` - Ver todos los libros (CU3)
- `GET /api/v1/libros/{id}` - Consultar libro por ID (CU1)
- `GET /api/v1/libros/isbn/{isbn}` - Consultar libro por ISBN (CU1)
- `POST /api/v1/libros` - Crear libro nuevo (CU2)

#### Operaciones CRUD
- `PUT /api/v1/libros/{id}` - Actualizar libro
- `DELETE /api/v1/libros/{id}` - Eliminar libro

#### Búsquedas
- `GET /api/v1/libros/buscar/titulo?titulo=...` - Buscar por título
- `GET /api/v1/libros/buscar/autor?nombre=...&apellido=...` - Buscar por autor
- `GET /api/v1/libros/buscar/anio?anio=...` - Buscar por año
- `GET /api/v1/libros/buscar/anio-rango?anioInicio=...&anioFin=...` - Buscar por rango de años
- `GET /api/v1/libros/buscar/precio?precioMin=...&precioMax=...` - Buscar por rango de precio
- `GET /api/v1/libros/buscar/stock?stockMinimo=...` - Buscar por stock disponible
- `GET /api/v1/libros/buscar/avanzada` - Búsqueda avanzada con múltiples criterios

### Autores
- `GET /api/v1/authors` - Listar todos los autores
- `GET /api/v1/authors/{id}` - Obtener autor por ID
- `POST /api/v1/authors` - Crear autor
- `DELETE /api/v1/authors/{id}` - Eliminar autor

## Ejemplos de Uso

### Crear un Autor
```bash
curl -X POST http://localhost:8080/api/v1/authors \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Gabriel",
    "apellido": "García Márquez",
    "fechaNacimiento": "1927-03-06",
    "biografia": "Escritor colombiano, premio Nobel de Literatura"
  }'
```

### Crear un Libro
```bash
curl -X POST http://localhost:8080/api/v1/libros \
  -H "Content-Type: application/json" \
  -d '{
    "isbn": "9788497592208",
    "titulo": "Cien años de soledad",
    "descripcion": "Obra maestra de la literatura latinoamericana",
    "anioPublicacion": 1967,
    "idioma": "Español",
    "numeroPaginas": 432,
    "precio": 25.99,
    "stockDisponible": 10,
    "author": {
      "id": 1
    }
  }'
```

### Buscar Libros
```bash
# Buscar por título
curl "http://localhost:8080/api/v1/libros/buscar/titulo?titulo=soledad"

# Buscar por autor
curl "http://localhost:8080/api/v1/libros/buscar/autor?nombre=Gabriel&apellido=García"

# Búsqueda avanzada
curl "http://localhost:8080/api/v1/libros/buscar/avanzada?titulo=soledad&anioMin=1960&anioMax=1970&precioMin=20&precioMax=30"
```

## Validaciones

### Libro
- ISBN: Obligatorio, formato 10 o 13 dígitos, único
- Título: Obligatorio, máximo 200 caracteres
- Año de publicación: Obligatorio, mínimo 1900
- Autor: Obligatorio

### Autor
- Nombre: Obligatorio, máximo 100 caracteres
- Apellido: Máximo 100 caracteres

## Base de Datos

El sistema crea automáticamente las siguientes tablas:
- `libros` - Información de libros
- `authors` - Información de autores

Las relaciones están configuradas como:
- Libro → Autor (Muchos a Uno) 