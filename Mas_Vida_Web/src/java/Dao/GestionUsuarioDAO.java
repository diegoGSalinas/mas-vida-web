

    //Document   : usuarios
    //Created on : 16 jul. 2025, 17:44:32
    //Author     : ale

package Dao;


import Modelo.Usuario;
import Modelo.TipoUsuario;
import Configuracion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

public class GestionUsuarioDAO {

    private final Conexion conexion;

    public GestionUsuarioDAO() {
        this.conexion = Conexion.Obtener_Conexion(); // Asumiendo que ya tienes esta clase
    }

    public List<Usuario> obtenerTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.nombre_usuario, u.contrasena, u.id_tipo_usuario as prioridad " +
                     "FROM usuario u";

        try (
            Connection con = conexion.Iniciar_Conexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getString("id_usuario"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setContrasena(rs.getString("contrasena")); 

                int prioridad = rs.getInt("prioridad");
                TipoUsuario tipo = mapearTipoUsuario(prioridad);
                usuario.setTipoUsuario(tipo);

                lista.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }

        return lista;
    }
    public List<String> obtenerNombresTipoUsuario() {
    List<String> nombres = new ArrayList<>();
    // Usamos la prioridad del enum TipoUsuario en lugar de la tabla
    for (TipoUsuario tipo : TipoUsuario.values()) {
        nombres.add(tipo.getNombre());
    }
    return nombres;
}
    
    public Usuario buscarPorId(String idUsuario) {
    Usuario usuario = null;
    String sql = "SELECT u.id_usuario, u.nombre_usuario, u.contrasena, u.id_tipo_usuario as prioridad " +
             "FROM usuario u " +
             "WHERE u.id_usuario = ?";

    try (
        Connection con = conexion.Iniciar_Conexion();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {
        ps.setString(1, idUsuario);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getString("id_usuario"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setContrasena(rs.getString("contrasena"));

                int prioridad = rs.getInt("prioridad");
                TipoUsuario tipo = mapearTipoUsuario(prioridad);
                usuario.setTipoUsuario(tipo);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al buscar usuario por ID: " + e.getMessage());
    }

    return usuario;
}
    private TipoUsuario mapearTipoUsuario(int prioridad) {
        for (TipoUsuario tipo : TipoUsuario.values()) {
            if (tipo.getPrioridad() == prioridad) {
                return tipo;
            }
        }
        return null;
    }
    public boolean guardar(Usuario usuario) {
    String sql = "INSERT INTO usuario (id_usuario, nombre_usuario, contrasena, id_tipo_usuario) VALUES (?, ?, ?, ?)";

    try (
        Connection con = conexion.Iniciar_Conexion();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {
        ps.setString(1, usuario.getIdUsuario());
        ps.setString(2, usuario.getNombreUsuario());
        ps.setString(3, usuario.getContrasena());
        ps.setInt(4, usuario.getTipoUsuario().getPrioridad()); // usamos la prioridad como id_tipo_usuario

        int filasAfectadas = ps.executeUpdate();
        return filasAfectadas > 0;
    } catch (SQLException e) {
        System.err.println("Error al guardar usuario: " + e.getMessage());
        return false;
    }
}
    public void actualizar(Usuario usuario, String estado) throws SQLException {
    String sql = "UPDATE usuario SET nombre_usuario = ?, contrasena = ?, estado = ?, id_tipo_usuario = ? WHERE id_usuario = ?";
    try (Connection con = conexion.Iniciar_Conexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, usuario.getNombreUsuario());
        ps.setString(2, usuario.getContrasena());
        ps.setString(3, estado);
        ps.setInt(4, usuario.getTipoUsuario().getPrioridad());
        ps.setString(5, usuario.getIdUsuario());

        ps.executeUpdate();
    }
}
    public void eliminar(String idUsuario) {
    String sql = "DELETE FROM usuario WHERE id_usuario = ?";
    try (Connection con = conexion.Iniciar_Conexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, idUsuario); // Cambio aquí: .setString en lugar de .setInt
        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public Usuario crearUsuarioYPersona(Usuario usuario, String nombres, String apPaterno, String apMaterno, String dni, String correo, String direccion, String telefono, String fechaNacimientoStr) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = conexion.Iniciar_Conexion();
            con.setAutoCommit(false); // Iniciar transacción

            // 1. Crear la persona primero
            String sqlPersona = "INSERT INTO persona (nombres, ap_paterno, ap_materno, dni, correo, direccion, telefono, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nombres);
            ps.setString(2, apPaterno);
            ps.setString(3, apMaterno);
            ps.setString(4, dni);
            ps.setString(5, correo);
            ps.setString(6, direccion);
            ps.setString(7, telefono);
            
            // Convertir la fecha de nacimiento de String a Date
            java.sql.Date fechaNacimiento = java.sql.Date.valueOf(fechaNacimientoStr);
            ps.setDate(8, fechaNacimiento);
            ps.executeUpdate();
            
            // Obtener el id_persona generado
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                Long idPersona = rs.getLong(1);
                usuario.setIdPersona(idPersona);
            }

            // 2. Crear el usuario
            String sqlUsuario = "INSERT INTO usuario (id_usuario, nombre_usuario, contrasena, id_tipo_usuario, id_persona) VALUES (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sqlUsuario);
            ps.setString(1, usuario.getIdUsuario());
            ps.setString(2, usuario.getNombreUsuario());
            ps.setString(3, usuario.getContrasena());
            ps.setInt(4, usuario.getTipoUsuario().getPrioridad());
            ps.setLong(5, usuario.getIdPersona());
            ps.executeUpdate();

            con.commit(); // Confirmar transacción
            return usuario;
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Revertir cambios en caso de error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
