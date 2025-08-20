package com.rodrigo.conversor;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase para probar el manejo avanzado de respuestas HTTP
 * Tarjeta 6: Pruebas de HttpResponse
 */
public class TestRespuestaHTTP {

    public static void main(String[] args) {
        System.out.println("🧪 INICIANDO PRUEBAS DE RESPUESTA HTTP AVANZADA");
        System.out.println("==============================================");

        SolicitudHTTP solicitudHTTP = new SolicitudHTTP();

        // Prueba 1: Análisis completo de respuesta exitosa
        System.out.println("\n🎯 PRUEBA 1: Análisis Completo - USD");
        try {
            RespuestaHTTP respuesta = solicitudHTTP.obtenerRespuestaProcesada("USD");
            respuesta.mostrarAnalisisCompleto();

            // Verificar información específica
            if (respuesta.esExitosa()) {
                RespuestaHTTP.InformacionMoneda info = respuesta.extraerInformacionMoneda();
                System.out.println("\n💰 Tasas específicas:");
                System.out.println("   USD → EUR: " + info.obtenerTasa("EUR"));
                System.out.println("   USD → GBP: " + info.obtenerTasa("GBP"));
                System.out.println("   USD → JPY: " + info.obtenerTasa("JPY"));
            }

        } catch (Exception e) {
            System.out.println("❌ Error en prueba 1: " + e.getMessage());
        }

        // Prueba 2: Análisis de diferentes códigos de estado
        System.out.println("\n🔍 PRUEBA 2: Análisis con EUR");
        try {
            RespuestaHTTP respuestaEUR = solicitudHTTP.obtenerRespuestaProcesada("EUR");

            System.out.println("📊 Estado de la respuesta EUR:");
            System.out.println("   Código: " + respuestaEUR.obtenerCodigoEstado());
            System.out.println("   Exitosa: " + respuestaEUR.esExitosa());
            System.out.println("   Mensaje: " + respuestaEUR.obtenerMensajeEstado());

            if (respuestaEUR.esExitosa()) {
                RespuestaHTTP.InformacionMoneda infoEUR = respuestaEUR.extraerInformacionMoneda();
                System.out.println("   Moneda base: " + infoEUR.monedaBase);
                System.out.println("   EUR → USD: " + infoEUR.obtenerTasa("USD"));
            }

        } catch (Exception e) {
            System.out.println("❌ Error en prueba 2: " + e.getMessage());
        }

        // Prueba 3: Headers específicos
        System.out.println("\n📄 PRUEBA 3: Análisis de Headers");
        try {
            RespuestaHTTP respuesta = solicitudHTTP.obtenerRespuestaProcesada("USD");

            System.out.println("🔍 Headers importantes:");
            String contentType = respuesta.obtenerHeader("content-type");
            String server = respuesta.obtenerHeader("server");
            String date = respuesta.obtenerHeader("date");

            System.out.println("   Content-Type: " + (contentType != null ? contentType : "No disponible"));
            System.out.println("   Server: " + (server != null ? server : "No disponible"));
            System.out.println("   Date: " + (date != null ? date : "No disponible"));

            System.out.println("\n📊 Total de headers: " + respuesta.obtenerHeaders().size());

        } catch (Exception e) {
            System.out.println("❌ Error en prueba 3: " + e.getMessage());
        }

        // Prueba 4: Manejo de errores simulado
        System.out.println("\n⚠️ PRUEBA 4: Simulación con Moneda Inválida");
        try {
            RespuestaHTTP respuestaInvalida = solicitudHTTP.obtenerRespuestaProcesada("INVALID");

            System.out.println("📊 Respuesta con moneda inválida:");
            System.out.println("   Código: " + respuestaInvalida.obtenerCodigoEstado());
            System.out.println("   Exitosa: " + respuestaInvalida.esExitosa());
            System.out.println("   Mensaje: " + respuestaInvalida.obtenerMensajeEstado());

            if (!respuestaInvalida.esExitosa()) {
                System.out.println("   ✅ Error manejado correctamente");
            }

        } catch (Exception e) {
            System.out.println("ℹ️ Error esperado con moneda inválida: " + e.getMessage());
        }

        // Prueba 5: Comparación de respuestas
        System.out.println("\n🔄 PRUEBA 5: Comparación USD vs EUR");
        try {
            RespuestaHTTP respuestaUSD = solicitudHTTP.obtenerRespuestaProcesada("USD");
            RespuestaHTTP respuestaEUR = solicitudHTTP.obtenerRespuestaProcesada("EUR");

            if (respuestaUSD.esExitosa() && respuestaEUR.esExitosa()) {
                RespuestaHTTP.InformacionMoneda infoUSD = respuestaUSD.extraerInformacionMoneda();
                RespuestaHTTP.InformacionMoneda infoEUR = respuestaEUR.extraerInformacionMoneda();

                double usdToEur = infoUSD.obtenerTasa("EUR");
                double eurToUsd = infoEUR.obtenerTasa("USD");

                System.out.println("📈 Tasas cruzadas:");
                System.out.println("   1 USD = " + usdToEur + " EUR");
                System.out.println("   1 EUR = " + eurToUsd + " USD");
                System.out.println("   ✅ Verificación: " + String.format("%.6f", usdToEur * eurToUsd) + " ≈ 1.0");
            }

        } catch (Exception e) {
            System.out.println("❌ Error en prueba 5: " + e.getMessage());
        }

        // Prueba 6: Deserialización a POJO con RespuestaAPI - Tarjeta 7
        System.out.println("\n📦 PRUEBA 6: Deserialización a POJO (Tarjeta 7)");
        try {
            RespuestaHTTP respuesta = solicitudHTTP.obtenerRespuestaProcesada("USD");
            /*
            // ✅ DEBUG: Ver el JSON completo
            System.out.println("📋 JSON COMPLETO (primeros 500 caracteres):");
            String jsonCompleto = respuesta.obtenerCuerpoTexto();
            System.out.println(jsonCompleto.substring(0, Math.min(500, jsonCompleto.length())) + "...");
            */

            RespuestaAPI respuestaAPI = respuesta.obtenerComoRespuestaAPI();
            /*

            // ✅ DEBUG: Ver el estado del objeto
            System.out.println("🔍 DEBUG - Estado de RespuestaAPI:");
            System.out.println("Result: " + respuestaAPI.getResult());
            System.out.println("Base: " + respuestaAPI.getCodigoBase());
            System.out.println("TasasConversion is null: " + (respuestaAPI.getTasasConversion() == null));
            */

            System.out.println("✅ POJO creado exitosamente");
            System.out.println("   Base: " + respuestaAPI.getCodigoBase());
            System.out.println("   Result: " + respuestaAPI.getResult());
            System.out.println("   Tasas: " + respuestaAPI.getTasasConversion().size());

            // Validar que las tasas contengan USD
            if (respuestaAPI.getTasasConversion().containsKey("USD")) {
                System.out.println("   ✅ USD está en las tasas: " + respuestaAPI.getTasasConversion().get("USD"));
            }

            // Comparar con el método tradicional
            RespuestaHTTP.InformacionMoneda info = respuesta.extraerInformacionMoneda();
            double tasaTradicional = info.obtenerTasa("EUR");
            double tasaPojo = respuestaAPI.obtenerTasa("EUR");

            System.out.println("   🔄 Comparación EUR:");
            System.out.println("      Tradicional: " + tasaTradicional);
            System.out.println("      POJO: " + tasaPojo);
            System.out.println("      ✅ Diferencia: " + Math.abs(tasaTradicional - tasaPojo));

        } catch (Exception e) {
            System.out.println("❌ Error en prueba 6: " + e.getMessage());
        }

        // Prueba 7: Filtrado de monedas - Tarjeta 8
        System.out.println("\n🎯 PRUEBA 7: Filtrado de Monedas (Tarjeta 8)");
        try {
            RespuestaHTTP respuesta = solicitudHTTP.obtenerRespuestaProcesada("USD");

            // Filtrar solo monedas latinoamericanas + USD
            Set<String> monedasLatam = new HashSet<>(Arrays.asList(
                    "USD", "ARS", "BOB", "BRL", "CLP", "COP", "MXN"
            ));

            FiltradorMonedas filtrador = new FiltradorMonedas(monedasLatam);

            // Método 1: Filtrar desde JsonObject
            JsonObject tasasCompletas = respuesta.obtenerDatosJSON().getAsJsonObject("rates");
            JsonObject tasasFiltradas = filtrador.filtrarTasas(tasasCompletas);

            System.out.println("✅ Tasas filtradas desde JsonObject:");
            filtrador.mostrarTasasFiltradas(tasasFiltradas);

            // Método 2: Filtrar desde RespuestaAPI
            RespuestaAPI respuestaAPI = respuesta.obtenerComoRespuestaAPI();
            JsonObject tasasFiltradas2 = filtrador.filtrarTasas(respuestaAPI);

            System.out.println("\n✅ Tasas filtradas desde RespuestaAPI:");
            filtrador.mostrarTasasFiltradas(tasasFiltradas2);

            // Verificar que tenemos las monedas correctas
            System.out.println("\n📊 Monedas incluidas en el filtro:");
            for (String moneda : monedasLatam) {
                if (tasasFiltradas.has(moneda)) {
                    System.out.println("   ✅ " + moneda + ": " + tasasFiltradas.get(moneda).getAsDouble());
                } else {
                    System.out.println("   ❌ " + moneda + ": No disponible");
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Error en prueba 7: " + e.getMessage());
        }




        System.out.println("\n🎉 PRUEBAS DE RESPUESTA HTTP COMPLETADAS");
        System.out.println("=========================================");
    }

}