package Controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import Dao.EspecialidadDAO;
import Dao.PersonaDAO;
import Dao.CitaMedicaDAO;
import Modelo.Especialidad;
import Modelo.Persona;
import Modelo.CitaMedica;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "ControladorCitaMedica", urlPatterns = {"/ControladorCitaMedica"})
public class ControladorCitaMedica extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        // Manejar acciones específicas
        if ("listar".equals(accion)) {
            try {
                CitaMedicaDAO citaDAO = CitaMedicaDAO.getInstance();
                List<CitaMedica> citas = citaDAO.listarCitas();
                request.setAttribute("citas", citas);
                request.getRequestDispatcher("jsp/citasLista.jsp").forward(request, response);
                return;
            } catch (SQLException e) {
                throw new ServletException("Error al listar citas: " + e.getMessage(), e);
            }
        } else if ("inicio".equals(accion)) {
            try {
                CitaMedicaDAO citaDAO = CitaMedicaDAO.getInstance();
                List<CitaMedica> citas = citaDAO.listarCitas();
                request.setAttribute("citas", citas);
                request.getRequestDispatcher("jsp/vistaRecepcionista.jsp").forward(request, response);
                return;
            } catch (SQLException e) {
                throw new ServletException("Error al listar citas: " + e.getMessage(), e);
            }
        }

        // Manejar nueva cita
        if (request.getMethod().equals("POST")) {
            String dni = request.getParameter("dni");
            if (dni == null) {
                request.getRequestDispatcher("jsp/vistaRecepcionista.jsp").forward(request, response);
                return;
            }

            PersonaDAO personaDAO = PersonaDAO.getInstance();
            try {
                long idPersona = personaDAO.obtenerIdPersonaPorDni(dni);

                if (idPersona == -1) {
                    request.setAttribute("error", "No se encontró la persona con DNI: " + dni);
                    request.getRequestDispatcher("jsp/buscarPersona.jsp").forward(request, response);
                    return;
                }

                Persona persona = personaDAO.buscarPorId(idPersona);

                if (persona == null) {
                    request.setAttribute("error", "Error al obtener los datos de la persona");
                    request.getRequestDispatcher("jsp/buscarPersona.jsp").forward(request, response);
                    return;
                }

                EspecialidadDAO especialidadDAO = new EspecialidadDAO();
                List<Especialidad> especialidades = especialidadDAO.listar();

                request.setAttribute("persona", persona);
                request.setAttribute("especialidades", especialidades);
                request.getRequestDispatcher("jsp/nuevaCita.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Error al acceder a la base de datos: " + e.getMessage(), e);
            }
        }
        String dni = request.getParameter("dni");
        if (dni == null) {
            response.sendRedirect(request.getContextPath() + "/ControladorCitaMedica?accion=inicio");
            return;
        }

        // Obtener la persona por DNI
        PersonaDAO personaDAO = PersonaDAO.getInstance();
        try {
            long idPersona = personaDAO.obtenerIdPersonaPorDni(dni);

            if (idPersona == -1) {
                request.setAttribute("error", "No se encontró la persona con DNI: " + dni);
                request.getRequestDispatcher("jsp/buscarPersona.jsp").forward(request, response);
                return;
            }

            // Obtener los datos completos de la persona
            Persona persona = personaDAO.buscarPorId(idPersona);

            if (persona == null) {
                request.setAttribute("error", "Error al obtener los datos de la persona");
                request.getRequestDispatcher("jsp/buscarPersona.jsp").forward(request, response);
                return;
            }

            // Obtener las especialidades
            EspecialidadDAO especialidadDAO = new EspecialidadDAO();
            List<Especialidad> especialidades = especialidadDAO.listar();

            // Pasar los datos al JSP
            request.setAttribute("persona", persona);
            request.setAttribute("especialidades", especialidades);
            request.getRequestDispatcher("jsp/nuevaCita.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al acceder a la base de datos: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("listarCitas".equals(accion)) {
            try {
                CitaMedicaDAO citaDAO = CitaMedicaDAO.getInstance();
                List<CitaMedica> citas = citaDAO.listarCitas();
                request.setAttribute("citas", citas);
                request.getRequestDispatcher("jsp/vistaRecepcionista.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Error al listar citas: " + e.getMessage(), e);
            }
        } else {
            processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("guardar".equals(accion)) {
            try {
                // Obtener parámetros
                String fechaSolicitud = request.getParameter("fechaSolicitud");
                String fechaCita = request.getParameter("fechaCita");
                String idEspecialidadStr = request.getParameter("idEspecialidad");
                int idEspecialidad = Integer.parseInt(idEspecialidadStr);
                String idPersona = request.getParameter("idPersona");
                String idPago = request.getParameter("idPago");

                // Crear la cita
                CitaMedicaDAO citaDAO = CitaMedicaDAO.getInstance();

                // Generar ID de cita
                int ultimoId = citaDAO.obtenerUltimoIdCita();
                String idCita = "CITA" + String.format("%04d", ultimoId + 1);

                // Crear objeto CitaMedica
                CitaMedica cita = new CitaMedica();
                cita.setIdCita(idCita);
                cita.setFechaSolicitud(java.time.LocalDateTime.parse(fechaSolicitud));
                cita.setFechaCita(java.time.LocalDateTime.parse(fechaCita));

                // Obtener la especialidad completa
                EspecialidadDAO especialidadDAO = new EspecialidadDAO();
                Especialidad especialidad = especialidadDAO.buscarPorId(idEspecialidad);

                // Establecer el estado por defecto y el tipo de pago
                cita.setEstado("Pendiente");
                cita.setIdPago(idPago);

                cita.setEspecialidad(especialidad);

                // Obtener la persona completa
                PersonaDAO personaDAO = PersonaDAO.getInstance();
                Persona paciente = personaDAO.buscarPorId(Integer.parseInt(idPersona));

                cita.setPaciente(paciente);

                try {
                    citaDAO.crearCita(cita);
                    request.setAttribute("mensaje", "Cita programada exitosamente");
                    request.setAttribute("tipoMensaje", "success");
                } catch (SQLException e) {
                    request.setAttribute("mensaje", "Error al programar la cita: " + e.getMessage());
                    request.setAttribute("tipoMensaje", "danger");
                }

                // Redirigir a la vista de recepcionista
                request.getRequestDispatcher("jsp/vistaRecepcionista.jsp").forward(request, response);

            } catch (SQLException e) {
                throw new ServletException("Error al guardar la cita: " + e.getMessage(), e);
            }
        } else {
            processRequest(request, response);
        }
    }
}
