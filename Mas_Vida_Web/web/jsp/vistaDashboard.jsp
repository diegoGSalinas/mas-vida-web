<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard - Mas Vida</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <!-- Chart.js -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <!-- Estilos específicos para dashboard -->
        <link href="${pageContext.request.contextPath}/css/vistaDashboard.css" rel="stylesheet">
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
                    <a href="${pageContext.request.contextPath}/ControladorConfiguracion"><i class="fas fa-cog me-2"></i>Configuración del Sistema</a>
                    <a href="${pageContext.request.contextPath}/ControladorReporte"><i class="fas fa-file-alt me-2"></i>Reportes</a>
                    <a href="${pageContext.request.contextPath}/ControladorDashboard"><i class="fas fa-chart-line me-2"></i>Dashboard</a>
                    <a href="${pageContext.request.contextPath}/ControladorLogout"><i class="fas fa-sign-out-alt me-2"></i>Cerrar Sesión</a>
                </div>

                <!-- Contenido Principal -->
                <div class="col-md-9">
                    <div class="main-content">
                        <!-- Cards de Estadísticas -->
                        <div class="row mb-4">
                        <div class="col-md-3">
                            <div class="card card-stats">
                                <div class="card-body">
                                    <div class="icon">
                                        <i class="fas fa-users"></i>
                                    </div>
                                    <div class="content">
                                        <h3 class="title">Usuarios</h3>
                                        <p class="number">${dashboard.totalUsuarios}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card card-stats">
                                <div class="card-body">
                                    <div class="icon">
                                        <i class="fas fa-user-injured"></i>
                                    </div>
                                    <div class="content">
                                        <h3 class="title">Pacientes</h3>
                                        <p class="number">${dashboard.totalPacientes}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card card-stats">
                                <div class="card-body">
                                    <div class="icon">
                                        <i class="fas fa-calendar-check"></i>
                                    </div>
                                    <div class="content">
                                        <h3 class="title">Citas Totales</h3>
                                        <p class="number">${dashboard.totalCitas}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card card-stats">
                                <div class="card-body">
                                    <div class="icon">
                                        <i class="fas fa-check-circle"></i>
                                    </div>
                                    <div class="content">
                                        <h3 class="title">Citas Confirmadas</h3>
                                        <p class="number">${dashboard.totalConfirmadas}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Gráficos -->
                    <div class="row">
                        <!-- Gráfico de Citas por Fecha -->
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title">Citas por Fecha</h5>
                                </div>
                                <div class="card-body">
                                    <canvas id="citasPorFechaChart"></canvas>
                                </div>
                            </div>
                        </div>

                        <!-- Gráfico de Citas por Especialidad -->
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title">Citas por Especialidad</h5>
                                </div>
                                <div class="card-body">
                                    <canvas id="citasPorEspecialidadChart"></canvas>
                                </div>
                            </div>
                        </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Inicialización de gráficos -->
        <script>
            // Gráfico de Citas por Fecha
            const citasPorFecha = ${dashboard.citasPorFecha};
            const ctxFecha = document.getElementById('citasPorFechaChart').getContext('2d');
            new Chart(ctxFecha, {
                type: 'line',
                data: {
                    labels: Object.keys(citasPorFecha),
                    datasets: [{
                        label: 'Citas por Fecha',
                        data: Object.values(citasPorFecha),
                        borderColor: 'rgb(75, 192, 192)',
                        tension: 0.1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });

            // Gráfico de Citas por Especialidad
            const citasPorEspecialidad = ${dashboard.citasPorEspecialidad};
            const ctxEspecialidad = document.getElementById('citasPorEspecialidadChart').getContext('2d');
            new Chart(ctxEspecialidad, {
                type: 'bar',
                data: {
                    labels: Object.keys(citasPorEspecialidad),
                    datasets: [{
                        label: 'Citas por Especialidad',
                        data: Object.values(citasPorEspecialidad),
                        backgroundColor: 'rgba(54, 162, 235, 0.5)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        </script>
    </body>
</html>
