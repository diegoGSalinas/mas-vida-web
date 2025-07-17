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
                                        <input type="text" class="form-control" id="dni" name="dni">
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
    </body>
</html>
