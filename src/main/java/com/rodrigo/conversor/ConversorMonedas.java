package com.rodrigo.conversor;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * Clase para realizar conversiones entre monedas
 * Tarjeta 9: Convertiendo los valores
 */
public class ConversorMonedas {

    private final Map<String, Double> tasasConversion;
    private final DecimalFormat formateador;

    public ConversorMonedas(Map<String, Double> tasasConversion) {
        this.tasasConversion = tasasConversion;
        this.formateador = new DecimalFormat("#,##0.0000");
    }

    /**
     * Convierte una cantidad de una moneda a otra
     * @param cantidad Cantidad a convertir
     * @param monedaOrigen Moneda de origen (ej: "USD")
     * @param monedaDestino Moneda de destino (ej: "EUR")
     * @return double cantidad convertida
     */
    public double convertir(double cantidad, String monedaOrigen, String monedaDestino) {
        validarMoneda(monedaOrigen);
        validarMoneda(monedaDestino);

        if (monedaOrigen.equals(monedaDestino)) {
            return cantidad;
        }

        // Convertir a USD primero (moneda base)
        double cantidadEnUSD;
        if (monedaOrigen.equals("USD")) {
            cantidadEnUSD = cantidad;
        } else {
            double tasaOrigen = tasasConversion.get(monedaOrigen);
            cantidadEnUSD = cantidad / tasaOrigen;
        }

        // Convertir de USD a moneda destino
        if (monedaDestino.equals("USD")) {
            return cantidadEnUSD;
        } else {
            double tasaDestino = tasasConversion.get(monedaDestino);
            return cantidadEnUSD * tasaDestino;
        }
    }

    /**
     * Convierte y formatea el resultado para mostrar al usuario
     */
    public String convertirYFormatear(double cantidad, String monedaOrigen, String monedaDestino) {
        double resultado = convertir(cantidad, monedaOrigen, monedaDestino);
        return formateador.format(resultado);
    }

    private void validarMoneda(String codigoMoneda) {
        if (!tasasConversion.containsKey(codigoMoneda)) {
            throw new IllegalArgumentException("Moneda no disponible: " + codigoMoneda);
        }
    }

    /**
     * Muestra una conversión formateada
     */
    public void mostrarConversion(double cantidad, String monedaOrigen, String monedaDestino) {
        try {
            String resultado = convertirYFormatear(cantidad, monedaOrigen, monedaDestino);
            System.out.printf("\n💱 %s %.2f = %s %s%n",
                    monedaOrigen, cantidad, monedaDestino, resultado);

            // Mostrar tasa de cambio también
            double tasa = obtenerTasaConversion(monedaOrigen, monedaDestino);
            System.out.printf("💹 Tasa de cambio: 1 %s = %.6f %s%n",
                    monedaOrigen, tasa, monedaDestino);

        } catch (Exception e) {
            System.out.println("❌ Error en conversión: " + e.getMessage());
        }
    }

    /**
     * Obtiene la tasa de conversión entre dos monedas
     */
    public double obtenerTasaConversion(String monedaOrigen, String monedaDestino) {
        validarMoneda(monedaOrigen);
        validarMoneda(monedaDestino);

        if (monedaOrigen.equals(monedaDestino)) {
            return 1.0;
        }

        // Tasa = (1 USD / monedaOrigen) * (monedaDestino / 1 USD)
        double tasaOrigen = monedaOrigen.equals("USD") ? 1.0 : tasasConversion.get(monedaOrigen);
        double tasaDestino = monedaDestino.equals("USD") ? 1.0 : tasasConversion.get(monedaDestino);

        return tasaDestino / tasaOrigen;
    }

    /**
     * Verifica si una moneda está disponible para conversión
     */
    public boolean monedaDisponible(String codigoMoneda) {
        return tasasConversion.containsKey(codigoMoneda);
    }

    /**
     * Obtiene la lista de monedas disponibles
     */
    public String[] obtenerMonedasDisponibles() {
        return tasasConversion.keySet().toArray(new String[0]);
    }
}