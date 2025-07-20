// Controlador actualizado 16/07/2025 17:35 por ale 

package Controlador;

import Dao.GestionUsuarioDAO;
import Modelo.Especialidad;
import Modelo.Usuario;
import Modelo.TipoUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ControladorUsuario", urlPatterns = {"/ControladorUsuario"})
public class ControladorUsuario extends HttpServlet {

    private GestionUsuarioDAO gestionUsuarioDAO;

    @Override
    public void init() throws ServletException {
        gestionUsuarioDAO = new GestionUsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) {
            response.sendRedirect(request.getContextPath() + "/vistaAdmin.jsp");
            return;
        }

        switch (accion) {
            case "editar":
            mostrarFormularioEditar(request, response);
            break;
            case "nuevo":
                mostrarFormularioNuevoUsuario(request, response);
                break;
            case "verUsuarios":
                listarUsuarios(request, response);
                break;
            case "eliminar":
            eliminarUsuario(request, response);
            break;
            default:
                response.sendRedirect(request.getContextPath() + "/vistaAdmin.jsp");
                break;
        }
    }
    
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String accion = request.getParameter("accion");
    System.out.println("Acción recibida: " + accion);
    if (accion == null) {
        response.sendRedirect(request.getContextPath() + "/vistaAdmin.jsp");
        return;
    }

    switch (accion) {
        
        case "actualizar":
            actualizarUsuario(request, response);
            break;
        case "guardar":
            guardarUsuario(request, response);
            break;
        default:
            response.sendRedirect(request.getContextPath() + "/vistaAdmin.jsp");
            break;
    }
}

    private void mostrarFormularioNuevoUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> nombresTipoUsuario = gestionUsuarioDAO.obtenerNombresTipoUsuario();
        List<Especialidad> especialidades = gestionUsuarioDAO.obtenerEspecialidades();
        request.setAttribute("nombresTipoUsuario", nombresTipoUsuario);
        request.setAttribute("especialidades", especialidades);
        request.getRequestDispatcher("/jsp/agregarUsuario.jsp").forward(request, response);
    }

    private void guardarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            // Obtener datos de la persona
            String nombres = new String(request.getParameter("nombres").getBytes("ISO-8859-1"), "UTF-8");
            String apPaterno = new String(request.getParameter("apPaterno").getBytes("ISO-8859-1"), "UTF-8");
            String apMaterno = new String(request.getParameter("apMaterno").getBytes("ISO-8859-1"), "UTF-8");
            String dni = request.getParameter("dni");
            String correo = new String(request.getParameter("correo").getBytes("ISO-8859-1"), "UTF-8");
            String direccion = new String(request.getParameter("direccion").getBytes("ISO-8859-1"), "UTF-8");
            String telefono = request.getParameter("telefono");
            String fechaNacimiento = request.getParameter("fechaNacimiento");

            // Obtener datos del usuario
            String id = request.getParameter("idUsuario");
            String nombre = new String(request.getParameter("nombre_usuario").getBytes("ISO-8859-1"), "UTF-8");
            String contrasena = new String(request.getParameter("contrasena").getBytes("ISO-8859-1"), "UTF-8");
            String tipo = new String(request.getParameter("tipoUsuario").getBytes("ISO-8859-1"), "UTF-8");
            String estado = new String(request.getParameter("estado").getBytes("ISO-8859-1"), "UTF-8");

            // Obtener datos específicos para doctores y técnicos
            String turno = new String(request.getParameter("turno").getBytes("ISO-8859-1"), "UTF-8");
            String idEspecialidad = request.getParameter("especialidad");

            // Crear objeto Usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setIdUsuario(id);
            nuevoUsuario.setNombreUsuario(nombre);
            nuevoUsuario.setContrasena(contrasena);
            // Convertir el nombre a mayúsculas para coincidir con el enum
            nuevoUsuario.setTipoUsuario(TipoUsuario.valueOf(tipo.toUpperCase()));

            // Crear usuario y persona usando el nuevo método
            gestionUsuarioDAO.crearUsuarioYPersona(nuevoUsuario, nombres, apPaterno, apMaterno, 
                                                   dni, correo, direccion, telefono, fechaNacimiento);

            // Crear entrada en la tabla correspondiente según el tipo de usuario
            if (nuevoUsuario.getTipoUsuario().getPrioridad() == 2) {
                // Si es doctor, crear en la tabla doctor
                gestionUsuarioDAO.crearDoctor(nuevoUsuario.getIdUsuario(), turno, Integer.parseInt(idEspecialidad));
            } else if (nuevoUsuario.getTipoUsuario().getPrioridad() == 4) {
                // Si es técnico, crear en la tabla tecnico
                gestionUsuarioDAO.crearTecnico(nuevoUsuario.getIdUsuario(), turno, Integer.parseInt(idEspecialidad));
            }

            // Actualizar estado
            gestionUsuarioDAO.actualizar(nuevoUsuario, estado);

            request.setAttribute("mensaje", "Usuario registrado exitosamente");
            request.setAttribute("tipoMensaje", "success");
            response.sendRedirect("ControladorUsuario?accion=verUsuarios");
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al registrar usuario: " + e.getMessage());
            request.setAttribute("tipoMensaje", "danger");
            request.getRequestDispatcher("/jsp/agregarUsuario.jsp").forward(request, response);
            throw new ServletException("Error al procesar el registro del usuario", e);
        }
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Usuario> usuarios = gestionUsuarioDAO.obtenerTodos();
            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher("/jsp/listaUsuarios.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error al obtener usuarios: " + e.getMessage());
            request.getRequestDispatcher("/jsp/vistaAdmin.jsp").forward(request, response);
        }
    }
    
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String id = request.getParameter("id");
     System.out.println("ID recibido: " + id);
    Usuario usuario = gestionUsuarioDAO.buscarPorId(id); // tu método corregido en DAO

    request.setAttribute("usuario", usuario);
    System.out.println("Usuario cargado: " + usuario.getIdUsuario());
    request.getRequestDispatcher("/jsp/editarUsuario.jsp").forward(request, response);
}
    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
    try {
        String id = request.getParameter("idUsuario");
        String nombre = request.getParameter("nombre_usuario");
        String contrasena = request.getParameter("contrasena"); 
        String tipo = request.getParameter("tipoUsuario");
        String estado = request.getParameter("estado");

        // Solo los datos que existen en la clase Usuario
        Usuario usuarioActualizado = new Usuario(id, nombre, contrasena, TipoUsuario.valueOf(tipo));

        // Llamada al DAO enviando también el estado como parámetro separado
        gestionUsuarioDAO.actualizar(usuarioActualizado, estado);

        response.sendRedirect("ControladorUsuario?accion=verUsuarios");
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("vistaAdmin.jsp");
    }
}
    
    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
        String id = request.getParameter("id");
        System.out.println("ID a eliminar: " + id);

        if (id != null && !id.trim().isEmpty()) {
            gestionUsuarioDAO.eliminar(id);
            System.out.println("Usuario eliminado correctamente.");
        } else {
            System.out.println("ID inválido o nulo.");
        }

        response.sendRedirect("ControladorUsuario?accion=verUsuarios");

    } catch (Exception e) {
        System.err.println("Error al eliminar el usuario: " + e.getMessage());
        e.printStackTrace();

        // Puedes redirigir a una página de error o mostrar un mensaje
        response.sendRedirect("error.jsp");
    }
}

}
