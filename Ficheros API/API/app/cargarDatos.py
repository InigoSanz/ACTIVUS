# Importar librerías
from flask import Flask, jsonify # Flask para la creación de la API y jsonify para devolver mensajes en formato JSON
from sqlalchemy import create_engine # SQL Alchemy para la conexión con la base de datos
from sqlalchemy.orm import sessionmaker # Sessionmaker para la creación de sesiones
import pandas as pd # Pandas para la manipulación de datos

# Función para cargar datos desde un archivo Excel a una tabla de la base de datos
def cargar_datos_desde_excel(path, url, db, table):
    # Crear la conexión con la base de datos
    engine = create_engine(url + db, echo=False)
    # Leer el archivo Excel
    df = pd.read_excel(path)
    print("Archivo leído")
    # Cargar los datos en la tabla de la base de datos, utilizamos df.to_sql para cargar los datos del DataFrame a la tabla
    df.to_sql(name=table, con=engine, if_exists='replace', index=False)

# Crear la aplicación de Flask
app = Flask(__name__)

# Configuración de la base de datos
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:proyectoFinal@localhost:3306/bbdd_proyecto'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

# Conexión a la base de datos
url = "mysql+pymysql://root:proyectoFinal@localhost:3306/"
engine = create_engine(url)
Session = sessionmaker(bind=engine)
session = Session()

# Probamos conexión
@app.route('/', methods=['GET'])
def home():
    print("API en funcionamiento")
    return jsonify({'message': 'API en funcionamiento'}), 200

# Ruta para cargar los datos desde el archivo Excel a la base de datos
@app.route('/cargar_datos', methods=['GET'])
def cargar_datos():
    # Estos podrían venir como variables de entorno o configuración
    path = r"C:\Users\Usuario\Downloads\ISD_TFG\ISD_TFG\Ficheros API\API\app\Lista_Medicamentos.xls"
    db = "bbdd_proyecto"
    table = "datos_excel"
    
    try:
        cargar_datos_desde_excel(path, url, db, table)
        # Mensaje para decirnos si se han cargado los datos
        print("Datos cargados exitosamente")
        return jsonify({'message': 'Datos cargados exitosamente'}), 200
      
    except Exception as e:
        # Mensaje para decirnos si ha habido algún error
        print("Error al cargar los datos")
        return jsonify({'error': str(e)}), 500

# Ruta para cargar los datos de compatibilidades desde el archivo Excel a la base de datos
@app.route('/cargar_datos_compatibilidades', methods=['GET'])
def cargar_datos_compatibilidades():
    # Ruta y configuración
    path = r"C:\Users\Usuario\Downloads\ISD_TFG\ISD_TFG\Ficheros API\API\app\\COMPATIBILIDAD-PA.xls"
    db = "bbdd_proyecto"
    table = "compatibilidades"
    
    try:
        cargar_datos_desde_excel(path, url, db, table)
        # Mensaje para decirnos si se han cargado los datos
        print("Datos compatibilidades cargados exitosamente")
        return jsonify({'message': 'Datos compatibilidades cargados exitosamente'}), 200
      
    except Exception as e:
        # Mensaje para decirnos si ha habido algún error
        print("Error al cargar los datos de compatibilidades")
        return jsonify({'error': str(e)}), 500

# Iniciar la aplicación
if __name__ == '__main__':
    app.run(debug=True)