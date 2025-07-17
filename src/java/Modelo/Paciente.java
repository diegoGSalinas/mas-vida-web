package Modelo;

import java.util.Date;

public class Paciente extends Persona {

    private String idPaciente;
    private Date fechaInscripcion;
    private Persona persona;

    public Paciente() {
        super();
    }

    public Paciente(String idPaciente, Date fechaInscripcion, String nombres, String apPaterno, String apMaterno, String dni, String correo, String direccion, String telefono, Date fechaNacimiento) {
        super(nombres, apPaterno, apMaterno, dni, correo, direccion, telefono, fechaNacimiento);
        this.idPaciente = idPaciente;
        this.fechaInscripcion = fechaInscripcion;
    }

    public Paciente(Date fechaInscripcion, String nombres, String apPaterno, String apMaterno, String dni, String correo, String direccion, String telefono, Date fechaNacimiento) {
        super(nombres, apPaterno, apMaterno, dni, correo, direccion, telefono, fechaNacimiento);
        this.fechaInscripcion = fechaInscripcion;
    }

    public Paciente(String idPaciente, Date fechaInscripcion) {
        this.idPaciente = idPaciente;
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

}
