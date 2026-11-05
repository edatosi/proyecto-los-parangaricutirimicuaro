<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.List,java.util.Map,co.umariana.trabalenguas.modelo.Trabalengua,co.umariana.trabalenguas.web.Html" %>
<%
    List<Trabalengua> trabalenguas = (List<Trabalengua>) request.getAttribute("trabalenguas");
    Map<String, Double> promedios = (Map<String, Double>) request.getAttribute("promedios");
    String orden = "ranking".equals(request.getParameter("orden")) ? "ranking" : "normal";
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Trabalenguas · Trabalenguario</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/paginas.css">
</head>
<body class="inner-page collection-page">
<header class="site-header">
    <div class="site-header-inner">
        <a class="site-brand" href="<%= request.getContextPath() %>/index.html"><span class="site-brand-mark">T!</span>trabalenguario<span class="brand-dot">.</span></a>
        <span class="site-header-label">Una colección de retos para la lengua</span>
    </div>
</header>
<nav class="site-nav" aria-label="Navegación principal">
    <div class="site-nav-inner">
        <a href="<%= request.getContextPath() %>/index.html">← Inicio</a>
        <a href="<%= request.getContextPath() %>/catalogo">Catálogo</a>
        <a class="site-nav-cta" href="<%= request.getContextPath() %>/registrar">Crear trabalengua ↗</a>
    </div>
</nav>
<main class="page-container">
    <nav class="breadcrumbs" aria-label="Navegación"><a href="<%= request.getContextPath() %>/index.html">Inicio</a><span>/</span> Mi colección</nav>
    <div class="page-heading">
        <div class="page-intro">
            <span class="page-kicker">TUS RETOS, A TU MANERA</span>
            <h1>Mi colección</h1>
            <p>Organiza tus trabalenguas y descubre cuál pone a prueba tu lengua.</p>
        </div>
        <a class="button collection-add" href="<%= request.getContextPath() %>/registrar"><span aria-hidden="true">＋</span> Registrar</a>
    </div>
    <div class="collection-toolbar">
    <div class="tabs" aria-label="Orden de la lista">
        <a class="<%= "normal".equals(orden) ? "selected" : "" %>" href="<%= request.getContextPath() %>/listar">Orden de registro</a>
        <a class="<%= "ranking".equals(orden) ? "selected" : "" %>" href="<%= request.getContextPath() %>/listar?orden=ranking">Ranking por enredo</a>
    </div>
    <span class="collection-total"><strong><%= trabalenguas == null ? 0 : trabalenguas.size() %></strong> <%= trabalenguas != null && trabalenguas.size() == 1 ? "trabalengua" : "trabalenguas" %></span>
    </div>
    <section class="panel average-panel">
        <div class="panel-title"><span class="panel-icon" aria-hidden="true">✳</span><div><h2>Enredo por categoría</h2><p>Promedio de dificultad en tu colección</p></div></div>
        <% if (promedios == null || promedios.isEmpty()) { %>
            <p class="muted">Aún no hay datos para calcular promedios. Agrega un trabalengua para comenzar.</p>
        <% } else { %>
            <div class="category-list average-list">
                <% for (Map.Entry<String, Double> promedio : promedios.entrySet()) { %>
                    <span class="category-chip"><span><%= Html.esc(promedio.getKey()) %></span><strong><%= String.format(java.util.Locale.ROOT, "%.1f", promedio.getValue()) %><small>/10</small></strong></span>
                <% } %>
            </div>
        <% } %>
    </section>
    <section class="panel collection-panel">
        <div class="section-heading"><div><h2><%= "ranking".equals(orden) ? "Ranking de enredo" : "Todos los trabalenguas" %></h2><p><%= "ranking".equals(orden) ? "Los retos más difíciles aparecen primero." : "Tus retos guardados, listos para practicar." %></p></div></div>
        <% if (trabalenguas == null || trabalenguas.isEmpty()) { %>
            <div class="collection-empty"><span aria-hidden="true">🗣️</span><h3>Tu colección empieza aquí</h3><p>Agrega un reto del catálogo o registra uno propio para practicar.</p><div><a class="button" href="<%= request.getContextPath() %>/catalogo">Explorar catálogo</a><a class="button button-secondary" href="<%= request.getContextPath() %>/registrar">Crear trabalengua</a></div></div>
        <% } else { %>
            <div class="table-wrap">
                <table class="collection-table">
                    <thead><tr><th>Trabalengua</th><th>Categoría</th><th>Dificultad</th><th>Tiempo</th><th>Acciones</th></tr></thead>
                    <tbody>
                    <% for (Trabalengua trabalengua : trabalenguas) { %>
                        <tr>
                            <td class="collection-name"><span class="tongue-icon" aria-hidden="true">〰</span><span><strong><%= Html.esc(trabalengua.getTitulo()) %></strong><small class="row-detail"><%= Html.esc(trabalengua.getCodigo()) %> · <%= Html.esc(trabalengua.getDescripcion()) %></small></span></td>
                            <td><span class="table-category"><%= Html.esc(trabalengua.getCategoria()) %></span></td>
                            <td><div class="difficulty"><div class="difficulty-track"><span style="width:<%= trabalengua.getEnredo() * 10 %>%"></span></div><strong><%= trabalengua.getEnredo() %><small>/10</small></strong></div></td>
                            <td class="table-time"><%= trabalengua.getTiempoSeg() %><small> s</small></td>
                            <td>
                                <form method="post" action="<%= request.getContextPath() %>/eliminar" onsubmit="return confirm('¿Eliminar este trabalengua?');">
                                    <input type="hidden" name="codigo" value="<%= Html.esc(trabalengua.getCodigo()) %>">
                                    <button class="delete-button" type="submit" aria-label="Eliminar <%= Html.esc(trabalengua.getTitulo()) %>" title="Eliminar">×</button>
                                </form>
                            </td>
                        </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        <% } %>
    </section>
</main>
<footer class="site-footer">Un pequeño tropiezo para la lengua, una gran sonrisa para todos. · Proyecto final</footer>
</body>
</html>
