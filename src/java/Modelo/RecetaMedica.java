package Modelo;

import java.time.LocalDateTime;

public class RecetaMedica {

    private String idReceta;
    private String medicamento;
    private int dosis;
    private int duracion;
    private LocalDateTime fechaEmision;
    private String instruccionesAdicionales;
    private CitaMedica cita;

    public RecetaMedica(String idReceta, String medicamento, int dosis, int duracion, LocalDateTime fechaEmision, String instruccionesAdicionales, CitaMedica cita) {
        this.idReceta = idReceta;
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.duracion = duracion;
        this.fechaEmision = fechaEmision;
        this.instruccionesAdicionales = instruccionesAdicionales;
        this.cita = cita;
    }

    public RecetaMedica(String medicamento, int dosis, int duracion, LocalDateTime fechaEmision, String instruccionesAdicionales, CitaMedica cita) {
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.duracion = duracion;
        this.fechaEmision = fechaEmision;
        this.instruccionesAdicionales = instruccionesAdicionales;
        this.cita = cita;
    }

    public RecetaMedica() {
    }

    public String getIdReceta() {
        return idReceta;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public int getDosis() {
        return dosis;
    }

    public int getDuracion() {
        return duracion;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public String getInstruccionesAdicionales() {
        return instruccionesAdicionales;
    }

    public CitaMedica getCita() {
        return cita;
    }

    public void setIdReceta(String idReceta) {
        this.idReceta = idReceta;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public void setInstruccionesAdicionales(String instruccionesAdicionales) {
        this.instruccionesAdicionales = instruccionesAdicionales;
    }

    public void setCita(CitaMedica cita) {
        this.cita = cita;
    }

}
