package com.java_project.jpa_code.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) de los datos de un archivo Excel.
 */
@Getter
@Setter
public class DtoDatos_Excel implements Serializable {

    // Variables
    private Long idCodigo;
    private String nombreProducto;
    private String tipoFarmaco;
    private String nombreGenerico;
    private Long codigoLaboratorio;
    private String nombreLaboratorio;
    private String estado;
    private Date fechaAlta;
    private Date fechaBaja;
    private String aportacion;
    private String principioActivo;
    private Long precioVentaPublico;
    private Long precioReferencia;
    private Long menorPrecioAgrupacion;
    private Long codigoAgrupacionProductor;
    private String nombreAgrupacionProducto;
    private String diagnostico;
    private String tratamientoLargo;
    private String controlMedico;
    private String medicamentoHuerfano;

    // Método adicional
    public void setOtraPropiedad(String stringCellValue) {
        // Implementar lógica si es necesario
    }
}