# Construccion2LauraEscobar

## Descripción

Este proyecto es un sistema de gestión para una clínica veterinaria, desarrollado en Java con Spring Boot. Permite registrar y gestionar usuarios (dueños, veterinarios, vendedores y administradores), mascotas, historiales médicos, órdenes médicas y facturación. El sistema puede usarse tanto por consola como a través de endpoints REST.

---

## Tecnología

- **Lenguaje de programación:** Java
- **Entorno de desarrollo:** Visual Studio Code
- **Gestor de dependencias:** Maven
- **Frameworks/Librerías:** Spring Boot, JPA
- **Base de datos:** MySQL (con XAMPP, puerto 3306, creación automática)

---

## Modos de Uso

- **Consola interactiva:**  
  Al ejecutar la aplicación, se muestra un menú principal donde los usuarios pueden iniciar sesión o registrarse como dueños. Cada rol tiene su propio menú y funcionalidades específicas.

- **API REST:**  
  El sistema expone endpoints REST para integración externa, permitiendo registrar usuarios, mascotas, consultar información, etc.

  **Endpoints principales:**
    - **Usuarios**
      - POST /users/register  - Registrar usuario
      - POST /users/login - Inicias sesión
      - GET /users/{userName} - Obtener usurio por username
      - GET /users  - Obtener todos los usuarios
      - DELETE /users/{document}  - Eliminar usuario por documento
      - POST /users/change-password?username={username}&oldPassword={old}&newPassword={new} - Cambiar contraseña
    
    - **Personas**
      - POST /persons - Registrar perosona
      - GET /persons/{document} - Obtener persona por documento
      - DELETE /api/persons/{document}  - Eliminar persona

    - **Dueños**
      - POST //owners/pet  - Registrar mascota
      - GET /owners/pet/{id}  - Buscar mascota por ID
      - PUT /owners/pet - Actualizar mascota
      - GET /owners/{ownerDoc}/pets - Listar todas las mascotas del dueño

    - **Vendedor**
      - POST /sellers/invoice - Registrar Factura
      - GET /sellers/invoices - Listar todas las facturas
      - GET /sellers/invoices/owner/{ownerDoc} - Listar facturas por documento del dueño
      - GET /sellers/orders - Listar las ordenes medicas

    - **Veterinario**
      - PUT /veterinarians/pet  - Registrar mascota
      - GET /veterinarians/orders/pet/{petId}   - Buscar orden medica por ID de mascota
      - GET /veterinarians/medical-histories/pet/{petId}  - Buscar historias clinicas por ID de mascota

---

## Instalación y Ejecución

1. **Clona el repositorio:**
   ```sh
   git clone https://github.com/MaoDhell/Construccion2LauraEscobar.git
   cd Construccion2LauraEscobar
   ```

2. **Asegúrate de tener MySQL corriendo en el puerto 3306**  
   (puedes usar XAMPP o tu gestor favorito).

---

## Funcionalidades Principales

- Registro y autenticación de usuarios con roles: ADMIN, VETERINARIO, SELLER, USER (OWNER)
- Registro y gestión de mascotas
- Registro y actualización de historias clínicas y órdenes médicas
- Facturación y consulta de ventas
- Menú interactivo por consola para cada tipo de usuario
- Validaciones de datos y manejo de errores
- Creación automática de la base de datos al iniciar la aplicación

---

## Estado del Proyecto

- **En desarrollo activo.** V.1.0
- Funcionalidades principales implementadas y probadas.
- La base de datos se crea automáticamente según la configuración de `application.properties` y se conecta por defecto al puerto **3306** de MySQL.
- Listo para pruebas y mejoras.

---

## Nota especial sobre el usuario administrador

Al iniciar la aplicación por primera vez, **se crea automáticamente un usuario administrador** si no existe.  
Este usuario tiene las siguientes credenciales por defecto:

- **Usuario:** MaoD
- **Contraseña:** admin123

Esto permite acceder inmediatamente al menú de administración para registrar otros usuarios y gestionar el sistema.

---

## Autor

Laura Daniela Escobar Ruiz

------



