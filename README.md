# Bellota112 - Sistema de Gestión de Incidencias
## Descripción 📝
Bellota112 es una aplicación web desarrollada en Spring Boot diseñada para el seguimiento y gestión de incidencias, con un enfoque especial en la coordinación de equipos de emergencia. La aplicación incluye:
- Autenticación y autorización de usuarios con Spring Security
- Gestión de usuarios con diferentes roles (ADMIN, ADMINLOCAL, USER)
- Registro y seguimiento de incidencias
- Interfaz web responsive con Thymeleaf y Bootstrap
- Persistencia de datos con MongoDB

## Características principales ✨
- Autenticación segura: Uso de BCrypt para el hash de contraseñas
- Roles de usuario:
  - ADMIN: Acceso completo
  - ADMINLOCAL: Gestión de usuarios e incidencias en su zona
  - USER: Acceso básico
- Gestión de incidencias: Creación, actualización y cierre de incidencias
- Filtrado por zona y cuerpo de emergencia
- Visualización en mapa con OpenStreetMap
- Gestión de usuarios:
  - Registro con validación
  - Actualización de perfiles
  - Activación/desactivación de cuentas

## Tecnologías utilizadas 🛠️
Backend:
- Java 17
- Spring Boot 3.4.0
- Spring Security
- Spring Data MongoDB

Frontend:
- Thymeleaf
- Bootstrap 4.5
- Leaflet (para mapas)

Base de datos:
- MongoDB Atlas

## Configuración ⚙️
1. Requisitos:
  - Java 17
  - Maven
  - MongoDB Atlas

2. Instalación:
  - git clone https://github.com/Javix01/Bellota112.git
  - cd Bellota112
  - mvn clean install

3. Configuración:
   Configurar la conexión a MongoDB en application.properties:
   ```text
   spring.data.mongodb.uri=mongodb+srv://usuario:contraseña@cluster.mongodb.net/Bellota112
   ```

5. Ejecución:
   ```text
   mvn spring-boot:run
   ```
   

## Estructura del proyecto 📂
```text
src/
├── main/
│   ├── java/
│   │   └── com/Bellota112/
│   │       ├── controllers/      # Controladores MVC
│   │       ├── domain/           # Entidades
│   │       ├── repositories/     # Repositorios MongoDB
│   │       ├── security/         # Configuración de seguridad
│   │       └── services/         # Lógica de negocio
│   └── resources/
│       ├── static/               # CSS, JS, imágenes
│       ├── templates/            # Vistas Thymeleaf
│       └── application.properties
└── test/                         # Pruebas
```

## Mejoras pendientes 📌
- Procesamiento de fotos desde la web
- Implementación de footer
- Mejoras en la gestión de contraseñas
- Notificaciones en tiempo real con WebSockets
