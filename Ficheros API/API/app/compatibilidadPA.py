# Importar librerías
from flask import Flask, request, jsonify
from sqlalchemy import create_engine, text
import pandas as pd
import re
from flask_cors import CORS

app = Flask(__name__)
CORS(app)  # Habilitar CORS para todas las rutas

# Configuración de la base de datos
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:proyectoFinal@localhost:3306/bbdd_proyecto'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

# Conexión a la base de datos
url = "mysql+pymysql://root:proyectoFinal@localhost:3306/bbdd_proyecto"
engine = create_engine(url)

# Crear la tabla resultados_compatibilidades si no existe y eliminarla si existe
def crear_tabla_resultados():
    with engine.connect() as conn:
        conn.execute(text("DROP TABLE IF EXISTS resultados_compatibilidades"))  # Eliminar la tabla si existe así no se repiten los datos
        print("Tabla resultados_compatibilidades eliminada")
        conn.execute(text("""
            CREATE TABLE IF NOT EXISTS resultados_compatibilidades (
                id INT AUTO_INCREMENT PRIMARY KEY,
                medicamento1 VARCHAR(255),
                medicamento2 VARCHAR(255),
                principioActivo1 VARCHAR(255),
                principioActivo2 VARCHAR(255),
                compatibilidad VARCHAR(255)
            )
        """))
    print("Tabla resultados_compatibilidades creada")

# Llamada a la función para crear la tabla resultados_compatibilidades
crear_tabla_resultados()

# Diccionario de mapeo de principios activos
diccionario_mapeo = {
    'HEPARINA SODICA': 'HEPARINA',
    'ESOMEPRAZOL MAGNESICO': 'OMEPRAZOL',
    'ESOMEPRAZOL SODICO': 'OMEPRAZOL',
    'ESOMEPRAZOL': 'OMEPRAZOL',
    'ESOMEPRAZOL MAGNESICO DIHIDRATO': 'OMEPRAZOL',
    'TRANEXAMICO ACIDO': 'ACIDO TRANEXAMICO',
    'AMIKACINA SULFATO': 'AMIKACINA',
    'AMIDARONA HIDROCLORURO': 'AMIODARONA',
    'AMPICILINA TRIHIDRATO': 'AMPICILINA',
    'AMPICILINA SODICA': 'AMPICILINA',
    'AMPICILINA-BENZATINA': 'AMPICILINA',
    'SODIO BICARBONATO': 'BICARBONATO DE SODIO',
    'CALCIO CLORURO DIHIDRATO': 'CALCIO CLORURO',
    'LACTOGLUCONATO CALCIO': 'CALCIO GLUCONATO',
    'CEFAZOLINA SODICA': 'CEFALOTINA',
    'CEFOTAXIMA SODICA': 'CEFOTAXIMA',
    'CEFTAZIDIMA PENTAHIDRATO': 'CEFTAZIDIMA',
    'CEFTRIAXONA SODICA': 'CEFTRIAXONA',
    'CEFUROXIMA AXETILO': 'CEFUROXIMA',
    'CEFUROXIMA SODICA': 'CEFUROXIMA',
    'CICLOSPORINA PARA MICROEMULSION': 'CICLOSPORINA',
    'CIPROFLOXACINO': 'CIPROFLOXACINA',
    'CIPROFLOXACINO HIDROCLORURO': 'CIPROFLOXACINA',
    'CLARITROMICINA CITRATO': 'CLARITROMICINA',
    'CLINDAMICINA HIDROCLORURO': 'CLINDAMICINA',
    'CLINDAMICINA FOSFATO': 'CLINDAMICINA',
    'CLONIDINA HIDROCLORURO': 'CLONIDINA',
    'CLORPROMAZINA HIDROCLORURO': 'CLORPROMAZINA',
    'DEXAMETASONA FOSFATO DISODIO': 'DEXAMETASONA',
    'DEXAMETASONA FOSFATO SODIO': 'DEXAMETASONA',
    'ENALAPRIL MALEATO': 'ENALAPRILATO',
    'ESTREPTOMICINA SULFATO': 'ESTREPTOMICINA',
    'FENITOINA SODICA': 'FENITOINA',
    'FENTANILO CITRATO': 'FENTANILO',
    'REMIFENTANILO': 'FENTANILO',
    'FUROSEMIDA XANTINOL': 'FUROSEMIDA',
    'GENTAMICINA SULFATO': 'GENTAMICINA',
    'HIDROCORTISONA ACETATO': 'HIDROCORTISONA',
    'HIDROCORTISONA BUTEPRATO': 'HIDROCORTISONA',
    'HIDROCORTISONA ACEPONATO': 'HIDROCORTISONA',
    'INDOMETACINA': 'INDOMETACINA SODICA',
    'INSULINA HUMANA ISOFANA': 'INSULINA REGULAR',
    'LABETALOL HIDROCLORURO': 'LABETALOL',
    'LIDOCAINA HIDROCLORURO': 'LIDOCAINA',
    'LORAZEPAM PIVALATO': 'LORAZEPAM',
    'METILPREDNISOLONA ACEPONATO': 'METILPREDNISOLONA',
    'METILPREDNISOLONA SUCCINATO SODIO': 'METILPREDNISOLONA',
    'METOCLOPRAMIDA HIDROCLORURO': 'METOCLOPRAMIDA',
    'METRONIDAZOL BENZOATO': 'METRONIDAZOL',
    'MIDAZOLAM HIDROCLORURO': 'MIDAZOLAM',
    'MORFINA HIDROCLORURO': 'MORFINA',
    'MORFINA SULFATO': 'MORFINA',
    'MORFINA SULFATO PENTAHIDRATO': 'MORFINA',
    'OCTREOTIDA ACETATO': 'OCTREOTIDA',
    'BENCILPENICILINA SODICA': 'PENICILINA G SODICA',
    'POTASIO CLORURO ANHIDRO': 'POTASIO CLORURO',
    'FENILPROPANOLAMINA HIDROCLORURO': 'PROPANOLOL',
    'TACROLIMUS MONOHIDRATO': 'TACROLIMUS',
    'SODIO CARBONATO': 'CARBONATO DE SODIO',
    'TRIMETROPINA': 'TRIMETOP-SULFAMETOXAZOL',
    'VANCOMICINA HIDROCLORURO': 'VANCOMICINA',
    # Agregar más mapeos según sea necesario
}

# Función para normalizar los principios activos
def normalizar_principio_activo(principio_activo):
    return diccionario_mapeo.get(principio_activo.strip().upper(), principio_activo.strip().upper())

# Función para limpiar la cadena de caracteres


def limpiar_cadena(cadena):
    return re.sub(r'[^a-zA-Z0-9\s]', '', cadena).strip().lower()

# Ruta principal para comprobar que la API está funcionando
@app.route('/', methods=['GET'])
def home():
    print("API en funcionamiento")
    return jsonify({'message': 'API en funcionamiento'}), 200

# Ruta para consultar los datos
@app.route('/consultar_datos_pa', methods=['GET'])
def consultar_datos_pa():
    try:
        datos = pd.read_sql("SELECT * FROM datos_excel", engine)
        print("Datos consultados exitosamente")
        return jsonify(datos.to_dict(orient='records')), 200
    except Exception as e:
        print("Error al consultar los datos")
        return jsonify({'error': str(e)}), 500

# Ruta para consultar información del medicamento por nombre
@app.route('/consulta_medicamento', methods=['GET'])
def consulta_medicamento():
    medicamento = request.args.get('medicamento', '')
    if medicamento:
        try:
            query = text("""
            SELECT idCodigo, nombreProducto, tipoFarmaco, codigoLaboratorio, estado, principioActivo, precioVentaPublico
            FROM datos_excel
            WHERE nombreProducto LIKE :medicamento
            """)
            resultados = pd.read_sql(query, engine, params={'medicamento': f'%{medicamento}%'})
            print("Resultados obtenidos de la base de datos:", resultados)
            return jsonify(resultados.to_dict(orient='records')), 200
        except Exception as e:
            return jsonify({'error': str(e)}), 500
    return jsonify({'error': 'No se proporcionó un nombre de medicamento'}), 400

# Función para obtener los principios activos y nombres completos de los medicamentos dados
def obtener_pa_medicamentos(medicamento1, medicamento2):
    try:
        medicamento1_limpio = limpiar_cadena(medicamento1)
        medicamento2_limpio = limpiar_cadena(medicamento2)
        
        # Consultar los principios activos de los medicamentos, se busca por nombre de medicamento y se eliminan los caracteres especiales convertidos a minúsculas
        query = text("""
        SELECT DISTINCT nombreProducto, principioActivo
        FROM datos_excel
        WHERE LOWER(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(nombreProducto, ',', ''), '%', ''), '/', ''), '+', ''), ' mg', ''), ' g', '')) LIKE :medicamento1
        OR LOWER(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(nombreProducto, ',', ''), '%', ''), '/', ''), '+', ''), ' mg', ''), ' g', '')) LIKE :medicamento2
        """)
        resultados = pd.read_sql(query, engine, params={'medicamento1': f'%{medicamento1_limpio}%', 'medicamento2': f'%{medicamento2_limpio}%'})
        
        if resultados.empty:
            return {'error': 'No se encontraron principios activos para los medicamentos proporcionados'}
        
        print("Resultados obtenidos de la base de datos:", resultados)

        # Listas para almacenar los nombres de los medicamentos y sus principios activos
        nombres_medicamento1 = []
        nombres_medicamento2 = []
        pa_medicamento1 = []
        pa_medicamento2 = []
        
        # Iterar sobre los resultados y almacenar los nombres de los medicamentos y sus principios activos, se utiliza la función normalizar_principio_activo para mapear los principios activos
        for index, row in resultados.iterrows():
            nombre_producto = row['nombreProducto']
            principio_activo_normalizado = normalizar_principio_activo(row['principioActivo'])
            nombre_producto_limpio = limpiar_cadena(nombre_producto)
            if medicamento1_limpio in nombre_producto_limpio:
                nombres_medicamento1.append(nombre_producto)
                pa_medicamento1.append(principio_activo_normalizado)
            if medicamento2_limpio in nombre_producto_limpio:
                nombres_medicamento2.append(nombre_producto)
                pa_medicamento2.append(principio_activo_normalizado)
        
        print("Datos consultados exitosamente")
        return {
            'nombres_medicamento1': nombres_medicamento1,
            'principios_activos_medicamento1': pa_medicamento1,
            'nombres_medicamento2': nombres_medicamento2,
            'principios_activos_medicamento2': pa_medicamento2
        }
    except Exception as e:
        print("Error al consultar los principios activos de los medicamentos:", str(e))
        return {'error': f"Error al consultar los principios activos de los medicamentos: {str(e)}"}

# Función para verificar la compatibilidad entre principios activos
def verificar_compatibilidad(nombres_medicamento1, pa_medicamento1, nombres_medicamento2, pa_medicamento2):
    try:
        df_compatibilidades = pd.read_sql("SELECT * FROM compatibilidades", engine)
        df_compatibilidades.set_index(df_compatibilidades.columns[0], inplace=True)

        print("Tabla de compatibilidades cargada:", df_compatibilidades)

        # Lista para almacenar los mensajes de compatibilidad, se utilizan indices para ir comparando los principios activos de los medicamentos encontrados
        mensajes_compatibilidad = []
        for idx1, pa1 in enumerate(pa_medicamento1):
            for idx2, pa2 in enumerate(pa_medicamento2):
                pa1_normalizado = normalizar_principio_activo(pa1)
                pa2_normalizado = normalizar_principio_activo(pa2)
                print(f"Verificando compatibilidad entre {pa1_normalizado} y {pa2_normalizado}")
                if pa1_normalizado in df_compatibilidades.index and pa2_normalizado in df_compatibilidades.columns:
                    valor = df_compatibilidades.at[pa1_normalizado, pa2_normalizado]
                    if valor == 1:
                        mensaje = f"{pa1_normalizado} y {pa2_normalizado} son compatibles."
                    elif valor == 2:
                        mensaje = f"{pa1_normalizado} y {pa2_normalizado} no son compatibles."
                    elif valor == 3:
                        mensaje = f"{pa1_normalizado} y {pa2_normalizado} pueden ser peligrosos juntos."
                    elif valor == 4:
                        mensaje = f"No se sabe si {pa1_normalizado} y {pa2_normalizado} son compatibles."

                    mensajes_compatibilidad.append({
                        'medicamento1': nombres_medicamento1[idx1],
                        'medicamento2': nombres_medicamento2[idx2],
                        'principioActivo1': pa1_normalizado,
                        'principioActivo2': pa2_normalizado,
                        'mensaje': mensaje
                    })
                else:
                    print(f"No se encontró compatibilidad para {pa1_normalizado} y {pa2_normalizado}")
        
        return mensajes_compatibilidad
    except Exception as e:
        print("Error al verificar la compatibilidad entre los principios activos:", str(e))
        return {'error': f"Error al verificar la compatibilidad entre los principios activos: {str(e)}"}

# Ruta para obtener la compatibilidad entre dos medicamentos
@app.route('/pa_medicamentos', methods=['POST'])
def pa_medicamentos():
    content = request.json
    medicamento1 = content['medicamento1']
    medicamento2 = content['medicamento2']
    resultado = obtener_pa_medicamentos(medicamento1, medicamento2) # Obtener los principios activos de los medicamentos
    if 'error' in resultado:
        return jsonify(resultado), 500

    nombres_medicamento1 = resultado['nombres_medicamento1']
    pa_medicamento1 = resultado['principios_activos_medicamento1']
    nombres_medicamento2 = resultado['nombres_medicamento2']
    pa_medicamento2 = resultado['principios_activos_medicamento2']

    # Verificar la compatibilidad entre los principios activos
    compatibilidad = verificar_compatibilidad(nombres_medicamento1, pa_medicamento1, nombres_medicamento2, pa_medicamento2)
    if 'error' in compatibilidad:
        return jsonify(compatibilidad), 500


    resultados_unicos = {f"{res['medicamento1']}_{res['medicamento2']}_{res['principioActivo1']}_{res['principioActivo2']}": res for res in compatibilidad}

    with engine.connect() as conn:
        for key, res in resultados_unicos.items():
            conn.execute(text("""
                INSERT INTO resultados_compatibilidades (medicamento1, medicamento2, principioActivo1, principioActivo2, compatibilidad)
                VALUES (:medicamento1, :medicamento2, :principioActivo1, :principioActivo2, :mensaje)
            """), res)

    # Retornar los resultados en formato JSON
    return jsonify({
        'medicamento1': medicamento1,
        'principios_activos_medicamento1': pa_medicamento1,
        'medicamento2': medicamento2,
        'principios_activos_medicamento2': pa_medicamento2,
        'compatibilidad': list(resultados_unicos.values())
    }), 200

# Cargar datos
if __name__ == '__main__':
    app.run(debug=True)