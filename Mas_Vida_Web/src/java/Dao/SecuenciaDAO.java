package Dao;

import Modelo.CitaMedica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Configuracion.Conexion;

public class SecuenciaDAO {

    private static final String SQL_MAX_ID = "SELECT MAX(id_cita) as maxId FROM cita_medica";

    private Connection getConnection() throws SQLException {
        Connection conn = Conexion.Obtener_Conexion().Iniciar_Conexion();
        if (conn == null) {
            throw new SQLException("No se pudo establecer la conexión con la base de datos");
        }
        return conn;
    }

    public synchronized String generarIdCita() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            // Obtener el máximo ID existente
            ps = conn.prepareStatement(SQL_MAX_ID);
            rs = ps.executeQuery();

            int maxId = 10000; // Iniciar desde 10000
            if (rs.next()) {
                String maxIdStr = rs.getString("maxId");
                if (maxIdStr != null && !maxIdStr.isEmpty()) {
                    maxId = Integer.parseInt(maxIdStr) + 1;
                }
            }

            // Convertir a String con 5 dígitos
            String nuevoId = String.format("%05d", maxId);

            // Solo retornamos el ID generado, sin insertarlo en la base de datos
            return nuevoId;

        } catch (SQLException ex) {
            Logger.getLogger(SecuenciaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al generar ID de cita: " + ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(SecuenciaDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    // Método para generar ID único para pacientes
    public synchronized String generarIdPaciente() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            // Obtener el máximo ID de paciente existente
            String sql = "SELECT MAX(id_paciente) as maxId FROM paciente";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            int maxId = 1; // Iniciar desde 1
            if (rs.next()) {
                String maxIdStr = rs.getString("maxId");
                if (maxIdStr != null && !maxIdStr.isEmpty()) {
                    // Extraer el número del ID (ej: "PACIENTE001" -> 1)
                    String numeroStr = maxIdStr.substring(9);
                    maxId = Integer.parseInt(numeroStr) + 1;
                }
            }

            // Formatear el nuevo ID con el prefijo y ceros a la izquierda
            String nuevoId = String.format("PACIENTE%03d", maxId);

            return nuevoId;

        } catch (SQLException ex) {
            Logger.getLogger(SecuenciaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al generar ID de paciente: " + ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SecuenciaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
