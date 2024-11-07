package com.example.pruebalaboratorio1.daos;

import com.example.pruebalaboratorio1.beans.genero;
import com.example.pruebalaboratorio1.beans.pelicula;
import com.example.pruebalaboratorio1.beans.streaming;

import java.sql.*;
import java.util.ArrayList;

public class peliculaDao extends baseDao{

    public ArrayList<pelicula> listarPeliculas() {

        ArrayList<pelicula> listaPeliculas = new ArrayList<>();

        try(Connection conn = getConnection()){
            Statement stmt = conn.createStatement();

            String sql = "SELECT A.*, B.NOMBRE, C.NOMBRESERVICIO FROM  \n" +
                    "(SELECT * FROM PELICULA ) AS A \n" +
                    "INNER JOIN \n" +
                    "(SELECT * FROM GENERO) AS B\n" +
                    "ON A.IDGENERO = B.IDGENERO\n" +
                    "INNER JOIN \n" +
                    "(SELECT * FROM STREAMING) AS C\n" +
                    "ON A.IDSTREAMING = C.IDSTREAMING\n" +
                    "ORDER BY RATING DESC , BOXOFFICE DESC";


            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                pelicula movie = new pelicula();
                genero genero = new genero();
                streaming streaming = new streaming();
                int idPelicula = rs.getInt(1);
                movie.setIdPelicula(idPelicula);
                String titulo = rs.getString("titulo");
                movie.setTitulo(titulo);
                String director = rs.getString("director");
                movie.setDirector(director);
                int anoPublicacion = rs.getInt("anoPublicacion");
                movie.setAnoPublicacion(anoPublicacion);
                double rating = rs.getDouble("rating");
                movie.setRating(rating);
                double boxOffice = rs.getDouble("boxOffice");
                movie.setBoxOffice(boxOffice);
                int idGenero = rs.getInt("idGenero");
                String nombregenero = rs.getString("nombre");
                genero.setIdGenero(idGenero);
                genero.setNombre(nombregenero);
                movie.setGenero(genero);
                String duracion = rs.getString("duracion");
                movie.setDuracion(duracion);
                String nombreStreaming = rs.getString("nombreServicio");
                streaming.setNombreServicio(nombreStreaming);
                movie.setStreaming(streaming);
                boolean premioOscar = rs.getBoolean("premioOscar");
                movie.setPremioOscar(premioOscar);
                boolean validador= validarBorrado(movie);
                movie.setValidadorBorrado(validador);

                listaPeliculas.add(movie);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaPeliculas;
    }

    public ArrayList<pelicula> listarPeliculasFiltradas(int idGenero, int idStreaming) {

        ArrayList<pelicula> listaPeliculasFiltradas= new ArrayList<>();

        String sql = "SELECT A.*, B.NOMBRE, C.NOMBRESERVICIO " +
                "FROM PELICULA A " +
                "INNER JOIN GENERO B ON A.IDGENERO = B.IDGENERO " +
                "INNER JOIN STREAMING C ON A.IDSTREAMING = C.IDSTREAMING " +
                "WHERE 1=1 ";

        if (idGenero != 0) {
            sql += "AND A.IDGENERO = " + idGenero + " ";
        }
        if (idStreaming != 0) {
            sql += "AND A.IDSTREAMING = " + idStreaming + " ";
        }

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                pelicula movie = new pelicula();
                genero gen = new genero();
                streaming stream = new streaming();

                movie.setIdPelicula(rs.getInt("IDPELICULA"));
                movie.setTitulo(rs.getString("TITULO"));
                movie.setDirector(rs.getString("DIRECTOR"));
                movie.setAnoPublicacion(rs.getInt("ANOPUBLICACION"));
                movie.setRating(rs.getDouble("RATING"));
                movie.setBoxOffice(rs.getDouble("BOXOFFICE"));
                gen.setIdGenero(rs.getInt("IDGENERO"));
                gen.setNombre(rs.getString("NOMBRE"));
                movie.setGenero(gen);
                movie.setDuracion(rs.getString("DURACION"));
                stream.setNombreServicio(rs.getString("NOMBRESERVICIO"));
                movie.setStreaming(stream);
                movie.setPremioOscar(rs.getBoolean("PREMIOOSCAR"));
                movie.setValidadorBorrado(validarBorrado(movie));

                listaPeliculasFiltradas.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return listaPeliculasFiltradas;
    }

    public void editarPelicula(int idPelicula, String titulo, String director, int anoPublicacion, double rating, double boxOffice, int idGenero, int idStreaming) {
        String sql = "UPDATE pelicula SET titulo = ?, director = ?, anoPublicacion = ?, rating = ?, boxOffice = ?, idGenero = ?, idStreaming = ? WHERE idPelicula = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, titulo);
            stmt.setString(2, director);
            stmt.setInt(3, anoPublicacion);
            stmt.setDouble(4, rating);
            stmt.setDouble(5, boxOffice);
            stmt.setInt(6, idGenero);
            stmt.setInt(7, idStreaming);
            stmt.setInt(8, idPelicula);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void borrarPelicula(int idPelicula) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            // Eliminar de la tabla protagonistas
            String sqlProtagonistas = "DELETE FROM PROTAGONISTAS WHERE IDPELICULA = ?";
            try (PreparedStatement pstmtProtagonistas = conn.prepareStatement(sqlProtagonistas)) {
                pstmtProtagonistas.setInt(1, idPelicula);
                pstmtProtagonistas.executeUpdate();
            }

            // Eliminar de la tabla peliculas
            String sqlPelicula = "DELETE FROM PELICULA WHERE IDPELICULA = ?";
            try (PreparedStatement pstmtPelicula = conn.prepareStatement(sqlPelicula)) {
                pstmtPelicula.setInt(1, idPelicula);
                pstmtPelicula.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
