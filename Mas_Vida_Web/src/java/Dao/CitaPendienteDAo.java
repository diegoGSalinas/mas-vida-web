/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Configuracion.Conexion;
import java.sql.*;
import java.util.*;

public class CitaPendienteDAo {

    public List<Map<String, String>> listarCitasPendientesPorDoctor(String idDoctor) {
        List<Map<String, String>> lista = new ArrayList<>();

        if (idDoctor == null || idDoctor.isEmpty()) {
            return lista; // Retorna lista vacía si el ID es nulo o vacío
        }

        String sql = "SELECT c.id_cita, c.fecha_solicitud, c.fecha_cita, " +
                     "CONCAT(p.nombres, ' ', p.ap_paterno, ' ', IFNULL(p.ap_materno, '')) AS nombre_paciente, " +
                     "c.estado " +
                     "FROM cita_medica c " +
                     "INNER JOIN paciente pa ON c.id_persona = pa.id_persona " +
                     "INNER JOIN persona p ON pa.id_persona = p.id_persona " +
                     "WHERE c.id_especialidad = (SELECT id_especialidad FROM doctor WHERE id_doctor = ?) " +
                     "AND c.estado = 'Pendiente' " +
                     "ORDER BY c.fecha_cita ASC";

        try (Connection conn = Conexion.Obtener_Conexion().Iniciar_Conexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idDoctor);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> fila = new HashMap<>();
                    fila.put("id_cita", rs.getString("id_cita"));
                    fila.put("fecha_solicitud", rs.getString("fecha_solicitud"));
                    fila.put("fecha_cita", rs.getString("fecha_cita"));
                    fila.put("nombre_paciente", rs.getString("nombre_paciente"));
                    fila.put("estado", rs.getString("estado"));
                    lista.add(fila);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener citas pendientes: " + e.getMessage());
            e.printStackTrace(); // Para depurar en desarrollo
        }

        return lista;
    }
}
