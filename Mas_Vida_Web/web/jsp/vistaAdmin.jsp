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
        <!-- Estilos específicos para admin -->
        <link href="${pageContext.request.contextPath}/css/vistaAdmin.css" rel="stylesheet">
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
                        <p class="text-white mb-0">Administrador</p>
                    </div>
                    <hr class="bg-white">
                    <a href="${pageContext.request.contextPath}/vistaAdmin"><i class="fas fa-home me-2"></i>Inicio</a>
                    <a href="${pageContext.request.contextPath}/ControladorUsuario?accion=verUsuarios"><i class="fas fa-users me-2"></i>Gestión de Usuarios</a>
                    <a href="${pageContext.request.contextPath}/controladorPersona?accion=listar"><i class="fas fa-users me-2"></i>Lista de Personas</a>
                    <a href="${pageContext.request.contextPath}/ControladorReporte"><i class="fas fa-file-alt me-2"></i>Reportes</a>
                    <a href="${pageContext.request.contextPath}/ControladorDashboard"><i class="fas fa-chart-line me-2"></i>Dashboard</a>
                    <a href="#" class="backup-link" onclick="realizarBackup()"><i class="fas fa-database me-2"></i>Realizar Backup</a>
                    <hr class="bg-white my-3">
                    <a href="${pageContext.request.contextPath}/ControladorLogout"><i class="fas fa-sign-out-alt me-2"></i>Cerrar Sesión</a>
                </div>

                <!-- Contenido Principal -->
                <div class="col-md-9">
                    <!-- Success Modal for Backup -->
                    <div class="modal fade" id="backupSuccessModal" tabindex="-1" aria-labelledby="backupSuccessModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="backupSuccessModalLabel">Backup Exitoso</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    Se ha realizado el backup de la base de datos exitosamente.
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Aceptar</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-4">
                            <div class="card admin-card text-white bg-primary">
                                <div class="card-body">
                                    <h5 class="card-title">Gestión de Usuarios</h5>
                                    <p class="card-text">Administra los usuarios del sistema</p>
                                    <a href="${pageContext.request.contextPath}/ControladorUsuario?accion=verUsuarios" class="btn btn-light">Ir a Gestión</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="card admin-card text-white bg-success">
                                <div class="card-body">
                                    <h5 class="card-title">Lista de Personas</h5>
                                    <p class="card-text">Ver lista completa de personas</p>
                                    <a href="${pageContext.request.contextPath}/controladorPersona?accion=listar" class="btn btn-light">Ver Personas</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="card admin-card text-white bg-info">
                                <div class="card-body">
                                    <h5 class="card-title">Reportes</h5>
                                    <p class="card-text">Genera reportes del sistema</p>
                                    <a href="${pageContext.request.contextPath}/ControladorReporte" class="btn btn-light">Ir a Reportes</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="card admin-card text-white bg-warning">
                                <div class="card-body">
                                    <h5 class="card-title">Dashboard</h5>
                                    <p class="card-text">Visualiza estadísticas y gráficos del sistema</p>
                                    <a href="${pageContext.request.contextPath}/ControladorDashboard" class="btn btn-light">Ir a Dashboard</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        
        <!-- JavaScript para el backup -->
        <script>
            function realizarBackup() {
                fetch('${pageContext.request.contextPath}/vistaAdmin?accion=backup', {
                    method: 'GET'
                })
                .then(response => {
                    if (response.ok) {
                        const modal = new bootstrap.Modal(document.getElementById('backupSuccessModal'));
                        modal.show();
                    } else {
                        console.error('Error al realizar el backup');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
            }
        </script>
    </body>
</html>
