# Red Social Backend

Backend para una red social desarrollado con Spring Boot que proporciona una API RESTful para la gestión de usuarios, publicaciones, comentarios, likes y follows.

## 🚀 Tecnologías Utilizadas

- **Java 17** - Lenguaje de programación principal
- **Spring Boot 4.0.3** - Framework principal
- **Spring Data JPA** - Para el acceso a datos
- **Spring Security** - Para la autenticación y autorización
- **PostgreSQL** - Base de datos principal
- **Liquibase** - Para la gestión de migraciones de base de datos
- **JWT (JSON Web Tokens)** - Para la autenticación basada en tokens
- **Lombok** - Para reducir código boilerplate
- **Maven** - Para la gestión de dependencias
- **Docker** - Para la contenerización

## 📁 Estructura del Proyecto

El proyecto sigue una arquitectura modular organizada en los siguientes paquetes:

```
src/main/java/com/juan/red_social_backend/
├── identity/          # Gestión de usuarios y autenticación
│   ├── controllers/   # Controladores REST para usuarios
│   ├── dto/          # Objetos de transferencia de datos
│   ├── entities/     # Entidades de la base de datos
│   ├── repositories/ # Repositorios JPA
│   └── services/     # Lógica de negocio
├── content/          # Gestión de contenido (posts, comentarios, likes)
│   ├── controllers/
│   ├── dto/
│   ├── entities/
│   ├── repositories/
│   └── services/
├── social/           # Funcionalidades sociales (follows, perfiles)
│   ├── controllers/
│   ├── entities/
│   └── services/
└── shared/           # Componentes compartidos
    ├── config/       # Configuración de Spring
    ├── exceptions/   # Excepciones personalizadas
    └── jwtUtils/     # Utilidades para JWT
```

## 🛠️ Configuración y Ejecución

### Prerrequisitos

- Java 17 o superior
- Maven 3.6 o superior
- PostgreSQL 15 o Docker
- Docker (opcional, para contenerización)

### Ejecución con Docker (Recomendado)

1. Clonar el repositorio:
```bash
git clone <repository-url>
cd red_social_backend
```

2. Ejecutar con Docker Compose:
```bash
docker-compose up --build
```

Esto iniciará:
- Base de datos PostgreSQL en el puerto 5432
- Aplicación Spring Boot en el puerto 8080

### Ejecución Local

1. Configurar la base de datos PostgreSQL:
```sql
CREATE DATABASE redsocial_db;
CREATE USER postgres WITH PASSWORD 'postgres123';
GRANT ALL PRIVILEGES ON DATABASE redsocial_db TO postgres;
```

2. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

## 📊 Funcionalidades Principales

### 🔐 Autenticación y Autorización
- Registro de nuevos usuarios
- Inicio de sesión con JWT
- Protección de endpoints con Spring Security

### 👥 Gestión de Usuarios
- Creación y actualización de perfiles
- Información básica de usuario

### 📝 Sistema de Publicaciones
- Crear, editar y eliminar posts
- Obtener lista de posts
- Posts por usuario

### 💬 Sistema de Comentarios
- Comentar posts
- Editar y eliminar comentarios propios
- Listar comentarios por post

### ❤️ Sistema de Likes
- Dar/quitar like a posts
- Contar likes por post
- Verificar si un usuario dio like

### 👥 Sistema de Follows
- Seguir/dejar de seguir usuarios
- Obtener lista de seguidores
- Obtener lista de usuarios seguidos

## 🔌 Endpoints Principales

### Autenticación
- `POST /api/auth/register` - Registrar nuevo usuario
- `POST /api/auth/login` - Iniciar sesión

### Usuarios
- `GET /api/users/{id}` - Obtener información de usuario
- `PUT /api/users/{id}` - Actualizar perfil

### Posts
- `GET /api/posts` - Obtener todos los posts
- `POST /api/posts` - Crear nuevo post
- `GET /api/posts/{id}` - Obtener post específico
- `PUT /api/posts/{id}` - Actualizar post
- `DELETE /api/posts/{id}` - Eliminar post

### Comentarios
- `GET /api/posts/{postId}/comments` - Obtener comentarios de un post
- `POST /api/posts/{postId}/comments` - Comentar un post
- `PUT /api/comments/{id}` - Actualizar comentario
- `DELETE /api/comments/{id}` - Eliminar comentario

### Likes
- `POST /api/posts/{postId}/like` - Dar like a un post
- `DELETE /api/posts/{postId}/like` - Quitar like a un post
- `GET /api/posts/{postId}/likes` - Obtener likes de un post

### Follows
- `POST /api/users/{userId}/follow` - Seguir usuario
- `DELETE /api/users/{userId}/follow` - Dejar de seguir usuario
- `GET /api/users/{userId}/followers` - Obtener seguidores
- `GET /api/users/{userId}/following` - Obtener seguidos

## 🗄️ Base de Datos

El proyecto utiliza PostgreSQL como base de datos principal y Liquibase para la gestión de migraciones. Las migraciones se encuentran en:

```
src/main/resources/db/changelog/
```

## 🔧 Variables de Entorno

Las siguientes variables de entorno pueden ser configuradas:

- `SPRING_DATASOURCE_URL` - URL de conexión a PostgreSQL
- `SPRING_DATASOURCE_USERNAME` - Usuario de la base de datos
- `SPRING_DATASOURCE_PASSWORD` - Contraseña de la base de datos
- `JWT_SECRET_KEY` - Clave secreta para JWT

## 🧪 Testing

El proyecto incluye pruebas unitarias para los servicios principales. Para ejecutar las pruebas:

```bash
mvn test
```

## 📝 Notas Adicionales

- Se implementan excepciones personalizadas para un mejor manejo de errores
- La autenticación se basa en JWT tokens
- Se sigue una estructura de paquetes por dominio (identity, content, social)


