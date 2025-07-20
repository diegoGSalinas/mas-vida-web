package Dao;


import Configuracion.Conexion;
import Modelo.CitaMedica;
import Modelo.Doctor;
import Modelo.Especialidad;
import Modelo.ExamenMedico;
import Modelo.Paciente;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExamenMedicoDAO {
    
    private final Conexion conexion;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public ExamenMedicoDAO() {
        this.conexion = Conexion.Obtener_Conexion();
    }

    public List<ExamenMedico> listarTodos() {
        List<ExamenMedico> lista = new ArrayList<>();

        String sql = "SELECT em.id_examen, em.fecha, em.resultados, "
           + "cm.id_cita, cm.id_especialidad, "
           + "e.nombre AS nombre_especialidad, "
           + "p.id_persona, p.nombres, p.ap_paterno "
           + "FROM examen_medico em "
           + "INNER JOIN cita_medica cm ON em.id_cita = cm.id_cita "
           + "INNER JOIN especialidad e ON cm.id_especialidad = e.id_especialidad "
           + "INNER JOIN persona p ON cm.id_persona = p.id_persona "
           + "ORDER BY em.fecha DESC";

        try {
            Connection con = conexion.Iniciar_Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Especialidad especialidad = new Especialidad();
                especialidad.setIdEspecialidad(rs.getString("id_especialidad"));
                especialidad.setNombre(rs.getString("nombre_especialidad"));

                CitaMedica cita = new CitaMedica();
                cita.setIdCita(rs.getString("id_cita"));
                cita.setEspecialidad(especialidad);

                Paciente paciente = new Paciente();
                paciente.setIdPaciente(rs.getString("id_persona"));
                paciente.setNombres(rs.getString("nombres"));
                paciente.setApPaterno(rs.getString("ap_paterno"));

                Doctor tecnico = new Doctor();
                

                ExamenMedico examen = new ExamenMedico();
                examen.setIdExamen(rs.getString("id_examen"));
                examen.setFechaExamen(rs.getTimestamp("fecha").toLocalDateTime());
                examen.setCitaMedica(cita);
                examen.setPaciente(paciente);
                examen.setResultados(rs.getString("resultados"));
                examen.setDoctor(tecnico);
                examen.setFechaRegistro(rs.getTimestamp("fecha").toLocalDateTime()); // Usamos la misma fecha por ahora
                System.out.println("Examen agregado: " + examen.getIdExamen());
                lista.add(examen);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Total exámenes encontrados: " + lista.size());
        return lista;
    }

    public List<ExamenMedico> listarPorEspecialidad(String especialidad) {
         if (especialidad == null || especialidad.trim().isEmpty()) {
        return listarTodos();
    }
    List<ExamenMedico> lista = new ArrayList<>();
    

    String sql = "SELECT em.id_examen, em.fecha, em.resultados, "
           + "cm.id_cita, cm.id_especialidad, "
           + "e.nombre AS nombre_especialidad, "
           + "p.id_persona, p.nombres, p.ap_paterno "
           + "FROM examen_medico em "
           + "INNER JOIN cita_medica cm ON em.id_cita = cm.id_cita "
           + "INNER JOIN especialidad e ON cm.id_especialidad = e.id_especialidad "
           + "INNER JOIN persona p ON cm.id_persona = p.id_persona "
           + "WHERE e.nombre = ? "
           + "ORDER BY em.fecha DESC";

    try {
        Connection con = conexion.Iniciar_Conexion();
        ps = con.prepareStatement(sql);
        ps.setString(1, especialidad);
        rs = ps.executeQuery();

        while (rs.next()) {
            Especialidad esp = new Especialidad();
            esp.setIdEspecialidad(rs.getString("id_especialidad"));
            esp.setNombre(rs.getString("nombre_especialidad"));

            CitaMedica cita = new CitaMedica();
            cita.setIdCita(rs.getString("id_cita"));
            cita.setEspecialidad(esp);

            Paciente paciente = new Paciente();
            paciente.setIdPaciente(rs.getString("id_persona"));
            paciente.setNombres(rs.getString("nombres"));
            paciente.setApPaterno(rs.getString("ap_paterno"));

            // No se están trayendo datos del doctor, así que dejamos el objeto vacío por ahora
            Doctor tecnico = new Doctor();

            ExamenMedico examen = new ExamenMedico();
            examen.setIdExamen(rs.getString("id_examen"));
            examen.setFechaExamen(rs.getTimestamp("fecha").toLocalDateTime());
            examen.setResultados(rs.getString("resultados"));
            examen.setCitaMedica(cita);
            examen.setPaciente(paciente);
            examen.setDoctor(tecnico); // Objeto vacío
            examen.setFechaRegistro(rs.getTimestamp("fecha").toLocalDateTime());

            lista.add(examen);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}

    public void registrarExamen(ExamenMedico examen) throws SQLException {
    String sql = "INSERT INTO examen_medico (id_examen, fecha, resultados, id_tecnico, id_cita) VALUES (?, ?, ?, ?, ?)";
    try (Connection con = conexion.Iniciar_Conexion();
        PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, examen.getIdExamen());
        ps.setTimestamp(2, Timestamp.valueOf(examen.getFechaExamen()));
        ps.setString(3, examen.getResultados());
        ps.setString(4, examen.getIdTecnico());
        ps.setString(5, examen.getIdCita());
        ps.executeUpdate();
    }
}
}
