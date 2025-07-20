<%-- 
    Document   : vistaTecnico
    Created on : 19 jul. 2025, 21:39:56
    Author     : roro
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.util.List" %>
<%@ page import="Modelo.ExamenMedico" %>
<%@ page import="Modelo.Especialidad" %>
<%@ page import="Modelo.Paciente" %>
<%@ page import="Modelo.Doctor" %>
<%@ page import="Modelo.CitaMedica" %>

<%
    String tecnicoNombre = (String) session.getAttribute("nombre_tecnico");
    String mensaje = (String) request.getAttribute("mensaje");
    
    List<Especialidad> listaEspecialidades = (List<Especialidad>) request.getAttribute("listaEspecialidades");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vista Técnico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <!-- Font Awesome -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
<!-- Bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            display: flex;
            min-height: 100vh;
        }
        .sidebar {
            width: 250px;
            background-color: #343a40;
            padding: 20px;
            color: white;
            flex-shrink: 0;
        }
        .sidebar a {
            color: white;
            display: block;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
        }
        .sidebar a:hover {
            background-color: #495057;
        }
        .main-content {
            flex: 1;
            padding: 20px;
            background-color: #f8f9fa;
        }
        .card-icon {
            font-size: 1.5rem;
            color: #0d6efd;
        }
    </style>
</head>
<body>

<!-- Sidebar actualizado -->
<div class="col-md-3 sidebar bg-dark p-4">
    <h3 class="text-white mb-4">Mas Vida</h3>
    <div class="user-info">
        <h5 class="text-white mb-2">Bienvenido</h5>
        <p class="text-white mb-0">${usuario.nombreUsuario}</p>
        <p class="text-white mb-0">Técnico</p>
    </div>
    <hr class="bg-white">
    <a href="${pageContext.request.contextPath}/vistaTecnico" class="d-block text-white mb-2">
    <i class="fas fa-home me-2"></i>Inicio
    </a>
    <a href="${pageContext.request.contextPath}/ControladorExamen?accion=programar" class="d-block text-white mb-2">
    <i class="fas fa-notes-medical me-2"></i>Programar examen
    </a>
    <a href="${pageContext.request.contextPath}/ControladorLogout" class="d-block text-white mb-2">
    <i class="fas fa-sign-out-alt me-2"></i>Cerrar Sesión
    </a>
</div>


<!-- Main content -->
<div class="main-content">

    <% if (mensaje != null) { %>
    <div id="mensaje-exito" class="alert alert-success text-center py-2 px-3 small shadow-sm" style="max-width: 500px; margin: 10px auto;">
        <%= mensaje %>
    </div>
<% } %>

   <div class="d-flex justify-content-between align-items-center mb-4 p-3 bg-light border rounded shadow-sm">
    <div class="d-flex align-items-center">
        <i class="bi bi-clipboard2-pulse-fill text-primary fs-3 me-2"></i>
        <h2 class="mb-0 text-primary">Exámenes Médicos</h2>
    </div>
   <form method="get" action="ControladorExamen" class="d-flex align-items-center">
    <input type="hidden" name="accion" value="filtrar" />
    <label for="especialidad" class="me-2 fw-semibold text-dark">
        <i class="bi bi-filter-circle text-secondary me-1"></i>Filtrar por especialidad:
    </label>
    <select name="especialidad" id="especialidad" class="form-select me-2" style="width: auto;">
        <option value="">Todas las especialidades</option>
        <%
            String especialidadSeleccionada = request.getParameter("especialidad");
            if (listaEspecialidades != null) {
                for (Especialidad esp : listaEspecialidades) {
                    String nombre = esp.getNombre();
                    String selected = (nombre.equals(especialidadSeleccionada)) ? "selected" : "";
        %>
            <option value="<%= nombre %>" <%= selected %>><%= nombre %></option>
        <%
                }
            }
        %>
    </select>
    <button type="submit" class="btn btn-primary">Filtrar</button>
</form>

</div>
<%
    List<ExamenMedico> listaExamenes = (List<ExamenMedico>) request.getAttribute("examenes");
%>


    <div class="row">
    <% if (listaExamenes != null && !listaExamenes.isEmpty()) {
        for (ExamenMedico ex : listaExamenes) { 
            Paciente paciente = ex.getPaciente();
            Doctor doctor = ex.getDoctor();
            CitaMedica cita = ex.getCitaMedica();
            Especialidad esp = cita.getEspecialidad(); %>

        <div class="col-md-4 mb-4">
            <div class="card shadow-sm h-100">
                <div class="card-body">
                    <div class="d-flex align-items-center mb-2">
                        <i class="bi bi-clipboard-check card-icon me-2"></i>
                        <h5 class="card-title mb-0">
                            <%= paciente.getNombres() %> <%= paciente.getApPaterno() %>
                        </h5>
                    </div>
                    <p class="card-text"><strong>ID Examen:</strong> <%= ex.getIdExamen() %></p>
                    <p class="card-text"><strong>Fecha:</strong> <%= ex.getFechaExamen() %></p>
                    <p class="card-text"><strong>Especialidad:</strong> <%= esp.getNombre() %></p>
                    <p class="card-text"><strong>ID Cita:</strong> <%= cita.getIdCita() %></p>
                    <p class="card-text"><strong>Resultados:</strong> <%= ex.getResultados() %></p>

                   <%-- 
                    <% if (doctor != null) { %>
                    <p class="card-text"><strong>Doctor:</strong> 
                    <%= doctor.getNombres() != null ? doctor.getNombres() : "N/A" %> 
                    <%= doctor.getApPaterno() != null ? doctor.getApPaterno() : "" %>
                    </p>
                    <% } else { %>
                        <p class="card-text"><strong>Doctor:</strong> No registrado</p>
                        <% } %>
                    --%>

                </div>
            </div>
        </div>
    <%  } // fin del for
       } else { %>
        <div class="col-12">
            <div class="alert alert-info text-center">No hay exámenes médicos para mostrar.</div>
        </div>
    <% } %>
</div>


</div>
    
    
    <script>
    // Ocultar el mensaje después de 3 segundos
    window.addEventListener('DOMContentLoaded', function () {
        const mensaje = document.getElementById('mensaje-exito');
        if (mensaje) {
            setTimeout(() => {
                mensaje.style.display = 'none';
            }, 2000); // 3000 milisegundos = 3 segundos
        }
    });
</script>
</body>
</html>
