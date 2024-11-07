package com.example.pruebalaboratorio1.daos;

import com.example.pruebalaboratorio1.beans.genero;
import com.example.pruebalaboratorio1.beans.pelicula;
import com.example.pruebalaboratorio1.beans.streaming;

import java.sql.*;

public class detallesDao {

    public pelicula obtenerPelicula(int idPelicula) {

        pelicula movie = new pelicula();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";
        String username = "root";
        String password = "root";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT P.*, G.IDGENERO, G.NOMBRE AS GENERO_NOMBRE, S.IDSTREAMING, S.NOMBRESERVICIO AS STREAMING_NOMBRE " +
                    "FROM PELICULA P " +
                    "INNER JOIN GENERO G ON P.IDGENERO = G.IDGENERO " +
                    "LEFT JOIN STREAMING S ON P.IDSTREAMING = S.IDSTREAMING " +
                    "WHERE P.IDPELICULA = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idPelicula);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        movie.setIdPelicula(rs.getInt("IDPELICULA"));
                        movie.setTitulo(rs.getString("titulo"));
                        movie.setDirector(rs.getString("director"));
                        movie.setAnoPublicacion(rs.getInt("anoPublicacion"));
                        movie.setRating(rs.getDouble("rating"));
                        movie.setBoxOffice(rs.getDouble("boxOffice"));

                        genero generoPelicula = new genero();
                        generoPelicula.setIdGenero(rs.getInt("IDGENERO"));
                        generoPelicula.setNombre(rs.getString("GENERO_NOMBRE"));
                        movie.setGenero(generoPelicula);

                        int idStreaming = rs.getInt("IDSTREAMING");
                        if (idStreaming != 0) {
                            streaming streamingPelicula = new streaming();
                            streamingPelicula.setIdStreaming(idStreaming);
                            streamingPelicula.setNombreServicio(rs.getString("STREAMING_NOMBRE"));
                            movie.setStreaming(streamingPelicula);
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return movie;
    }
}
