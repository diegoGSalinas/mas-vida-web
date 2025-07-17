package Modelo;

import java.time.LocalDateTime;
import java.sql.Date    ;

public class CitaMedica {

    private String idCita;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaCita;
    private java.sql.Date fechaSolicitudSql;
    private java.sql.Date fechaCitaSql;
    private Especialidad especialidad;
    private String idPago;
    private String estado;
    private Persona paciente;
    private String idPersona;

    // Constructor vacío
    public CitaMedica() {
        // Inicialización básica
        this.fechaSolicitud = LocalDateTime.now();
        this.fechaSolicitudSql = java.sql.Date.valueOf(this.fechaSolicitud.toLocalDate());
        this.estado = "Pendiente";
        this.idPago = "NORMAL"; // Por defecto es normal
    }

    // Constructor específico para creación de citas
    public CitaMedica(String idCita, LocalDateTime fechaSolicitud, LocalDateTime fechaCita, Persona paciente, String idPago, String estado, Especialidad especialidad) {
        this.idCita = idCita;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaSolicitudSql = java.sql.Date.valueOf(fechaSolicitud.toLocalDate());
        this.fechaCita = fechaCita;
        this.fechaCitaSql = java.sql.Date.valueOf(fechaCita.toLocalDate());
        this.paciente = paciente;
        this.idPago = idPago;
        this.estado = estado;
        this.especialidad = especialidad;
        this.idPersona = String.valueOf(paciente.getIdPersona());
    }

    public CitaMedica(LocalDateTime fechaSolicitud, LocalDateTime fechaCita, Persona paciente, String idPago, String estado, Especialidad especialidad) {
        this.fechaSolicitud = fechaSolicitud;
        this.fechaCita = fechaCita;
        this.paciente = paciente;
        this.idPago = idPago;
        this.estado = estado;
        this.especialidad = especialidad;
        this.idPersona = String.valueOf(paciente.getIdPersona());
    }

    public String getIdCita() {
        return idCita;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public LocalDateTime getFechaCita() {
        return fechaCita;
    }

    public Persona getPaciente() {
        return paciente;
    }



    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }



    public String getEstado() {
        return estado;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public void setPaciente(Persona paciente) {
        this.paciente = paciente;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    public String getIdPago() {
        return idPago;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Setters para fechas
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
        this.fechaSolicitudSql = java.sql.Date.valueOf(fechaSolicitud.toLocalDate());
    }

    public void setFechaCita(LocalDateTime fechaCita) {
        this.fechaCita = fechaCita;
        this.fechaCitaSql = java.sql.Date.valueOf(fechaCita.toLocalDate());
    }

    public void setFechaSolicitudSql(java.sql.Date fechaSolicitudSql) {
        this.fechaSolicitudSql = fechaSolicitudSql;
    }

    public void setFechaCitaSql(java.sql.Date fechaCitaSql) {
        this.fechaCitaSql = fechaCitaSql;
    }

}
