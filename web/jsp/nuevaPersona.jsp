<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Nueva Persona - Mas Vida</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0">Ingresar Nueva Persona</h5>
                        </div>
                        <form action="${pageContext.request.contextPath}/controladorPersona" method="POST" id="formNuevaPersona">
                            <input type="hidden" name="accion" value="crear">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="nombres" class="form-label">Nombres</label>
                                        <input type="text" class="form-control" id="nombres" name="nombres" required>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="apPaterno" class="form-label">Apellido Paterno</label>
                                        <input type="text" class="form-control" id="apPaterno" name="apPaterno" required>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="apMaterno" class="form-label">Apellido Materno</label>
                                        <input type="text" class="form-control" id="apMaterno" name="apMaterno" required>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="dni" class="form-label">DNI</label>
                                        <input type="text" class="form-control" id="dni" name="dni" required maxlength="8">
                                        <div class="error"></div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="correo" class="form-label">Correo Electrónico</label>
                                        <input type="email" class="form-control" id="correo" name="correo" required>
                                        <div class="error"></div>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="telefono" class="form-label">Teléfono</label>
                                        <input type="tel" class="form-control" id="telefono" name="telefono" required maxlength="9">
                                        <div class="error"></div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 mb-3">
                                        <label for="direccion" class="form-label">Dirección</label>
                                        <input type="text" class="form-control" id="direccion" name="direccion" required>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="fechaNacimiento" class="form-label">Fecha de Nacimiento</label>
                                        <input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento" required>
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer text-end">
                                <a href="${pageContext.request.contextPath}/ControladorCitaMedica?accion=inicio" class="btn btn-secondary">Cancelar</a>
                                <button type="submit" class="btn btn-primary">Guardar Persona</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <!-- Estilos personalizados -->
        <link href="../css/nuevaPersona.css" rel="stylesheet">

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const form = document.getElementById('formNuevaPersona');
                const dniInput = document.getElementById('dni');
                const correoInput = document.getElementById('correo');
                const telefonoInput = document.getElementById('telefono');

                // Validación de DNI
                dniInput.addEventListener('input', function () {
                    const dniValue = this.value;
                    const errorDiv = this.nextElementSibling;

                    if (!/^[0-9]{0,8}$/.test(dniValue)) {
                        errorDiv.textContent = 'El DNI debe contener solo números y tener 8 dígitos';
                        this.classList.add('is-invalid');
                    } else {
                        errorDiv.textContent = '';
                        this.classList.remove('is-invalid');
                    }
                });

                // Validación de Correo
                correoInput.addEventListener('input', function () {
                    const correoValue = this.value;
                    const errorDiv = this.nextElementSibling;

                    const correoRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                    if (!correoRegex.test(correoValue)) {
                        errorDiv.textContent = 'Formato de correo inválido';
                        this.classList.add('is-invalid');
                    } else {
                        errorDiv.textContent = '';
                        this.classList.remove('is-invalid');
                    }
                });

                // Validación de Teléfono
                telefonoInput.addEventListener('input', function () {
                    const telefonoValue = this.value;
                    const errorDiv = this.nextElementSibling;

                    if (!/^[0-9]{0,9}$/.test(telefonoValue)) {
                        errorDiv.textContent = 'El teléfono debe contener solo números y tener 9 dígitos';
                        this.classList.add('is-invalid');
                    } else {
                        errorDiv.textContent = '';
                        this.classList.remove('is-invalid');
                    }
                });

                // Validación final antes de enviar el formulario
                form.addEventListener('submit', function (e) {
                    let isValid = true;

                    // Verificar DNI
                    if (!/^[0-9]{8}$/.test(dniInput.value)) {
                        dniInput.nextElementSibling.textContent = 'El DNI debe contener 8 dígitos';
                        dniInput.classList.add('is-invalid');
                        isValid = false;
                    }

                    // Verificar Correo
                    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(correoInput.value)) {
                        correoInput.nextElementSibling.textContent = 'Formato de correo inválido';
                        correoInput.classList.add('is-invalid');
                        isValid = false;
                    }

                    // Verificar Teléfono
                    if (!/^[0-9]{9}$/.test(telefonoInput.value)) {
                        telefonoInput.nextElementSibling.textContent = 'El teléfono debe contener 9 dígitos';
                        telefonoInput.classList.add('is-invalid');
                        isValid = false;
                    }

                    if (!isValid) {
                        e.preventDefault();
                    }
                });
            });
        </script>
    </body>
</html>
