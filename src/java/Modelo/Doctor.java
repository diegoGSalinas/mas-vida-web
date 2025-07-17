package Modelo;

import java.util.Date;

public class Doctor extends Persona {

    private String idDoctor;
    private Especialidad especialidad;
    private String turno;
    private String estado;

    public Doctor(Especialidad especialidad, String turno, String estado, String nombres, String apPaterno, String apMaterno, String dni, String correo, String direccion, String telefono, Date fechaNacimiento) {
        super(nombres, apPaterno, apMaterno, dni, correo, direccion, telefono, fechaNacimiento);
        this.especialidad = especialidad;
        this.turno = turno;
        this.estado = estado;
    }

    public Doctor() {
    }

    public String getIdDoctor() {
        return idDoctor;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public String getTurno() {
        return turno;
    }

    public String getEstado() {
        return estado;
    }

    public void setIdDoctor(String idDoctor) {
        this.idDoctor = idDoctor;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Persona getPersona() {
        return this;
    }

    public void setPersona(Persona persona) {
        // Como Doctor extiende de Persona, no necesitamos hacer nada especial
        // La persona se establece al crear el Doctor
    }

}
