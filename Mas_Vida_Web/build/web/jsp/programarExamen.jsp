<%-- 
    Document   : programarExamen
    Created on : 19 jul. 2025, 23:15:52
    Author     : roro
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Programar Examen Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body class="bg-light">

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card shadow">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0"><i class="fas fa-calendar-plus me-2"></i>Programar Examen Médico</h4>
                </div>
                <div class="card-body">

                    <form action="${pageContext.request.contextPath}/ControladorExamen" method="post">
                        <input type="hidden" name="accion" value="registrar">

                        <div class="mb-3">
                            <label for="id_examen" class="form-label"><i class="fas fa-id-badge me-1"></i>ID Examen</label>
                            <input type="text" class="form-control" name="id_examen" id="id_examen" required>
                        </div>

                        <div class="mb-3">
                            <label for="fecha" class="form-label"><i class="fas fa-calendar-day me-1"></i>Fecha</label>
                            <input type="datetime-local" class="form-control" name="fecha" id="fecha" required> 
                        </div>

                        <div class="mb-3">
                            <label for="resultados" class="form-label"><i class="fas fa-file-medical-alt me-1"></i>Resultados</label>
                            <textarea class="form-control" name="resultados" id="resultados" rows="3" required></textarea>
                        </div>

                        <div class="mb-3">
                            <label for="id_tecnico" class="form-label"><i class="fas fa-user-nurse me-1"></i>ID Técnico</label>
                            <input type="text" class="form-control" name="id_tecnico" id="id_tecnico" required>
                        </div>

                        <div class="mb-3">
                            <label for="id_cita" class="form-label"><i class="fas fa-notes-medical me-1"></i>ID Cita</label>
                            <input type="text" class="form-control" name="id_cita" id="id_cita" required>
                        </div>

                        <div class="d-flex justify-content-between">
                            <button type="submit" class="btn btn-success">
                                <i class="fas fa-save me-1"></i>Guardar
                            </button>
                            <a href="${pageContext.request.contextPath}/ControladorExamen" class="btn btn-secondary">
                            <i class="fas fa-arrow-left me-1"></i> Cancelar
                            </a>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
