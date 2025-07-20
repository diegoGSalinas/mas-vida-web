package Controlador;


import Dao.EspecialidadDAO;
import Modelo.ExamenMedico;
import Dao.ExamenMedicoDAO;
import Modelo.Especialidad;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@WebServlet(name = "ControladorExamen", urlPatterns = {"/ControladorExamen"})
public class ControladorExamen extends HttpServlet {

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String accion = request.getParameter("accion");
    System.out.println("ACCION: " + accion);
    ExamenMedicoDAO dao = new ExamenMedicoDAO();

   if (accion == null) {
    accion = "listar"; // Acción por defecto al ingresar
}

    switch (accion) {
        case "listar": {
    // DAO para exámenes
    ExamenMedicoDAO examenDAO = new ExamenMedicoDAO();
    List<ExamenMedico> listaExamenes = examenDAO.listarTodos(); // Debe devolver todos los exámenes

    // DAO para especialidades
    EspecialidadDAO especialidadDAO = new EspecialidadDAO();
    List<Especialidad> listaEspecialidades = especialidadDAO.listar();

    // Mostrar en consola para verificar
    System.out.println("Entra a listar exámenes");
    System.out.println("Total exámenes encontrados: " + listaExamenes.size());

    // Enviar datos al JSP
    request.setAttribute("listaEspecialidades", listaEspecialidades);
    request.setAttribute("examenes", listaExamenes);

    // Redirigir a la vista del técnico
    request.getRequestDispatcher("/jsp/vistaTecnico.jsp").forward(request, response);
    break;
}

    case "filtrar": {
    String especialidad = request.getParameter("especialidad");

    List<ExamenMedico> listaExamenes;
    if (especialidad == null || especialidad.trim().isEmpty()) {
        listaExamenes = dao.listarTodos(); // sin filtro
    } else {
        listaExamenes = dao.listarPorEspecialidad(especialidad); // con filtro
    }

    EspecialidadDAO especialidadDAO = new EspecialidadDAO();
    List<Especialidad> listaEspecialidades = especialidadDAO.listar();

    request.setAttribute("listaEspecialidades", listaEspecialidades);
    request.setAttribute("examenes", listaExamenes);
    request.setAttribute("especialidadSeleccionada", especialidad);

    request.getRequestDispatcher("jsp/vistaTecnico.jsp").forward(request, response);
    break;
}
        case "programar": {
            // Aquí puedes agregar cualquier lógica que necesites antes de mostrar el formulario
            request.getRequestDispatcher("jsp/programarExamen.jsp").forward(request, response);
            break;
        }
        default: {
            response.sendRedirect("Vista/vistaTecnico.jsp");
        }
    }
}

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String accion = request.getParameter("accion");
    if ("registrar".equals(accion)) {
        ExamenMedicoDAO dao = new ExamenMedicoDAO();

        String idExamen = request.getParameter("id_examen");
        String fecha = request.getParameter("fecha");
        String resultados = request.getParameter("resultados");
        String idTecnico = request.getParameter("id_tecnico");
        String idCita = request.getParameter("id_cita");

        ExamenMedico examen = new ExamenMedico();
        examen.setIdExamen(idExamen);
        examen.setFechaExamen(LocalDateTime.parse(fecha)); // Formato: yyyy-MM-ddTHH:mm
        examen.setResultados(resultados);
        examen.setIdTecnico(idTecnico);
        examen.setIdCita(idCita);

        try {
            dao.registrarExamen(examen);
            response.sendRedirect("ControladorExamen?accion=listar");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "No se pudo registrar el examen");
            request.getRequestDispatcher("jsp/formularioExamen.jsp").forward(request, response);
        }
    } else {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Acción POST no válida");
    }
}

}
