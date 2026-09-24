# 📚 Matrícula API - Backend REST Empresarial

**Caso de Negocio:** EduAndes - Sistema de Matrícula Académica  
**Asignatura:** Lenguaje de Programación II  
**Institución:** Universidad Peruana Unión (UPeU)  
**Semestre:** 2026-2  
**Estudiante:** Alef Altamirano  

---

## 📌 Descripción del Proyecto

API REST modular desarrollada en Java 21 y Spring Boot 3.2 para la gestión automatizada de matrículas académicas del instituto **EduAndes**. 

El sistema reemplaza procesos manuales garantizando la integridad de datos, atomicidad en las transacciones de cabecera-detalle, control automático de vacantes y cumplimiento estricto de las reglas de negocio académicas en la capa de servicio.

---

## 🏗️ Arquitectura del Sistema

La API sigue una **arquitectura en capas (Layered Architecture)** estricta, desacoplando la lógica de negocio de los controladores e infraestructura:
src/main/java/pe/edu/upeu/MatriculaBackend/
├── config/             # Configuración de OpenAPI / Swagger UI
├── controller/         # Endpoints REST (ResponseEntity + DTOs)
├── dto/                # Data Transfer Objects (Request / Response)
├── entity/             # Entidades JPA (Mapeo ORM Oracle)
├── enums/              # Enumeraciones del dominio (EstadoMatricula)
├── exception/          # Excepciones personalizadas y GlobalExceptionHandler
├── repository/         # Interfaces Spring Data JPA
└── service/            # Interfaces e Implementaciones de lógica de negocio
├── generic/        # Contrato genérico CrudService
└── impl/           # Transacciones, validaciones y reglas de negocio


### 🧩 Principios Técnicos Clave
* **DTOs Exclusivos:** Los controladores nunca reciben ni retornan entidades JPA directamente.
* **Manejo Uniforme de Errores:** `GlobalExceptionHandler` intercepta excepciones y responde con `ErrorResponseDTO` (códigos HTTP 400, 404, 409 y 500).
* **Atomicidad Transaccional:** Uso de `@Transactional` para garantizar operaciones *todo-o-nada* (rollback automático ante fallos de reglas).
* **Copias de Histórico:** `DetalleMatricula` guarda una copia inmutable de los créditos y costos al momento de matricular.

---

## ⚙️ Requisitos del Sistema

* **Java Development Kit (JDK):** Version 21
* **Build Tool:** Apache Maven 3.8+
* **Database:** Oracle Database 19c / 21c / Express Edition (XE / FREEPDB1)
* **IDE Recomendado:** IntelliJ IDEA

---

## 🚀 Configuración y Perfiles de Ejecución

El proyecto utiliza perfiles de Spring Boot configurados en `src/main/resources/`.

### 1. Perfil de Desarrollo (`dev`) - Activo por defecto
Conecta a la base de datos Oracle local y habilita la interfaz interactiva de Swagger.

**Archivo `application-dev.yaml`:**
---yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:oracle:thin:@localhost:1522/FREEPDB1
    username: MatriculaBackend
    password: tu_password_oracle
    driver-class-name: oracle.jdbc.OracleDriver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.OracleDialect

springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html

🛠️ Instrucciones de EjecuciónClonar el repositorio:
Bashgit clone [https://github.com/AlefAltamirano/MatriculaBackend.git](https://github.com/AlefAltamirano/MatriculaBackend.git)
cd MatriculaBackend
Asegurar base de datos Oracle en ejecución:Verifica que la instancia de Oracle esté activa en el puerto 1522 
(o el configurado en tu application-dev.yaml) y que el usuario MatriculaBackend tenga permisos sobre el esquema.
Cargar Datos Semilla (Anexo A):Ejecuta el script SQL datos_semilla.sql ubicado en la raíz o en el paquete de recursos dentro de tu consola SQL Developer o DataGrip para poblar las carreras, cursos y estudiantes iniciales.Compilar y Ejecutar la Aplicación:Bash# Opción Maven Wrapper
./mvnw spring-boot:run

# O desde tu IDE ejecutando MatriculaBackendApplication.java
Acceder a la Documentación (Swagger UI):Una vez iniciada la aplicación, abre el navegador en:👉 http://localhost:8080/swagger-ui.html
📋 Endpoints PrincipalesMóduloMétodoEndpointDescripciónSaludGET/api/v1/health
Verifica la conexión real a Oracle DB (200 UP / 503 DOWN)CarrerasGET, POST, PUT, DELETE/api/v1/carreras
CRUD completo con validación de nombre únicoCursosGET, POST, PUT, DELETE/api/v1/cursos
CRUD de cursos asignados a carrerasCursosGET/api/v1/carreras/{id}/cursos
Obtiene los cursos pertencientes a una carreraCursosGET/api/v1/cursos/buscar
Búsqueda avanzada con filtros combinables y ordenamientoEstudiantesGET, POST, PUT, DELETE/api/v1/estudiantes
CRUD con validación de código (9 dig) y DNI (8 dig)MatrículaPOST/api/v1/matriculas
Registro atómico de cabecera-detalle con validación de RNsMatrículaGET/api/v1/matriculas/{id}
Consulta de cabecera con lista de detallesMatrículaPATCH/api/v1/matriculas/{id}/anular
Anula matrícula y restablece las vacantes consumidas📏 Reglas de Negocio Implementadas (Capa Servicio)
RN-01: Solo se matricula un estudiante activo, en cursos activos pertenecientes a su misma carrera.
RN-02: No se puede matricular en cursos con 0 vacantes. Se descuenta 1 vacante por materia y la anulación la devuelve.
RN-03: Un estudiante no puede registrar más de 1 matrícula en estado REGISTRADA dentro del mismo período (ej. 2026-2).
RN-04: La matrícula no puede superar los 20 créditos. El costo es $\text{créditos} \times 120.00$.
🌿 Flujo de Trabajo en Git y EtiquetadoEl proyecto se administró bajo el modelo Git Flow:main: Producción y entregas estables.develop: 
Integración continua de características.feature/...: 
Ramas de desarrollo de módulos.Etiqueta de versión entregada: v1.0-unidad1Bashgit tag -l
# Salida: v1.0-unidad1

