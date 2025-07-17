package Modelo;

import java.util.Date;

public class Recepcionista extends Persona {

    private String idRecepcionista;
    private String turno;
    private String estado;

    public Recepcionista(String idRecepcionista, String turno, String estado, String nombres, String apPaterno, String apMaterno, String dni, String correo, String direccion, String telefono, Date fechaNacimiento) {
        super(nombres, apPaterno, apMaterno, dni, correo, direccion, telefono, fechaNacimiento);
        this.idRecepcionista = idRecepcionista;
        this.turno = turno;
        this.estado = estado;
    }

    public Recepcionista(String turno, String estado, String nombres, String apPaterno, String apMaterno, String dni, String correo, String direccion, String telefono, Date fechaNacimiento) {
        super(nombres, apPaterno, apMaterno, dni, correo, direccion, telefono, fechaNacimiento);
        this.turno = turno;
        this.estado = estado;
    }

    public Recepcionista(String turno, String estado) {
        this.turno = turno;
        this.estado = estado;
    }

    public Recepcionista(String idRecepcionista, String turno, String estado) {
        this.idRecepcionista = idRecepcionista;
        this.turno = turno;
        this.estado = estado;
    }

    public String getIdRecepcionista() {
        return idRecepcionista;
    }

    public String getTurno() {
        return turno;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setIdRecepcionista(String idRecepcionista) {
        this.idRecepcionista = idRecepcionista;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

}
