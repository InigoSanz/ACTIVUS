package com.java_project.jpa_code.service;

import com.java_project.jpa_code.dto.DtoDatos_Excel;
import com.java_project.jpa_code.model.Datos_Excel;
import com.java_project.jpa_code.repository.Datos_ExcelRepository;
import com.java_project.jpa_code.service.mapper.Datos_ExcelMapper;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Servicio de negocio para la entidad Datos_Excel.
 * Implementa la interfaz AbstractBusinessService.
 * Se encarga de cargar datos desde un archivo Excel.
 */
@Service
public class Datos_ExcelService extends AbstractBusinessService<Datos_Excel, Long, DtoDatos_Excel, Datos_ExcelRepository, Datos_ExcelMapper> {

    private final Datos_ExcelRepository repo;
    private final Datos_ExcelMapper mapper;

    @Autowired
    public Datos_ExcelService(Datos_ExcelRepository repo, Datos_ExcelMapper mapper) {
        super(repo, mapper);
        this.repo = repo;
        this.mapper = mapper;
    }

    /**
     * Cargar datos desde un archivo Excel.
     * @param is InputStream del archivo Excel.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Transactional // Se requiere una transacción para guardar los datos, ya que se realizan múltiples operaciones de escritura
    public void cargarDatosDesdeExcel(InputStream is) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.rowIterator();

            if (rows.hasNext()) { // Se salta la primera fila, que contiene los encabezados
                rows.next();
            }

            // Se recorren las filas del archivo Excel
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                DtoDatos_Excel dto = new DtoDatos_Excel();
                dto.setIdCodigo((long) currentRow.getCell(0).getNumericCellValue()); // Se asume que el primer campo es numérico

                // Se verifica si el segundo campo es nulo, ya que no se puede llamar a getCell() en un campo nulo, lo que causaría una excepción
                if (currentRow.getCell(1) != null) {
                    currentRow.getCell(1).setCellType(CellType.STRING);
                    dto.setOtraPropiedad(currentRow.getCell(1).getStringCellValue());
                }

                Datos_Excel entidad = mapper.dtoToEntity(dto);
                repo.save(entidad);
            }
        } // El workbook se cierra automáticamente aquí
    }
}