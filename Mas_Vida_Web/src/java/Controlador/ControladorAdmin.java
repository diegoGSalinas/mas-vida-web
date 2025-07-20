package Controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import Modelo.BackupJob;
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

        // Verificar si se solicitó un backup
        String accion = request.getParameter("accion");
        if ("backup".equals(accion)) {
            try {
                BackupJob.ejecutarBackup();
                request.setAttribute("backupMensaje", "✅ Backup realizado exitosamente");
            } catch (Exception e) {
                request.setAttribute("backupMensaje", "❌ Error al realizar el backup: " + e.getMessage());
            }
        }

        // Lógica específica del administrador a implementar
        request.setAttribute("titulo", "Panel de Administrador");
        
        // Si hay un mensaje de backup, mostrar un popup usando JavaScript
        if (request.getAttribute("backupMensaje") != null) {
            String mensaje = (String) request.getAttribute("backupMensaje");
            String script = "<script type='text/javascript'>"
                    + "setTimeout(function() {"
                    + "    alert(\"" + mensaje + "\");"
                    + "}, 100);"
                    + "</script>";
            request.setAttribute("script", script);
        }
        
        // Mantener el usuario en la sesión
        request.getSession().setAttribute("usuario", usuario);
        
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
