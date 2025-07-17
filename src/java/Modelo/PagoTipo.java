package Modelo;

public class PagoTipo {
    private int id;
    private String nombre;
    private double precio;

    public static final PagoTipo CITA_NORMAL = new PagoTipo(1, "Cita Normal", 50.0);
    public static final PagoTipo CITA_Urgente = new PagoTipo(2, "Cita Urgente", 100.0);
    public static final PagoTipo CITA_EMERGENCIA = new PagoTipo(3, "Cita Emergencia", 200.0);

    private PagoTipo(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return nombre + " (S/" + precio + ")";
    }

    public static PagoTipo[] getTipos() {
        return new PagoTipo[] { CITA_NORMAL, CITA_Urgente, CITA_EMERGENCIA };
    }
}
