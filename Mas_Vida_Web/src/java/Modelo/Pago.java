package Modelo;

import java.time.LocalDateTime;

public class Pago {

    private String idPago;
    private double monto;
    private LocalDateTime fechaPago;
    private String estado;

    public Pago(String idPago, double monto, LocalDateTime fechaPago, String estado) {
        this.idPago = idPago;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.estado = estado;
    }

    public Pago() {
    }

    public String getIdPago() {
        return idPago;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
