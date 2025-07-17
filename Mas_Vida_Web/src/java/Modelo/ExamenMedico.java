package Modelo;

import java.time.LocalDateTime;

public class ExamenMedico {

    private String idExamen;
    private CitaMedica citaMedica;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaExamen;
    private Paciente paciente;
    private Doctor doctor;

    public ExamenMedico(String idExamen, CitaMedica citaMedica, LocalDateTime fechaRegistro, LocalDateTime fechaExamen, Paciente paciente, Doctor doctor) {
        this.idExamen = idExamen;
        this.citaMedica = citaMedica;
        this.fechaRegistro = fechaRegistro;
        this.fechaExamen = fechaExamen;
        this.paciente = paciente;
        this.doctor = doctor;
    }

    public ExamenMedico(CitaMedica citaMedica, LocalDateTime fechaRegistro, LocalDateTime fechaExamen, Paciente paciente, Doctor doctor) {
        this.citaMedica = citaMedica;
        this.fechaRegistro = fechaRegistro;
        this.fechaExamen = fechaExamen;
        this.paciente = paciente;
        this.doctor = doctor;
    }

    public ExamenMedico() {
    }

    public String getIdExamen() {
        return idExamen;
    }

    public CitaMedica getCitaMedica() {
        return citaMedica;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public LocalDateTime getFechaExamen() {
        return fechaExamen;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setIdExamen(String idExamen) {
        this.idExamen = idExamen;
    }

    public void setCitaMedica(CitaMedica citaMedica) {
        this.citaMedica = citaMedica;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setFechaExamen(LocalDateTime fechaExamen) {
        this.fechaExamen = fechaExamen;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

}
