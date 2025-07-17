package Dao;

import Modelo.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PacienteDAO {

    private static PacienteDAO instance;

    private PacienteDAO() {
    }

    public static synchronized PacienteDAO getInstance() {
        if (instance == null) {
            instance = new PacienteDAO();
        }
        return instance;
    }

    public void crearPaciente(Paciente paciente) throws SQLException {
        String sql = "INSERT INTO paciente (id_paciente, fecha_inscripcion, id_persona) VALUES (?, ?, ?)";

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, paciente.getIdPaciente());
            pstmt.setDate(2, new java.sql.Date(paciente.getFechaInscripcion().getTime()));
            pstmt.setLong(3, paciente.getIdPersona());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating paciente failed, no rows affected.");
            }
        } catch (SQLException e) {
            System.err.println("Error al crear paciente: " + e.getMessage());
            throw e;
        }
    }
}
