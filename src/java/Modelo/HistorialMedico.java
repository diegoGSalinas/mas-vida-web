package Modelo;

import java.time.LocalDateTime;
import java.util.List;

public class HistorialMedico {

    private String idHistorial;
    private LocalDateTime fechaCreacion;
    private List<CitaMedica> citasMedicas;
    private List<RecetaMedica> recetasMedicas;
    private List<ExamenMedico> examenesMedicos;

    public HistorialMedico(String idHistorial, LocalDateTime fechaCreacion, List<CitaMedica> citasMedicas, List<RecetaMedica> recetasMedicas, List<ExamenMedico> examenesMedicos) {
        this.idHistorial = idHistorial;
        this.fechaCreacion = fechaCreacion;
        this.citasMedicas = citasMedicas;
        this.recetasMedicas = recetasMedicas;
        this.examenesMedicos = examenesMedicos;
    }

    public HistorialMedico(LocalDateTime fechaCreacion, List<CitaMedica> citasMedicas, List<RecetaMedica> recetasMedicas, List<ExamenMedico> examenesMedicos) {
        this.fechaCreacion = fechaCreacion;
        this.citasMedicas = citasMedicas;
        this.recetasMedicas = recetasMedicas;
        this.examenesMedicos = examenesMedicos;
    }

    public HistorialMedico() {
    }

    public String getIdHistorial() {
        return idHistorial;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public List<CitaMedica> getCitasMedicas() {
        return citasMedicas;
    }

    public List<RecetaMedica> getRecetasMedicas() {
        return recetasMedicas;
    }

    public List<ExamenMedico> getExamenesMedicos() {
        return examenesMedicos;
    }

    public void setIdHistorial(String idHistorial) {
        this.idHistorial = idHistorial;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setCitasMedicas(List<CitaMedica> citasMedicas) {
        this.citasMedicas = citasMedicas;
    }

    public void setRecetasMedicas(List<RecetaMedica> recetasMedicas) {
        this.recetasMedicas = recetasMedicas;
    }

    public void setExamenesMedicos(List<ExamenMedico> examenesMedicos) {
        this.examenesMedicos = examenesMedicos;
    }

}
