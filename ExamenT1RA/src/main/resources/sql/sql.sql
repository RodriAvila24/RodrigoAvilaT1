use bd_gestion_academica;

INSERT INTO estudiantes (nombre, email, matricula) VALUES
('Lucas Herrera', 'lucas.herrera@example.com', 'MAT1001'),
('Valeria Soto', 'valeria.soto@example.com', 'MAT1002'),
('Daniel Paredes', 'daniel.paredes@example.com', 'MAT1003'),
('Natalia Ruiz', 'natalia.ruiz@example.com', 'MAT1004'),
('Jorge Medina', 'jorge.medina@example.com', 'MAT1005');

INSERT INTO profesores (nombre, especialidad) VALUES
('Dr. Fernando Salazar', 'Inteligencia Artificial'),
('Mg. Carolina Ponce', 'Redes'),
('Ricardo Torres', 'Desarrollo Web'),
('Susana Campos', 'Base de Datos'),
('Elvira Rodríguez', 'Algoritmos');

INSERT INTO cursos (nombre, codigo, creditos) VALUES
('Programación I', 'PRG101', 4),
('Estructuras de Datos', 'ED202', 3),
('Base de Datos', 'BD303', 4),
('Desarrollo Web', 'DW404', 3),
('Redes I', 'RD505', 3);

INSERT INTO curso_profesor (curso_id, profesor_id) VALUES
(1, 1), (2, 2), (3, 4), (4, 3), (5, 2),
(2, 1), (3, 1), (4, 2), (1, 3), (5, 4);

INSERT INTO inscripciones (curso_id, estudiante_id, fecha_inscripcion, estado) VALUES
(1, 1, '2025-01-15', 'ACTIVA'),
(2, 2, '2025-01-20', 'ACTIVA'),
(3, 3, '2025-02-05', 'RETIRADA'),
(4, 4, '2025-02-15', 'ACTIVA'),
(5, 5, '2025-02-20', 'FINALIZADA'),
(1, 2, '2025-03-01', 'ACTIVA'),
(2, 3, '2025-03-10', 'FINALIZADA'),
(3, 4, '2025-03-15', 'ACTIVA'),
(4, 5, '2025-03-20', 'RETIRADA'),
(5, 1, '2025-03-25', 'ACTIVA'),
(2, 1, '2025-04-01', 'ACTIVA'),
(3, 2, '2025-04-05', 'FINALIZADA'),
(1, 3, '2025-04-10', 'ACTIVA'),
(5, 4, '2025-04-15', 'FINALIZADA'),
(4, 2, '2025-04-20', 'RETIRADA');
