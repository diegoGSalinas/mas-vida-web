<%-- 
    Document   : index
    Created on : 12 jul. 2025, 11:35:38 a. m.
    Author     : Diego
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Mas Vida - Login</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <!-- Estilos personalizados -->
        <link href="css/index.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <div class="login-container">
                <div class="login-header">
                    <h1>Mas Vida</h1>
                    <p class="text-muted">Sistema de Gestión Médica</p>
                </div>
                
                <form action="ControladorLogin" method="POST" class="needs-validation" novalidate>
                    <div class="mb-3">
                        <label for="usuario" class="form-label">Usuario</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="fas fa-user"></i></span>
                            <input type="text" class="form-control" id="usuario" name="usuario" required>
                            <div class="invalid-feedback">
                                Por favor ingrese su usuario
                            </div>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label">Contraseña</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="fas fa-lock"></i></span>
                            <input type="password" class="form-control" id="password" name="password" required>
                            <div class="invalid-feedback">
                                Por favor ingrese su contraseña
                            </div>
                        </div>
                    </div>

                    <div class="mb-3 form-check">
                        <input type="checkbox" class="form-check-input" id="recordar">
                        <label class="form-check-label" for="recordar">Recordarme</label>
                    </div>

                    <button type="submit" class="btn btn-primary w-100">Iniciar Sesión</button>
                </form>

                <% 
                    String error = (String) request.getAttribute("error");
                    String mensaje = (String) request.getAttribute("mensaje");
                %>
                
                <% if (error != null) { %>
                    <div class="alert alert-danger mt-3" role="alert">
                        <i class="fas fa-exclamation-triangle me-2"></i>
                        <%= error %>
                    </div>
                <% } else if (mensaje != null) { %>
                    <div class="alert alert-success mt-3" role="alert">
                        <i class="fas fa-check-circle me-2"></i>
                        <%= mensaje %>
                    </div>
                <% } %>
            </div>
        </div>

        <!-- Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Form Validation -->
        <script>
            (function () {
                'use strict'
                var forms = document.querySelectorAll('.needs-validation')
                Array.prototype.slice.call(forms)
                    .forEach(function (form) {
                        form.addEventListener('submit', function (event) {
                            if (!form.checkValidity()) {
                                event.preventDefault()
                                event.stopPropagation()
                            }
                            form.classList.add('was-validated')
                        }, false)
                    })
            })()
        </script>
    </body>
</html>
