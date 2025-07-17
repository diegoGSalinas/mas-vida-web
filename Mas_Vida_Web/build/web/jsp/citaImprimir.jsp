<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Imprimir Cita - Mas Vida</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Estilos personalizados para impresión -->
        <style>
            @media print {
                .no-print {
                    display: none;
                }
                .print-container {
                    width: 100%;
                    max-width: 800px;
                    margin: 0 auto;
                }
                .print-header {
                    text-align: center;
                    margin-bottom: 30px;
                }
                .print-content {
                    margin: 20px 0;
                }
                .print-row {
                    display: flex;
                    justify-content: space-between;
                    margin: 10px 0;
                }
                .print-label {
                    font-weight: bold;
                    width: 200px;
                }
            }
        </style>
    </head>
    <body class="no-print">
        <div class="container mt-5">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h5 class="mb-0">Imprimir Cita</h5>
                </div>
                <div class="card-body">
                    <div class="print-container">
                        <div class="print-header">
                            <h3>Mas Vida - Cita Médica</h3>
                            <p>Fecha de Impresión: <fmt:formatDate value="${fechaActual}" pattern="dd/MM/yyyy HH:mm" /></p>
                        </div>

                        <div class="print-content">
                            <div class="print-row">
                                <span class="print-label">ID de Cita:</span>
                                <span>${cita.idCita}</span>
                            </div>

                            <div class="print-row">
                                <span class="print-label">Fecha de Solicitud:</span>
                                <span><fmt:formatDate value="${cita.fechaSolicitud}" pattern="dd/MM/yyyy HH:mm" /></span>
                            </div>

                            <div class="print-row">
                                <span class="print-label">Fecha de Cita:</span>
                                <span><fmt:formatDate value="${cita.fechaCita}" pattern="dd/MM/yyyy HH:mm" /></span>
                            </div>

                            <div class="print-row">
                                <span class="print-label">Especialidad:</span>
                                <span>${cita.especialidad.nombre}</span>
                            </div>

                            <div class="print-row">
                                <span class="print-label">Estado:</span>
                                <span>${cita.estado}</span>
                            </div>

                            <div class="print-row">
                                <span class="print-label">Datos del Paciente:</span>
                                <span>${cita.paciente.nombres} ${cita.paciente.apPaterno} ${cita.paciente.apMaterno}</span>
                            </div>

                            <div class="print-row">
                                <span class="print-label">DNI:</span>
                                <span>${cita.paciente.dni}</span>
                            </div>
                        </div>
                    </div>

                    <div class="text-center mt-4">
                        <button onclick="window.print()" class="btn btn-primary me-2">
                            <i class="fas fa-print me-1"></i>Imprimir
                        </button>
                        <a href="${pageContext.request.contextPath}/jsp/citaPorPersona.jsp" class="btn btn-secondary">
                            <i class="fas fa-arrow-left me-1"></i>Volver
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Font Awesome -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/js/all.min.js"></script>
    </body>
</html>
