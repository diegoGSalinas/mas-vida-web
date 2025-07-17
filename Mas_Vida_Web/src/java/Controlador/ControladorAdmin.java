package Controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import Modelo.Usuario;

@WebServlet(name = "ControladorAdmin", urlPatterns = {"/vistaAdmin"})
public class ControladorAdmin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Verificar que el usuario es administrador
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario == null || usuario.getTipoUsuario().getPrioridad() != 1) {
            response.sendRedirect("index.jsp");
            return;
        }

        // Lógica específica del administrador a implementar
        request.setAttribute("titulo", "Panel de Administrador");
        request.getRequestDispatcher("/jsp/vistaAdmin.jsp").forward(request, response);
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
        return "Controlador del Panel de Administrador";
    }
}
