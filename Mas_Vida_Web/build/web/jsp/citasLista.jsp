<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Mas Vida - Lista de Citas</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <!-- Estilos específicos para citas -->
        <link href="${pageContext.request.contextPath}/css/citasLista.css" rel="stylesheet">
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
                        <p class="text-white mb-0">Recepcionista</p>
                    </div>
                    <hr class="bg-white">
                    <a href="${pageContext.request.contextPath}/ControladorCitaMedica?accion=inicio"><i class="fas fa-home me-2"></i>Inicio</a>
                    <a href="${pageContext.request.contextPath}/ControladorCitaMedica?accion=listar"><i class="fas fa-calendar-alt me-2"></i>Ver Todas las Citas</a>
                    <a href="${pageContext.request.contextPath}/ControladorLogout"><i class="fas fa-sign-out-alt me-2"></i>Cerrar Sesión</a>
                </div>

                <!-- Main Content -->
                <div class="col-md-9 main-content">
                    <div class="welcome-message">
                        <i class="fas fa-user-circle me-2"></i>
                        <span>Bienvenido ${usuario.nombreUsuario}</span>
                    </div>

                    <!-- Mensajes -->
                    <div class="mb-4">
                        <c:if test="${not empty mensaje}">
                            <div class="alert alert-success">
                                <i class="fas fa-check-circle me-2"></i>${mensaje}
                            </div>
                        </c:if>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">
                                <i class="fas fa-exclamation-circle me-2"></i>${error}
                            </div>
                        </c:if>
                    </div>

                    <!-- Tabla de citas -->
                    <div class="card">
                        <div class="card-header">
                            <h5 class="card-title mb-0">Lista de Citas</h5>
                            <c:if test="${empty citas}">
                                <div class="alert alert-info">
                                    <i class="fas fa-info-circle me-2"></i>No hay citas registradas.
                                </div>
                            </c:if>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-citas">
                                    <thead>
                                        <tr>
                                            <th>ID Cita</th>
                                            <th>Fecha Solicitud</th>
                                            <th>Fecha Cita</th>
                                            <th>Estado</th>
                                            <th>Tipo Pago</th>
                                            <th>Paciente</th>
                                            <th>Especialidad</th>
                                            <th>Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="cita" items="${citas}">
                                            <tr>
                                                <td>${cita.idCita}</td>
                                                <td>${cita.fechaSolicitud}</td>
                                                <td>${cita.fechaCita}</td>
                                                <td>
                                                    <span class="badge ${cita.estado == 'Pendiente' ? 'bg-warning' : (cita.estado == 'Confirmada' ? 'bg-success' : 'bg-danger')}">
                                                        ${cita.estado}
                                                    </span>
                                                </td>
                                                <td>${cita.idPago}</td>
                                                <td>${cita.paciente.nombres} ${cita.paciente.apPaterno} ${cita.paciente.apMaterno}</td>
                                                <td>${cita.especialidad.nombre}</td>
                                                <td>
                                                    <div class="btn-group">
                                                        <a href="${pageContext.request.contextPath}/ControladorCitaMedica?accion=editar&idCita=${cita.idCita}" class="btn btn-sm btn-primary">
                                                            <i class="fas fa-edit"></i>
                                                        </a>
                                                        <button type="button" class="btn btn-sm btn-danger" onclick="confirmarEliminacion('${cita.idCita}')">
                                                            <i class="fas fa-trash"></i>
                                                        </button>
                                                    </div>
                                                </td>
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
        <script>
                                                            function confirmarEliminacion(idCita) {
                                                                if (confirm('¿Está seguro de que desea eliminar esta cita?')) {
                                                                    window.location.href = '${pageContext.request.contextPath}/ControladorCitaMedica?accion=eliminar&idCita=' + idCita;
                                                                }
                                                            }
        </script>
    </body>
</html>
