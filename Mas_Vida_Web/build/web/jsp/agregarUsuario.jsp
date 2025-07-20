<%-- 
    Document   : agregarUsuario
    Created on : 16 jul. 2025, 20:00:51
    Author     : ale
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.TipoUsuario" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Agregar Usuario</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <div class="container mt-5">
            <h2 class="mb-4">Agregar Nuevo Usuario</h2>

            <form action="ControladorUsuario" method="post" class="needs-validation" novalidate>
                <input type="hidden" name="accion" value="guardar">

                <!-- ID de Usuario con generación automática -->
                <div class="mb-3">
                    <label for="idUsuario" class="form-label">ID de Usuario</label>
                    <div class="input-group">
                        <input type="text" class="form-control" id="idUsuario" name="idUsuario"
                               pattern="[A-Za-z0-9]{4,30}" title="Entre 4 y 30 caracteres alfanuméricos" required readonly
                               placeholder="Seleccione el tipo de usuario y luego genere el ID">
                        <button class="btn btn-secondary" type="button" onclick="generarId()">Generar ID</button>
                    </div>
                    <div class="invalid-feedback">Presiona "Generar ID" o ingrésalo manualmente.</div>
                </div>

                <!-- Nombre de Usuario -->
                <div class="mb-3">
                    <label for="nombreUsuario" class="form-label">Nombre de Usuario</label>
                    <input type="text" class="form-control" id="nombreUsuario" name="nombre_usuario"
                           pattern="[A-Za-z0-9]{4,20}" title="Entre 4 y 20 caracteres alfanuméricos" required
                           placeholder="Nombre para inicio de sesión">
                    <div class="invalid-feedback">Ingrese un nombre de usuario válido (4-20 caracteres alfanuméricos).</div>
                </div>

                <!-- Contraseña -->
                <div class="mb-3">
                    <label for="contrasena" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="contrasena" name="contrasena"
                           pattern=".{6,}" title="Debe tener al menos 6 caracteres" required
                           placeholder="Mayor a 6 caracteres">
                    <div class="invalid-feedback">La contraseña debe tener al menos 6 caracteres.</div>
                </div>

                <!-- Tipo de Usuario -->
                <div class="mb-3">
                    <label for="tipoUsuario" class="form-label">Tipo de Usuario</label>
                    <select name="tipoUsuario" class="form-control" required>
                        <option value="" disabled selected hidden>Seleccione un tipo de usuario</option>
                        <c:forEach var="nombre" items="${nombresTipoUsuario}">
                            <option value="${nombre}">${nombre}</option>
                        </c:forEach>
                    </select>
                    <div class="invalid-feedback">Seleccione un tipo de usuario.</div>
                </div>

                <!-- Estado -->
                <div class="mb-3">
                    <label for="estado" class="form-label">Estado</label>
                    <select class="form-select" id="estado" name="estado" required>
                        <option value="">Seleccione</option>
                        <option value="Activo">Activo</option>
                        <option value="Inactivo">Inactivo</option>
                    </select>
                    <div class="invalid-feedback">Seleccione el estado.</div>
                </div>

                <!-- Botones -->
                <div class="d-flex justify-content-between">
                    <button type="submit" class="btn btn-success">Guardar</button>
                    <a href="${pageContext.request.contextPath}/ControladorUsuario?accion=verUsuarios" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>

            <script>
                // Validación Bootstrap
                (function () {
                    'use strict';
                    window.addEventListener('load', function () {
                        const forms = document.getElementsByClassName('needs-validation');
                        Array.prototype.filter.call(forms, function (form) {
                            form.addEventListener('submit', function (event) {
                                if (!form.checkValidity()) {
                                    event.preventDefault();
                                    event.stopPropagation();
                                }
                                form.classList.add('was-validated');
                            }, false);
                        });
                    }, false);
                })();
                //GENERACION DE ID POR TIPO
                function generarId() {
                    const tipoSelect = document.querySelector('select[name="tipoUsuario"]');
                    const tipo = tipoSelect.value;
                    const inputId = document.getElementById("idUsuario");

                    if (!tipo) {
                        alert("Por favor seleccione un tipo de usuario.");
                        return;
                    }

                    // Define los prefijos por tipo de usuario
                    const prefijos = {
                        ADMINISTRADOR: "ADMIN",
                        DOCTOR: "DOC",
                        RECEPCIONISTA: "RECEP",
                        TECNICO: "TEC",
                        PACIENTE: "PAC"
                    };

                    // Generar número aleatorio de 3 dígitos (puedes cambiarlo por lógica de contador)
                    const numero = String(Math.floor(Math.random() * 999) + 1).padStart(3, "0");

                    const prefijo = prefijos[tipo] || "USR";
                    const idGenerado = prefijo + numero;

                    inputId.value = idGenerado;
                }
            </script>
        </div>
    </body>
</html>
