package com.rodrigo.conversor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Cliente HTTP para realizar solicitudes a la API de tasas de cambio
 */
public class ClienteHTTP {

    private static final String BASE_URL = "https://open.er-api.com/v6/latest/";
    private final HttpClient client;

    public ClienteHTTP() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public String obtenerTasasCambio(String monedaBase) throws IOException, InterruptedException {
        if (monedaBase == null || monedaBase.trim().isEmpty()) {
            throw new IllegalArgumentException("La moneda base no puede ser nula o vacía");
        }

        String url = BASE_URL + monedaBase.toUpperCase();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new RuntimeException("Error HTTP: " + response.statusCode());
        }
    }

    public boolean verificarConectividad() {
        try {
            String respuesta = obtenerTasasCambio("USD");
            return respuesta != null && !respuesta.isEmpty();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }
}