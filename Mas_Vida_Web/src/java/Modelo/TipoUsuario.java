package Modelo;

public enum TipoUsuario {

    ADMINISTRADOR(1, "ADMINISTRADOR", "Tiene acceso completo al sistema"),
    DOCTOR(2, "DOCTOR", "Puede gestionar exámenes y citas"),
    RECEPCIONISTA(3, "RECEPCIONISTA", "Puede gestionar citas y pacientes"),
    TECNICO(4, "TECNICO", "Puede realizar exámenes"),
    PACIENTE(5, "PACIENTE", "Puede crear y ver citas propias");
    

    private final int prioridad;
    private final String nombre;
    private final String descripcion;

    TipoUsuario(int prioridad, String nombre, String descripcion) {
        this.prioridad = prioridad;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene el TipoUsuario por su nombre
     *
     * @param nombre Nombre del tipo de usuario
     * @return TipoUsuario correspondiente o null si no existe
     */
    public static TipoUsuario fromNombre(String nombre) {
        for (TipoUsuario tipo : TipoUsuario.values()) {
            if (tipo.getNombre().equalsIgnoreCase(nombre)) {
                return tipo;
            }
        }
        return null;
    }

    /**
     * Verifica si el usuario puede crear citas
     *
     * @return true si puede crear citas
     */
    public boolean puedeCrearCitas() {
        return this == PACIENTE || this == RECEPCIONISTA || this == ADMINISTRADOR;
    }

    /**
     * Verifica si el usuario puede modificar citas
     *
     * @return true si puede modificar citas
     */
    public boolean puedeModificarCitas() {
        return this == RECEPCIONISTA || this == ADMINISTRADOR;
    }

    /**
     * Verifica si el usuario puede ver citas
     *
     * @return true si puede ver citas
     */
    public boolean puedeVerCitas() {
        return this == PACIENTE || this == RECEPCIONISTA || this == ADMINISTRADOR;
    }

    /**
     * Verifica si el usuario puede gestionar pacientes
     *
     * @return true si puede gestionar pacientes
     */
    public boolean puedeGestionarPacientes() {
        return this == RECEPCIONISTA || this == ADMINISTRADOR;
    }

    /**
     * Verifica si un tipo de usuario tiene prioridad sobre otro
     *
     * @param otroTipo Tipo de usuario a comparar
     * @return true si este tipo tiene prioridad sobre el otro
     */
    public boolean tienePrioridadSobre(TipoUsuario otroTipo) {
        return this.prioridad <= otroTipo.prioridad;
    }
}
