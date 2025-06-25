-- datos de prueba para la biblioteca duoc
-- este archivo se ejecuta automaticamente al iniciar la aplicacion

-- insertar autores
INSERT INTO authors (nombre, apellido, fecha_nacimiento, biografia) VALUES
('Gabriel', 'García Márquez', '1927-03-06', 'Escritor colombiano, premio Nobel de Literatura en 1982'),
('Mario', 'Vargas Llosa', '1936-03-28', 'Escritor peruano, premio Nobel de Literatura en 2010'),
('Isabel', 'Allende', '1942-08-02', 'Escritora chilena, una de las autoras más leídas en español'),
('Pablo', 'Neruda', '1904-07-12', 'Poeta chileno, premio Nobel de Literatura en 1971'),
('Jorge Luis', 'Borges', '1899-08-24', 'Escritor argentino, uno de los más importantes del siglo XX');

-- insertar libros (despues de que existan autores)
INSERT INTO libros (fecha_ingreso, isbn, titulo, descripcion, anio_publicacion, idioma, numero_paginas, precio, stock_disponible, author_id) VALUES
(CURDATE(), '9788497592208', 'Cien años de soledad', 'Obra maestra de la literatura latinoamericana que narra la historia de la familia Buendía', 1967, 'Español', 432, 25.99, 15, 1),
(CURDATE(), '9788497592215', 'El amor en los tiempos del cólera', 'Historia de amor que transcurre durante cincuenta años', 1985, 'Español', 368, 22.50, 12, 1),
(CURDATE(), '9788497592222', 'Crónica de una muerte anunciada', 'Novela basada en un suceso real ocurrido en Colombia', 1981, 'Español', 128, 18.75, 8, 1),
(CURDATE(), '9788497592239', 'La ciudad y los perros', 'Primera novela de Vargas Llosa, ambientada en un colegio militar', 1963, 'Español', 320, 24.99, 10, 2),
(CURDATE(), '9788497592246', 'La casa de los espíritus', 'Primera novela de Isabel Allende, saga familiar en Chile', 1982, 'Español', 448, 26.50, 18, 3),
(CURDATE(), '9788497592253', 'Veinte poemas de amor y una canción desesperada', 'Obra poética más famosa de Pablo Neruda', 1924, 'Español', 96, 15.99, 25, 4),
(CURDATE(), '9788497592260', 'Ficciones', 'Colección de cuentos de Borges, incluye "El Aleph"', 1944, 'Español', 224, 21.75, 14, 5),
(CURDATE(), '9788497592277', 'El Aleph', 'Cuento fantástico de Borges sobre el infinito', 1949, 'Español', 160, 19.50, 20, 5),
(CURDATE(), '9788497592284', 'El general en su laberinto', 'Novela histórica sobre los últimos días de Simón Bolívar', 1989, 'Español', 288, 23.99, 11, 1),
(CURDATE(), '9788497592291', 'Eva Luna', 'Novela de Isabel Allende sobre una narradora de historias', 1987, 'Español', 320, 24.50, 16, 3); 