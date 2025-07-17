<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Buscar Persona - Mas Vida</title>
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
                            <h5 class="mb-0">Buscar Persona</h5>
                        </div>
                        <form action="${pageContext.request.contextPath}/controladorBuscarPersona" method="GET">
                            <input type="hidden" name="accion" value="buscar">
                            <div class="card-body">
                                <!-- Mensajes -->
                                <c:if test="${not empty mensaje}">
                                    <div class="alert alert-info">
                                        <i class="fas fa-info-circle me-2"></i>${mensaje}
                                    </div>
                                </c:if>
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger">
                                        <i class="fas fa-exclamation-circle me-2"></i>${error}
                                    </div>
                                </c:if>

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="dni" class="form-label">DNI</label>
                                        <input type="text" class="form-control" id="dni" name="dni" maxlength="8">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="apPaterno" class="form-label">Apellido Paterno</label>
                                        <input type="text" class="form-control" id="apPaterno" name="apPaterno">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="apMaterno" class="form-label">Apellido Materno</label>
                                        <input type="text" class="form-control" id="apMaterno" name="apMaterno">
                                    </div>
                                </div>

                                <!-- Resultados de búsqueda -->
                                <c:if test="${not empty personas}">
                                    <div class="card mb-4">
                                        <div class="card-header bg-light">
                                            <h5 class="mb-0">Resultados de Búsqueda</h5>
                                        </div>
                                        <c:forEach var="persona" items="${personas}">
                                            <div class="card-body border-bottom mb-3">
                                                <div class="row">
                                                    <div class="col-md-6 mb-3">
                                                        <label class="form-label">DNI</label>
                                                        <input type="text" class="form-control" value="${persona.dni}" readonly>
                                                    </div>
                                                    <div class="col-md-6 mb-3">
                                                        <label class="form-label">Nombres</label>
                                                        <input type="text" class="form-control" value="${persona.nombres}" readonly>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-6 mb-3">
                                                        <label class="form-label">Apellido Paterno</label>
                                                        <input type="text" class="form-control" value="${persona.apPaterno}" readonly>
                                                    </div>
                                                    <div class="col-md-6 mb-3">
                                                        <label class="form-label">Apellido Materno</label>
                                                        <input type="text" class="form-control" value="${persona.apMaterno}" readonly>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-6 mb-3">
                                                        <label class="form-label">Correo</label>
                                                        <input type="email" class="form-control" value="${persona.correo}" readonly>
                                                    </div>
                                                    <div class="col-md-6 mb-3">
                                                        <label class="form-label">Teléfono</label>
                                                        <input type="tel" class="form-control" value="${persona.telefono}" readonly>
                                                    </div>
                                                </div>
                                                <div class="card-footer text-end">
                                                    <a href="${pageContext.request.contextPath}/ControladorCitaMedica?dni=${persona.dni}" class="btn btn-success">
                                                        <i class="fas fa-calendar-plus me-2"></i>Programar Nueva Cita
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/ControladorCitaPorPersona?dni=${persona.dni}" class="btn btn-primary">
                                                        <i class="fas fa-search me-2"></i>Ver Citas Médicas
                                                    </a>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </c:if>
                            </div>
                            <div class="card-footer text-end">
                                <a href="${pageContext.request.contextPath}/ControladorCitaMedica?accion=inicio" class="btn btn-secondary">Cancelar</a>
                                <button type="submit" class="btn btn-primary">Buscar</button>
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
            document.addEventListener('DOMContentLoaded', function() {
                const dniInput = document.getElementById('dni');
                const apPaternoInput = document.getElementById('apPaterno');
                const apMaternoInput = document.getElementById('apMaterno');
                
                // Mensajes de error
                const dniError = dniInput.parentElement.appendChild(document.createElement('div'));
                dniError.className = 'error';
                
                const apellidosError = apPaternoInput.parentElement.appendChild(document.createElement('div'));
                apellidosError.className = 'error';
                
                // Validación de DNI
                dniInput.addEventListener('input', function() {
                    const dniValue = this.value;
                    
                    if (!/^[0-9]{0,8}$/.test(dniValue)) {
                        dniError.textContent = 'El DNI debe contener solo números y tener 8 dígitos';
                        this.classList.add('is-invalid');
                    } else {
                        dniError.textContent = '';
                        this.classList.remove('is-invalid');
                    }
                });
                
                // Validación de Apellidos
                apPaternoInput.addEventListener('input', function() {
                    if (apPaternoInput.value && apMaternoInput.value) {
                        apellidosError.textContent = '';
                        apPaternoInput.classList.remove('is-invalid');
                        apMaternoInput.classList.remove('is-invalid');
                    }
                });
                
                apMaternoInput.addEventListener('input', function() {
                    if (apPaternoInput.value && apMaternoInput.value) {
                        apellidosError.textContent = '';
                        apPaternoInput.classList.remove('is-invalid');
                        apMaternoInput.classList.remove('is-invalid');
                    }
                });
                
                // Validación final antes de enviar el formulario
                document.querySelector('form').addEventListener('submit', function(e) {
                    const dniValue = dniInput.value;
                    const apPaternoValue = apPaternoInput.value;
                    const apMaternoValue = apMaternoInput.value;
                    
                    // Validar DNI
                    if (dniValue && !/^[0-9]{8}$/.test(dniValue)) {
                        dniError.textContent = 'El DNI debe contener 8 dígitos';
                        dniInput.classList.add('is-invalid');
                        e.preventDefault();
                        return;
                    }
                    
                    // Validar Apellidos
                    if (!dniValue && (!apPaternoValue || !apMaternoValue)) {
                        apellidosError.textContent = 'Debes ingresar ambos apellidos';
                        apPaternoInput.classList.add('is-invalid');
                        apMaternoInput.classList.add('is-invalid');
                        e.preventDefault();
                        return;
                    }
                    
                    // Si se ingresó DNI, los apellidos son opcionales
                    if (dniValue) {
                        apellidosError.textContent = '';
                        apPaternoInput.classList.remove('is-invalid');
                        apMaternoInput.classList.remove('is-invalid');
                    }
                    
                    // Si se ingresaron apellidos, el DNI es opcional
                    if (apPaternoValue && apMaternoValue) {
                        dniError.textContent = '';
                        dniInput.classList.remove('is-invalid');
                    }
                });
            });
        </script>
    </body>
</html>
