package com.rodrigo.conversor;

import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase para filtrar monedas específicas de la respuesta JSON
 * Tarjeta 8: Filtrando las monedas
 */
public class FiltradorMonedas {

    private final Set<String> monedasFiltradas;

    public FiltradorMonedas() {
        // Monedas por defecto para el desafío
        this.monedasFiltradas = new HashSet<>(Arrays.asList(
                "USD", "ARS", "BOB", "BRL", "CLP", "COP"
        ));
    }

    public FiltradorMonedas(Set<String> monedas) {
        this.monedasFiltradas = new HashSet<>(monedas);
    }

    /**
     * Filtra las tasas de cambio para mostrar solo las monedas especificadas
     * @param tasasCompletas JsonObject con todas las tasas de la API
     * @return JsonObject solo con las monedas filtradas
     */
    public JsonObject filtrarTasas(JsonObject tasasCompletas) {
        JsonObject tasasFiltradas = new JsonObject();

        for (String codigoMoneda : monedasFiltradas) {
            if (tasasCompletas.has(codigoMoneda)) {
                double tasa = tasasCompletas.get(codigoMoneda).getAsDouble();
                tasasFiltradas.addProperty(codigoMoneda, tasa);
            }
        }

        return tasasFiltradas;
    }

    /**
     * Filtra las tasas usando un objeto RespuestaAPI
     * @param respuestaAPI Objeto con todas las tasas
     * @return JsonObject con las tasas filtradas
     */
    public JsonObject filtrarTasas(RespuestaAPI respuestaAPI) {
        JsonObject tasasFiltradas = new JsonObject();

        for (String codigoMoneda : monedasFiltradas) {
            if (respuestaAPI.getTasasConversion().containsKey(codigoMoneda)) {
                double tasa = respuestaAPI.getTasasConversion().get(codigoMoneda);
                tasasFiltradas.addProperty(codigoMoneda, tasa);
            }
        }

        return tasasFiltradas;
    }

    public void agregarMoneda(String codigoMoneda) {
        monedasFiltradas.add(codigoMoneda.toUpperCase());
    }

    public void removerMoneda(String codigoMoneda) {
        monedasFiltradas.remove(codigoMoneda.toUpperCase());
    }

    public Set<String> getMonedasFiltradas() {
        return new HashSet<>(monedasFiltradas);
    }

    /**
     * Muestra las tasas filtradas de forma formateada
     * @param tasasFiltradas JsonObject con las tasas a mostrar
     */
    public void mostrarTasasFiltradas(JsonObject tasasFiltradas) {
        System.out.println("\n💱 TASAS DE CAMBIO FILTRADAS");
        System.out.println("============================");

        tasasFiltradas.entrySet().forEach(entry -> {
            String moneda = entry.getKey();
            double tasa = entry.getValue().getAsDouble();
            System.out.printf("   %s: %.4f%n", moneda, tasa);
        });
    }
}