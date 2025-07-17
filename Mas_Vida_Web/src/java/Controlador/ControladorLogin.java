package Controlador;

import Dao.UsuarioDAO;
import Modelo.Usuario;
import Dao.CitaMedicaDAO;
import Modelo.CitaMedica;
import java.util.List;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "ControladorLogin", urlPatterns = {"/ControladorLogin"})
public class ControladorLogin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");

        if (usuario == null || usuario.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Por favor ingrese usuario y contraseña");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        Usuario user = dao.validarUsuario(usuario, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", user);
            request.setAttribute("mensaje", "Inicio de sesión exitoso");

            // Redirigir según el tipo de usuario
            int prioridad = user.getTipoUsuario().getPrioridad();

            switch (prioridad) {
                case 1: // Administrador
                    request.setAttribute("titulo", "Panel General");
                    request.getRequestDispatcher("/jsp/vistaAdmin.jsp").forward(request, response);
                    break;
                case 5: // Recepcionista
                    request.setAttribute("titulo", "Panel General");
                    try {
                        CitaMedicaDAO citaDAO = CitaMedicaDAO.getInstance();
                        List<CitaMedica> citas = citaDAO.listarCitas();
                        request.setAttribute("citas", citas);
                    } catch (Exception e) {
                        request.setAttribute("error", "Error al cargar las citas: " + e.getMessage());
                    }
                    request.getRequestDispatcher("/jsp/vistaRecepcionista.jsp").forward(request, response);
                    break;
                default:
                    request.setAttribute("titulo", "Panel General");
                    request.getRequestDispatcher("/jsp/vistaGeneral.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
        return "Controlador de Login";
    }
}
