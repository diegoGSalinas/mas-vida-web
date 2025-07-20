<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Personas</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .table {
                margin-top: 20px;
            }
            .btn-editar {
                margin-right: 5px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2 class="mb-4">Lista de Personas</h2>
            
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div class="d-flex">
                    <select id="filtroTipo" class="form-select me-2">
                        <option value="">Todos los tipos</option>
                        <c:forEach var="tipo" items="${tiposUsuario}">
                            <option value="${tipo.nombre}" ${param.tipoUsuario == tipo.nombre ? 'selected' : ''}>${tipo.nombre}</option>
                        </c:forEach>
                    </select>
                    <button id="btnFiltrar" class="btn btn-primary">Filtrar</button>
                </div>
                <a href="${pageContext.request.contextPath}/controladorPersona?accion=volver" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Volver a Administración
                </a>
            </div>

            <div class="table-responsive">
                <table class="table table-striped">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Apellidos</th>
                            <th>Nombres</th>
                            <th>DNI</th>
                            <th>Correo</th>
                            <th>Teléfono</th>
                            <th>Tipo Usuario</th>
                            <th>Fecha Nac.</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="tablaPersonas">
                        <c:forEach var="persona" items="${personas}">
                            <tr>
                                <td>${persona.idPersona}</td>
                                <td>${persona.apPaterno} ${persona.apMaterno}</td>
                                <td>${persona.nombres}</td>
                                <td>${persona.dni}</td>
                                <td>${persona.correo}</td>
                                <td>${persona.telefono}</td>
                                <td>${persona.tipoUsuario}</td>
                                <td>${persona.fechaNacimiento}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/ControladorPersona?accion=ver&id=${persona.idPersona}" 
                                       class="btn btn-primary btn-sm">
                                        <i class="fas fa-eye"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/ControladorPersona?accion=editar&id=${persona.idPersona}" 
                                       class="btn btn-warning btn-sm">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Bootstrap JS y dependencias -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Font Awesome -->
        <script src="https://kit.fontawesome.com/your-code.js" crossorigin="anonymous"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const filtroSelect = document.getElementById('filtroTipo');
            const btnFiltrar = document.getElementById('btnFiltrar');
            const tbody = document.querySelector('.table tbody');

            // Función para filtrar la tabla
            function filtrarTabla() {
                const valorFiltro = filtroSelect.value.toLowerCase();
                const filas = tbody.getElementsByTagName('tr');

                // Mostrar todas las filas
                for (let i = 0; i < filas.length; i++) {
                    filas[i].style.display = '';
                }

                // Si hay un filtro específico, ocultar las que no coincidan
                if (valorFiltro) {
                    for (let i = 0; i < filas.length; i++) {
                        const celdaTipo = filas[i].getElementsByTagName('td')[6]; // La columna del tipo usuario
                        if (celdaTipo) {
                            const tipoUsuario = celdaTipo.textContent.toLowerCase();
                            if (!tipoUsuario.includes(valorFiltro)) {
                                filas[i].style.display = 'none';
                            }
                        }
                    }
                }
            }

            // Evento para el botón filtrar
            btnFiltrar.addEventListener('click', function(e) {
                e.preventDefault();
                console.log('Filtrando...');
                filtrarTabla();
            });

            // Evento para el cambio en el select
            filtroSelect.addEventListener('change', function(e) {
                e.preventDefault();
                console.log('Cambiando filtro a:', this.value);
                filtrarTabla();
            });

            // Inicializar el filtro
            filtrarTabla();
        });
    </script>
    </body>
</html>
