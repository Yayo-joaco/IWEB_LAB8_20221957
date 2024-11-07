

<%@page import="java.util.ArrayList"%>
<%@page import="com.example.pruebalaboratorio1.beans.pelicula"%>
<%@page import="com.example.pruebalaboratorio1.beans.genero"%>
<%@page import="com.example.pruebalaboratorio1.beans.streaming"%>
<%@page import="java.text.NumberFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ArrayList<pelicula> listaPeliculas = (ArrayList) request.getAttribute("listaPeliculas");
    ArrayList<genero> listaGeneros = (ArrayList) request.getAttribute("listaGeneros");
    ArrayList<streaming> listaStreaming = (ArrayList) request.getAttribute("listaStreaming");
    Integer generoSeleccionado = (Integer) request.getAttribute("generoSeleccionado");
    Integer streamingSeleccionado = (Integer) request.getAttribute("streamingSeleccionado");
    NumberFormat formatter = NumberFormat.getInstance();
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Películas</title>
</head>
<body>

<h1>Lista de Películas</h1>

<form action="listaPeliculas" method="POST" onsubmit="return validarFiltro()">
    <div class="combobox-container">
        <select name="genero">
            <option value="" <%= generoSeleccionado == null ? "selected" : "" %>>Seleccione Género</option>
            <% for (genero gen : listaGeneros) { %>
            <option value="<%= gen.getIdGenero() %>" <%= generoSeleccionado != null && generoSeleccionado == gen.getIdGenero() ? "selected" : "" %>><%= gen.getNombre() %></option>
            <% } %>
        </select>

        <select name="streaming">
            <option value="" <%= streamingSeleccionado == null ? "selected" : "" %>>Seleccione Streaming</option>
            <% for (streaming stream : listaStreaming) { %>
            <option value="<%= stream.getIdStreaming() %>" <%= streamingSeleccionado != null && streamingSeleccionado == stream.getIdStreaming() ? "selected" : "" %>><%= stream.getNombreServicio() %></option>
            <% } %>
        </select>

        <input type="hidden" name="action" value="filtrar">
        <button type="submit">Filtrar</button>
    </div>
</form>

<form action="listaPeliculas" method="GET">
    <input type="hidden" name="action" value="listar">
    <button type="submit">Limpiar</button>
</form>


<table border="1">
    <tr>

        <th>Titulo</th>
        <th>Director</th>
        <th>Ano Publicacion</th>
        <th>Rating</th>
        <th>BoxOffice</th>
        <th>Genero</th>
        <th>Duracion</th>
        <th>Streaming</th>
        <th>Premio Oscar</th>
        <th>Actores</th>
        <th>Borrar</th>

    </tr>
    <%
        for (pelicula movie : listaPeliculas) {
    %>
    <tr>

        <td><a href="viewPelicula?idPelicula=<%= movie.getIdPelicula() %>"><%=movie.getTitulo()%></a></td>
        <td><%=movie.getDirector()%></td>
        <td><%=movie.getAnoPublicacion()%></td>
        <td><%=movie.getRating()%>/10</td>
        <td>$<%=formatter.format(movie.getBoxOffice())%></td>
        <td><%=movie.getGenero().getNombre()%></td>
        <td><%=movie.getDuracion()%></td>
        <td><%=movie.getStreaming().getNombreServicio()%></td>
        <td><%=movie.isPremioOscar() ? "Sí" : "No" %></td>
        <td><a href="listaActores?idPelicula=<%= movie.getIdPelicula() %>">Ver Actores</a></td>
        <%
            if (movie.isValidadorBorrado()) {
        %>
        <td>
            <% if (movie.isValidadorBorrado()) { %>
            <form action="listaPeliculas" method="POST" style="display:inline;">
                <input type="hidden" name="action" value="borrar">
                <input type="hidden" name="idPelicula" value="<%= movie.getIdPelicula() %>">
                <button type="submit" class="button-link" onclick="return confirm('¿Estás seguro de que deseas borrar esta película?');">Borrar</button>
            </form>
            <% } %>
        </td>
        <%
            }
        %>
    </tr>

    <%
        }
    %>

</table>

<script>
    function validarFiltro() {
        var genero = document.forms[0]["genero"].value;
        var streaming = document.forms[0]["streaming"].value;

        if (genero === "" && streaming === "") {
            alert("Seleccione un Género o Streaming");
            return false;
        }
        return true;
    }
</script>

</body>
</html>