package Dao;

import Modelo.ListaExamenMedico;
import Configuracion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListaExamenMedicoDAO {

    private static ListaExamenMedicoDAO instance;
    private Connection conn;

    private ListaExamenMedicoDAO() {
        this.conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion();
    }

    public static ListaExamenMedicoDAO getInstance() {
        if (instance == null) {
            instance = new ListaExamenMedicoDAO();
        }
        return instance;
    }

    public List<ListaExamenMedico> listarTodos() throws SQLException {
        List<ListaExamenMedico> examenes = new ArrayList<>();
        String sql = "SELECT * FROM lista_examen_medico";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ListaExamenMedico examen = new ListaExamenMedico();
                examen.setIdExamenMedico(rs.getString("id_examen_medico"));
                examen.setNombre(rs.getString("nombre"));
                examen.setPrecio(rs.getDouble("precio"));
                examen.setDescripcion(rs.getString("descripcion"));
                examenes.add(examen);
            }
        }
        return examenes;
    }

    public ListaExamenMedico buscarPorId(String idExamenMedico) throws SQLException {
        String sql = "SELECT * FROM lista_examen_medico WHERE id_examen_medico = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idExamenMedico);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ListaExamenMedico examen = new ListaExamenMedico();
                    examen.setIdExamenMedico(rs.getString("id_examen_medico"));
                    examen.setNombre(rs.getString("nombre"));
                    examen.setPrecio(rs.getDouble("precio"));
                    examen.setDescripcion(rs.getString("descripcion"));
                    return examen;
                }
            }
        }
        return null;
    }

    public void crearExamen(ListaExamenMedico examen) throws SQLException {
        String sql = "INSERT INTO lista_examen_medico (id_examen_medico, nombre, precio, descripcion) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, examen.getIdExamenMedico());
            ps.setString(2, examen.getNombre());
            ps.setDouble(3, examen.getPrecio());
            ps.setString(4, examen.getDescripcion());
            ps.executeUpdate();
        }
    }
}
