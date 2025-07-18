<%-- 
    Document   : editarUsuario
    Created on : 17 jul. 2025, 10:02:08
    Author     : ale
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="Modelo.Usuario" %>
<html>
<head>
    <title>Editar Usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Editar Usuario</h2>

    <form action="ControladorUsuario?accion=actualizar" method="post">
        <!-- ID (visible solo lectura) -->
        <div class="mb-3">
            <label for="idUsuario" class="form-label">ID Usuario</label>
            <input type="text" id="idUsuario" name="idUsuario" value="${usuario.idUsuario}" class="form-control" readonly>
        </div>

        <!-- Nombre -->
        <div class="mb-3">
            <label for="nombreUsuario" class="form-label">Nombre de usuario</label>
            <input type="text" id="nombreUsuario" name="nombre_usuario" value="${usuario.nombreUsuario}" class="form-control" required>
        </div>

        <!-- Contraseña -->
        <div class="mb-3 position-relative">
    <label for="contrasena" class="form-label">Contraseña</label>
    <div class="input-group">
        <input type="password" class="form-control" id="contrasena" name="contrasena"
               value="${usuario.contrasena}" required>
        <button class="btn btn-outline-secondary" type="button" id="togglePassword">
            <i class="fas fa-eye" id="iconoPassword"></i>
        </button>
    </div>
    <div class="invalid-feedback">Ingrese la contraseña.</div>
    </div>

        <!-- Tipo de usuario -->
        <div class="mb-3">
            <label for="tipoUsuario" class="form-label">Tipo de Usuario</label>
            <input type="text" id="tipoUsuario" name="tipoUsuario" value="${usuario.tipoUsuario}" class="form-control" readonly>
        </div>

        <!-- Estado -->
        <div class="mb-3">
            <label for="estado" class="form-label">Estado</label>
            <select class="form-select" id="estado" name="estado" required>
                <option value="" disabled selected>Seleccione</option>
                <option value="ACTIVO" ${estado == 'Activo' ? 'selected' : ''}>ACTIVO</option>
                <option value="INACTIVO" ${estado == 'Inactivo' ? 'selected' : ''}>INACTIVO</option>
            </select>
            <div class="invalid-feedback">Seleccione el estado.</div>
        </div>

        <!-- Botones -->
        <button type="submit" class="btn btn-success">Guardar cambios</button>
        <a href="ControladorUsuario?accion=verUsuarios" class="btn btn-secondary">Cancelar</a>
    </form>
</div>
            
       <!-- Script para cambiar visibilidad -->
<script>
    const togglePassword = document.getElementById("togglePassword");
    const passwordInput = document.getElementById("contrasena");
    const icono = document.getElementById("iconoPassword");

    togglePassword.addEventListener("click", function () {
        const tipo = passwordInput.type === "password" ? "text" : "password";
        passwordInput.type = tipo;
        icono.classList.toggle("fa-eye");
        icono.classList.toggle("fa-eye-slash");
    });
</script>
</body>
</html>


