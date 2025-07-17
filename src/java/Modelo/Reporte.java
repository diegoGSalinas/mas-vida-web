package Modelo;

import java.time.LocalDateTime;
import java.util.List;

public class Reporte {
    private String idReporte;
    private String tipo;
    private LocalDateTime fechaGeneracion;
    private String descripcion;
    private List<Object> datos;

    public Reporte() {
    }

    public Reporte(String idReporte, String tipo, String descripcion) {
        this.idReporte = idReporte;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fechaGeneracion = LocalDateTime.now();
    }

    public String getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(String idReporte) {
        this.idReporte = idReporte;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Object> getDatos() {
        return datos;
    }

    public void setDatos(List<Object> datos) {
        this.datos = datos;
    }
}
