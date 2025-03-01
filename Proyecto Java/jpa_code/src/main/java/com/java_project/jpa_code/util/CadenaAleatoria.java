package com.java_project.jpa_code.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase que genera cadenas aleatorias de caracteres.
 *
 * @version 1.0
 */
public class CadenaAleatoria {

    public static int numeroAleatorioEnRango(int minimo, int maximo) {

        // nextInt regresa en rango pero con límite superior exclusivo, por eso se suma 1
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }

    public static String obtenerToken(int longitud) {

        // El banco de caracteres
        String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        // La cadena donde se irán agregando caracteres aleatorios
        String cadena = "";
        for (int x = 0; x < longitud; x++) {
            int indiceAleatorio = numeroAleatorioEnRango(0, banco.length() - 1);
            char caracterAleatorio = banco.charAt(indiceAleatorio);
            cadena += caracterAleatorio;
        }
        return cadena;
    }
}
