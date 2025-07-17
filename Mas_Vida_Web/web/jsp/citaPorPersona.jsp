<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Citas Médicas - Mas Vida</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-10">
                    <div class="card">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0">Citas Médicas de ${persona.nombres} ${persona.apPaterno} ${persona.apMaterno}</h5>
                        </div>
                        <div class="card-body">
                            <!-- Mensajes -->
                            <c:if test="${not empty mensaje}">
                                <div class="alert alert-info">
                                    <i class="fas fa-info-circle me-2"></i>${mensaje}
                                </div>
                            </c:if>
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger">
                                    <i class="fas fa-exclamation-circle me-2"></i>${error}
                                </div>
                            </c:if>

                            <!-- Tabla de Citas -->
                            <c:if test="${not empty citas}">
                                <div class="table-responsive">
                                    <table class="table table-striped">
                                        <thead class="table-primary">
                                            <tr>
                                                <th>ID Cita</th>
                                                <th>Fecha Solicitud</th>
                                                <th>Fecha Cita</th>
                                                <th>Especialidad</th>
                                                <th>Estado</th>
                                                <th>Acciones</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="cita" items="${citas}">
                                                <tr>
                                                    <td>${cita.idCita}</td>
                                                    <td>${cita.fechaSolicitud}</td>
                                                    <td>${cita.fechaCita}</td>
                                                    <td>${cita.especialidad.nombre}</td>
                                                    <td>
                                                        <span class="badge bg-${cita.estado == 'Pendiente' ? 'warning' : 
                                                                                (cita.estado == 'Confirmada' ? 'success' : 'danger')}">
                                                                  ${cita.estado}
                                                              </span>
                                                        </td>
                                                        <td>
                                                            <div class="row">
                                                                <div class="col">
                                                                    <c:if test="${cita.estado == 'Pendiente'}">
                                                                        <a href="${pageContext.request.contextPath}/ControladorModificarCita?idCita=${cita.idCita}" 
                                                                           class="btn btn-sm btn-primary w-100">
                                                                            <i class="fas fa-edit me-1"></i>Editar
                                                                        </a>
                                                                    </c:if>
                                                                </div>
                                                                <div class="col">
                                                                    <a href="${pageContext.request.contextPath}/ControladorCitaPorPersona?accion=generarPdf&idCita=${cita.idCita}" 
                                                                       class="btn btn-sm btn-info w-100">
                                                                        <i class="fas fa-file-pdf me-1"></i>Descargar PDF
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </c:if>
                                <c:if test="${empty citas}">
                                    <div class="alert alert-info">
                                        <i class="fas fa-info-circle me-2"></i>No se encontraron citas médicas para esta persona.
                                    </div>
                                </c:if>
                            </div>
                            <div class="card-footer text-end">
                                <a href="${pageContext.request.contextPath}/jsp/buscarPersona.jsp" class="btn btn-secondary">
                                    <i class="fas fa-arrow-left me-2"></i>Volver
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Bootstrap Bundle with Popper -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>
    </html>
