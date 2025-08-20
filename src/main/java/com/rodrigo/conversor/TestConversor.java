package com.rodrigo.conversor;

import java.util.HashMap;
import java.util.Map;

public class TestConversor {
    public static void main(String[] args) {
        System.out.println("🧪 PROBANDO CONVERSOR DE MONEDAS");

        // Crear tasas de ejemplo (basadas en USD)
        Map<String, Double> tasasEjemplo = new HashMap<>();
        tasasEjemplo.put("USD", 1.0);
        tasasEjemplo.put("EUR", 0.86);
        tasasEjemplo.put("GBP", 0.74);
        tasasEjemplo.put("ARS", 1292.33);
        tasasEjemplo.put("BRL", 5.44);
        tasasEjemplo.put("CLP", 964.02);
        tasasEjemplo.put("COP", 4016.15);
        tasasEjemplo.put("MXN", 18.81);

        ConversorMonedas conversor = new ConversorMonedas(tasasEjemplo);

        System.out.println("✅ Conversor creado con " + tasasEjemplo.size() + " monedas");

        // Pruebas de conversión
        System.out.println("\n🔍 PRUEBAS DE CONVERSIÓN:");

        // Prueba 1: USD a EUR
        System.out.println("\n✅ Prueba 1: USD a EUR");
        conversor.mostrarConversion(100, "USD", "EUR");

        // Prueba 2: EUR a USD
        System.out.println("\n✅ Prueba 2: EUR a USD");
        conversor.mostrarConversion(100, "EUR", "USD");

        // Prueba 3: USD a ARS
        System.out.println("\n✅ Prueba 3: USD a ARS");
        conversor.mostrarConversion(1, "USD", "ARS");

        // Prueba 4: ARS a USD
        System.out.println("\n✅ Prueba 4: ARS a USD");
        conversor.mostrarConversion(1000, "ARS", "USD");

        // Prueba 5: EUR a BRL (conversión cruzada)
        System.out.println("\n✅ Prueba 5: EUR a BRL");
        conversor.mostrarConversion(50, "EUR", "BRL");

        // Prueba 6: Misma moneda
        System.out.println("\n✅ Prueba 6: Misma moneda (USD a USD)");
        conversor.mostrarConversion(100, "USD", "USD");

        System.out.println("\n🎉 Pruebas completadas!");
    }
}
