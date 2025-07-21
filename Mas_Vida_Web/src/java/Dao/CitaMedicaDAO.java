package Dao;

import Modelo.CitaMedica;
import Modelo.Especialidad;
import Modelo.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CitaMedicaDAO {

    private static CitaMedicaDAO instance;

    private CitaMedicaDAO() {
    }

    public static synchronized CitaMedicaDAO getInstance() {
        if (instance == null) {
            instance = new CitaMedicaDAO();
        }
        return instance;
    }

    public int obtenerUltimoIdCita() throws SQLException {
        String sql = "SELECT MAX(CAST(SUBSTRING(id_cita, 5) AS INTEGER)) AS ultimo_id FROM cita_medica";

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("ultimo_id");
                    }
                }
            }
        }
        return 0;
    }

    public int contarCitasDelDia(String idEspecialidad) throws SQLException {
        String sql = "SELECT COUNT(*) as total_citas "
                + "FROM cita_medica "
                + "WHERE id_especialidad = ? "
                + "AND DATE(fecha_cita) = CURRENT_DATE "
                + "AND estado = 'Pendiente'";

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, idEspecialidad);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("total_citas");
                    }
                }
            }
        }
        return 0;
    }

    public boolean actualizarEstadoCita(String idCita, String nuevoEstado) throws SQLException {
        String sql = "UPDATE cita_medica SET estado = ? WHERE id_cita = ?";

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nuevoEstado);
                pstmt.setString(2, idCita);
                return pstmt.executeUpdate() > 0;
            }
        }
    }

    public void crearCita(CitaMedica cita) throws SQLException {
        if (cita == null) {
            throw new IllegalArgumentException("La cita no puede ser null");
        }

        if (cita.getPaciente() == null || cita.getEspecialidad() == null) {
            throw new IllegalArgumentException("Paciente y especialidad son requeridos");
        }

        String sql = "INSERT INTO cita_medica (id_cita, fecha_solicitud, fecha_cita, id_especialidad, id_pago, estado, id_persona) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Validar y establecer cada parámetro
            if (cita.getIdCita() == null) {
                throw new IllegalArgumentException("ID de cita es requerido");
            }
            pstmt.setString(1, cita.getIdCita());

            if (cita.getFechaSolicitud() == null) {
                throw new IllegalArgumentException("Fecha de solicitud es requerida");
            }
            if (cita.getFechaSolicitud() == null) {
                throw new IllegalArgumentException("Fecha de solicitud es requerida");
            }
            pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(cita.getFechaSolicitud()));

            if (cita.getFechaCita() == null) {
                throw new IllegalArgumentException("Fecha de cita es requerida");
            }
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(cita.getFechaCita()));

            if (cita.getEspecialidad().getIdEspecialidad() == null || cita.getEspecialidad().getIdEspecialidad().trim().isEmpty()) {
                throw new IllegalArgumentException("ID de especialidad es requerido");
            }
            pstmt.setString(4, cita.getEspecialidad().getIdEspecialidad().trim());

            if (cita.getIdPago() == null) {
                throw new IllegalArgumentException("Tipo de pago es requerido");
            }
            pstmt.setString(5, cita.getIdPago());

            if (cita.getEstado() == null) {
                throw new IllegalArgumentException("Estado es requerido");
            }
            pstmt.setString(6, cita.getEstado());

            if (cita.getPaciente().getIdPersona() == null) {
                throw new IllegalArgumentException("ID de persona es requerido");
            }
            pstmt.setString(7, String.valueOf(cita.getPaciente().getIdPersona()));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating cita failed, no rows affected.");
            }
        }
    }

    public List<CitaMedica> listarCitasPorPaciente(String idUsuario) throws SQLException {
        List<CitaMedica> citas = new ArrayList<>();
        String sql = "SELECT cm.*, e.nombre as nombre_especialidad "
                + "FROM cita_medica cm "
                + "JOIN especialidad e ON cm.id_especialidad = e.id_especialidad "
                + "WHERE cm.id_persona = (SELECT id_persona FROM usuario WHERE id_usuario = ?)";

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, idUsuario);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        CitaMedica cita = new CitaMedica();
                        cita.setIdCita(rs.getString("id_cita"));
                        cita.setFechaSolicitud(rs.getTimestamp("fecha_solicitud").toLocalDateTime());
                        cita.setFechaCita(rs.getTimestamp("fecha_cita").toLocalDateTime());

                        // Crear especialidad
                        Especialidad especialidad = new Especialidad();
                        especialidad.setIdEspecialidad(rs.getString("id_especialidad"));
                        especialidad.setNombre(rs.getString("nombre_especialidad"));
                        cita.setEspecialidad(especialidad);

                        // Crear paciente
                        Persona paciente = new Persona();
                        paciente.setIdPersona(rs.getLong("id_persona"));
                        cita.setPaciente(paciente);

                        cita.setIdPago(rs.getString("id_pago"));
                        cita.setEstado(rs.getString("estado"));

                        citas.add(cita);
                    }
                }
            }
        }
        return citas;
    }

    public String obtenerNombreEspecialidad(String idEspecialidad) throws SQLException {
        String sql = "SELECT nombre FROM especialidad WHERE id_especialidad = ?";
        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, idEspecialidad);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("nombre");
                    }
                }
            }
        }
        return "No especificado";
    }

    public void actualizarCita(CitaMedica cita) throws SQLException {
        if (cita == null) {
            throw new IllegalArgumentException("La cita no puede ser null");
        }

        if (cita.getIdCita() == null) {
            throw new IllegalArgumentException("ID de cita es requerido");
        }

        String sql = "UPDATE cita_medica SET fecha_cita = ?, id_especialidad = ?, estado = ? WHERE id_cita = ?";

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);

            if (cita.getFechaCita() == null) {
                throw new IllegalArgumentException("Fecha de cita es requerida");
            }
            pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(cita.getFechaCita()));

            if (cita.getEspecialidad().getIdEspecialidad() == null || cita.getEspecialidad().getIdEspecialidad().trim().isEmpty()) {
                throw new IllegalArgumentException("ID de especialidad es requerido");
            }
            pstmt.setString(2, cita.getEspecialidad().getIdEspecialidad().trim());

            if (cita.getEstado() == null) {
                throw new IllegalArgumentException("Estado es requerido");
            }
            pstmt.setString(3, cita.getEstado());

            pstmt.setString(4, cita.getIdCita());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating cita failed, no rows affected.");
            }
        }
    }

    public List<CitaMedica> listarCitas() throws SQLException {
        String sql = "SELECT cm.*, p.dni, p.nombres, p.ap_paterno, p.ap_materno "
                + "FROM cita_medica cm "
                + "JOIN persona p ON cm.id_persona = p.id_persona "
                + "ORDER BY fecha_cita DESC";

        List<CitaMedica> citas = new ArrayList<>();

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        CitaMedica cita = new CitaMedica();
                        cita.setIdCita(rs.getString("id_cita"));
                        cita.setFechaSolicitud(rs.getTimestamp("fecha_solicitud").toLocalDateTime());
                        cita.setFechaCita(rs.getTimestamp("fecha_cita").toLocalDateTime());
                        cita.setIdPago(rs.getString("id_pago"));
                        cita.setEstado(rs.getString("estado"));

                        Persona paciente = new Persona();
                        paciente.setIdPersona(Long.parseLong(rs.getString("id_persona")));
                        paciente.setDni(rs.getString("dni"));
                        paciente.setNombres(rs.getString("nombres"));
                        paciente.setApPaterno(rs.getString("ap_paterno"));
                        paciente.setApMaterno(rs.getString("ap_materno"));
                        cita.setPaciente(paciente);

                        Especialidad especialidad = new Especialidad();
                        especialidad.setIdEspecialidad(rs.getString("id_especialidad"));
                        cita.setEspecialidad(especialidad);

                        citas.add(cita);
                    }
                }
            }
        }
        return citas;
    }

    public List<CitaMedica> listarCitasPorEspecialidad(String idEspecialidad) throws SQLException {
        List<CitaMedica> citas = new ArrayList<>();
        String sql = "SELECT cm.*, p.dni, p.nombres, p.ap_paterno, p.ap_materno, e.nombre as nombre_especialidad "
                + "FROM cita_medica cm "
                + "JOIN persona p ON cm.id_persona = p.id_persona "
                + "JOIN especialidad e ON cm.id_especialidad = e.id_especialidad "
                + "WHERE cm.id_especialidad = ?";

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(idEspecialidad));
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        CitaMedica cita = new CitaMedica();
                        cita.setIdCita(rs.getString("id_cita"));
                        cita.setFechaSolicitud(rs.getTimestamp("fecha_solicitud").toLocalDateTime());
                        cita.setFechaCita(rs.getTimestamp("fecha_cita").toLocalDateTime());
                        cita.setIdPago(rs.getString("id_pago"));
                        cita.setEstado(rs.getString("estado"));

                        // Crear objeto Persona
                        Persona paciente = new Persona();
                        paciente.setIdPersona(Long.parseLong(rs.getString("id_persona")));
                        paciente.setDni(rs.getString("dni"));
                        paciente.setNombres(rs.getString("nombres"));
                        paciente.setApPaterno(rs.getString("ap_paterno"));
                        paciente.setApMaterno(rs.getString("ap_materno"));
                        cita.setPaciente(paciente);

                        Especialidad especialidad = new Especialidad();
                        especialidad.setIdEspecialidad(Integer.toString(rs.getInt("id_especialidad")));
                        especialidad.setNombre(rs.getString("nombre_especialidad"));
                        cita.setEspecialidad(especialidad);

                        citas.add(cita);
                    }
                }
            }
        }
        return citas;
    }
}
