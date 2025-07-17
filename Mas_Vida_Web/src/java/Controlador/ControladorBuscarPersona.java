package Controlador;

import Dao.PersonaDAO;
import Modelo.Persona;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ControladorBuscarPersona", urlPatterns = {"/controladorBuscarPersona"})
public class ControladorBuscarPersona extends HttpServlet {

    private PersonaDAO personaDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        personaDAO = PersonaDAO.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if (accion == null) {
            accion = "";
        }

        switch (accion) {
            case "buscar":
                buscarPersona(request, response);
                break;
            default:
                request.getRequestDispatcher("/jsp/buscarPersona.jsp").forward(request, response);
        }
    }

    private void buscarPersona(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener parámetros de búsqueda
            String dni = request.getParameter("dni");
            String apPaterno = request.getParameter("apPaterno");
            String apMaterno = request.getParameter("apMaterno");

            // Validar que se ingrese al menos uno de los criterios
            if ((dni == null || dni.isEmpty())
                    && (apPaterno == null || apPaterno.isEmpty())
                    && (apMaterno == null || apMaterno.isEmpty())) {

                request.setAttribute("error", "Debe ingresar al menos uno de los criterios: DNI o Apellidos");
                request.getRequestDispatcher("/jsp/buscarPersona.jsp").forward(request, response);
                return;
            }

            // Buscar personas según el criterio proporcionado
            ArrayList<Persona> personasEncontradas = new ArrayList<>();

            if (dni != null && !dni.isEmpty()) {
                Persona persona = personaDAO.buscarPorDni(dni);
                if (persona != null) {
                    personasEncontradas.add(persona);
                }
            } else if (apPaterno != null && !apPaterno.isEmpty() && apMaterno != null && !apMaterno.isEmpty()) {
                personasEncontradas.addAll(personaDAO.buscarPorApellidos(apPaterno, apMaterno));
            } else {
                request.setAttribute("error", "Debe ingresar ambos apellidos o el DNI completo");
                request.getRequestDispatcher("/jsp/buscarPersona.jsp").forward(request, response);
                return;
            }

            if (personasEncontradas.isEmpty()) {
                request.setAttribute("mensaje", "No se encontraron personas que coincidan con los criterios de búsqueda");
            }

            request.setAttribute("personas", personasEncontradas);
            request.getRequestDispatcher("/jsp/buscarPersona.jsp").forward(request, response);

        } catch (SQLException ex) {
            request.setAttribute("error", "Error en la base de datos: " + ex.getMessage());
            request.getRequestDispatcher("/jsp/buscarPersona.jsp").forward(request, response);
        }
    }
}
