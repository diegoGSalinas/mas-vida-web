package Dao;

import Modelo.Dashboard;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DashboardDAO {
    private static DashboardDAO instance;

    private DashboardDAO() {
    }

    public static synchronized DashboardDAO getInstance() {
        if (instance == null) {
            instance = new DashboardDAO();
        }
        return instance;
    }

    public Dashboard obtenerDashboard() throws SQLException {
        Dashboard dashboard = new Dashboard();
        
        // Obtener estadísticas básicas
        obtenerEstadisticasBasicas(dashboard);
        
        // Obtener citas por fecha
        obtenerCitasPorFecha(dashboard);
        
        // Obtener citas por especialidad
        obtenerCitasPorEspecialidad(dashboard);
        
        return dashboard;
    }

    private void obtenerEstadisticasBasicas(Dashboard dashboard) throws SQLException {
        String sql = "SELECT "
                + "(SELECT COUNT(*) FROM usuario) as total_usuarios, "
                + "(SELECT COUNT(*) FROM paciente) as total_pacientes, "
                + "(SELECT COUNT(*) FROM cita_medica) as total_citas, "
                + "(SELECT COUNT(*) FROM cita_medica WHERE estado = 'Pendiente') as total_pendientes, "
                + "(SELECT COUNT(*) FROM cita_medica WHERE estado = 'Confirmada') as total_confirmadas, "
                + "(SELECT COUNT(*) FROM cita_medica WHERE estado = 'Cancelada') as total_canceladas";

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        dashboard.setTotalUsuarios(rs.getInt("total_usuarios"));
                        dashboard.setTotalPacientes(rs.getInt("total_pacientes"));
                        dashboard.setTotalCitas(rs.getInt("total_citas"));
                        dashboard.setTotalPendientes(rs.getInt("total_pendientes"));
                        dashboard.setTotalConfirmadas(rs.getInt("total_confirmadas"));
                        dashboard.setTotalCanceladas(rs.getInt("total_canceladas"));
                    }
                }
            }
        }
    }

    private void obtenerCitasPorFecha(Dashboard dashboard) throws SQLException {
        String sql = "SELECT DATE(fecha_cita) as fecha, COUNT(*) as cantidad "
                + "FROM cita_medica "
                + "WHERE fecha_cita >= CURRENT_DATE - INTERVAL 30 DAY "
                + "GROUP BY DATE(fecha_cita) "
                + "ORDER BY fecha";

        Map<LocalDate, Integer> citasPorFecha = new HashMap<>();
        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        LocalDate fecha = rs.getDate("fecha").toLocalDate();
                        int cantidad = rs.getInt("cantidad");
                        citasPorFecha.put(fecha, cantidad);
                    }
                }
            }
        }
        dashboard.setCitasPorFecha(citasPorFecha);
    }

    private void obtenerCitasPorEspecialidad(Dashboard dashboard) throws SQLException {
        String sql = "SELECT e.nombre as especialidad, COUNT(*) as cantidad "
                + "FROM cita_medica cm "
                + "JOIN especialidad e ON cm.id_especialidad = e.id_especialidad "
                + "GROUP BY e.nombre "
                + "ORDER BY cantidad DESC";

        Map<String, Integer> citasPorEspecialidad = new HashMap<>();
        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String especialidad = rs.getString("especialidad");
                        int cantidad = rs.getInt("cantidad");
                        citasPorEspecialidad.put(especialidad, cantidad);
                        System.out.println("Especialidad: " + especialidad + ", Cantidad: " + cantidad);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener citas por especialidad: " + e.getMessage());
            throw e;
        }
        
        // Verificar si hay datos antes de asignar
        if (citasPorEspecialidad.isEmpty()) {
            System.out.println("No se encontraron citas por especialidad");
        } else {
            System.out.println("Total de especialidades encontradas: " + citasPorEspecialidad.size());
        }
        
        dashboard.setCitasPorEspecialidad(citasPorEspecialidad);
    }
}
