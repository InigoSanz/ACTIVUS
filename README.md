# 🚀 ACTIVUS - Compatibilidad de Medicamentos

ACTIVUS es una aplicación web diseñada para analizar la compatibilidad entre medicamentos según sus principios activos. Está planteada como **proyecto académico / demo técnica**, orientado a mostrar arquitectura, backend, frontend e integración con una API de apoyo para comparación.

## 🏥 ¿Qué hace ACTIVUS?

- Consulta y búsqueda de medicamentos en una base de datos local.
- Comparación de medicamentos para detectar interacciones.
- Sistema de usuarios con roles (médicos y administradores).
- Interfaz web con Thymeleaf, HTML, CSS y Bootstrap.
- Autenticación y control de acceso.
- Soporte multilenguaje en español e inglés.
- Recuperación de contraseña mediante correo electrónico.

## 🛠️ Tecnologías utilizadas

### Backend
- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- MySQL

### Frontend
- Thymeleaf
- HTML
- CSS
- Bootstrap
- JavaScript

### API de comparación
- Flask
- SQLAlchemy
- Pandas
- PyMySQL
- Flask-CORS

## 🔧 Instalación y configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/InigoSanz/ACTIVUS
cd ACTIVUS
```

### 2. Configuración del backend (Spring Boot + MySQL)

#### 2.1 Crear la base de datos

Ejecuta en MySQL:

```sql
CREATE DATABASE bbdd_proyecto;
```

#### 2.2 Crear configuración local no versionada

Usa como referencia el archivo:

```text
Proyecto Java/jpa_code/src/main/resources/application-example.properties
```

Y crea tu configuración local en:

```text
Proyecto Java/jpa_code/src/main/resources/application-local.properties
```

Ejemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bbdd_proyecto
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD

server.port=8082

spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

#### 2.3 Ejecutar el backend

Desde la carpeta del backend:

```bash
cd "Proyecto Java/jpa_code"
mvn spring-boot:run
```

La aplicación se iniciará en:

```text
http://localhost:8082
```

> Si usas IntelliJ, puedes ejecutar la aplicación activando el perfil `local`.

### 3. Configuración de la API en Python

Comprueba la carpeta exacta de la API en tu entorno local y ejecuta allí los comandos correspondientes.

Instalación de dependencias:

```bash
pip install -r requirements.txt
```

Ejecución de la API Flask:

```bash
flask run
```

La API estará disponible en:

```text
http://localhost:5000
```

### 4. Orden recomendado de ejecución para la API

1. Cargar datos en la base de datos:

```bash
python cargarDatos.py
```

2. Ejecutar la lógica de compatibilidad:

```bash
python compatibilidadPA.py
```

### 5. Verificación de endpoints con Postman

**URL**

```text
http://localhost:5000/pa_medicamentos
```

**Body**

```json
{
  "medicamento1": "Ibuprofeno",
  "medicamento2": "Paracetamol"
}
```

## 🔍 Uso de ACTIVUS

1. Accede a la aplicación en `http://localhost:8082`.
2. Inicia sesión con usuarios de prueba configurados en tu entorno local.
3. Busca medicamentos y revisa su información.
4. Compara medicamentos y revisa interacciones potenciales.
5. Gestiona usuarios si tu cuenta tiene permisos de administración.
6. Consulta información de medicamentos almacenados en la base de datos.

> Este repositorio no publica credenciales por defecto.

## 📌 Mejoras futuras

- Expansión de la base de datos con nuevas fuentes oficiales.
- Implementación de notificaciones y alertas personalizadas.
- Optimización del algoritmo de comparación de medicamentos para múltiples principios activos.
- Creación de un foro de discusión.
- Mejora del rendimiento de los algoritmos de detección de interacciones.

## ⚠️ Aviso importante

**ACTIVUS es un proyecto académico / demo técnica.**  
No debe utilizarse como herramienta clínica ni para tomar decisiones sanitarias reales.

## 📖 Licencia

ACTIVUS está bajo la licencia MIT.

## 🎯 Contribuciones

Si tienes dudas o propuestas de mejora, puedes abrir un issue en GitHub.

## ⚠️ Nota final

Este README puede requerir ajustes según la estructura local exacta del proyecto y del entorno de ejecución.
