package com.rodrigo.conversor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Clase para manejar solicitudes HTTP de manera estructurada
 * Actualizada para Tarjeta 6: Integración con RespuestaHTTP
 */
public class SolicitudHTTP {

    private final HttpClient cliente;
    private final String urlBase;

    /**
     * Constructor que inicializa el cliente HTTP con configuraciones específicas
     */
    public SolicitudHTTP() {
        this.urlBase = "https://open.er-api.com/v6/latest/";
        this.cliente = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    /**
     * Construye una solicitud HTTP personalizada para obtener tasas de cambio
     * @param monedaBase La moneda base para la consulta (ej: "USD", "EUR")
     * @return HttpRequest configurado y listo para enviar
     */
    public HttpRequest construirSolicitud(String monedaBase) {
        String urlCompleta = urlBase + monedaBase;

        return HttpRequest.newBuilder()
                .uri(URI.create(urlCompleta))
                .timeout(Duration.ofSeconds(30))
                .header("Accept", "application/json")
                .header("User-Agent", "ConversorMonedas/1.0")
                .GET()
                .build();
    }

    /**
     * Envía la solicitud y obtiene la respuesta HTTP completa
     * @param solicitud La solicitud HTTP a enviar
     * @return HttpResponse<String> respuesta completa del servidor
     */
    public HttpResponse<String> enviarSolicitudCompleta(HttpRequest solicitud) {
        try {
            return cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException("Error al realizar la solicitud: " + e.getMessage());
        }
    }

    /**
     * Método conveniente que obtiene una RespuestaHTTP procesada
     * @param monedaBase La moneda base para consultar
     * @return RespuestaHTTP objeto con toda la información procesada
     */
    public RespuestaHTTP obtenerRespuestaProcesada(String monedaBase) {
        HttpRequest solicitud = construirSolicitud(monedaBase);
        HttpResponse<String> httpResponse = enviarSolicitudCompleta(solicitud);
        return new RespuestaHTTP(httpResponse);
    }

    /**
     * Método legacy para compatibilidad (mantiene la funcionalidad anterior)
     * @param solicitud La solicitud HTTP a enviar
     * @return String con la respuesta JSON
     */
    public String enviarSolicitud(HttpRequest solicitud) {
        HttpResponse<String> response = enviarSolicitudCompleta(solicitud);

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new RuntimeException("Error en la API: Código " + response.statusCode());
        }
    }

    /**
     * Método legacy para compatibilidad
     * @param monedaBase La moneda base para consultar
     * @return String con la respuesta JSON
     */
    public String obtenerTasasCambio(String monedaBase) {
        HttpRequest solicitud = construirSolicitud(monedaBase);
        return enviarSolicitud(solicitud);
    }

    /**
     * Obtiene información detallada sobre la última solicitud realizada
     * @param monedaBase La moneda para consultar
     */
    public void mostrarDetallesSolicitud(String monedaBase) {
        HttpRequest solicitud = construirSolicitud(monedaBase);

        System.out.println("=== DETALLES DE LA SOLICITUD HTTP ===");
        System.out.println("🌐 URL: " + solicitud.uri());
        System.out.println("📋 Método: " + solicitud.method());
        System.out.println("⏱️ Timeout: " + solicitud.timeout().orElse(Duration.ofSeconds(0)).getSeconds() + " segundos");
        System.out.println("📄 Headers:");
        solicitud.headers().map().forEach((key, value) ->
                System.out.println("   " + key + ": " + String.join(", ", value)));
        System.out.println("=====================================");
    }
}