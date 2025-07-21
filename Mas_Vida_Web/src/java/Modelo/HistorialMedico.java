package Modelo;

public class HistorialMedico {

    private String idHistorial;
    private long idPersona;
    private String idExamen = null;
    private String motivo;
    private String diagnostico;
    private String tratamiento;

    public HistorialMedico() {
    }

    public HistorialMedico(String idHistorial, long idPersona, String idExamen, String motivo, String diagnostico, String tratamiento) {
        this.idHistorial = idHistorial;
        this.idPersona = idPersona;
        this.idExamen = idExamen;
        this.motivo = motivo;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
    }

    public String getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(String idHistorial) {
        this.idHistorial = idHistorial;
    }

    public long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(long idPersona) {
        this.idPersona = idPersona;
    }

    public String getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(String idExamen) {
        this.idExamen = idExamen;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

}
