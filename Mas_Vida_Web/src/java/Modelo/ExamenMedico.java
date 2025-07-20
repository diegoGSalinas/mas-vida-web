package Modelo;

import java.time.LocalDateTime;

public class ExamenMedico {

    private String idExamen;
    private CitaMedica citaMedica;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaExamen;
    private Paciente paciente;
    private Doctor doctor;
    
    private String resultados;
    private String idTecnico;
    private String idCita;

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
    
    public ExamenMedico(String idExamen, LocalDateTime fechaExamen, String resultados, String idTecnico, String idCita) {
    this.idExamen = idExamen;
    this.fechaExamen = fechaExamen;
    this.resultados = resultados;
    this.idTecnico = idTecnico;
    this.idCita = idCita;
}

    public ExamenMedico() {
    }

    public String getResultados() {
        return resultados;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
    }

    public String getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(String idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
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
