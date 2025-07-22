package Controlador;

import Dao.UsuarioDAO;
import Modelo.Usuario;
import Dao.CitaMedicaDAO;
import Dao.EspecialidadDAO;
import Dao.ExamenMedicoDAO;
import Modelo.Especialidad;
import Modelo.ExamenMedico;
import Modelo.CitaMedica;
import Modelo.TipoUsuario;
import MasVida.Log.LoggerUtil;
import java.util.List;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ControladorLogin", urlPatterns = {"/ControladorLogin"})
public class ControladorLogin extends HttpServlet {
    private static final Logger logger = LoggerUtil.getLogger(ControladorLogin.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Monitoreo de inicio de petición
        long startTime = System.currentTimeMillis();
        logger.info("Inicio de proceso de login - IP: " + request.getRemoteAddr());
        
        response.setContentType("text/html;charset=UTF-8");

        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");
        
        // Monitoreo de parámetros
        if (usuario != null) {
            logger.info("Usuario intentando login: " + usuario);
        }

        if (usuario == null || usuario.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            // Monitoreo de error
            logger.warning("Error de login: Campos vacíos");
            logger.warning("Redirigiendo a index.jsp por campos vacíos");
            request.setAttribute("error", "Por favor ingrese usuario y contraseña");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // Monitoreo de validación de usuario
        logger.info("Iniciando validación de usuario: " + usuario);
        
        UsuarioDAO dao = new UsuarioDAO();
        Usuario user = dao.validarUsuario(usuario, password);
        
        if (user == null) {
            logger.warning("Error de login: Usuario o contraseña incorrectos");
        }

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", user);
            session.setAttribute("nuevoInicioSesion", "true");
            
            // Monitoreo de éxito de login
            logger.info("Login exitoso para usuario: " + user.getNombreUsuario());
            logger.info("Tipo de usuario: " + user.getTipoUsuario());
            
            // Solo mostrar mensaje de éxito si es el primer inicio de sesión
            String nuevoInicio = (String) session.getAttribute("nuevoInicioSesion");
            if (nuevoInicio != null && nuevoInicio.equals("true")) {
                request.setAttribute("mensaje", " Inicio de sesión exitoso");
                session.removeAttribute("nuevoInicioSesion");
            }

            // Redirigir según el tipo de usuario
            int prioridad = user.getTipoUsuario().getPrioridad();

            switch (prioridad) {
                case 1: // Administrador
                    logger.info("Redirigiendo a vistaAdmin.jsp para usuario: " + user.getNombreUsuario());
                    request.setAttribute("titulo", "Panel General");
                    request.getRequestDispatcher("/jsp/vistaAdmin.jsp").forward(request, response);
                    break;
                case 2: // Doctor
                    // Verificar si el usuario es realmente un doctor
                    if (user.getTipoUsuario().equals(TipoUsuario.DOCTOR)) {
                        logger.info("Redirigiendo a vistaDoctor.jsp para usuario: " + user.getNombreUsuario());
                        EspecialidadDAO especialidadDAO = new EspecialidadDAO();
                        String idEspecialidad = especialidadDAO.obtenerEspecialidadPorDoctor(user.getIdUsuario());
                        
                        if (idEspecialidad != null) {
                            session.setAttribute("id_especialidad", idEspecialidad);
                            request.setAttribute("titulo", "Panel del Doctor");
                            request.getRequestDispatcher("/jsp/vistaDoctor.jsp").forward(request, response);
                        } else {
                            // Si no se encuentra la especialidad, mostrar mensaje de error en la vista del doctor
                            session.setAttribute("id_especialidad", "0"); // Usar un valor por defecto
                            request.setAttribute("error", "No se pudo obtener la especialidad del doctor");
                            request.setAttribute("titulo", "Panel del Doctor");
                            request.getRequestDispatcher("/jsp/vistaDoctor.jsp").forward(request, response);
                        }
                    } else {
                        request.setAttribute("error", "Usuario no es un doctor");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                    break;
                case 3: // Recepcionista
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
                case 4: // Tecnico
                    request.setAttribute("titulo", "Panel General");
                    response.sendRedirect("ControladorExamen?accion=listar");        
                    break;
                case 5: // Paciente
                    request.setAttribute("titulo", "Panel del Paciente");
                    try {
                        CitaMedicaDAO citaDAO = CitaMedicaDAO.getInstance();
                        List<CitaMedica> citas = citaDAO.listarCitasPorPaciente(user.getIdUsuario());
                        request.setAttribute("citas", citas);
                    } catch (Exception e) {
                        request.setAttribute("error", "Error al cargar las citas: " + e.getMessage());
                    }
                    request.getRequestDispatcher("/jsp/vistaPaciente.jsp").forward(request, response);
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
