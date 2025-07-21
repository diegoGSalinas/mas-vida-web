<%-- 
    Document   : vistaPacienteDoc
    Created on : 20 jul 2025, 13:58:30
    Author     : Edu
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mas Vida - Pacientes</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <!-- Estilos personalizados -->
    <link href="${pageContext.request.contextPath}/css/vistaGeneral.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
        <div class="col-md-3 sidebar">
            <h3 class="text-white mb-4">Mas Vida</h3>
            <div class="user-info">
                <h5 class="text-white mb-2">Bienvenido</h5>
                <p class="text-white mb-0">${usuario.nombreUsuario}</p>
                <p class="text-white mb-0">${usuario.tipoUsuario.nombre}</p>
            </div>
            <hr class="bg-white">
            <a href="${pageContext.request.contextPath}/ControladorDoctor?accion=inicio"><i class="fas fa-home me-2"></i>Inicio</a>
            <a href="#"><i class="fas fa-user me-2"></i>Mi Perfil</a>
            <a href="#"><i class="fas fa-cog me-2"></i>Configuraciones</a>
            <a href="${pageContext.request.contextPath}/ControladorLogout"><i class="fas fa-sign-out-alt me-2"></i>Cerrar Sesión</a>
        </div>

        <!-- Main Content -->
        <div class="col-md-9 main-content mt-4">
            <h2>Pacientes del Doctor</h2>
            <table class="table table-bordered mt-3">
                <thead>
                <tr>
                    <th>ID Paciente</th>
                    <th>Nombre</th>
                    <th>Fecha Inscripción</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="paciente" items="${listaPacientes}">
                    <tr>
                        <td>${paciente.idPaciente}</td>
                        <td>${paciente.nombre}</td>
                        <td>${paciente.fechaInscripcion}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Bootstrap Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
