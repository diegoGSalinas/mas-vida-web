<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Historial Médico - Mas Vida</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <!-- Estilos específicos para doctor -->
        <link href="${pageContext.request.contextPath}/css/vistaDoctor.css" rel="stylesheet">
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
                        <p class="text-white mb-0">Doctor</p>
                    </div>
                    <hr class="bg-white">
                    <a href="${pageContext.request.contextPath}/ControladorDoctor"><i class="fas fa-home me-2"></i>Inicio</a>
                    <hr class="bg-white my-3">
                    <a href="${pageContext.request.contextPath}/ControladorLogout"><i class="fas fa-sign-out-alt me-2"></i>Cerrar Sesión</a>
                </div>

                <!-- Main Content -->
                <div class="col-md-9 main-content">
                    <div class="welcome-message">
                        <i class="fas fa-user-circle me-2"></i>
                        <span>Bienvenido ${usuario.nombreUsuario}</span>
                    </div>

                    <c:if test="${not empty mensaje}">
                        <div class="alert alert-success">
                            ${mensaje}
                        </div>
                    </c:if>

                    <div class="d-flex justify-content-end mt-3 mb-4">
                        <a href="${pageContext.request.contextPath}/ControladorDoctor?accion=verCitasMedicas" class="btn btn-secondary">
                            <i class="fas fa-arrow-left me-2"></i>
                            Volver
                        </a>
                    </div>

                    <div class="card">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0">Historial Médico de ${paciente.nombres} ${paciente.apPaterno} ${paciente.apMaterno}</h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>ID Historial</th>
                                            <th>Examen Médico</th>
                                            <th>Motivo de Consulta</th>
                                            <th>Diagnóstico</th>
                                            <th>Tratamiento</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="historial" items="${historiales}">
                                            <tr>
                                                <td>${historial.idHistorial}</td>
                                                <td>${historial.idExamen}</td>
                                                <td>${historial.motivo}</td>
                                                <td>${historial.diagnostico}</td>
                                                <td>${historial.tratamiento}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
