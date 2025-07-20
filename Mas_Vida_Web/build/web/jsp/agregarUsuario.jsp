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
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h3 class="mb-0">Agregar Nuevo Usuario</h3>
                </div>
                <div class="card-body">
                    <form action="ControladorUsuario" method="post" class="needs-validation" novalidate>
                        <input type="hidden" name="accion" value="guardar">
                        <!-- Mensajes -->
                        <c:if test="${not empty mensaje}">
                            <div class="alert alert-${tipoMensaje}">
                                ${mensaje}
                            </div>
                        </c:if>

                        <!-- Datos Personales -->
                        <h4 class="mb-4">Datos Personales</h4>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="nombres" class="form-label">Nombres:</label>
                                    <input type="text" class="form-control" id="nombres" name="nombres" required>
                                    <div class="invalid-feedback">Este campo es requerido.</div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="apPaterno" class="form-label">Apellido Paterno:</label>
                                    <input type="text" class="form-control" id="apPaterno" name="apPaterno" required>
                                    <div class="invalid-feedback">Este campo es requerido.</div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="apMaterno" class="form-label">Apellido Materno:</label>
                                    <input type="text" class="form-control" id="apMaterno" name="apMaterno">
                                    <div class="invalid-feedback">Este campo es opcional.</div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="dni" class="form-label">DNI:</label>
                                    <input type="text" class="form-control" id="dni" name="dni" pattern="\d{8}" required>
                                    <div class="invalid-feedback">El DNI debe tener exactamente 8 dígitos numéricos.</div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="correo" class="form-label">Correo:</label>
                                    <input type="email" class="form-control" id="correo" name="correo" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" required>
                                    <div class="invalid-feedback">Ingrese un correo electrónico válido.</div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="telefono" class="form-label">Teléfono:</label>
                                    <input type="text" class="form-control" id="telefono" name="telefono" pattern="\d{9}" required>
                                    <div class="invalid-feedback">El teléfono debe tener exactamente 9 dígitos numéricos.</div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="fechaNacimiento" class="form-label">Fecha de Nacimiento:</label>
                                    <input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento" required>
                                    <div class="invalid-feedback">Ingrese una fecha válida.</div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="direccion" class="form-label">Dirección:</label>
                                    <textarea class="form-control" id="direccion" name="direccion" rows="3" required></textarea>
                                    <div class="invalid-feedback">Este campo es requerido.</div>
                                </div>
                            </div>
                        </div>

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

                <!-- Campos adicionales para Doctores y Técnicos -->
                <div id="camposEspeciales" style="display: none;">
                    <div class="mb-3">
                        <label for="turno" class="form-label">Turno</label>
                        <select class="form-select" id="turno" name="turno" required>
                            <option value="">Seleccione</option>
                            <option value="Mañana">Diurno</option>
                            <option value="Tarde">Vesperino</option>
                        </select>
                        <div class="invalid-feedback">Seleccione el turno.</div>
                    </div>

                    <div class="mb-3">
                        <label for="especialidad" class="form-label">Especialidad</label>
                        <select class="form-select" id="especialidad" name="especialidad" required>
                            <option value="">Seleccione</option>
                            <c:forEach var="especialidad" items="${especialidades}">
                                <option value="${especialidad.idEspecialidad}">${especialidad.nombre}</option>
                            </c:forEach>
                        </select>
                        <div class="invalid-feedback">Seleccione la especialidad.</div>
                    </div>
                </div>

                <!-- Botones -->
                <div class="mt-4">
                    <button type="submit" class="btn btn-primary" name="accion" value="guardar">Guardar</button>
                    <a href="${pageContext.request.contextPath}/ControladorUsuario?accion=verUsuarios" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    // Función para mostrar/ocultar campos según el tipo de usuario
    function mostrarCamposAdicionales() {
        const tipoUsuario = document.querySelector('select[name="tipoUsuario"]').value;
        const camposEspeciales = document.getElementById('camposEspeciales');
        const prioridadesDoctorTecnico = ['DOCTOR', 'TECNICO'];
        
        camposEspeciales.style.display = prioridadesDoctorTecnico.includes(tipoUsuario) ? 'block' : 'none';
    }

    // Event listener para el cambio en el select de tipo de usuario
    document.addEventListener('DOMContentLoaded', function() {
        const tipoUsuarioSelect = document.querySelector('select[name="tipoUsuario"]');
        if (tipoUsuarioSelect) {
            tipoUsuarioSelect.addEventListener('change', mostrarCamposAdicionales);
            // Llamar a la función inicialmente para mostrar/ocultar campos
            mostrarCamposAdicionales();
        }
    });

    // Validación Bootstrap
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            const forms = document.getElementsByClassName('needs-validation');
            Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    const idUsuario = document.getElementById('idUsuario').value.trim();
                    const dni = document.getElementById('dni').value.trim();
                    const telefono = document.getElementById('telefono').value.trim();
                    
                    if (!idUsuario) {
                        alert('Debe generar o ingresar un ID de usuario válido');
                        event.preventDefault();
                        return;
                    }

                    // Validación adicional para DNI y teléfono
                    if (dni.length !== 8) {
                        alert('El DNI debe tener exactamente 8 dígitos');
                        event.preventDefault();
                        return;
                    }

                    if (telefono.length !== 9) {
                        alert('El teléfono debe tener exactamente 9 dígitos');
                        event.preventDefault();
                        return;
                    }

                    // Validación adicional para doctores y técnicos
                    const camposEspeciales = document.getElementById('camposEspeciales');
                    if (camposEspeciales.style.display === 'block') {
                        const turno = document.getElementById('turno').value;
                        const especialidad = document.getElementById('especialidad').value;
                        
                        if (!turno || !especialidad) {
                            alert('Debe seleccionar el turno y la especialidad para doctores y técnicos');
                            event.preventDefault();
                            return;
                        }
                    }

                    if (!form.checkValidity()) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        });
    })();
</script>

<script>
    // Validación Bootstrap
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            const forms = document.getElementsByClassName('needs-validation');
            Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    const idUsuario = document.getElementById('idUsuario').value.trim();
                    const dni = document.getElementById('dni').value.trim();
                    const telefono = document.getElementById('telefono').value.trim();
                    
                    if (!idUsuario) {
                        alert('Debe generar o ingresar un ID de usuario válido');
                        event.preventDefault();
                        return;
                    }

                    // Validación adicional para DNI y teléfono
                    if (dni.length !== 8) {
                        alert('El DNI debe tener exactamente 8 dígitos');
                        event.preventDefault();
                        return;
                    }

                    if (telefono.length !== 9) {
                        alert('El teléfono debe tener exactamente 9 dígitos');
                        event.preventDefault();
                        return;
                    }

                    if (!form.checkValidity()) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();

    // Validación personalizada para DNI
    const dniInput = document.getElementById('dni');
    dniInput.addEventListener('input', function() {
        const dni = this.value;
        if (dni.length > 8) {
            this.value = dni.slice(0, 8);
        }
        // Verificar que solo sean números
        if (!/^[0-9]*$/.test(dni)) {
            this.value = dni.replace(/[^0-9]/g, '');
        }
    });

    // Validación personalizada para teléfono
    const telefonoInput = document.getElementById('telefono');
    telefonoInput.addEventListener('input', function() {
        const telefono = this.value;
        if (telefono.length > 9) {
            this.value = telefono.slice(0, 9);
        }
        // Verificar que solo sean números
        if (!/^[0-9]*$/.test(telefono)) {
            this.value = telefono.replace(/[^0-9]/g, '');
        }
    });

    // GENERACION DE ID POR TIPO
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
</body>
</html>
    