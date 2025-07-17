package Controlador;

import Dao.CitaMedicaDAO;
import Modelo.CitaMedica;
import Modelo.Especialidad;
import Modelo.Persona;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@WebServlet(name = "ControladorPaciente", urlPatterns = {"/ControladorPaciente"})
public class ControladorPaciente extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();

        if (accion != null) {
            switch (accion) {
                case "inicio":
                    listarCitas(request, response);
                    break;
                case "nuevaCita":
                    nuevaCita(request, response);
                    break;
                case "listar":
                    listarCitas(request, response);
                    break;
                default:
                    listarCitas(request, response);
                    break;
            }
        } else {
            listarCitas(request, response);
        }
    }

    private void listarCitas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener el usuario de la sesión
            HttpSession session = request.getSession();
            if (session.getAttribute("usuario") != null) {
                CitaMedicaDAO citaMedicaDAO = CitaMedicaDAO.getInstance();
                request.setAttribute("CitaMedicaDAO", citaMedicaDAO);

                // Obtener las citas del paciente
                String idUsuario = ((Modelo.Usuario) session.getAttribute("usuario")).getIdUsuario();
                List<CitaMedica> citas = citaMedicaDAO.listarCitasPorPaciente(idUsuario);
                request.setAttribute("citas", citas);

                request.getRequestDispatcher("/jsp/vistaPaciente.jsp").forward(request, response);
            } else {
                response.sendRedirect("/login.jsp");
            }
        } catch (SQLException ex) {
            request.setAttribute("error", "Error al listar citas: " + ex.getMessage());
            request.getRequestDispatcher("/jsp/vistaPaciente.jsp").forward(request, response);
        }
    }

    private void nuevaCita(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener datos del formulario
            String tipoExamen = request.getParameter("tipoExamen");
            String fecha = request.getParameter("fecha");
            String hora = request.getParameter("hora");

            // Crear objeto CitaMedica
            CitaMedica cita = new CitaMedica();
            cita.setFechaSolicitud(java.time.LocalDateTime.now());
            cita.setFechaCita(java.time.LocalDateTime.parse(fecha + "T" + hora));

            // Crear especialidad
            Especialidad especialidad = new Especialidad();
            especialidad.setIdEspecialidad(tipoExamen);
            cita.setEspecialidad(especialidad);

            // Obtener paciente de la sesión
            HttpSession session = request.getSession();
            Persona paciente = (Persona) session.getAttribute("persona");
            cita.setPaciente(paciente);

            // Establecer otros datos
            cita.setIdPago("CONTADO");
            cita.setEstado("PENDIENTE");

            // Generar ID de cita
            CitaMedicaDAO citaMedicaDAO = CitaMedicaDAO.getInstance();
            int ultimoId = citaMedicaDAO.obtenerUltimoIdCita();
            cita.setIdCita("CITA" + String.format("%04d", ultimoId + 1));

            // Crear cita
            citaMedicaDAO.crearCita(cita);

            request.setAttribute("mensaje", "Cita creada exitosamente");
            listarCitas(request, response);

        } catch (SQLException ex) {
            request.setAttribute("error", "Error al crear cita: " + ex.getMessage());
            request.getRequestDispatcher("/jsp/vistaPaciente.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controlador de Citas Médicas";
    }
}
