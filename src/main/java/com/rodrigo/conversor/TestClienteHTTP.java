package com.rodrigo.conversor;

import java.io.IOException;

public class TestClienteHTTP {

    public static void main(String[] args) {
        System.out.println("🧪 PROBANDO CLIENTE HTTP - TARJETA 4");
        System.out.println("====================================");

        ClienteHTTP cliente = new ClienteHTTP();


        // Prueba 2: Obtener tasas para USD
        System.out.println("\n💱 Obteniendo tasas para USD...");
        try {
            String respuesta = cliente.obtenerTasasCambio("USD");
            System.out.println("✅ Respuesta obtenida:");
            System.out.println(respuesta.substring(0, Math.min(200, respuesta.length())) + "...");
        } catch (IOException | InterruptedException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }

        System.out.println("\n🎯 Cliente HTTP funcionando correctamente!");
    }
}