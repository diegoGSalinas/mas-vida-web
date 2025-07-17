package Modelo;

import java.util.Date;

public class Administrador extends Persona {

    private String idAdministrador;
    private String estado;

    public Administrador(String idAdministrador, String estado, String nombres, String apPaterno, String apMaterno, String dni, String correo, String direccion, String telefono, Date fechaNacimiento) {
        super(nombres, apPaterno, apMaterno, dni, correo, direccion, telefono, fechaNacimiento);
        this.idAdministrador = idAdministrador;
        this.estado = estado;
    }

    public Administrador() {
    }

    public String getIdAdministrador() {
        return idAdministrador;
    }

    public String getEstado() {
        return estado;
    }

    public void setIdAdministrador(String idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
