<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Panel del Paciente - Mas Vida</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <!-- Estilos específicos para paciente -->
        <link href="${pageContext.request.contextPath}/css/vistaPaciente.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <!-- Sidebar -->
        <div class="sidebar">
            <div class="sidebar-content">
                <h3>Mas Vida</h3>
                <div class="user-info">
                    <h5>Bienvenido</h5>
                    <p>${usuario.nombreUsuario}</p>
                    <p>Paciente</p>
                </div>
                <hr>
                <a href="${pageContext.request.contextPath}/ControladorPaciente?accion=inicio"><i class="fas fa-home"></i> Inicio</a>
                <a href="${pageContext.request.contextPath}/ControladorPaciente?accion=listar"><i class="fas fa-calendar-alt"></i> Ver Mis Citas</a>
                <a href="${pageContext.request.contextPath}/ControladorPaciente?accion=nuevaCita"><i class="fas fa-plus"></i> Solicitar Nueva Cita</a>
            </div>
            <hr>
            <a href="${pageContext.request.contextPath}/ControladorLogout" class="logout-button">
                <i class="fas fa-sign-out-alt"></i> Cerrar Sesión
            </a>
        </div>

        <!-- Main Content -->
        <div class="dashboard-container">
            <div class="dashboard-content">
                <div class="welcome-message">
                    <i class="fas fa-user-circle me-2"></i>
                    <span>Bienvenido ${usuario.nombreUsuario}</span>
                </div>

                <!-- Mensajes -->
                <div class="mb-4">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <c:if test="${not empty mensaje}">
                        <div class="alert alert-success">${mensaje}</div>
                    </c:if>
                </div>

                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <h3>Mis Citas Médicas</h3>
                            <div class="table-container">
                                <c:choose>
                                    <c:when test="${not empty citas}">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>Fecha de Solicitud</th>
                                                    <th>Fecha de Cita</th>
                                                    <th>Especialidad</th>
                                                    <th>Estado</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="cita" items="${citas}">
                                                    <tr>
                                                        <td>${cita.fechaSolicitud}</td>
                                                        <td>${cita.fechaCita}</td>
                                                        <td>
                                                            ${cita.especialidad.nombre}
                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${cita.estado == 'PENDIENTE'}">
                                                                    <span class="status pending">${cita.estado}</span>
                                                                </c:when>
                                                                <c:when test="${cita.estado == 'CONFIRMADA'}">
                                                                    <span class="status confirmed">${cita.estado}</span>
                                                                </c:when>
                                                                <c:when test="${cita.estado == 'REALIZADA'}">
                                                                    <span class="status completed">${cita.estado}</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="status cancelled">${cita.estado}</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="text-center py-4">No tienes citas programadas actualmente.</p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                    <div class="col-12">
                        <div class="card">
                            <h3>Solicitar Nueva Cita</h3>
                            <form action="${pageContext.request.contextPath}/ControladorPaciente" method="POST">
                                <input type="hidden" name="accion" value="nuevaCita">
                                <div class="form-group">
                                    <label for="tipoExamen">Tipo de Examen:</label>
                                    <select name="tipoExamen" id="tipoExamen" class="form-control" required>
                                        <option value="">Seleccione un tipo de examen</option>
                                        <option value="RAYOS_X">Rayos X</option>
                                        <option value="ULTRASONIDO">Ultrasonido</option>
                                        <option value="TOMOGRAFIA">Tomografía</option>
                                        <option value="RESONANCIA">Resonancia Magnética</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="fecha">Fecha:</label>
                                    <input type="date" name="fecha" id="fecha" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label for="hora">Hora:</label>
                                    <input type="time" name="hora" id="hora" class="form-control" required>
                                </div>
                                <button type="submit" class="btn btn-primary w-100">Solicitar Cita</button>
                            </form>
                        </div>
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
