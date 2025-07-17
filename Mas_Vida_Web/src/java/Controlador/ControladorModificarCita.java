package Controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import Dao.CitaMedicaDAO;
import Dao.EspecialidadDAO;
import Dao.PersonaDAO;
import Modelo.CitaMedica;
import Modelo.Especialidad;
import Modelo.Persona;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "ControladorModificarCita", urlPatterns = {"/ControladorModificarCita"})
public class ControladorModificarCita extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("modificar".equals(accion)) {
            try {
                // Obtener parámetros
                String idCita = request.getParameter("idCita");
                String fechaCita = request.getParameter("fechaCita");
                String idEspecialidadStr = request.getParameter("idEspecialidad");
                int idEspecialidad = Integer.parseInt(idEspecialidadStr);
                boolean cancelar = request.getParameter("cancelar") != null;

                // Obtener todas las citas y filtrar por ID
                CitaMedicaDAO citaDAO = CitaMedicaDAO.getInstance();
                List<CitaMedica> citas = citaDAO.listarCitas();
                CitaMedica cita = citas.stream()
                        .filter(c -> c.getIdCita().equals(idCita))
                        .findFirst()
                        .orElse(null);

                if (cita == null) {
                    request.setAttribute("error", "No se encontró la cita");
                    request.getRequestDispatcher("jsp/buscarPersona.jsp").forward(request, response);
                    return;
                }

                // Obtener el paciente desde la base de datos
                PersonaDAO personaDAO = PersonaDAO.getInstance();
                try {
                    Persona paciente = personaDAO.buscarPorId(cita.getPaciente().getIdPersona());
                    if (paciente != null) {
                        cita.setPaciente(paciente);

                        // Validar que la cita esté en estado Pendiente
                        if (!"Pendiente".equals(cita.getEstado())) {
                            request.setAttribute("error", "Solo se pueden modificar citas en estado Pendiente");
                            request.getRequestDispatcher("jsp/citaPorPersona.jsp").forward(request, response);
                            return;
                        }

                        // Actualizar los datos de la cita
                        cita.setFechaCita(LocalDateTime.parse(fechaCita));

                        // Obtener la especialidad
                        EspecialidadDAO especialidadDAO = new EspecialidadDAO();
                        Especialidad especialidad = especialidadDAO.buscarPorId(idEspecialidad);
                        cita.setEspecialidad(especialidad);
                        cita.setPaciente(paciente);

                        // Si se cancela la cita, cambiar el estado a Devolución
                        if (cancelar) {
                            cita.setEstado("Devolución");
                        }

                        try {
                            citaDAO.actualizarCita(cita);
                            request.setAttribute("mensaje", "Cita modificada exitosamente");
                            request.setAttribute("tipoMensaje", "success");
                        } catch (SQLException e) {
                            request.setAttribute("mensaje", "Error al modificar la cita: " + e.getMessage());
                            request.setAttribute("tipoMensaje", "danger");
                        }

                        // Redirigir a la vista de citas por persona
                        String dni = cita.getPaciente().getDni();
                        response.sendRedirect(request.getContextPath() + "/ControladorCitaPorPersona?dni=" + dni);
                    } else {
                        request.setAttribute("error", "No se encontró el paciente");
                        request.getRequestDispatcher("jsp/buscarPersona.jsp").forward(request, response);
                        return;
                    }
                } catch (SQLException e) {
                    throw new ServletException("Error al obtener los datos del paciente: " + e.getMessage(), e);
                }

            } catch (SQLException e) {
                throw new ServletException("Error al modificar la cita: " + e.getMessage(), e);
            }
        } else {
            // Obtener la cita para mostrar el formulario de modificación
            try {
                String idCita = request.getParameter("idCita");

                if (idCita == null) {
                    request.setAttribute("error", "ID de cita no especificado");
                    request.getRequestDispatcher("jsp/buscarPersona.jsp").forward(request, response);
                    return;
                }

                // Obtener todas las citas y filtrar por ID
                CitaMedicaDAO citaDAO = CitaMedicaDAO.getInstance();
                List<CitaMedica> citas = citaDAO.listarCitas();
                CitaMedica cita = citas.stream()
                        .filter(c -> c.getIdCita().equals(idCita))
                        .findFirst()
                        .orElse(null);

                if (cita == null) {
                    request.setAttribute("error", "No se encontró la cita");
                    request.getRequestDispatcher("jsp/buscarPersona.jsp").forward(request, response);
                    return;
                }

                // Obtener todas las especialidades
                EspecialidadDAO especialidadDAO = new EspecialidadDAO();
                List<Especialidad> especialidades = especialidadDAO.listar();

                // Pasar los datos al JSP
                request.setAttribute("cita", cita);
                request.setAttribute("especialidades", especialidades);
                request.getRequestDispatcher("jsp/modificarCita.jsp").forward(request, response);

            } catch (SQLException e) {
                throw new ServletException("Error al obtener la cita: " + e.getMessage(), e);
            }
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
}
