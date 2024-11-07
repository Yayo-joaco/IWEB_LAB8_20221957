<%@page import="java.util.ArrayList"%>
<%@page import="com.example.pruebalaboratorio1.beans.pelicula"%>
<%@page import="java.text.NumberFormat"%>
<%@ page import="com.example.pruebalaboratorio1.beans.genero" %>
<%@ page import="com.example.pruebalaboratorio1.beans.streaming" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

    pelicula movie = (pelicula) request.getAttribute("pelicula");
    ArrayList<genero> listaGeneros = (ArrayList<genero>) request.getAttribute("listaGeneros");
    ArrayList<streaming> listaStreaming = (ArrayList<streaming>) request.getAttribute("listaStreaming");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=movie.getTitulo()%></title>
</head>
<body>

<h1>Pelicula Numero <%=movie.getIdPelicula()%></h1>

<table border="1">
    <form action="listaPeliculas" method="POST">
        <input type="hidden" name="idPelicula" value="<%=movie.getIdPelicula()%>">
        <tr>
            <th>Titulo</th>
            <td contenteditable>
                <input name="titulo" value="<%=movie.getTitulo()%>">
            </td>
        </tr>
        <tr>
            <th>Director</th>
            <td contenteditable>
                <input name="director" value="<%=movie.getDirector()%>">
            </td>
        </tr>
        <tr>
            <th>Año Publicacion</th>
            <td contenteditable>
                <input name="anoPublicacion" value="<%=movie.getAnoPublicacion()%>">
            </td>
        </tr>
        <tr>
            <th>Rating</th>
            <td contenteditable>
                <input name="rating" value="<%=movie.getRating()%>">
            </td>
        </tr>
        <tr>
            <th>BoxOffice</th>
            <td contenteditable>
                <input name="boxOffice" value="<%=movie.getBoxOffice()%>">
            </td>
        </tr>
        <tr>
            <th>Genero</th>
            <td>
                <select name="genero">
                    <% for (genero gen : listaGeneros) { %>
                    <option value="<%= gen.getIdGenero() %>" <%= movie.getGenero().getIdGenero() == gen.getIdGenero() ? "selected" : "" %>>
                        <%= gen.getNombre() %>
                    </option>
                    <% } %>
                </select>
            </td>
        </tr>
        <tr>
            <th>Streaming</th>
            <td>
                <select name="streaming">
                    <% for (streaming str : listaStreaming) { %>
                    <option value="<%= str.getIdStreaming() %>" <%= movie.getStreaming() != null && movie.getStreaming().getIdStreaming() == str.getIdStreaming() ? "selected" : "" %>>
                        <%= str.getNombreServicio() %>
                    </option>
                    <% } %>
                </select>
            </td>
        </tr>
        <tr>
            <th>Actores</th>
            <td><a href="listaActores?idPelicula=<%= movie.getIdPelicula() %>">Ver Actores</a></td>
        </tr>
        <input type="hidden" name="action" value="editar">
        <button type="submit">Editar Pelicula</button>
    </form>
</table>

</body>
</html>