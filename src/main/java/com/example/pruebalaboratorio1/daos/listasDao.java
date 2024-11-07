package com.example.pruebalaboratorio1.daos;

import com.example.pruebalaboratorio1.beans.genero;
import com.example.pruebalaboratorio1.beans.streaming;
import com.example.pruebalaboratorio1.beans.pelicula;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class listasDao extends baseDao{

    public ArrayList<genero> listarGeneros() {

        ArrayList<genero> listaGeneros = new ArrayList<>();

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String sql = "SELECT * FROM GENERO";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                genero gen = new genero();
                gen.setIdGenero(rs.getInt("IDGENERO"));
                gen.setNombre(rs.getString("NOMBRE"));
                listaGeneros.add(gen);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaGeneros;
    }

    public ArrayList<streaming> listarStreaming() {

        ArrayList<streaming> listaStreaming = new ArrayList<>();

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String sql = "SELECT * FROM STREAMING";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                streaming stream = new streaming();
                stream.setIdStreaming(rs.getInt("IDSTREAMING"));
                stream.setNombreServicio(rs.getString("NOMBRESERVICIO"));
                listaStreaming.add(stream);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaStreaming;
    }
}
