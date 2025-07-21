<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${titulo}</title>
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
                <jsp:useBean id="fechaHoy" class="java.util.Date" scope="page"/>
                <fmt:formatDate value="${fechaHoy}" pattern="yyyy-MM-dd" var="fechaHoy"/>
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

                <div class="col-md-9 main-content">
                    <div class="welcome-message">
                        <i class="fas fa-user-circle me-2"></i>
                        <span>Bienvenido ${usuario.nombreUsuario}</span>
                    </div>

                    <div class="d-flex justify-content-end mt-3 mb-4">
                        <a href="${pageContext.request.contextPath}/jsp/vistaCitasPendientes.jsp" class="btn btn-secondary">
                            <i class="fas fa-arrow-left me-2"></i>
                            Volver
                        </a>
                    </div>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">
                            ${error}
                        </div>
                    </c:if>

                    <!-- Tabla de citas médicas -->
                    <c:if test="${not empty citas}">
                        <!-- Tabla Citas del Día -->
                        <div class="card mb-4">
                            <div class="card-header bg-success text-white">
                                <h5 class="mb-0">Citas del Día (${fechaHoy})</h5>
                            </div>
                            <div class="card-body">
                                <c:if test="${not empty citas}">
                                    <div class="table-responsive">
                                        <table class="table table-striped">
                                            <thead>
                                                <tr>
                                                    <th>Paciente</th>
                                                    <th>Hora</th>
                                                    <th>Especialidad</th>
                                                    <th>Estado</th>
                                                    <th>Tipo de Pago</th>
                                                    <th>Acciones</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="cita" items="${citas}">
                                                    <c:if test="${cita.fechaCita.toLocalDate() == fechaHoy}">
                                                        <tr>
                                                            <td>${cita.paciente.nombres} ${cita.paciente.apPaterno} ${cita.paciente.apMaterno}</td>
                                                            <td>${cita.fechaCita.toLocalTime()}</td>
                                                            <td>${cita.especialidad.nombre}</td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${cita.estado == 'Pendiente'}">
                                                                        <span class="badge bg-warning">Pendiente</span>
                                                                    </c:when>
                                                                    <c:when test="${cita.estado == 'Confirmada'}">
                                                                        <span class="badge bg-success">Confirmada</span>
                                                                    </c:when>
                                                                    <c:when test="${cita.estado == 'Cancelada'}">
                                                                        <span class="badge bg-danger">Cancelada</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="badge bg-secondary">${cita.estado}</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${cita.idPago == 'NORMAL'}">
                                                                        <span class="badge bg-info">Cita Normal</span>
                                                                    </c:when>
                                                                    <c:when test="${cita.idPago == 'URGENTE'}">
                                                                        <span class="badge bg-warning">Cita Urgente</span>
                                                                    </c:when>
                                                                    <c:when test="${cita.idPago == 'EMERGENCIA'}">
                                                                        <span class="badge bg-danger">Cita Emergencia</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="badge bg-secondary">${cita.idPago}</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td class="d-flex align-items-center">
                                                                <div class="d-flex align-items-center me-2">
                                                                    <a href="${pageContext.request.contextPath}/ControladorRevisarPaciente?idPersona=${cita.paciente.idPersona}" 
                                                                       class="btn btn-primary btn-sm me-2">
                                                                        <i class="fas fa-user-md me-2"></i>
                                                                        Atender
                                                                    </a>
                                                                </div>
                                                                <div class="d-flex align-items-center">
                                                                    <form action="${pageContext.request.contextPath}/ControladorDoctor" method="POST" class="d-flex align-items-center">
                                                                        <input type="hidden" name="accion" value="editarEstado">
                                                                        <input type="hidden" name="idCita" value="${cita.idCita}">
                                                                        <select name="nuevoEstado" class="form-select form-select-sm me-2" style="width: 150px;">
                                                                            <option value="Pendiente" ${cita.estado == 'Pendiente' ? 'selected' : ''}>Pendiente</option>
                                                                            <option value="Esperando Examen" ${cita.estado == 'Esperando Examen' ? 'selected' : ''}>Esperando Examen</option>
                                                                            <option value="Finalizado" ${cita.estado == 'Finalizado' ? 'selected' : ''}>Finalizado</option>
                                                                        </select>
                                                                        <button type="submit" class="btn btn-secondary btn-sm">
                                                                            <i class="fas fa-check me-2"></i>
                                                                            Confirmar
                                                                        </button>
                                                                    </form>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </c:if>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </c:if>
                                <c:if test="${empty citas}">
                                    <div class="alert alert-info">
                                        <i class="fas fa-info-circle me-2"></i>
                                        No hay citas para hoy
                                    </div>
                                </c:if>
                            </div>
                        </div>

                        <!-- Tabla Citas Programadas -->
                        <div class="card mb-4">
                            <div class="card-header bg-primary text-white">
                                <h5 class="mb-0">Citas Programadas</h5>
                            </div>
                            <div class="card-body">
                                <c:if test="${not empty citas}">
                                    <div class="table-responsive">
                                        <table class="table table-striped">
                                            <thead>
                                                <tr>
                                                    <th>Paciente</th>
                                                    <th>Fecha</th>
                                                    <th>Hora</th>
                                                    <th>Especialidad</th>
                                                    <th>Estado</th>
                                                    <th>Tipo de Pago</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="cita" items="${citas}">
                                                    <c:if test="${cita.fechaCita.toLocalDate() != fechaHoy}">
                                                        <tr>
                                                            <td>${cita.paciente.nombres} ${cita.paciente.apPaterno} ${cita.paciente.apMaterno}</td>
                                                            <td>${cita.fechaCita.toLocalDate()}</td>
                                                            <td>${cita.fechaCita.toLocalTime()}</td>
                                                            <td>${cita.especialidad.nombre}</td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${cita.estado == 'Pendiente'}">
                                                                        <span class="badge bg-warning">Pendiente</span>
                                                                    </c:when>
                                                                    <c:when test="${cita.estado == 'Esperando Examen'}">
                                                                        <span class="badge bg-info">Esperando Examen</span>
                                                                    </c:when>
                                                                    <c:when test="${cita.estado == 'Finalizado'}">
                                                                        <span class="badge bg-success">Finalizado</span>
                                                                    </c:when>
                                                                </c:choose>
                                                            </td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${cita.idPago == 'NORMAL'}">
                                                                        <span class="badge bg-info">Cita Normal</span>
                                                                    </c:when>
                                                                    <c:when test="${cita.idPago == 'URGENTE'}">
                                                                        <span class="badge bg-warning">Cita Urgente</span>
                                                                    </c:when>
                                                                    <c:when test="${cita.idPago == 'EMERGENCIA'}">
                                                                        <span class="badge bg-danger">Cita Emergencia</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="badge bg-secondary">${cita.idPago}</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                        </tr>
                                                    </c:if>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </c:if>
                                <c:if test="${empty citas}">
                                    <div class="alert alert-info">
                                        <i class="fas fa-info-circle me-2"></i>
                                        No hay citas programadas
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${empty citas}">
                        <div class="alert alert-info">
                            <i class="fas fa-info-circle me-2"></i>
                            No hay citas médicas disponibles
                        </div>
                    </c:if>
                </div>
            </div>
        </div>

        <!-- Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
