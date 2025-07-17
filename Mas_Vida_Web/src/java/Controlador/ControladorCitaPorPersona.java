package Controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import Dao.CitaMedicaDAO;
import Dao.EspecialidadDAO;
import Dao.PersonaDAO;
import Modelo.CitaMedica;
import Modelo.Especialidad;
import Modelo.Persona;
import java.util.List;
import java.util.stream.Collectors;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet(name = "ControladorCitaPorPersona", urlPatterns = {"/ControladorCitaPorPersona"})
public class ControladorCitaPorPersona extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("generarPdf".equals(accion)) {
            try {
                String idCita = request.getParameter("idCita");

                if (idCita == null) {
                    request.setAttribute("error", "ID de cita no especificado");
                    request.getRequestDispatcher("jsp/buscarPersona.jsp").forward(request, response);
                    return;
                }

                // Obtener todas las citas y filtrar por ID
                CitaMedicaDAO citaDAO = CitaMedicaDAO.getInstance();
                List<CitaMedica> citas = citaDAO.listarCitas();
                CitaMedica cita = citas.stream()
                        .filter(c -> c.getIdCita().equals(idCita))
                        .findFirst()
                        .orElse(null);

                if (cita == null) {
                    request.setAttribute("error", "No se encontró la cita");
                    request.getRequestDispatcher("jsp/buscarPersona.jsp").forward(request, response);
                    return;
                }

                // Obtener el paciente desde la base de datos
                PersonaDAO personaDAO = PersonaDAO.getInstance();
                Persona paciente = personaDAO.buscarPorId(cita.getPaciente().getIdPersona());
                cita.setPaciente(paciente);

                // Obtener la especialidad desde la base de datos
                try {
                    EspecialidadDAO especialidadDAO = new EspecialidadDAO();
                    Especialidad especialidad = especialidadDAO.buscarPorId(Integer.parseInt(cita.getEspecialidad().getIdEspecialidad()));
                    if (especialidad != null) {
                        cita.setEspecialidad(especialidad);
                    }
                } catch (NumberFormatException e) {
                    throw new ServletException("Error al convertir el ID de especialidad: " + e.getMessage(), e);
                } catch (RuntimeException e) {
                    throw new ServletException("Error al obtener la especialidad: " + e.getMessage(), e);
                }

                // Configurar la respuesta para descargar el PDF
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=" + cita.getIdCita() + "_cita.pdf");

                // Crear el documento PDF
                Document document = new Document();
                PdfWriter.getInstance(document, response.getOutputStream());
                document.open();

                // Estilo para el título principal
                Font tituloFont = new Font(Font.FontFamily.HELVETICA, 36, Font.BOLD);
                Paragraph titulo = new Paragraph("Mas Vida - Cita Médica", tituloFont);
                titulo.setAlignment(Element.ALIGN_CENTER);
                document.add(titulo);
                document.add(new Paragraph("\n"));

                // Estilo para la fecha
                Font fechaFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
                Paragraph fecha = new Paragraph("Fecha de Generación: " + new java.util.Date(), fechaFont);
                fecha.setAlignment(Element.ALIGN_RIGHT);
                document.add(fecha);
                document.add(new Paragraph("\n"));

                // Crear tabla para los datos
                PdfPTable tabla = new PdfPTable(2);
                tabla.setWidthPercentage(100);
                tabla.setSpacingBefore(15f);
                tabla.setSpacingAfter(15f);
                tabla.getDefaultCell().setBorderWidth(1);

                // Lista de mensajes de salud
                String[] mensajesSalud = {
                    "¡Cuida tu salud, es tu mayor riqueza!",
                    "La salud es el primer tesoro de la vida.",
                    "Un cuerpo sano es un templo para el espíritu.",
                    "La salud es la verdadera riqueza.",
                    "Cuida tu cuerpo, es el único lugar donde tienes que vivir.",
                    "La salud es el mayor de los bienes."
                };
                String mensajeAleatorio = mensajesSalud[new java.util.Random().nextInt(mensajesSalud.length)];

                // Estilo para las celdas
                PdfPCell cell = null;
                Font labelFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                Font valueFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

                // Crear tabla principal
                PdfPTable tablaPrincipal = new PdfPTable(1);
                tablaPrincipal.setWidthPercentage(100);
                tablaPrincipal.setSpacingBefore(30f);
                tablaPrincipal.setSpacingAfter(30f);
                tablaPrincipal.getDefaultCell().setBorderWidth(1);
                tablaPrincipal.setSpacingBefore(20f);
                tablaPrincipal.setSpacingAfter(20f);

                // Marco 1: Datos básicos
                PdfPTable tablaDatosBasicos = new PdfPTable(2);
                tablaDatosBasicos.setWidthPercentage(100);
                tablaDatosBasicos.getDefaultCell().setBorderWidth(1);
                tablaDatosBasicos.setSpacingBefore(10f);
                tablaDatosBasicos.setSpacingAfter(10f);

                // Agregar datos básicos
                cell = new PdfPCell(new Paragraph("ID de Cita:", labelFont));
                cell.setBorder(0);
                tablaDatosBasicos.addCell(cell);
                cell = new PdfPCell(new Paragraph(cita.getIdCita(), valueFont));
                cell.setBorder(0);
                tablaDatosBasicos.addCell(cell);

                cell = new PdfPCell(new Paragraph("Fecha de Solicitud:", labelFont));
                cell.setBorder(0);
                tablaDatosBasicos.addCell(cell);
                cell = new PdfPCell(new Paragraph(cita.getFechaSolicitud().toString(), valueFont));
                cell.setBorder(0);
                tablaDatosBasicos.addCell(cell);

                cell = new PdfPCell(new Paragraph("Fecha de Cita:", labelFont));
                cell.setBorder(0);
                tablaDatosBasicos.addCell(cell);
                cell = new PdfPCell(new Paragraph(cita.getFechaCita().toString(), valueFont));
                cell.setBorder(0);
                tablaDatosBasicos.addCell(cell);

                // Agregar tabla de datos básicos a la tabla principal
                PdfPCell cellDatosBasicos = new PdfPCell(tablaDatosBasicos);
                cellDatosBasicos.setBorderWidth(2);
                tablaPrincipal.addCell(cellDatosBasicos);

                // Marco 2: Especialidad y Estado
                PdfPTable tablaEspecialidadEstado = new PdfPTable(2);
                tablaEspecialidadEstado.setWidthPercentage(100);
                tablaEspecialidadEstado.getDefaultCell().setBorderWidth(1);
                tablaEspecialidadEstado.setSpacingBefore(10f);
                tablaEspecialidadEstado.setSpacingAfter(10f);

                // Agregar especialidad y estado
                cell = new PdfPCell(new Paragraph("Especialidad:", labelFont));
                cell.setBorder(0);
                tablaEspecialidadEstado.addCell(cell);
                cell = new PdfPCell(new Paragraph(cita.getEspecialidad().getNombre(), valueFont));
                cell.setBorder(0);
                tablaEspecialidadEstado.addCell(cell);

                cell = new PdfPCell(new Paragraph("Estado:", labelFont));
                cell.setBorder(0);
                tablaEspecialidadEstado.addCell(cell);
                cell = new PdfPCell(new Paragraph(cita.getEstado(), valueFont));
                cell.setBorder(0);
                tablaEspecialidadEstado.addCell(cell);

                // Agregar tabla de especialidad y estado a la tabla principal
                PdfPCell cellEspecialidadEstado = new PdfPCell(tablaEspecialidadEstado);
                cellEspecialidadEstado.setBorderWidth(2);
                tablaPrincipal.addCell(cellEspecialidadEstado);

                // Marco 3: Datos del Paciente
                PdfPTable tablaDatosPaciente = new PdfPTable(2);
                tablaDatosPaciente.setWidthPercentage(100);
                tablaDatosPaciente.getDefaultCell().setBorderWidth(1);
                tablaDatosPaciente.setSpacingBefore(10f);
                tablaDatosPaciente.setSpacingAfter(10f);

                // Agregar datos del paciente
                cell = new PdfPCell(new Paragraph("Nombre:", labelFont));
                cell.setBorder(0);
                tablaDatosPaciente.addCell(cell);
                cell = new PdfPCell(new Paragraph(paciente.getNombres() + " " + paciente.getApPaterno() + " " + paciente.getApMaterno(), valueFont));
                cell.setBorder(0);
                tablaDatosPaciente.addCell(cell);

                cell = new PdfPCell(new Paragraph("DNI:", labelFont));
                cell.setBorder(0);
                tablaDatosPaciente.addCell(cell);
                cell = new PdfPCell(new Paragraph(paciente.getDni(), valueFont));
                cell.setBorder(0);
                tablaDatosPaciente.addCell(cell);

                // Agregar tabla de datos del paciente a la tabla principal
                PdfPCell cellDatosPaciente = new PdfPCell(tablaDatosPaciente);
                cellDatosPaciente.setBorderWidth(2);
                tablaPrincipal.addCell(cellDatosPaciente);

                // Agregar mensaje de salud
                Font mensajeFont = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC);
                Paragraph mensajeSalud = new Paragraph(mensajeAleatorio, mensajeFont);
                mensajeSalud.setAlignment(Element.ALIGN_CENTER);
                mensajeSalud.setSpacingBefore(20f);
                PdfPCell cellMensaje = new PdfPCell(mensajeSalud);
                cellMensaje.setBorder(0);
                tablaPrincipal.addCell(cellMensaje);

                // Agregar tabla principal al documento
                document.add(tablaPrincipal);
                document.close();

                // Agregar los datos de la cita
                cell = new PdfPCell(new Paragraph("ID de Cita:", labelFont));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(cita.getIdCita(), valueFont));
                tabla.addCell(cell);

                cell = new PdfPCell(new Paragraph("Fecha de Solicitud:", labelFont));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(cita.getFechaSolicitud().toString(), valueFont));
                tabla.addCell(cell);

                cell = new PdfPCell(new Paragraph("Fecha de Cita:", labelFont));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(cita.getFechaCita().toString(), valueFont));
                tabla.addCell(cell);

                cell = new PdfPCell(new Paragraph("Especialidad:", labelFont));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(cita.getEspecialidad().getNombre(), valueFont));
                tabla.addCell(cell);

                cell = new PdfPCell(new Paragraph("Estado:", labelFont));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(cita.getEstado(), valueFont));
                tabla.addCell(cell);

                // Línea divisoria
                PdfPCell linea = new PdfPCell(new Paragraph(""));
                linea.setBorderWidthBottom(2);
                linea.setColspan(2);
                linea.setPaddingBottom(5);
                tabla.addCell(linea);

                // Datos del paciente
                cell = new PdfPCell(new Paragraph("Datos del Paciente", labelFont));
                cell.setColspan(2);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);

                cell = new PdfPCell(new Paragraph("Nombre:", labelFont));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(paciente.getNombres() + " " + paciente.getApPaterno() + " " + paciente.getApMaterno(), valueFont));
                tabla.addCell(cell);

                cell = new PdfPCell(new Paragraph("DNI:", labelFont));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(paciente.getDni(), valueFont));
                tabla.addCell(cell);

                document.add(tabla);
                document.close();

                // Descarga
                response.getOutputStream().flush();
                response.getOutputStream().close();
                return;
            } catch (SQLException | DocumentException | IOException e) {
                throw new ServletException("Error al generar el PDF: " + e.getMessage(), e);
            }
        }

        String dni = request.getParameter("dni");

        if (dni == null || dni.isEmpty()) {
            request.setAttribute("error", "Debe proporcionar un DNI válido");
            request.getRequestDispatcher("jsp/buscarPersona.jsp").forward(request, response);
            return;
        }

        try {
            // Obtener la persona por DNI
            PersonaDAO personaDAO = PersonaDAO.getInstance();
            long idPersona = personaDAO.obtenerIdPersonaPorDni(dni);

            if (idPersona == -1) {
                request.setAttribute("error", "No se encontró la persona con DNI: " + dni);
                request.getRequestDispatcher("jsp/buscarPersona.jsp").forward(request, response);
                return;
            }

            // Obtener los datos completos de la persona
            Persona persona = personaDAO.buscarPorId(idPersona);

            if (persona == null) {
                request.setAttribute("error", "Error al obtener los datos de la persona");
                request.getRequestDispatcher("jsp/buscarPersona.jsp").forward(request, response);
                return;
            }

            // Obtener las citas médicas
            CitaMedicaDAO citaDAO = CitaMedicaDAO.getInstance();
            List<CitaMedica> citas = citaDAO.listarCitas();

            // Filtrar citas por persona
            citas = citas.stream()
                    .filter(c -> c.getPaciente().getIdPersona().equals(idPersona))
                    .collect(Collectors.toList());

            // Obtener especialidades para cada cita
            for (CitaMedica cita : citas) {
                EspecialidadDAO especialidadDAO = new EspecialidadDAO();
                Especialidad especialidad = especialidadDAO.buscarPorId(Integer.parseInt(cita.getEspecialidad().getIdEspecialidad()));
                cita.setEspecialidad(especialidad);
            }

            // Pasar los datos al JSP
            request.setAttribute("persona", persona);
            request.setAttribute("citas", citas);
            request.getRequestDispatcher("jsp/citaPorPersona.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al obtener las citas médicas: " + e.getMessage(), e);
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
