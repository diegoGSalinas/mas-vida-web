package Controlador;

import Dao.CitaMedicaDAO;
import Dao.HistorialMedicoDAO;
import Dao.PersonaDAO;
import Modelo.CitaMedica;
import Modelo.HistorialMedico;
import Modelo.Persona;
import Modelo.Usuario;
import Modelo.TipoUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.sql.SQLException;

@WebServlet(name = "ControladorDoctor", urlPatterns = {"/ControladorDoctor"})
public class ControladorDoctor extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession(false);

        if (sesion == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        String idEspecialidad = (String) sesion.getAttribute("id_especialidad");

        // Verificación de sesión y tipo de usuario
        if (usuario == null || usuario.getTipoUsuario() != TipoUsuario.DOCTOR || idEspecialidad == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String accion = request.getParameter("accion");

        if (accion == null) {
            try {
                CitaMedicaDAO dao = CitaMedicaDAO.getInstance();
                List<CitaMedica> citas = dao.listarCitasPorEspecialidad(idEspecialidad);

                // citas del día
                int citasDelDia = dao.contarCitasDelDia(idEspecialidad);

                request.setAttribute("citasDelDia", citasDelDia);
                System.out.println("Citas del día: " + citasDelDia);
                request.setAttribute("citas", citas);
                request.setAttribute("titulo", "Citas Médicas");
                request.getRequestDispatcher("/jsp/vistaDoctor.jsp").forward(request, response);
            } catch (SQLException e) {
                request.setAttribute("error", "Error al obtener las citas: " + e.getMessage());
                request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            }
            return;
        }

        switch (accion) {
            case "verCitas":
                try {
                    CitaMedicaDAO dao = CitaMedicaDAO.getInstance();
                    List<CitaMedica> citas = dao.listarCitasPorEspecialidad(idEspecialidad);

                    request.setAttribute("titulo", "Citas Pendientes");
                    request.setAttribute("citasPendientes", citas);
                    request.getRequestDispatcher("/jsp/vistaCitasPendientes.jsp").forward(request, response);
                } catch (SQLException e) {
                    request.setAttribute("error", "Error al obtener las citas: " + e.getMessage());
                    request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
                }
                break;

            case "verPacientes":
                request.setAttribute("titulo", "Pacientes del Doctor");
                request.getRequestDispatcher("/jsp/vistaPacienteDoc.jsp").forward(request, response);
                break;

            case "verCitasMedicas":
                try {
                    CitaMedicaDAO dao = CitaMedicaDAO.getInstance();
                    List<CitaMedica> citas = dao.listarCitasPorEspecialidad(idEspecialidad);
                    request.setAttribute("citas", citas);
                    request.setAttribute("titulo", "Citas Médicas por Especialidad");
                    request.getRequestDispatcher("/jsp/vistaCitasMedicas.jsp").forward(request, response);
                } catch (SQLException e) {
                    request.setAttribute("error", "Error al obtener las citas: " + e.getMessage());
                    request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
                }
                break;

            case "verExamenes":
                request.setAttribute("titulo", "Exámenes Médicos");
                request.getRequestDispatcher("/jsp/vistaExamenDoc.jsp").forward(request, response);
                break;

            case "verHistorial":
                try {
                    String idPersonaStr = request.getParameter("idPersona");
                    if (idPersonaStr == null) {
                        throw new IllegalArgumentException("ID de paciente no proporcionado");
                    }

                    long idPersona = Long.parseLong(idPersonaStr);

                    // historial del paciente
                    HistorialMedicoDAO historialDAO = HistorialMedicoDAO.getInstance();
                    List<HistorialMedico> historiales = historialDAO.listarPorPersona(idPersona);

                    // datos del paciente
                    PersonaDAO personaDAO = PersonaDAO.getInstance();
                    Persona paciente = personaDAO.buscarPorId(idPersona);

                    if (paciente == null) {
                        throw new IllegalArgumentException("Paciente no encontrado");
                    }

                    request.setAttribute("historiales", historiales);
                    request.setAttribute("paciente", paciente);
                    request.getRequestDispatcher("/jsp/historialPaciente.jsp").forward(request, response);
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "ID de paciente inválido: " + e.getMessage());
                    request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
                } catch (SQLException e) {
                    request.setAttribute("error", "Error al obtener el historial: " + e.getMessage());
                    request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
                }
                break;

            case "editarEstado":
                try {
                    String idCita = request.getParameter("idCita");
                    String nuevoEstado = request.getParameter("nuevoEstado");

                    if (idCita == null || nuevoEstado == null) {
                        request.setAttribute("error", "Faltan parámetros necesarios");
                        request.getRequestDispatcher("/jsp/vistaCitasMedicas.jsp").forward(request, response);
                        return;
                    }

                    CitaMedicaDAO dao = CitaMedicaDAO.getInstance();
                    boolean exito = dao.actualizarEstadoCita(idCita, nuevoEstado);

                    if (exito) {
                        request.setAttribute("mensaje", "Estado de la cita actualizado exitosamente");
                    } else {
                        request.setAttribute("error", "Error al actualizar el estado de la cita");
                    }

                    response.sendRedirect("/Mas_Vida_Web/ControladorDoctor?accion=verCitasMedicas");
                    return;
                } catch (SQLException e) {
                    request.setAttribute("error", "Error al actualizar el estado: " + e.getMessage());
                    request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
                }
                break;

            default:
                request.getRequestDispatcher("/jsp/vistaCitasPendientes.jsp").forward(request, response);
                break;
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
        return "Controlador del Panel de Doctor";
    }
}
