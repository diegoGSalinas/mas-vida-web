package Test;

import Controlador.ControladorLogin;
import Modelo.Usuario;
import Modelo.TipoUsuario;
import Dao.UsuarioDAO;
import Dao.EspecialidadDAO;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

public class ControladorLoginTest {
    
    @Mock
    private HttpServletRequest request;
    
    @Mock
    private HttpServletResponse response;
    
    @Mock
    private HttpSession session;
    
    private ControladorLogin controlador;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controlador = new ControladorLogin();
        
        // Configuración común para todas las pruebas
        Mockito.when(request.getSession()).thenReturn(session);
    }
    
    @Test
    public void testLoginExitosoAdministrador() throws Exception {
        // Configuración del mock
        Mockito.when(request.getParameter("usuario")).thenReturn("admin");
        Mockito.when(request.getParameter("password")).thenReturn("123456");
        
        Usuario usuario = new Usuario();
        usuario.setIdUsuario("1");
        usuario.setNombreUsuario("admin");
        usuario.setContrasena("123456");
        usuario.setTipoUsuario(TipoUsuario.ADMINISTRADOR);
        
        UsuarioDAO dao = Mockito.mock(UsuarioDAO.class);
        Mockito.when(dao.validarUsuario("admin", "123456")).thenReturn(usuario);
        
        // Ejecución del test usando reflection para acceder a doPost
        Method doPost = ControladorLogin.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        doPost.setAccessible(true);
        doPost.invoke(controlador, request, response);
        
        // Verificación
        Mockito.verify(session).setAttribute("usuario", usuario);
        Mockito.verify(session).setAttribute("nuevoInicioSesion", "true");
        Mockito.verify(request).setAttribute("titulo", "Panel General");
        Mockito.verify(request).setAttribute("mensaje", "✅ Inicio de sesión exitoso");
        Mockito.verify(request).getRequestDispatcher("/jsp/vistaAdmin.jsp").forward(request, response);
    }
    
    @Test
    public void testLoginExitosoDoctor() throws Exception {
        // Configuración del mock
        Mockito.when(request.getParameter("usuario")).thenReturn("doctor1");
        Mockito.when(request.getParameter("password")).thenReturn("123456");
        
        Usuario usuario = new Usuario();
        usuario.setIdUsuario("2");
        usuario.setNombreUsuario("doctor1");
        usuario.setContrasena("123456");
        usuario.setTipoUsuario(TipoUsuario.DOCTOR);
        
        UsuarioDAO dao = Mockito.mock(UsuarioDAO.class);
        Mockito.when(dao.validarUsuario("doctor1", "123456")).thenReturn(usuario);
        
        EspecialidadDAO especialidadDAO = Mockito.mock(EspecialidadDAO.class);
        Mockito.when(especialidadDAO.obtenerEspecialidadPorDoctor("2")).thenReturn("1");
        
        // Ejecución del test usando reflection para acceder a doPost
        Method doPost = ControladorLogin.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        doPost.setAccessible(true);
        doPost.invoke(controlador, request, response);
        
        // Verificación
        Mockito.verify(session).setAttribute("usuario", usuario);
        Mockito.verify(session).setAttribute("nuevoInicioSesion", "true");
        Mockito.verify(session).setAttribute("id_especialidad", "1");
        Mockito.verify(request).setAttribute("titulo", "Panel del Doctor");
        Mockito.verify(request).setAttribute("mensaje", "✅ Inicio de sesión exitoso");
        Mockito.verify(request).getRequestDispatcher("/jsp/vistaDoctor.jsp").forward(request, response);
    }
    
    @Test
    public void testLoginFallido() throws Exception {
        // Configuración del mock
        Mockito.when(request.getParameter("usuario")).thenReturn("usuarioNoExiste");
        Mockito.when(request.getParameter("password")).thenReturn("123456");
        
        UsuarioDAO dao = Mockito.mock(UsuarioDAO.class);
        Mockito.when(dao.validarUsuario("usuarioNoExiste", "123456")).thenReturn(null);
        
        // Ejecución del test usando reflection para acceder a doPost
        Method doPost = ControladorLogin.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        doPost.setAccessible(true);
        doPost.invoke(controlador, request, response);
        
        // Verificación
        Mockito.verify(request).setAttribute("error", "Usuario o contraseña incorrectos");
        Mockito.verify(request).getRequestDispatcher("index.jsp").forward(request, response);
    }
    
    @Test
    public void testLoginSinCampos() throws Exception {
        // Configuración del mock
        Mockito.when(request.getParameter("usuario")).thenReturn("");
        Mockito.when(request.getParameter("password")).thenReturn("");
        
        // Ejecución del test usando reflection para acceder a doPost
        Method doPost = ControladorLogin.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        doPost.setAccessible(true);
        doPost.invoke(controlador, request, response);
        
        // Verificación
        Mockito.verify(request).setAttribute("error", "Por favor ingrese usuario y contraseña");
        Mockito.verify(request).getRequestDispatcher("index.jsp").forward(request, response);
    }
    
    @Test
    public void testLoginDoctorSinEspecialidad() throws Exception {
        // Configuración del mock
        Mockito.when(request.getParameter("usuario")).thenReturn("doctor2");
        Mockito.when(request.getParameter("password")).thenReturn("123456");
        
        Usuario usuario = new Usuario();
        usuario.setIdUsuario("3");
        usuario.setNombreUsuario("doctor2");
        usuario.setContrasena("123456");
        usuario.setTipoUsuario(TipoUsuario.DOCTOR);
        
        UsuarioDAO dao = Mockito.mock(UsuarioDAO.class);
        Mockito.when(dao.validarUsuario("doctor2", "123456")).thenReturn(usuario);
        
        EspecialidadDAO especialidadDAO = Mockito.mock(EspecialidadDAO.class);
        Mockito.when(especialidadDAO.obtenerEspecialidadPorDoctor("3")).thenReturn(null);
        
        // Ejecución del test usando reflection para acceder a doPost
        Method doPost = ControladorLogin.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        doPost.setAccessible(true);
        doPost.invoke(controlador, request, response);
        
        // Verificación
        Mockito.verify(session).setAttribute("usuario", usuario);
        Mockito.verify(session).setAttribute("nuevoInicioSesion", "true");
        Mockito.verify(request).setAttribute("titulo", "Panel del Doctor");
        Mockito.verify(request).setAttribute("mensaje", "✅ Inicio de sesión exitoso");
        Mockito.verify(request).getRequestDispatcher("/jsp/vistaDoctor.jsp").forward(request, response);
    }
}
