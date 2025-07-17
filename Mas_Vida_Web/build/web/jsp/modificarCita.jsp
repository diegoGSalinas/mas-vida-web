<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Modificar Cita - Mas Vida</title>
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
                            <h5 class="mb-0">Modificar Cita</h5>
                        </div>
                        <form action="${pageContext.request.contextPath}/ControladorModificarCita" method="POST">
                            <input type="hidden" name="accion" value="modificar">
                            <input type="hidden" name="idCita" value="${cita.idCita}">
                            <div class="card-body">
                                <!-- Mensajes -->
                                <c:if test="${not empty param.mensaje}">
                                    <div class="alert alert-success">
                                        <i class="fas fa-check-circle me-2"></i>${param.mensaje}
                                    </div>
                                </c:if>

                                <!-- Datos del paciente (solo lectura) -->
                                <div class="card mb-4">
                                    <div class="card-header bg-light">
                                        <h6 class="mb-0">Datos del Paciente</h6>
                                    </div>
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label for="dni" class="form-label">DNI</label>
                                                <input type="text" class="form-control" id="dni" name="dni" 
                                                       value="${cita.paciente.dni}" readonly>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label for="nombres" class="form-label">Nombres</label>
                                                <input type="text" class="form-control" id="nombres" 
                                                       value="${cita.paciente.nombres}" readonly>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label for="apPaterno" class="form-label">Apellido Paterno</label>
                                                <input type="text" class="form-control" id="apPaterno" 
                                                       value="${cita.paciente.apPaterno}" readonly>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label for="apMaterno" class="form-label">Apellido Materno</label>
                                                <input type="text" class="form-control" id="apMaterno" 
                                                       value="${cita.paciente.apMaterno}" readonly>
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
                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label for="fechaSolicitud" class="form-label">Fecha de Solicitud</label>
                                                <input type="datetime-local" class="form-control" id="fechaSolicitud" name="fechaSolicitud" 
                                                       value="${cita.fechaSolicitud}" readonly>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label for="fechaCita" class="form-label">Fecha de Cita</label>
                                                <input type="datetime-local" class="form-control" id="fechaCita" name="fechaCita" 
                                                       value="${cita.fechaCita}" min="${currentDate}" required>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12 mb-3">
                                                <label for="especialidad" class="form-label">Especialidad</label>
                                                <select class="form-select" id="especialidad" name="idEspecialidad" required>
                                                    <c:forEach var="especialidad" items="${especialidades}">
                                                        <option value="${especialidad.idEspecialidad}" 
                                                                ${cita.especialidad.idEspecialidad == especialidad.idEspecialidad ? 'selected' : ''}>
                                                            ${especialidad.nombre}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Opciones de estado -->
                                <div class="card mb-4">
                                    <div class="card-header bg-light">
                                        <h6 class="mb-0">Opciones</h6>
                                    </div>
                                    <div class="card-body">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" id="cancelar" name="cancelar">
                                            <label class="form-check-label" for="cancelar">
                                                Cancelar cita (cambiar estado a Devolución)
                                            </label>
                                        </div>
                                    </div>
                                </div>

                                <div class="card-footer text-end">
                                    <a href="${pageContext.request.contextPath}/ControladorCitaPorPersona?dni=${cita.paciente.dni}" 
                                       class="btn btn-secondary">
                                        <i class="fas fa-arrow-left me-2"></i>Volver
                                    </a>
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-save me-1"></i>Guardar Cambios
                                    </button>
                                </div>
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
