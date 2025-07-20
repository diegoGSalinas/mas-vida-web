<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                <!-- Sidebar -->
                <div class="col-md-3 sidebar">
                    <h3 class="text-white mb-4">Mas Vida</h3>
                    <div class="user-info">
                        <h5 class="text-white mb-2">Bienvenido</h5>
                        <p class="text-white mb-0">${usuario.nombreUsuario}</p>
                        <p class="text-white mb-0">Doctor</p>
                    </div>
                    <hr class="bg-white">
                    <a href="${pageContext.request.contextPath}/ControladorCitaMedica?accion=listarCitasDoctor"><i class="fas fa-calendar-alt me-2"></i>Mis Citas</a>
                    <a href="${pageContext.request.contextPath}/ControladorCitaMedica?accion=agregarCita"><i class="fas fa-plus me-2"></i>Nueva Cita</a>
                    <a href="${pageContext.request.contextPath}/ControladorCitaMedica?accion=verHistorial"><i class="fas fa-history me-2"></i>Historial de Citas</a>
                    <hr class="bg-white my-3">
                    <a href="${pageContext.request.contextPath}/ControladorLogout"><i class="fas fa-sign-out-alt me-2"></i>Cerrar Sesión</a>
                </div>

                <!-- Main Content -->
                <div class="col-md-9 main-content">
                    <div class="welcome-message">
                        <i class="fas fa-user-circle me-2"></i>
                        <span>Bienvenido ${usuario.nombreUsuario}</span>
                    </div>

                    <!-- Mensajes -->
                    <c:if test="${not empty mensaje}">
                        <div class="alert alert-success">
                            ${mensaje}
                        </div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">
                            ${error}
                        </div>
                    </c:if>

                    <!-- Tarjetas de información -->
                    <div class="row">
                        <div class="col-md-4">
                            <div class="doctor-card">
                                <div class="card-body">
                                    <h5 class="card-title">Citas Pendientes</h5>
                                    <h2 class="card-text">0</h2>
                                    <a href="${pageContext.request.contextPath}/ControladorCitaMedica?accion=listarCitasDoctor" class="btn btn-primary">Ver Citas</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="doctor-card">
                                <div class="card-body">
                                    <h5 class="card-title">Pacientes</h5>
                                    <h2 class="card-text">0</h2>
                                    <a href="${pageContext.request.contextPath}/ControladorCitaMedica?accion=listarPacientes" class="btn btn-primary">Ver Pacientes</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="doctor-card">
                                <div class="card-body">
                                    <h5 class="card-title">Historial</h5>
                                    <h2 class="card-text">0</h2>
                                    <a href="${pageContext.request.contextPath}/ControladorCitaMedica?accion=verHistorial" class="btn btn-primary">Ver Historial</a>
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
