<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="co.umariana.trabalenguas.modelo.Trabalengua,co.umariana.trabalenguas.web.Html" %>
<%
    Trabalengua trabalengua = (Trabalengua) request.getAttribute("trabalengua");
    int total = request.getAttribute("total") == null ? 0 : (Integer) request.getAttribute("total");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Visor · Trabalenguario</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<header>
    <h1>🗣️ Trabalenguario</h1>
    <p>Lee en voz alta. ¡Sin equivocarte!</p>
</header>
<main class="narrow">
    <nav class="breadcrumbs" aria-label="Navegación"><a href="<%= request.getContextPath() %>/index.html">Inicio</a> / <a href="<%= request.getContextPath() %>/listar">Trabalenguas</a> / Visor</nav>
    <section class="panel viewer">
        <div class="section-heading"><h2>Visor de trabalenguas</h2><span class="count"><%= total %> en total</span></div>
        <% if (trabalengua == null) { %>
            <div class="empty-state">
                <p>No hay trabalenguas para mostrar todavía.</p>
                <a class="button" href="<%= request.getContextPath() %>/catalogo">Explorar el catálogo</a>
            </div>
        <% } else { %>
            <figure class="image-frame">
                <img src="<%= request.getContextPath() %>/media/<%= Html.esc(trabalengua.getArchivo()) %>" alt="<%= Html.esc(trabalengua.getTitulo()) %>">
            </figure>
            <p class="eyebrow"><%= Html.esc(trabalengua.getCategoria()) %> · Enredo <%= trabalengua.getEnredo() %>/10</p>
            <h2 class="viewer-title"><%= Html.esc(trabalengua.getTitulo()) %></h2>
            <p><%= Html.esc(trabalengua.getDescripcion()) %></p>
            <p class="muted">Código: <%= Html.esc(trabalengua.getCodigo()) %> · Tiempo: <%= trabalengua.getTiempoSeg() %> s</p>
        <% } %>
        <nav class="viewer-controls" aria-label="Controles del visor">
            <a class="button button-secondary" href="<%= request.getContextPath() %>/visor?accion=anterior" <%= total == 0 ? "aria-disabled=\"true\"" : "" %>>← Anterior</a>
            <a class="button" href="<%= request.getContextPath() %>/visor?accion=siguiente" <%= total == 0 ? "aria-disabled=\"true\"" : "" %>>Siguiente →</a>
        </nav>
    </section>
</main>
<footer>Proyecto final · Estructuras de Datos I</footer>
</body>
</html>
