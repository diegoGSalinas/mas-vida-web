package Controlador;

import Dao.PersonaDAO;
import Dao.ListaExamenMedicoDAO;
import Dao.HistorialMedicoDAO;
import Modelo.Persona;
import Modelo.HistorialMedico;
import Modelo.ListaExamenMedico;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;

@WebServlet(name = "ControladorRevisarPaciente", urlPatterns = {"/ControladorRevisarPaciente"})
public class ControladorRevisarPaciente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idPersona = request.getParameter("idPersona");
        if (idPersona != null) {
            PersonaDAO personaDAO = PersonaDAO.getInstance();
            try {
                long idPersonaLong = Long.parseLong(idPersona);
                Persona paciente = personaDAO.buscarPorId(idPersonaLong);
                if (paciente != null) {
                    // Cargar lista de exámenes
                    ListaExamenMedicoDAO examenDAO = ListaExamenMedicoDAO.getInstance();
                    List<ListaExamenMedico> examenes = examenDAO.listarTodos();
                    if (examenes != null && !examenes.isEmpty()) {
                        request.setAttribute("mensaje", "Se encontraron " + examenes.size() + " exámenes");
                    } else {
                        request.setAttribute("error", "No se encontraron exámenes en la base de datos");
                    }
                    request.setAttribute("examenes", examenes);

                    request.setAttribute("paciente", paciente);
                    request.getRequestDispatcher("/jsp/revisarPaciente.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Paciente no encontrado");
                    request.getRequestDispatcher("/jsp/vistaCitasMedicas.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID de paciente inválido");
                request.getRequestDispatcher("/jsp/vistaCitasMedicas.jsp").forward(request, response);
            } catch (SQLException e) {
                request.setAttribute("error", "Error al obtener datos del paciente: " + e.getMessage());
                request.getRequestDispatcher("/jsp/vistaCitasMedicas.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("error", "Error inesperado: " + e.getMessage());
                request.getRequestDispatcher("/jsp/vistaCitasMedicas.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "ID de paciente no proporcionado");
            request.getRequestDispatcher("/jsp/vistaCitasMedicas.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String motivoConsulta = request.getParameter("motivoConsulta");
            String diagnostico = request.getParameter("diagnostico");
            String tratamiento = request.getParameter("tratamiento");
            String idExamen = request.getParameter("examen");
            String idPersonaStr = request.getParameter("idPersona");

            if (motivoConsulta == null || diagnostico == null || tratamiento == null
                    || idPersonaStr == null) {
                throw new IllegalArgumentException("Faltan datos necesarios en el formulario");
            }

            long idPersona;
            try {
                idPersona = Long.parseLong(idPersonaStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("ID de persona inválido");
            }

            String idHistorial = "HIST" + System.currentTimeMillis();

            HistorialMedico historial = new HistorialMedico();
            historial.setIdHistorial(idHistorial);
            historial.setIdPersona(idPersona);

            if (idExamen != null && idExamen.equals("0")) {
                historial.setIdExamen("Sin Examen");
            } else {
                historial.setIdExamen(idExamen);
            }

            historial.setMotivo(motivoConsulta);
            historial.setDiagnostico(diagnostico);
            historial.setTratamiento(tratamiento);

            HistorialMedicoDAO historialDAO = HistorialMedicoDAO.getInstance();
            try {
                historialDAO.crearHistorialMedico(historial);
            } catch (SQLException e) {
                throw new RuntimeException("Error al guardar el historial: " + e.getMessage(), e);
            }

            response.sendRedirect("/Mas_Vida_Web/ControladorDoctor?accion=verCitasMedicas&mensaje=Revisión guardada exitosamente");
            return;

        } catch (Exception e) {
            request.setAttribute("error", "Error al guardar la revisión: " + e.getMessage());
            request.getRequestDispatcher("/jsp/revisarPaciente.jsp").forward(request, response);
        }
    }
}
