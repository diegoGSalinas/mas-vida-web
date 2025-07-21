package Dao;

import Modelo.HistorialMedico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistorialMedicoDAO {

    private static HistorialMedicoDAO instance;
    private final Configuracion.Conexion conexion;

    private HistorialMedicoDAO() {
        this.conexion = Configuracion.Conexion.Obtener_Conexion();
        this.conexion.Iniciar_Conexion();
    }

    public static HistorialMedicoDAO getInstance() {
        if (instance == null) {
            instance = new HistorialMedicoDAO();
        }
        return instance;
    }

    public void crearHistorialMedico(HistorialMedico historial) throws SQLException {
        String sql = "INSERT INTO historial_medico (id_historial, id_persona, id_examen, motivo, diagnostico, tratamiento) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.Iniciar_Conexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, historial.getIdHistorial());
            pstmt.setLong(2, historial.getIdPersona());

            if (historial.getIdExamen() == null) {
                System.out.println("[DEBUG] Guardando null para id_examen");
                pstmt.setNull(3, java.sql.Types.VARCHAR);
            } else {
                System.out.println("[DEBUG] Guardando id_examen: " + historial.getIdExamen());
                pstmt.setString(3, historial.getIdExamen());
            }

            pstmt.setString(4, historial.getMotivo());
            pstmt.setString(5, historial.getDiagnostico());
            pstmt.setString(6, historial.getTratamiento());

            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("[DEBUG] Filas afectadas: " + filasAfectadas);

            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo guardar el historial médico");
            }
        }
    }

    public List<HistorialMedico> listarPorPersona(long idPersona) throws SQLException {
        List<HistorialMedico> historiales = new ArrayList<>();
        String sql = "SELECT * FROM historial_medico WHERE id_persona = ? ORDER BY id_historial DESC";

        try (Connection conn = conexion.Iniciar_Conexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, idPersona);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    HistorialMedico historial = new HistorialMedico();
                    historial.setIdHistorial(rs.getString("id_historial"));
                    historial.setIdPersona(rs.getLong("id_persona"));
                    historial.setIdExamen(rs.getString("id_examen"));
                    historial.setMotivo(rs.getString("motivo"));
                    historial.setDiagnostico(rs.getString("diagnostico"));
                    historial.setTratamiento(rs.getString("tratamiento"));

                    historiales.add(historial);
                }
            }
        }
        return historiales;
    }
}
