<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
                                            <h3 class="title">Total de Usuarios</h3>
                                            <p class="number">${dashboard.totalUsuarios}</p>
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
                        </div>

                        <!-- Tabla de Citas por Especialidad -->
                        <div class="col-12 mt-4">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Citas Médicas por Especialidad</h4>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-striped">
                                            <thead>
                                                <tr>
                                                    <th>Especialidad</th>
                                                    <th>Cantidad</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="entry" items="${dashboard.citasPorEspecialidad}">
                                                    <tr>
                                                        <td>${entry.key}</td>
                                                        <td>${entry.value}</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Gráfico de Barras de Citas por Especialidad -->
                        <div class="col-12 mt-4">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Citas Médicas por Especialidad</h4>
                                </div>
                                <div class="card-body">
                                    <div class="grafico-barras">
                                        <c:forEach var="entry" items="${dashboard.citasPorEspecialidad}" varStatus="status">
                                            <div class="barra-contenedor">
                                                <div class="especialidad">${entry.key}</div>
                                                <div class="barra" style="width: ${(entry.value / dashboard.totalCitas) * 100}%">
                                                    <div class="cantidad">${entry.value}</div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Scripts -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Cargar la biblioteca de Google Charts -->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</body>
</html>
