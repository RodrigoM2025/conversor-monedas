package com.rodrigo.conversor;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Clase POJO para mapeo automático de la respuesta JSON de la API
 * Tarjeta 7: Analizando la respuesta en formato JSON
 */
public class RespuestaAPI {
    private String result;

    @SerializedName("documentation")
    private String urlDocumentacion;

    @SerializedName("terms_of_use")
    private String terminosUso;

    @SerializedName("time_last_update_unix")
    private long ultimaActualizacionUnix;

    @SerializedName("time_last_update_utc")
    private String ultimaActualizacionUTC;

    @SerializedName("time_next_update_unix")
    private long proximaActualizacionUnix;

    @SerializedName("time_next_update_utc")
    private String proximaActualizacionUTC;

    @SerializedName("base_code")
    private String codigoBase;

    @SerializedName("rates")
    private Map<String, Double> tasasConversion;

    // Getters y Setters en español
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public String getUrlDocumentacion() { return urlDocumentacion; }
    public void setUrlDocumentacion(String urlDocumentacion) { this.urlDocumentacion = urlDocumentacion; }

    public String getTerminosUso() { return terminosUso; }
    public void setTerminosUso(String terminosUso) { this.terminosUso = terminosUso; }

    public long getUltimaActualizacionUnix() { return ultimaActualizacionUnix; }
    public void setUltimaActualizacionUnix(long ultimaActualizacionUnix) { this.ultimaActualizacionUnix = ultimaActualizacionUnix; }

    public String getUltimaActualizacionUTC() { return ultimaActualizacionUTC; }
    public void setUltimaActualizacionUTC(String ultimaActualizacionUTC) { this.ultimaActualizacionUTC = ultimaActualizacionUTC; }

    public long getProximaActualizacionUnix() { return proximaActualizacionUnix; }
    public void setProximaActualizacionUnix(long proximaActualizacionUnix) { this.proximaActualizacionUnix = proximaActualizacionUnix; }

    public String getProximaActualizacionUTC() { return proximaActualizacionUTC; }
    public void setProximaActualizacionUTC(String proximaActualizacionUTC) { this.proximaActualizacionUTC = proximaActualizacionUTC; }

    public String getCodigoBase() { return codigoBase; }
    public void setCodigoBase(String codigoBase) { this.codigoBase = codigoBase; }

    public Map<String, Double> getTasasConversion() { return tasasConversion; }
    public void setTasasConversion(Map<String, Double> tasasConversion) { this.tasasConversion = tasasConversion; }

    /**
     * Obtiene la tasa de conversión para una moneda específica
     * @param codigoMoneda Código de la moneda (ej: "EUR", "USD")
     * @return double tasa de conversión
     */
    public double obtenerTasa(String codigoMoneda) {
        if (tasasConversion.containsKey(codigoMoneda)) {
            return tasasConversion.get(codigoMoneda);
        }
        throw new IllegalArgumentException("Moneda no encontrada: " + codigoMoneda);
    }

    /**
     * Verifica si la respuesta de la API fue exitosa
     * @return boolean true si el resultado es "success"
     */
    public boolean esExitosa() {
        return "success".equals(result);
    }
}