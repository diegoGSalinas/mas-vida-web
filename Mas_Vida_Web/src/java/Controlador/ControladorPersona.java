package Controlador;

import Dao.PersonaDAO;
import Modelo.Persona;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ControladorPersona", urlPatterns = {"/controladorPersona"})
public class ControladorPersona extends HttpServlet {

    private PersonaDAO personaDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        personaDAO = PersonaDAO.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if (accion == null) {
            accion = "";
        }

        switch (accion) {
            case "crear":
                crearPersona(request, response);
                break;
            default:
                request.setAttribute("error", "Acción no válida");
                request.getRequestDispatcher("/jsp/vistaRecepcionista.jsp").forward(request, response);
        }
    }

    private void crearPersona(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener datos del formulario
            String nombres = request.getParameter("nombres");
            String apPaterno = request.getParameter("apPaterno");
            String apMaterno = request.getParameter("apMaterno");
            String dni = request.getParameter("dni");
            String correo = request.getParameter("correo");
            String direccion = request.getParameter("direccion");
            String telefono = request.getParameter("telefono");
            String fechaNacimientoStr = request.getParameter("fechaNacimiento");

            // Validar datos
            if (nombres == null || nombres.isEmpty()
                    || apPaterno == null || apPaterno.isEmpty()
                    || apMaterno == null || apMaterno.isEmpty()
                    || dni == null || dni.isEmpty()
                    || correo == null || correo.isEmpty()
                    || direccion == null || direccion.isEmpty()
                    || telefono == null || telefono.isEmpty()
                    || fechaNacimientoStr == null || fechaNacimientoStr.isEmpty()) {

                request.setAttribute("error", "Todos los campos son obligatorios");
                request.getRequestDispatcher("/jsp/vistaRecepcionista.jsp").forward(request, response);
                return;
            }

            // Crear objeto Persona
            Persona persona = new Persona();
            persona.setNombres(nombres);
            persona.setApPaterno(apPaterno);
            persona.setApMaterno(apMaterno);
            persona.setDni(dni);
            persona.setCorreo(correo);
            persona.setDireccion(direccion);
            persona.setTelefono(telefono);
            persona.setFechaNacimiento(java.sql.Date.valueOf(fechaNacimientoStr));

            // Crear la persona en la base de datos
            long idPersona = personaDAO.crearPersona(persona);

            if (idPersona > 0) {
                response.sendRedirect(request.getContextPath() + "/jsp/vistaRecepcionista.jsp?mensaje=Persona creada exitosamente");
            } else {
                response.sendRedirect(request.getContextPath() + "/jsp/vistaRecepcionista.jsp?error=Error al crear la persona");
            }

        } catch (SQLException ex) {
            request.setAttribute("error", "Error en la base de datos: " + ex.getMessage());
            request.getRequestDispatcher("/jsp/vistaRecepcionista.jsp").forward(request, response);
        }
    }
}
