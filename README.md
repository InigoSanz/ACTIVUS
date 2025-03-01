🚀 ACTIVUS - Compatibilidad de Medicamentos

ACTIVUS es una aplicación web diseñada para analizar la compatibilidad entre medicamentos según sus principios activos. Esta herramienta facilita a médicos y farmacéuticos la evaluación de interacciones entre fármacos, optimizando la seguridad en tratamientos combinados.

🏥 ¿Qué hace ACTIVUS?

✔️ 1️⃣ Consulta y búsqueda de medicamentos en una base de datos local.

✔️ 2️⃣ Comparación de medicamentos para detectar interacciones.

✔️ 3️⃣ Sistema de usuarios con roles (médicos y administradores).

✔️ 4️⃣ Interfaz moderna y adaptable con HTML, CSS y Thymeleaf.

✔️ 5️⃣ Seguridad con autenticación de usuarios y control de acceso.

✔️ 6️⃣ Multilenguaje con soporte para español e inglés.

✔️ 7️⃣ Recuperación de contraseña con envío de correo electrónico (con seguridad).

🛠️ Tecnologías Utilizadas

🔹 1️⃣ Backend (Lógica del Servidor)

Java 17 con Spring Boot (MVC, Data JPA, Security).

MySQL (WAMP/XAMPP) como base de datos relacional.

Spring Security para autenticación y roles de usuario.

🔹 2️⃣ Frontend (Interfaz de Usuario)

Thymeleaf para renderizar plantillas dinámicas.

HTML, CSS y Bootstrap para una experiencia responsiva.

JavaScript para interacciones dinámicas.

🔹 3️⃣ API de Comparación (Python)

Flask para la gestión de solicitudes REST.

SQLAlchemy y Pandas para análisis de datos.

PyMySQL para conexión con la base de datos.

Flask-CORS para comunicación segura entre frontend y API.

🔧 Instalación y Configuración

🔹 1️⃣ Clonar el Repositorio

 git clone https://github.com/InigoSanz/ACTIVUS
 cd ACTIVUS

🔹 2️⃣ Configuración del Backend (Spring Boot + MySQL)

1️⃣ Crear la base de datos en MySQL (ejecutar en MySQL Workbench o consola):

CREATE DATABASE bbdd_proyecto;

2️⃣ Configurar credenciales en application.properties (ubicado en backend/src/main/resources/):

spring.datasource.url=jdbc:mysql://localhost:3306/bbdd_proyecto
spring.datasource.username=root
spring.datasource.password=proyectoFinal
spring.jpa.hibernate.ddl-auto=update
server.port=8082  # Modificar si quieres usar otro puerto

3️⃣ Ejecutar el backend desde la terminal o el IDE:

cd backend
mvn spring-boot:run

Esto iniciará el servidor en http://localhost:8080 (o en http://localhost:8082 si configuraste otro puerto).

🔹 3️⃣ Configuración de la API en Python

1️⃣ Instalar dependencias necesarias (desde la carpeta api/):

cd api
pip install -r requirements.txt

2️⃣ Ejecutar la API Flask:

flask run

La API estará disponible en http://localhost:5000

3️⃣ Orden de Ejecución para la API:

1️⃣ Cargar datos en la base de datos:

python cargarDatos.py

2️⃣ Ejecutar la lógica de compatibilidad:

python compatibilidadPA.py

4️⃣ Verificar los endpoints con Postman:

Para consultar medicamentos:

Método: GET

URL: http://localhost:5000/medicamentos

Para comparar medicamentos:

Método: POST

URL: http://localhost:5000/comparar

Body (JSON):

{
  "medicamento1": "Ibuprofeno",
  "medicamento2": "Paracetamol"
}

🔍 Uso de ACTIVUS

1️⃣ Accede a la aplicación: Abre un navegador y ve a http://localhost:8080.

2️⃣ Inicia sesión: Crea una cuenta o usa credenciales preconfiguradas:

Usuario: admin
Contraseña: admin123

3️⃣ Busca medicamentos: Introduce el nombre de un fármaco y revisa su información.

4️⃣ Compara medicamentos: Selecciona dos y revisa sus interacciones potenciales.

5️⃣ Gestión de usuarios: Si eres administrador, puedes gestionar cuentas de usuarios.

6️⃣ Recupera información de medicamentos: Consulta detalles de cualquier fármaco en la base de datos.

📌 Mejoras Futuras

✔️ 1️⃣ Expansión de la base de datos con nuevas fuentes oficiales.

✔️ 2️⃣ Implementación de notificaciones y alertas personalizadas.

✔️ 3️⃣ Optimización del algoritmo de comparación de medicamentos para admitir múltiples principios activos.

✔️ 4️⃣ Creación de un foro para discutir interacciones y experiencias clínicas.

✔️ 5️⃣ Implementación de algoritmos más eficientes para la detección de interacciones.

📖 Licencia

ACTIVUS está bajo la Licencia MIT.

🎯 Puede probar el proyecto, si tienes dudas o mejoras abre un issue en GitHub.

⚠️ Disclaimer: Este README puede contener errores para el despliegue de la aplicación. Se recomienda consultar la documentación en la memoria y los anexos para más detalles.
