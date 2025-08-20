package com.rodrigo.conversor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APITest {
    public static void main(String[] args) {
        System.out.println("🚀 Probando API de Exchange Rate...\n");

        String apiURL = "https://open.er-api.com/v6/latest/USD";

        try {
            // Realizar la solicitud HTTP
            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Verificar código de respuesta
            int responseCode = connection.getResponseCode();
            System.out.println("📡 Código de respuesta: " + responseCode);

            if (responseCode == 200) {
                // Leer la respuesta
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Procesar JSON con Gson
                Gson gson = new Gson();
                JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);

                // Mostrar información básica
                System.out.println("✅ Resultado: " + jsonResponse.get("result").getAsString());
                System.out.println("🏦 Proveedor: " + jsonResponse.get("provider").getAsString());
                System.out.println("📅 Última actualización: " + jsonResponse.get("time_last_update_utc").getAsString());
                System.out.println("💱 Moneda base: " + jsonResponse.get("base_code").getAsString());

                // Obtener algunas tasas específicas
                JsonObject rates = jsonResponse.getAsJsonObject("rates");

                System.out.println("\n💰 Algunas tasas de cambio (USD a otras monedas):");
                System.out.println("EUR: " + rates.get("EUR").getAsDouble());
                System.out.println("GBP: " + rates.get("GBP").getAsDouble());
                System.out.println("JPY: " + rates.get("JPY").getAsDouble());
                System.out.println("CAD: " + rates.get("CAD").getAsDouble());
                System.out.println("MXN: " + rates.get("MXN").getAsDouble());

                // Ejemplo de conversión
                double amountUSD = 100.0;
                double rateEUR = rates.get("EUR").getAsDouble();
                double convertedAmount = amountUSD * rateEUR;

                System.out.println("\n🔄 Ejemplo de conversión:");
                System.out.printf("$%.2f USD = €%.2f EUR%n", amountUSD, convertedAmount);

                System.out.println("\n🎉 ¡API funcionando correctamente!");

            } else {
                System.out.println("❌ Error en la solicitud: " + responseCode);
            }

        } catch (IOException e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error general: " + e.getMessage());
        }
    }
}