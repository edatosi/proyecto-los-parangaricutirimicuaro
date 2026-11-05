<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="co.umariana.trabalenguas.web.Html" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registrar trabalengua · Trabalenguario</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<header>
    <h1>🗣️ Trabalenguario</h1>
    <p>Un nuevo reto para la colección</p>
</header>
<main class="narrow">
    <nav class="breadcrumbs" aria-label="Navegación"><a href="<%= request.getContextPath() %>/index.html">Inicio</a> / <a href="<%= request.getContextPath() %>/listar">Trabalenguas</a> / Registrar</nav>
    <section class="panel">
        <h2>Registrar trabalengua</h2>
        <p class="muted">Completa los datos para agregar un nuevo trabalengua.</p>
        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null && !error.isBlank()) { %>
            <p class="alert" role="alert"><%= Html.esc(error) %></p>
        <% } %>
        <form class="form-grid" method="post" action="<%= request.getContextPath() %>/registrar">
            <label for="codigo">Código</label>
            <input id="codigo" name="codigo" type="text" maxlength="40" required value="<%= Html.esc(request.getParameter("codigo")) %>" placeholder="Ej. L004">
            <label for="titulo">Título</label>
            <input id="titulo" name="titulo" type="text" maxlength="120" required value="<%= Html.esc(request.getParameter("titulo")) %>">
            <label for="categoria">Categoría</label>
            <input id="categoria" name="categoria" type="text" maxlength="80" required value="<%= Html.esc(request.getParameter("categoria")) %>" placeholder="Ej. Tradicional">
            <label for="enredo">Nivel de enredo</label>
            <input id="enredo" name="enredo" type="number" min="1" max="10" step="1" required value="<%= Html.esc(request.getParameter("enredo")) %>">
            <label for="tiempo">Tiempo (segundos)</label>
            <input id="tiempo" name="tiempo" type="number" min="0.01" step="any" required value="<%= Html.esc(request.getParameter("tiempo")) %>">
            <label for="descripcion">Descripción</label>
            <textarea id="descripcion" name="descripcion" rows="4" maxlength="500"><%= Html.esc(request.getParameter("descripcion")) %></textarea>
            <label for="archivo">Archivo de imagen</label>
            <input id="archivo" name="archivo" type="text" maxlength="120" required value="<%= Html.esc(request.getParameter("archivo")) %>" placeholder="Ej. mi_trabalengua.png">
            <div class="form-actions">
                <button class="button" type="submit">Guardar trabalengua</button>
                <a class="button button-secondary" href="<%= request.getContextPath() %>/listar">Cancelar</a>
            </div>
        </form>
        <p class="hint">Las imágenes del catálogo incluyen <code>pablito.png</code>, <code>pocas_copas.jpeg</code> y <code>tres_tigres.jpg</code>.</p>
    </section>
</main>
<footer>Proyecto final · Estructuras de Datos I</footer>
</body>
</html>
