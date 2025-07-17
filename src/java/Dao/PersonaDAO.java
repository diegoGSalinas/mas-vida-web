package Dao;

import Modelo.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonaDAO {

    private static PersonaDAO instance;

    private PersonaDAO() {
    }

    public static synchronized PersonaDAO getInstance() {
        if (instance == null) {
            instance = new PersonaDAO();
        }
        return instance;
    }

    public long crearPersona(Persona persona) throws SQLException {
        String sql = "INSERT INTO persona (nombres, ap_paterno, ap_materno, dni, correo, direccion, telefono, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, persona.getNombres());
            pstmt.setString(2, persona.getApPaterno());
            pstmt.setString(3, persona.getApMaterno());
            pstmt.setString(4, persona.getDni());
            pstmt.setString(5, persona.getCorreo());
            pstmt.setString(6, persona.getDireccion());
            pstmt.setString(7, persona.getTelefono());
            pstmt.setDate(8, new java.sql.Date(persona.getFechaNacimiento().getTime()));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating persona failed, no rows affected.");
            }

            try (java.sql.ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
                throw new SQLException("Creating persona failed, no ID obtained.");
            }
        }
    }

    public long obtenerUltimoIdPersona() throws SQLException {
        String sql = "SELECT MAX(id_persona) as max_id FROM persona";

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("max_id");
                }
                return 0; // Si no hay registros
            }
        }
    }

    public long obtenerIdPersonaPorDni(String dni) throws SQLException {
        String sql = "SELECT id_persona FROM persona WHERE dni = ?";

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dni);
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id_persona");
                }
                return -1; // -1 si no se encuentra la persona
            }
        }
    }

    public Persona buscarPorId(long idPersona) throws SQLException {
        String sql = "SELECT * FROM persona WHERE id_persona = ?";

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, idPersona);
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Persona persona = new Persona();
                    persona.setIdPersona(rs.getLong("id_persona"));
                    persona.setNombres(rs.getString("nombres"));
                    persona.setApPaterno(rs.getString("ap_paterno"));
                    persona.setApMaterno(rs.getString("ap_materno"));
                    persona.setDni(rs.getString("dni"));
                    persona.setCorreo(rs.getString("correo"));
                    persona.setDireccion(rs.getString("direccion"));
                    persona.setTelefono(rs.getString("telefono"));
                    persona.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                    return persona;
                }
                return null;
            }
        }
    }

    public Persona buscarPorDni(String dni) throws SQLException {
        String sql = "SELECT * FROM persona WHERE dni = ?";

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dni);

            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Persona persona = new Persona();
                    persona.setIdPersona(rs.getLong("id_persona"));
                    persona.setNombres(rs.getString("nombres"));
                    persona.setApPaterno(rs.getString("ap_paterno"));
                    persona.setApMaterno(rs.getString("ap_materno"));
                    persona.setDni(rs.getString("dni"));
                    persona.setCorreo(rs.getString("correo"));
                    persona.setDireccion(rs.getString("direccion"));
                    persona.setTelefono(rs.getString("telefono"));
                    persona.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                    return persona;
                }
                return null;
            }
        }
    }

    public java.util.List<Persona> buscarPorApellidos(String apPaterno, String apMaterno) throws SQLException {
        // Convertir los parámetros a minúsculas para hacer la búsqueda insensible a mayúsculas/minúsculas
        apPaterno = apPaterno.toLowerCase();
        apMaterno = apMaterno.toLowerCase();

        String sql = "SELECT * FROM persona WHERE LOWER(ap_paterno) = ? AND LOWER(ap_materno) = ?";

        try (Connection conn = Configuracion.Conexion.Obtener_Conexion().Iniciar_Conexion()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, apPaterno);
            pstmt.setString(2, apMaterno);

            java.util.List<Persona> personas = new java.util.ArrayList<>();
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Persona persona = new Persona();
                    persona.setIdPersona(rs.getLong("id_persona"));
                    persona.setNombres(rs.getString("nombres"));
                    persona.setApPaterno(rs.getString("ap_paterno"));
                    persona.setApMaterno(rs.getString("ap_materno"));
                    persona.setDni(rs.getString("dni"));
                    persona.setCorreo(rs.getString("correo"));
                    persona.setDireccion(rs.getString("direccion"));
                    persona.setTelefono(rs.getString("telefono"));
                    persona.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                    personas.add(persona);
                }
            }
            return personas;
        }
    }
}
