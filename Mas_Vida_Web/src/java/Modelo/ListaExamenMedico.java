package Modelo;

public class ListaExamenMedico {
    private String idExamenMedico;
    private String nombre;
    private double precio;
    private String descripcion;

    public ListaExamenMedico() {
    }

    public String getIdExamenMedico() {
        return idExamenMedico;
    }

    public void setIdExamenMedico(String idExamenMedico) {
        this.idExamenMedico = idExamenMedico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
