package com.java_project.jpa_code.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Clase que representa un objeto de datos de un archivo Excel.
 * Se utiliza para almacenar los datos de un archivo Excel en la base de datos.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "datos_excel")
public class Datos_Excel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long idCodigo;

    @Column(name = "nombreProducto", nullable = false)
    private String nombreProducto;

    @Column(name = "tipoFarmaco", nullable = false)
    private String tipoFarmaco;

    @Column(name = "nombreGenerico", nullable = false)
    private String nombreGenerico;

    @Column(name = "codigoLaboratorio", nullable = false)
    private Long codigoLaboratorio;

    @Column(name = "nombreLaboratorio", nullable = false)
    private String nombreLaboratorio;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "fechaAlta", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

    @Column(name = "fechaBaja", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date fechaBaja;

    @Column(name = "aportacion", nullable = false)
    private String aportacion;

    @Column(name = "principioActivo", nullable = false)
    private String principioActivo;

    @Column(name = "precioVentaPublico", nullable = false)
    private Long precioVentaPublico;

    @Column(name = "precioReferencia", nullable = true)
    private Long precioReferencia;

    @Column(name = "menorPrecioAgrupacion", nullable = true)
    private Long menorPrecioAgrupacion;

    @Column(name = "codigoAgrupacionProductor", nullable = false)
    private Long codigoAgrupacionProductor;

    @Column(name = "nombreAgrupacionProducto", nullable = false)
    private String nombreAgrupacionProducto;

    @Column(name = "diagnostico", nullable = true)
    private String diagnostico;

    @Column(name = "tratamientoLargo", nullable = true)
    private String tratamientoLargo;

    @Column(name = "controlMedico", nullable = true)
    private String controlMedico;

    @Column(name = "medicamentoHuerfano", nullable = true)
    private String medicamentoHuerfano;
}