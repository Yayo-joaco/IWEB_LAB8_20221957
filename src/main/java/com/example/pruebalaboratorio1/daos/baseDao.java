package com.example.pruebalaboratorio1.daos;

import com.example.pruebalaboratorio1.beans.pelicula;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class baseDao {

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima", "root", "root");
    }


    public boolean validarBorrado(pelicula movie) {
        String duracionString = movie.getDuracion();
        String numeros = duracionString.replaceAll("\\D+", "");
        int duracionInt = Integer.parseInt(numeros);
        return duracionInt > 120 && !movie.isPremioOscar();
    }
}

