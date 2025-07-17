<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <c:set var="currentDate" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(new java.util.Date())%>" />
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Nueva Cita - Mas Vida</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0">Programar Nueva Cita</h5>
                        </div>
                        <form action="${pageContext.request.contextPath}/ControladorCitaMedica" method="POST">
                            <input type="hidden" name="accion" value="guardar">
                            <div class="card-body">
                                <!-- Mensajes -->
                                <c:if test="${not empty param.mensaje}">
                                    <div class="alert alert-success">
                                        <i class="fas fa-check-circle me-2"></i>${param.mensaje}
                                    </div>
                                </c:if>

                                <!-- Datos del paciente -->
                                <div class="card mb-4">
                                    <div class="card-header bg-light">
                                        <h6 class="mb-0">Datos del Paciente</h6>
                                    </div>
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label for="dni" class="form-label">DNI</label>
                                                <input type="text" class="form-control" id="dni" name="dni" 
                                                       value="${persona.getDni()}" readonly>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label for="nombres" class="form-label">Nombres</label>
                                                <input type="text" class="form-control" id="nombres" 
                                                       value="${persona.getNombres()}" readonly>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label for="apPaterno" class="form-label">Apellido Paterno</label>
                                                <input type="text" class="form-control" id="apPaterno" 
                                                       value="${persona.getApPaterno()}" readonly>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label for="apMaterno" class="form-label">Apellido Materno</label>
                                                <input type="text" class="form-control" id="apMaterno" 
                                                       value="${persona.getApMaterno()}" readonly>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label for="correo" class="form-label">Correo</label>
                                                <input type="email" class="form-control" id="correo" 
                                                       value="${persona.getCorreo()}" readonly>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label for="telefono" class="form-label">Teléfono</label>
                                                <input type="tel" class="form-control" id="telefono" 
                                                       value="${persona.getTelefono()}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Datos de la cita -->
                                <div class="card mb-4">
                                    <div class="card-header bg-light">
                                        <h6 class="mb-0">Datos de la Cita</h6>
                                    </div>
                                    <div class="card-body">
                                        <c:if test="${especialidades == null or empty especialidades}">
                                            <div class="alert alert-danger">
                                                No se encontraron especialidades disponibles
                                            </div>
                                        </c:if>
                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label class="form-label">Fecha de Solicitud</label>
                                                <input type="text" class="form-control" 
                                                       value="${currentDate}" 
                                                       name="fechaSolicitud"
                                                       readonly>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label for="fechaCita" class="form-label">Fecha de Cita</label>
                                                <input type="datetime-local" class="form-control" id="fechaCita" name="fechaCita" 
                                                       min="${currentDate}" required>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label for="idEspecialidad" class="form-label">Especialidad</label>
                                                <select class="form-select" id="idEspecialidad" name="idEspecialidad" required>
                                                    <option value="">Seleccione una especialidad</option>
                                                    <c:forEach var="especialidad" items="${especialidades}">
                                                        <option value="${especialidad.idEspecialidad}">${especialidad.nombre}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label for="idPago" class="form-label">Tipo de Atención</label>
                                                <select class="form-select" id="idPago" name="idPago" required>
                                                    <option value="">Seleccione...</option>
                                                    <option value="NORMAL">Cita Normal (S/50.00)</option>
                                                    <option value="URGENTE">Cita Urgente (S/100.00)</option>
                                                    <option value="EMERGENCIA">Cita Emergencia (S/200.00)</option>
                                                </select>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <div class="card-footer text-end">
                                <input type="hidden" name="idPersona" value="${persona.getIdPersona()}">
                                <a href="${pageContext.request.contextPath}/jsp/vistaRecepcionista.jsp" class="btn btn-secondary">
                                    <i class="fas fa-times me-2"></i>Cancelar
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save me-2"></i>Guardar
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
