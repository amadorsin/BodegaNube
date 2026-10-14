# Caso 05: BodegaNube - Arquitectura de Microservicios

Desarrollado en Java 17 con Spring Boot, JPA/Hibernate y conexión a Oracle Cloud Autonomous Database (`BDJAVA`).

## Microservicios implementados
1. **ms-autenticacion (Puerto 8081):** Gestión de usuarios y validación de credenciales por rol (`OPERARIO` / `COMERCIO`).
2. **ms-inventario (Puerto 8082):** Gestión de categorías y catálogo de productos, consulta y descuento de stock.
3. **ms-ordenes (Puerto 8083):** Recepción de avisos de venta por Webhook con control de duplicados y consulta de órdenes.
4. **ms-picking (Puerto 8084):** Registro de preparación de pedidos (picking), despacho y número de seguimiento.

## Requisitos previos
- Java JDK 17 instalado.
- Apache Maven instalado.
- Wallet de Oracle Cloud (`BDJAVA`) descomprimida en `C:/wallet`.

## Instrucciones para clonar y ejecutar localmente
1. **Clonar el repositorio desde GitHub:**
   git clone https://github.com/amadorsin/BodegaNube.git
   cd bodegaNube

2. **Entrar a la carpeta del microservicio que se desea ejecutar (ejemplo con ms-inventario):**
   `cd ms-inventario/ms-inventario`

3. **Limpiar, instalar dependencias y empaquetar el archivo .jar con Maven:**
   `./mvnw clean install`
   `./mvnw package`

4. **Ejecutar la aplicación localmente:**
   `java -jar target/ms-inventario-0.0.1-SNAPSHOT.jar`

*(Nota: También se puede iniciar cada microservicio directamente desde el IDE ejecutando su clase principal `*Application.java`).*

## 4. Endpoints Principales para Pruebas en Postman
* **Autenticación:** `http://localhost:8081/api/usuarios` y `http://localhost:8081/api/usuarios/login`
* **Inventario:** `http://localhost:8082/api/productos` y `http://localhost:8082/api/productos/categorias`
* **Órdenes (Webhook):** `http://localhost:8083/api/ordenes` y `http://localhost:8083/api/ordenes/webhook`
* **Picking y Despacho:** `http://localhost:8084/api/despachos`