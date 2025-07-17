package Modelo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Dashboard {
    private Map<String, Integer> estadisticas;
    private Map<LocalDate, Integer> citasPorFecha;
    private Map<String, Integer> citasPorEspecialidad;
    private int totalUsuarios;
    private int totalPacientes;
    private int totalCitas;
    private int totalPendientes;
    private int totalConfirmadas;
    private int totalCanceladas;

    public Dashboard() {
        this.estadisticas = new HashMap<>();
        this.citasPorFecha = new HashMap<>();
        this.citasPorEspecialidad = new HashMap<>();
    }

    public Map<String, Integer> getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(Map<String, Integer> estadisticas) {
        this.estadisticas = estadisticas;
    }

    public Map<LocalDate, Integer> getCitasPorFecha() {
        return citasPorFecha;
    }

    public void setCitasPorFecha(Map<LocalDate, Integer> citasPorFecha) {
        this.citasPorFecha = citasPorFecha;
    }

    public Map<String, Integer> getCitasPorEspecialidad() {
        return citasPorEspecialidad;
    }

    public void setCitasPorEspecialidad(Map<String, Integer> citasPorEspecialidad) {
        this.citasPorEspecialidad = citasPorEspecialidad;
    }

    public int getTotalUsuarios() {
        return totalUsuarios;
    }

    public void setTotalUsuarios(int totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }

    public int getTotalPacientes() {
        return totalPacientes;
    }

    public void setTotalPacientes(int totalPacientes) {
        this.totalPacientes = totalPacientes;
    }

    public int getTotalCitas() {
        return totalCitas;
    }

    public void setTotalCitas(int totalCitas) {
        this.totalCitas = totalCitas;
    }

    public int getTotalPendientes() {
        return totalPendientes;
    }

    public void setTotalPendientes(int totalPendientes) {
        this.totalPendientes = totalPendientes;
    }

    public int getTotalConfirmadas() {
        return totalConfirmadas;
    }

    public void setTotalConfirmadas(int totalConfirmadas) {
        this.totalConfirmadas = totalConfirmadas;
    }

    public int getTotalCanceladas() {
        return totalCanceladas;
    }

    public void setTotalCanceladas(int totalCanceladas) {
        this.totalCanceladas = totalCanceladas;
    }


}
