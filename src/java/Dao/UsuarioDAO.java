package Dao;

import Configuracion.Conexion;
import Modelo.Usuario;
import Modelo.TipoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    private final Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    private static final Conexion conexion = Configuracion.Conexion.Obtener_Conexion();

    public UsuarioDAO() {
        con = conexion.Iniciar_Conexion();
    }

    public Usuario validarUsuario(String usuario, String password) {
        Usuario user = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = conexion.Iniciar_Conexion();

            if (usuario == null || password == null) {
                throw new SQLException("Usuario o contraseña no pueden ser nulos");
            }

            String sql = "SELECT u.*, tu.prioridad FROM usuario u "
                    + "INNER JOIN tipo_usuario tu ON u.id_tipo_usuario = tu.id_tipo_usuario "
                    + "WHERE u.nombre_usuario = ? AND u.contrasena = ? AND u.estado = 'Activo'";

            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new Usuario();
                user.setIdUsuario(rs.getString("id_usuario"));
                user.setNombreUsuario(rs.getString("nombre_usuario"));

                // Obtener el TipoUsuario usando el método fromPrioridad
                int prioridad = rs.getInt("prioridad");
                TipoUsuario tipo = TipoUsuario.values()[prioridad - 1];

                user.setTipoUsuario(tipo);
            }
        } catch (SQLException e) {
            System.err.println("Error al validar usuario: " + e.getMessage());
            return null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexiones: " + e.getMessage());
            }
        }
        return user;
    }
}
