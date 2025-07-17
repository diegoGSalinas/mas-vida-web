package Dao;

import Modelo.Especialidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Configuracion.Conexion;
import java.sql.Connection;

public class EspecialidadDAO {

    private Connection obtenerConexion() {
        return Conexion.Obtener_Conexion().Iniciar_Conexion();
    }

    public List<Especialidad> listar() {
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement("SELECT id_especialidad, nombre, descripcion FROM especialidad"); ResultSet rs = ps.executeQuery()) {

            List<Especialidad> especialidades = new ArrayList<>();
            while (rs.next()) {
                Especialidad especialidad = new Especialidad(
                        rs.getString("id_especialidad"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                especialidades.add(especialidad);
            }
            return especialidades;

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar especialidades: " + e.getMessage(), e);
        }
    }

    public Especialidad buscarPorId(int id) {
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement("SELECT id_especialidad, nombre, descripcion FROM especialidad WHERE id_especialidad = ?")) {

            ps.setString(1, String.valueOf(id));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Especialidad(
                        rs.getString("id_especialidad"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar especialidad: " + e.getMessage(), e);
        }
    }
}
