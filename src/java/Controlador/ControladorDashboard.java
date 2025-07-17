package Controlador;

import Dao.DashboardDAO;
import Modelo.Dashboard;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ControladorDashboard", urlPatterns = {"/ControladorDashboard"})
public class ControladorDashboard extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if (accion == null) {
            accion = "inicio";
        }

        switch (accion) {
            case "inicio":
                try {
                    DashboardDAO dashboardDAO = DashboardDAO.getInstance();
                    Dashboard dashboard = dashboardDAO.obtenerDashboard();
                    request.setAttribute("dashboard", dashboard);
                    request.getRequestDispatcher("jsp/vistaDashboard.jsp").forward(request, response);
                } catch (SQLException e) {
                    throw new ServletException("Error al obtener dashboard: " + e.getMessage(), e);
                }
                break;

            default:
                request.setAttribute("mensaje", "Acción no válida");
                request.getRequestDispatcher("jsp/vistaDashboard.jsp").forward(request, response);
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
}
