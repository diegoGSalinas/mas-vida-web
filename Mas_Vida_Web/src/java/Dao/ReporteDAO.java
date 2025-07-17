package Dao;

import Modelo.Reporte;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {
    private static ReporteDAO instance;

    private ReporteDAO() {
    }

    public static synchronized ReporteDAO getInstance() {
        if (instance == null) {
            instance = new ReporteDAO();
        }
        return instance;
    }

    public List<Reporte> listarReportes() throws SQLException {
        String sql = "SELECT * FROM reportes ORDER BY fecha_generacion DESC";
        List<Reporte> reportes = new ArrayList<>();

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Reporte reporte = new Reporte();
                        reporte.setIdReporte(rs.getString("id_reporte"));
                        reporte.setTipo(rs.getString("tipo"));
                        reporte.setFechaGeneracion(rs.getTimestamp("fecha_generacion").toLocalDateTime());
                        reporte.setDescripcion(rs.getString("descripcion"));
                        reportes.add(reporte);
                    }
                }
            }
        }
        return reportes;
    }

    public void crearReporte(Reporte reporte) throws SQLException {
        String sql = "INSERT INTO reportes (id_reporte, tipo, fecha_generacion, descripcion) VALUES (?, ?, ?, ?)";

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, reporte.getIdReporte());
            pstmt.setString(2, reporte.getTipo());
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(reporte.getFechaGeneracion()));
            pstmt.setString(4, reporte.getDescripcion());
            pstmt.executeUpdate();
        }
    }
}
