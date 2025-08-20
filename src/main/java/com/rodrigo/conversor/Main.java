/* package com.rodrigo.conversor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Main {
    public static void main(String[] args) {
        System.out.println("¡Bienvenido al Conversor de Monedas!");

        // Prueba básica de Gson
        Gson gson = new Gson();

        // Crear un JSON simple para probar
        JsonObject jsonTest = new JsonObject();
        jsonTest.addProperty("moneda", "USD");
        jsonTest.addProperty("valor", 100.50);

        System.out.println("Prueba de Gson: " + jsonTest.toString());
        System.out.println("✅ Gson está funcionando correctamente!");
    }
}*/
package com.rodrigo.conversor;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 Iniciando Conversor de Monedas...");

        try {
            // Obtener tasas de la API
            System.out.println("📡 Conectando con API de tasas de cambio...");
            SolicitudHTTP solicitud = new SolicitudHTTP();
            RespuestaHTTP respuesta = solicitud.obtenerRespuestaProcesada("USD");

            if (!respuesta.esExitosa()) {
                System.out.println("❌ Error: No se pudieron obtener las tasas de cambio");
                return;
            }

            // Crear conversor con las tasas obtenidas
            RespuestaAPI respuestaAPI = respuesta.obtenerComoRespuestaAPI();
            ConversorMonedas conversor = new ConversorMonedas(respuestaAPI.getTasasConversion());

            System.out.println("✅ Tasas obtenidas correctamente (" +
                    respuestaAPI.getTasasConversion().size() + " monedas disponibles)");

            // Iniciar interfaz de usuario
            InterfazUsuario interfaz = new InterfazUsuario(conversor);
            interfaz.iniciar();

        } catch (Exception e) {
            System.out.println("❌ Error al iniciar la aplicación: " + e.getMessage());
            System.out.println("🔌 Verifica tu conexión a internet e intenta nuevamente.");
        }
    }
}
