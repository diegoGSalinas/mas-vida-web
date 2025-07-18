

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

public class GestionUsuarioDAO {

    private final Conexion conexion;

    public GestionUsuarioDAO() {
        this.conexion = Conexion.Obtener_Conexion(); // Asumiendo que ya tienes esta clase
    }

    public List<Usuario> obtenerTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.nombre_usuario, u.contrasena, tu.prioridad " +
                     "FROM usuario u " +
                     "INNER JOIN tipo_usuario tu ON u.id_tipo_usuario = tu.id_tipo_usuario";

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
    String sql = "SELECT nombre FROM tipo_usuario";

    try (Connection con = conexion.Iniciar_Conexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            nombres.add(rs.getString("nombre"));
        }

    } catch (SQLException e) {
        System.out.println("Error al obtener nombres de tipo usuario: " + e.getMessage());
    }

    return nombres;
}
    
    public Usuario buscarPorId(String idUsuario) {
    Usuario usuario = null;
    String sql = "SELECT u.id_usuario, u.nombre_usuario, u.contrasena, tu.nombre " +
             "FROM usuario u " +
             "INNER JOIN tipo_usuario tu ON u.id_tipo_usuario = tu.id_tipo_usuario " +
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

    String nombreTipo = rs.getString("nombre");
    TipoUsuario tipo = TipoUsuario.valueOf(nombreTipo.toUpperCase()); // Si coincide con enum
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

}
