package com.rodrigo.conversor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Clase para manejar y analizar respuestas HTTP de manera detallada
 * Tarjeta 6: Construyendo la Respuesta (HttpResponse)
 */
public class RespuestaHTTP {

    private final HttpResponse<String> respuestaOriginal;
    private final JsonObject datosJSON;
    private final LocalDateTime tiempoRespuesta;
    private final Gson gson;

    /**
     * Constructor que analiza y procesa la respuesta HTTP
     * @param response La respuesta HTTP recibida
     */
    public RespuestaHTTP(HttpResponse<String> response) {
        this.respuestaOriginal = response;
        this.tiempoRespuesta = LocalDateTime.now();
        this.gson = new Gson();
        this.datosJSON = procesarJSON(response.body());
    }

    /**
     * Procesa el JSON de la respuesta de manera segura
     */
    private JsonObject procesarJSON(String jsonString) {
        try {
            // ✅ FORMA MODERNA (recomendada para Gson 2.8.6+)
            return JsonParser.parseString(jsonString).getAsJsonObject();
        } catch (JsonSyntaxException e) {
            System.err.println("⚠️ Error al procesar JSON: " + e.getMessage());
            return new JsonObject();
        }
    }


    /**
     * Obtiene el código de estado HTTP
     * @return int código de estado (200, 404, 500, etc.)
     */
    public int obtenerCodigoEstado() {
        return respuestaOriginal.statusCode();
    }

    /**
     * Verifica si la respuesta fue exitosa
     * @return boolean true si el código está entre 200-299
     */
    public boolean esExitosa() {
        int codigo = obtenerCodigoEstado();
        return codigo >= 200 && codigo < 300;
    }

    /**
     * Obtiene el mensaje descriptivo del código de estado
     * @return String mensaje explicativo
     */
    public String obtenerMensajeEstado() {
        int codigo = obtenerCodigoEstado();
        switch (codigo) {
            case 200: return "✅ OK - Solicitud exitosa";
            case 400: return "❌ Bad Request - Solicitud incorrecta";
            case 401: return "🔒 Unauthorized - No autorizado";
            case 404: return "🔍 Not Found - Recurso no encontrado";
            case 429: return "⏰ Too Many Requests - Demasiadas solicitudes";
            case 500: return "🔥 Internal Server Error - Error del servidor";
            default: return "ℹ️ Código " + codigo;
        }
    }

    /**
     * Obtiene todos los headers de la respuesta
     * @return Map con todos los headers
     */
    public Map<String, java.util.List<String>> obtenerHeaders() {
        return respuestaOriginal.headers().map();
    }

    /**
     * Obtiene un header específico
     * @param nombreHeader nombre del header a buscar
     * @return String valor del header o null si no existe
     */
    public String obtenerHeader(String nombreHeader) {
        return respuestaOriginal.headers().firstValue(nombreHeader).orElse(null);
    }

    /**
     * Obtiene el cuerpo de la respuesta como texto
     * @return String contenido completo de la respuesta
     */
    public String obtenerCuerpoTexto() {
        return respuestaOriginal.body();
    }

    /**
     * Obtiene el JSON parseado
     * @return JsonObject con los datos de la API
     */
    public JsonObject obtenerDatosJSON() {
        return datosJSON;
    }

    /**
     * ✅ Tarjeta 7: Obtiene la respuesta deserializada como objeto RespuestaAPI usando Gson
     * @return RespuestaAPI objeto con todos los datos de la respuesta
     */
    public RespuestaAPI obtenerComoRespuestaAPI() {
        try {
            Gson gson = new Gson();
            return gson.fromJson(obtenerCuerpoTexto(), RespuestaAPI.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al deserializar RespuestaAPI: " + e.getMessage());
        }
    }

    /**
     * Extrae información específica de la API de monedas
     * @return InformacionMoneda objeto con datos procesados
     */
    public InformacionMoneda extraerInformacionMoneda() {
        if (!datosJSON.has("result") || !esExitosa()) {
            throw new RuntimeException("No se puede extraer información: respuesta inválida");
        }

        String resultado = datosJSON.get("result").getAsString();
        String monedaBase = datosJSON.get("base_code").getAsString();
        long tiempoActualizacion = datosJSON.get("time_last_update_unix").getAsLong();
        JsonObject tasas = datosJSON.getAsJsonObject("rates");

        return new InformacionMoneda(resultado, monedaBase, tiempoActualizacion, tasas);
    }

    /**
     * Muestra un análisis completo de la respuesta
     */
    public void mostrarAnalisisCompleto() {
        System.out.println("\n📊 === ANÁLISIS COMPLETO DE RESPUESTA HTTP ===");
        System.out.println("🕐 Tiempo de análisis: " + tiempoRespuesta.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println("📈 Código de estado: " + obtenerCodigoEstado());
        System.out.println("📝 Mensaje: " + obtenerMensajeEstado());
        System.out.println("🌐 URL solicitada: " + respuestaOriginal.uri());

        System.out.println("\n📄 Headers importantes:");
        String contentType = obtenerHeader("content-type");
        String server = obtenerHeader("server");
        String contentLength = obtenerHeader("content-length");

        if (contentType != null) System.out.println("   📋 Content-Type: " + contentType);
        if (server != null) System.out.println("   🖥️ Server: " + server);
        if (contentLength != null) System.out.println("   📏 Content-Length: " + contentLength + " bytes");

        if (esExitosa() && datosJSON.has("result")) {
            System.out.println("\n💰 Información de moneda:");
            try {
                InformacionMoneda info = extraerInformacionMoneda();
                System.out.println("   ✅ Estado API: " + info.resultado);
                System.out.println("   💱 Moneda base: " + info.monedaBase);
                System.out.println("   🕐 Última actualización: " + info.obtenerFechaActualizacion());
                System.out.println("   📊 Total de tasas: " + info.tasas.size());
            } catch (Exception e) {
                System.out.println("   ⚠️ Error al procesar información de moneda: " + e.getMessage());
            }
            // ✅ Tarjeta 7: Mostrar también usando RespuestaAPI (POJO)
            System.out.println("\n📋 Información con RespuestaAPI (POJO):");
            try {
                RespuestaAPI respuestaAPI = obtenerComoRespuestaAPI();
                System.out.println("   📊 Base: " + respuestaAPI.getCodigoBase());
                System.out.println("   📅 Actualización: " + respuestaAPI.getUltimaActualizacionUTC());
                System.out.println("   🔢 Total tasas: " + respuestaAPI.getTasasConversion().size());

                // Validación cruzada para demostrar consistencia
                if (datosJSON.has("base_code") &&
                        respuestaAPI.getCodigoBase().equals(datosJSON.get("base_code").getAsString())) {
                    System.out.println("   ✅ Validación cruzada: OK");
                }
            } catch (Exception e) {
                System.out.println("   ⚠️ Error al obtener RespuestaAPI: " + e.getMessage());
            }
        }

        System.out.println("=============================================");
    }

    /**
     * Clase interna para encapsular información específica de monedas
     */
    public static class InformacionMoneda {
        public final String resultado;
        public final String monedaBase;
        public final long tiempoActualizacion;
        public final JsonObject tasas;

        public InformacionMoneda(String resultado, String monedaBase, long tiempoActualizacion, JsonObject tasas) {
            this.resultado = resultado;
            this.monedaBase = monedaBase;
            this.tiempoActualizacion = tiempoActualizacion;
            this.tasas = tasas;
        }

        /**
         * Convierte el timestamp a fecha legible
         */
        public String obtenerFechaActualizacion() {
            LocalDateTime fecha = LocalDateTime.ofEpochSecond(tiempoActualizacion, 0,
                    java.time.ZoneOffset.UTC);
            return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "UTC";
        }

        /**
         * Obtiene la tasa de una moneda específica
         */
        public double obtenerTasa(String codigoMoneda) {
            if (tasas.has(codigoMoneda)) {
                return tasas.get(codigoMoneda).getAsDouble();
            }
            throw new IllegalArgumentException("Moneda no encontrada: " + codigoMoneda);
        }
    }
}