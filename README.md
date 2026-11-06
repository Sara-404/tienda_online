# 🛒 API REST de Tienda Online
*Este proyecto se encuentra en desarrollo*

¡Bienvenido/a a mi proyecto de práctica! Esta es una **API REST robusta** desarrollada con **Spring Boot** para gestionar un sistema de compras, control de stock de productos, saldos de clientes y procesamiento de pedidos. 

El objetivo principal de este proyecto es aplicar las mejores prácticas del ecosistema Spring, el diseño de APIs RESTful, el aislamiento de datos mediante DTOs y la gestión transaccional de la base de datos.

---

## Tecnologías y Herramientas Utilizadas

* **Java 17**
* **Spring Boot 4.1.2**
* **Spring Data JPA** (Persistencia de datos)
* **Spring Boot Validation** (Validación de datos de entrada)
* **Base de Datos:** MySQL
* **Contenedores e Infraestructura:** Docker & Docker Compose (Virtualización de la base de datos en local)
* **Documentación de API:** OpenAPI / Swagger UI
* **Gestor de Dependencias:** Maven

---

## Arquitectura y Buenas Prácticas

El proyecto sigue una arquitectura limpia en capas para mantener el código desacoplado y fácil de mantener:

* **Controladores (Controllers):** Definición de los endpoints REST utilizando `ResponseEntity` y gestionando respuestas correctas con códigos de estado adecuados (`201 Created` con cabeceras `Location`, `200 OK`, etc.).
* **Servicios (Services):** Capa lógica de negocio donde se gestionan las transacciones (`@Transactional`) para operaciones críticas como descontar saldo y reducir stock de manera atómica.
* **Repositorios (Repositories):** Intercambio de datos con la base de datos mediante abstracciones de Spring Data.
* **DTOs (Data Transfer Objects):** Separación estricta entre las entidades de la base de datos y la información expuesta al cliente (`RequestDTO` para entrada, `ResponseDTO` para salida).

---

## Endpoints Principales

### Clientes (`/api/clientes`)
Métodos POST:
* `POST /api/clientes` - Registra un nuevo cliente (Devuelve `201 Created` con su URI).

Métodos GET:
* `GET /api/clientes/{id}` - Obtiene el detalle de un cliente (Devuelve `200 OK`).

Métodos PATCH:
* `PATCH /api/clientes/{id}/nombre` - Actualiza el nombre de un cliente (Devuelve `200 OK`).
* `PATCH /api/clientes/{id}/email` - Actualiza el email de un cliente (Devuelve `200 OK`).
* `PATCH /api/clientes/{id}/subir-saldo` - Incrementa el saldo de un cliente (Devuelve `200 OK`).

Métodos DELETE:
* `DELETE /api/clientes/{id}` - Elimina un cliente de la base de datos (Devuelve `204 No Content`).

### Productos (`/api/productos`)
Métodos POST:
* `POST /api/productos` - Registra un nuevo producto (Devuelve `201 Created` con su URI).

Métodos GET:
* `GET /api/productos` - Lista todos los productos disponibles (Devuelve `200 OK`).
* `GET /api/productos/{id}` - Obtiene el detalle de un producto (Devuelve `200 OK`).

Métodos PATCH:
* `PATCH /api/productos/{id}/nombre` - Actualiza el nombre de un producto (Devuelve `200 OK`).
* `PATCH /api/productos/{id}/precio` - Actualiza el precio de un producto (Devuelve `200 OK`).
* `PATCH /api/productos/{id}/subir-stock` - Incrementa el stock de un producto (Devuelve `200 OK`).

Métodos DELETE:
* `DELETE /api/productos/{id}` - Elimina un producto de la base de datos (Devuelve `204 No Content`).

### Pedidos (`/api/pedidos`)
Métodos POST:
* `POST /api/pedidos` - Crea un nuevo pedido, validando stock y saldo de forma transaccional, comprobando antes que el cliente y el producto existan en la base de datos (Devuelve `201 Created` con su URI).

Métodos GET:
* `GET /api/pedidos/{idCliente}` - Lista los pedidos que ha hecho un cliente según su id (Devuelve `200 OK`).

Métodos DELETE:
* `DELETE /api/pedidos/{id}` - Elimina un pedido de la base de datos devolviendo el precio de este al cliente correspondiente, y devolviendo la cantidad de stock al producto correspondiente (Devuelve `204 No Content`).

---
## Configuración e Instalación Local

### Requisitos Previos
* **Java 17** (JDK) instalado.
* **Docker y Docker Desktop** instalados y en ejecución.
* **Maven** instalado (opcional, puedes usar el wrapper incluido `./mvnw`).

### Configuración de la Base de Datos y Entorno
El proyecto utiliza Docker para levantar la base de datos de manera aislada y variables de entorno para proteger las credenciales.

1. **Clona el repositorio:**
   ```bash
   git clone https://github.com/Sara-404/tienda_online
   cd tienda_online
   ```

2. **Configura tus variables de entorno:**
   Crea un archivo llamado `.env` en la raíz del proyecto (basándote en el archivo `.env.example` proporcionado) y define tus credenciales locales:
   ```env
    DB_HOST=localhost
    DB_PORT=3306
    DB_NAME=tiendadb
    DB_USER=user
    DB_PASSWORD=password
   ```
   *(Nota: Asegúrate de que tu IDE cargue este archivo `.env` al arrancar el proyecto).*

3. **Levanta la base de datos con Docker:**
   Ejecuta el siguiente comando para descargar e iniciar el contenedor de MySQL en segundo plano:
   ```bash
   docker compose up -d
   ```

4. **Compila y ejecuta la aplicación de Spring Boot:**
   Una vez que el contenedor esté en ejecución, arranca la API desde tu terminal:
   ```bash
   ./mvnw spring-boot:run
   ```
   La API estará disponible y escuchando peticiones en `http://localhost:8080`.

### Documentación Interactiva (Swagger / OpenAPI)
Una vez que la aplicación esté en ejecución, puedes explorar, probar e interactuar con todos los endpoints de la API de forma visual desde tu navegador web:

* **Interfaz de Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* **Documentación OpenAPI (JSON):** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---
## Próximos Pasos (En Desarrollo)
Como parte de mi proceso de aprendizaje, tengo planeado añadir próximamente:
* [x] Documentar los endpoints de forma interactiva utilizando **Swagger / OpenAPI**.
* [x] Métodos de actualización y de borrado de todas las entidades de la base de datos.
* [ ] Añadir paginación y ordenamiento.
* [ ] Implementar pruebas unitarias y de integración.
* [ ] Añadir seguridad con **Spring Security**.

---
