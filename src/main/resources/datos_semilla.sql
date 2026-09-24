-- =========================================================
-- SCRIPT DE DATOS SEMILLA
-- Asignatura: Lenguaje de Programación II
-- Base de Datos: Oracle (BIBLIODB / FREEPDB1)
-- =========================================================
-- 1. CARRERAS (3 mínimas exigidas)
INSERT INTO carreras (id, nombre, descripcion, estado, fecha_creacion)
VALUES (1, 'Ingenieria de Sistemas', 'EP Ingeniería de Sistemas', 1, SYSTIMESTAMP);

INSERT INTO carreras (id, nombre, descripcion, estado, fecha_creacion)
VALUES (2, 'Ingenieria Civil', 'EP Ingeniería Civil', 1, SYSTIMESTAMP);

INSERT INTO carreras (id, nombre, descripcion, estado, fecha_creacion)
VALUES (3, 'Arquitectura', 'EP Arquitectura y Urbanismo', 1, SYSTIMESTAMP);

-- 2. CURSOS (12 mínimos exigidos: incluye 0 vacantes e inactivos)
-- Cursos de Ingeniería de Sistemas (Carrera ID: 1)
INSERT INTO cursos (id, codigo, nombre, creditos, ciclo, vacantes, estado, carrera_id, fecha_creacion)
VALUES (1, 'IS401', 'Lenguaje de Programacion II', 3, 4, 30, 1, 1, SYSTIMESTAMP);

INSERT INTO cursos (id, codigo, nombre, creditos, ciclo, vacantes, estado, carrera_id, fecha_creacion)
VALUES (2, 'IS402', 'Base de Datos II', 4, 4, 25, 1, 1, SYSTIMESTAMP);

INSERT INTO cursos (id, codigo, nombre, creditos, ciclo, vacantes, estado, carrera_id, fecha_creacion)
VALUES (3, 'IS403', 'Ingenieria de Requisitos', 3, 4, 2, 1, 1, SYSTIMESTAMP);

INSERT INTO cursos (id, codigo, nombre, creditos, ciclo, vacantes, estado, carrera_id, fecha_creacion)
VALUES (4, 'IS404', 'Estadistica Aplicada', 3, 4, 0, 1, 1, SYSTIMESTAMP);

INSERT INTO cursos (id, codigo, nombre, creditos, ciclo, vacantes, estado, carrera_id, fecha_creacion)
VALUES (5, 'IS501', 'Arquitectura de Software', 4, 5, 20, 1, 1, SYSTIMESTAMP);

INSERT INTO cursos (id, codigo, nombre, creditos, ciclo, vacantes, estado, carrera_id, fecha_creacion)
VALUES (6, 'IS502', 'Sistemas Operativos', 4, 5, 15, 1, 1, SYSTIMESTAMP);

INSERT INTO cursos (id, codigo, nombre, creditos, ciclo, vacantes, estado, carrera_id, fecha_creacion)
VALUES (7, 'IS503', 'Redes de Computadoras', 4, 5, 20, 1, 1, SYSTIMESTAMP);

INSERT INTO cursos (id, codigo, nombre, creditos, ciclo, vacantes, estado, carrera_id, fecha_creacion)
VALUES (8, 'IS504', 'Analisis de Algoritmos', 3, 5, 10, 0, 1, SYSTIMESTAMP);

-- Cursos de Ingeniería Civil (Carrera ID: 2)
INSERT INTO cursos (id, codigo, nombre, creditos, ciclo, vacantes, estado, carrera_id, fecha_creacion)
VALUES (9, 'IC401', 'Mecanica de Suelos', 4, 4, 30, 1, 2, SYSTIMESTAMP);

INSERT INTO cursos (id, codigo, nombre, creditos, ciclo, vacantes, estado, carrera_id, fecha_creacion)
VALUES (10, 'IC402', 'Resistencia de Materiales', 4, 4, 0, 1, 2, SYSTIMESTAMP);

-- Cursos de Arquitectura (Carrera ID: 3)
INSERT INTO cursos (id, codigo, nombre, creditos, ciclo, vacantes, estado, carrera_id, fecha_creacion)
VALUES (11, 'AR401', 'Taller de Diseno IV', 6, 4, 15, 1, 3, SYSTIMESTAMP);

INSERT INTO cursos (id, codigo, nombre, creditos, ciclo, vacantes, estado, carrera_id, fecha_creacion)
VALUES (12, 'AR402', 'Historia de la Arquitectura', 3, 4, 20, 1, 3, SYSTIMESTAMP);

-- 3. ESTUDIANTES (6 mínimos exigidos)
INSERT INTO estudiantes (id, codigo, dni, nombres, apellidos, email, estado, carrera_id, fecha_creacion)
VALUES (1, '202410001', '71234567', 'Ana Lucia', 'Quispe Mamani', 'ana.quispe@upeu.edu.pe', 1, 1, SYSTIMESTAMP);

INSERT INTO estudiantes (id, codigo, dni, nombres, apellidos, email, estado, carrera_id, fecha_creacion)
VALUES (2, '202410002', '72345678', 'Jorge Luis', 'Condori Apaza', 'jorge.condori@upeu.edu.pe', 1, 1, SYSTIMESTAMP);

INSERT INTO estudiantes (id, codigo, dni, nombres, apellidos, email, estado, carrera_id, fecha_creacion)
VALUES (3, '202410003', '73456789', 'Maria Elena', 'Huaman Torres', 'maria.huaman@upeu.edu.pe', 1, 2, SYSTIMESTAMP);

INSERT INTO estudiantes (id, codigo, dni, nombres, apellidos, email, estado, carrera_id, fecha_creacion)
VALUES (4, '202410004', '74567890', 'Carlos Alberto', 'Mamani Flores', 'carlos.mamani@upeu.edu.pe', 0, 1, SYSTIMESTAMP);

INSERT INTO estudiantes (id, codigo, dni, nombres, apellidos, email, estado, carrera_id, fecha_creacion)
VALUES (5, '202410005', '75678901', 'Lucia Fernanda', 'Benitez Morales', 'lucia.benitez@upeu.edu.pe', 1, 3, SYSTIMESTAMP);

INSERT INTO estudiantes (id, codigo, dni, nombres, apellidos, email, estado, carrera_id, fecha_creacion)
VALUES (6, '202410006', '76789012', 'Diego Alonso', 'Flores Huaman', 'diego.flores@upeu.edu.pe', 1, 1, SYSTIMESTAMP);

COMMIT;