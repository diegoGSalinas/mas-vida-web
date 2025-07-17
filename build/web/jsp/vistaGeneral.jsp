<%-- 
    Document   : vistaGeneral
    Created on : 12 jul. 2025, 11:56:01 a. m.
    Author     : Diego
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Mas Vida - Panel General</title>
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
                    <a href="#"><i class="fas fa-home me-2"></i>Inicio</a>
                    <a href="#"><i class="fas fa-user me-2"></i>Mi Perfil</a>
                    <a href="#"><i class="fas fa-cog me-2"></i>Configuraciones</a>
                    <a href="${pageContext.request.contextPath}/ControladorLogout"><i class="fas fa-sign-out-alt me-2"></i>Cerrar Sesión</a>
                </div>

                <!-- Main Content -->
                <div class="col-md-9 main-content">
                    <div class="welcome-message">
                        <i class="fas fa-user-circle me-2"></i>
                        <span>Bienvenido ${usuario.nombreUsuario}</span>
                    </div>

                    <div class="row">
                        <!-- Tarjetas de información -->
                        <div class="col-md-4 mb-4">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Citas Pendientes</h5>
                                    <h2 class="card-text">0</h2>
                                    <a href="#" class="btn btn-primary">Ver Citas</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4 mb-4">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Pacientes</h5>
                                    <h2 class="card-text">0</h2>
                                    <a href="#" class="btn btn-primary">Ver Pacientes</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4 mb-4">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Exámenes</h5>
                                    <h2 class="card-text">0</h2>
                                    <a href="#" class="btn btn-primary">Ver Exámenes</a>
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