package Modelo;

import java.util.Date;

public class Tecnico extends Persona {

    private String idTecnico;
    private Especialidad especialidad;
    private String estado;

    public Tecnico(String idTecnico, Especialidad especialidad, String estado, String nombres, String apPaterno, String apMaterno, String dni, String correo, String direccion, String telefono, Date fechaNacimiento) {
        super(nombres, apPaterno, apMaterno, dni, correo, direccion, telefono, fechaNacimiento);
        this.idTecnico = idTecnico;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public Tecnico(Especialidad especialidad, String nombres, String apPaterno, String apMaterno, String dni, String correo, String direccion, String telefono, Date fechaNacimiento) {
        super(nombres, apPaterno, apMaterno, dni, correo, direccion, telefono, fechaNacimiento);
        this.especialidad = especialidad;
    }

    public String getIdTecnico() {
        return idTecnico;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setIdTecnico(String idTecnico) {
        this.idTecnico = idTecnico;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
