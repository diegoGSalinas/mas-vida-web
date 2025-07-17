<%-- 
    Document   : usuarios
    Created on : 16 jul. 2025, 17:44:32
    Author     : ale
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.Usuario" %>
<%@ page import="Modelo.TipoUsuario" %>

<%
    List<Usuario> listaUsuarios = (List<Usuario>) request.getAttribute("usuarios");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <!-- Coloca esto en la cabecera de tu listaUsuarios.jsp -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <title>Lista de Usuarios</title>
    
</head>

<body>
    <div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2 class="mb-0">Usuarios Registrados</h2>
        <!-- Botón Agregar Usuario -->
      <a href="<%= request.getContextPath() %>/ControladorUsuario?accion=nuevo" class="btn btn-success">
    <i class="fas fa-user-plus"></i> Agregar Usuario
</a>

    </div>
   
    <table class="table table-striped table-hover">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Contraseña</th>
                <th>Tipo</th>
                <th>Acciones</th> <!-- Nueva columna para los botones -->
            </tr>
        </thead>
        <tbody>
        <%
            if (listaUsuarios != null) {
                for (Usuario u : listaUsuarios) {
        %>
            <tr>
                <td><%= u.getIdUsuario() %></td>
                <td><%= u.getNombreUsuario() %></td>
                <td><%= "*".repeat(u.getContrasena().length()) %></td>
                <td><%= u.getTipoUsuario().toString() %></td>
                <td>
                    <!-- Botón Editar -->
                    <a href="ControladorUsuario?accion=editar&id=<%= u.getIdUsuario() %>" class="btn btn-warning btn-sm">Editar</a>

                    <!-- Botón Eliminar (puede ser GET o POST con confirmación) -->
                    <a href="#" 
                    class="btn btn-danger btn-sm" 
                    accesskey=""data-bs-toggle="modal" 
                    data-bs-target="#modalEliminar" 
                    data-id="<%= u.getIdUsuario() %>">
                    Eliminar
                     </a>
                   
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="4">No hay usuarios registrados.</td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>
        <div class="mt-4">
   <a href="<%= request.getContextPath() %>/jsp/vistaAdmin.jsp" class="btn btn-secondary">Volver</a>

</div>
   
   <!-- Modal de Confirmación -->
<div class="modal fade" id="modalEliminar" tabindex="-1" aria-labelledby="modalEliminarLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header bg-danger text-white">
        <h5 class="modal-title" id="modalEliminarLabel">Confirmar eliminación</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
      </div>
      <div class="modal-body">
        ¿Estás seguro de que deseas eliminar este usuario?
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
        <a id="btnConfirmarEliminar" href="#" class="btn btn-danger">Eliminar</a>
      </div>
    </div>
  </div>
</div>
   
   <script>
  var modalEliminar = document.getElementById('modalEliminar');
  modalEliminar.addEventListener('show.bs.modal', function (event) {
    var boton = event.relatedTarget;
    var userId = boton.getAttribute('data-id');
    var link = document.getElementById('btnConfirmarEliminar');
    link.href = 'ControladorUsuario?accion=eliminar&id=' + userId;
  });
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

