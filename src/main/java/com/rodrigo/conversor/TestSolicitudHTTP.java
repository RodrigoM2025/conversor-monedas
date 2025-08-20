package com.rodrigo.conversor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Clase para probar la funcionalidad de SolicitudHTTP
 * Tarjeta 5: Pruebas del HttpRequest personalizado
 */
public class TestSolicitudHTTP {

    public static void main(String[] args) {
        System.out.println("🧪 INICIANDO PRUEBAS DE SOLICITUD HTTP");
        System.out.println("=====================================");

        // Crear instancia de nuestra clase de solicitudes
        SolicitudHTTP solicitudHTTP = new SolicitudHTTP();

        // Prueba 1: Mostrar detalles de la solicitud
        System.out.println("\n🔍 PRUEBA 1: Detalles de la Solicitud");
        solicitudHTTP.mostrarDetallesSolicitud("USD");

        // Prueba 2: Realizar solicitud y obtener respuesta
        System.out.println("\n🌐 PRUEBA 2: Realizando Solicitud HTTP");
        try {
            String respuestaJSON = solicitudHTTP.obtenerTasasCambio("USD");
            System.out.println("✅ Solicitud exitosa!");

            // Parsear la respuesta para mostrar información útil
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(respuestaJSON, JsonObject.class);

            String resultado = jsonObject.get("result").getAsString();
            String monedaBase = jsonObject.get("base_code").getAsString();

            System.out.println("📊 Resultado: " + resultado);
            System.out.println("💰 Moneda base: " + monedaBase);

            // Mostrar algunas tasas importantes
            JsonObject tasas = jsonObject.getAsJsonObject("rates");
            System.out.println("\n💱 Tasas de cambio principales:");
            System.out.println("   USD → EUR: " + tasas.get("EUR").getAsDouble());
            System.out.println("   USD → GBP: " + tasas.get("GBP").getAsDouble());
            System.out.println("   USD → JPY: " + tasas.get("JPY").getAsDouble());
            System.out.println("   USD → CAD: " + tasas.get("CAD").getAsDouble());

        } catch (Exception e) {
            System.out.println("❌ Error en la solicitud: " + e.getMessage());
        }

        // Prueba 3: Probar con diferentes monedas
        System.out.println("\n🌍 PRUEBA 3: Probando con EUR como base");
        try {
            solicitudHTTP.mostrarDetallesSolicitud("EUR");
            String respuestaEUR = solicitudHTTP.obtenerTasasCambio("EUR");

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(respuestaEUR, JsonObject.class);
            JsonObject tasas = jsonObject.getAsJsonObject("rates");

            System.out.println("💱 Tasas desde EUR:");
            System.out.println("   EUR → USD: " + tasas.get("USD").getAsDouble());
            System.out.println("   EUR → GBP: " + tasas.get("GBP").getAsDouble());

        } catch (Exception e) {
            System.out.println("❌ Error con EUR: " + e.getMessage());
        }

        System.out.println("\n🎉 PRUEBAS COMPLETADAS");
        System.out.println("=====================================");
    }
}