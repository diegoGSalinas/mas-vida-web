<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Revisar Paciente</title>
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
                    

                    <!-- Botones de acción -->
                    <div class="d-flex justify-content-end mt-3 mb-4">
                        <a href="${pageContext.request.contextPath}/ControladorDoctor?accion=verCitasMedicas" class="btn btn-secondary me-2">
                            <i class="fas fa-arrow-left me-2"></i>
                            Volver
                        </a>
                        <a href="${pageContext.request.contextPath}/ControladorDoctor?accion=verHistorial&idPersona=${paciente.idPersona}" class="btn btn-info me-2">
                            <i class="fas fa-history me-2"></i>
                            Ver Historial
                        </a>
                    </div>

                    <!-- Formulario paciente -->
                    <div class="card">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0">Revisar Paciente</h5>
                        </div>
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/ControladorRevisarPaciente" method="POST">
                                <div class="mb-3">
                                    <label for="nombres" class="form-label">Nombres</label>
                                    <input type="text" class="form-control" id="nombres" name="nombres" 
                                           value="${paciente.nombres}" readonly>
                                </div>
                                <div class="mb-3">
                                    <label for="apPaterno" class="form-label">Apellido Paterno</label>
                                    <input type="text" class="form-control" id="apPaterno" name="apPaterno" 
                                           value="${paciente.apPaterno}" readonly>
                                </div>
                                <div class="mb-3">
                                    <label for="apMaterno" class="form-label">Apellido Materno</label>
                                    <input type="text" class="form-control" id="apMaterno" name="apMaterno" 
                                           value="${paciente.apMaterno}" readonly>
                                </div>
                                
                                <div class="mb-3">
                                    <label for="motivoConsulta" class="form-label">Motivo de Consulta</label>
                                    <textarea class="form-control" id="motivoConsulta" name="motivoConsulta" rows="3" required></textarea>
                                </div>
                                
                                <div class="mb-3">
                                    <label for="diagnostico" class="form-label">Diagnóstico</label>
                                    <textarea class="form-control" id="diagnostico" name="diagnostico" rows="3" required></textarea>
                                </div>
                                
                                <div class="mb-3">
                                    <label for="tratamiento" class="form-label">Tratamiento</label>
                                    <textarea class="form-control" id="tratamiento" name="tratamiento" rows="3" required></textarea>
                                </div>
                                
                                <div class="mb-3">
                                    <label for="examen" class="form-label">Examen Médico</label>
                                    <select class="form-select" id="examen" name="examen">
                                        <option value="0" selected>Sin Examen</option>
                                        <c:forEach var="examen" items="${examenes}">
                                            <option value="${examen.idExamenMedico}">${examen.nombre} - S/. ${examen.precio}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                
                                <input type="hidden" name="idPersona" value="${paciente.idPersona}">
                                
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save me-2"></i>
                                    Guardar Revisión
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
