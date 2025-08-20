package com.rodrigo.conversor;

import java.util.Scanner;

/**
 * Clase para manejar la interfaz de usuario por consola
 * Tarjeta 9: Convertiendo los valores
 */
public class InterfazUsuario {

    private final Scanner scanner;
    private final ConversorMonedas conversor;

    public InterfazUsuario(ConversorMonedas conversor) {
        this.scanner = new Scanner(System.in);
        this.conversor = conversor;
    }

    /**
     * Inicia la interfaz de usuario
     */
    public void iniciar() {
        System.out.println("\n🎉 BIENVENIDO AL CONVERSOR DE MONEDAS");
        System.out.println("=====================================");

        boolean continuar = true;
        while (continuar) {
            try {
                mostrarMenuPrincipal();
                int opcion = leerOpcion();

                switch (opcion) {
                    case 1:
                        realizarConversion();
                        break;
                    case 2:
                        mostrarTasasDisponibles();
                        break;
                    case 3:
                        continuar = false;
                        System.out.println("👋 ¡Gracias por usar el conversor!");
                        break;
                    default:
                        System.out.println("❌ Opción inválida. Intenta nuevamente.");
                }

            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
                scanner.nextLine(); // Limpiar buffer
            }
        }

        scanner.close();
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n📋 MENU PRINCIPAL");
        System.out.println("1. Realizar conversión");
        System.out.println("2. Ver tasas disponibles");
        System.out.println("3. Salir");
        System.out.print("Selecciona una opción: ");
    }

    private int leerOpcion() {
        while (!scanner.hasNextInt()) {
            System.out.println("❌ Por favor ingresa un número.");
            scanner.next(); // Limpiar entrada inválida
            System.out.print("Selecciona una opción: ");
        }
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        return opcion;
    }

    private void realizarConversion() {
        System.out.println("\n💱 CONVERSIÓN DE MONEDAS");

        System.out.print("Ingresa la cantidad a convertir: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("❌ Por favor ingresa un número válido.");
            scanner.next(); // Limpiar entrada inválida
            System.out.print("Ingresa la cantidad a convertir: ");
        }
        double cantidad = scanner.nextDouble();
        scanner.nextLine(); // Limpiar buffer

        System.out.print("Moneda de origen (ej: USD, EUR, ARS): ");
        String monedaOrigen = scanner.nextLine().toUpperCase().trim();

        if (!conversor.monedaDisponible(monedaOrigen)) {
            System.out.println("❌ Moneda no disponible: " + monedaOrigen);
            return;
        }

        System.out.print("Moneda de destino (ej: USD, EUR, ARS): ");
        String monedaDestino = scanner.nextLine().toUpperCase().trim();

        if (!conversor.monedaDisponible(monedaDestino)) {
            System.out.println("❌ Moneda no disponible: " + monedaDestino);
            return;
        }

        // Realizar y mostrar conversión
        conversor.mostrarConversion(cantidad, monedaOrigen, monedaDestino);
    }

    private void mostrarTasasDisponibles() {
        System.out.println("\n📊 TASAS DE CAMBIO DISPONIBLES");
        System.out.println("(Basadas en USD)");
        System.out.println("==============================");

        // Mostrar algunas monedas principales
        String[] monedasPrincipales = {"USD", "EUR", "GBP", "JPY", "ARS", "BRL", "CLP", "COP", "MXN"};

        for (String moneda : monedasPrincipales) {
            if (conversor.monedaDisponible(moneda)) {
                double tasa = conversor.obtenerTasaConversion("USD", moneda);
                System.out.printf("💰 USD → %s: %.4f%n", moneda, tasa);
            }
        }

        System.out.println("\n💡 Hay " + conversor.obtenerMonedasDisponibles().length +
                " monedas disponibles en total.");
        System.out.println("Usa la opción de conversión para ver todas las opciones.");
    }
}