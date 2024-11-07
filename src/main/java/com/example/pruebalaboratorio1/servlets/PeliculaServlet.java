package com.example.pruebalaboratorio1.servlets;

import com.example.pruebalaboratorio1.beans.genero;
import com.example.pruebalaboratorio1.beans.pelicula;
import com.example.pruebalaboratorio1.beans.streaming;
import com.example.pruebalaboratorio1.daos.listasDao;
import com.example.pruebalaboratorio1.daos.peliculaDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "pelicula-servlet", value = "/listaPeliculas")
public class PeliculaServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String action = request.getParameter("action");
        peliculaDao peliculaDao = new peliculaDao();
        listasDao listaDao = new listasDao();
        //ArrayList<genero> listaGeneros = listaDao.listarGeneros();
        //ArrayList<streaming> listaStreaming = listaDao.listarStraming();

        switch (action) {
            case "listar":
                ArrayList<pelicula> listaPeliculas = peliculaDao.listarPeliculas();
                ArrayList<genero> listaGeneros = listaDao.listarGeneros();
                ArrayList<streaming> listaStreaming = listaDao.listarStreaming();

                request.setAttribute("listaPeliculas", listaPeliculas);
                request.setAttribute("listaGeneros", listaGeneros);
                request.setAttribute("listaStreaming", listaStreaming);

                RequestDispatcher view = request.getRequestDispatcher("listaPeliculas.jsp");
                view.forward(request,response);
                break;

            case "borrar":

                response.sendRedirect(request.getContextPath()+"/listaPeliculas?action=listar");
                break;

        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String action = request.getParameter("action");
        peliculaDao peliculaDao = new peliculaDao();
        int idPelicula;
        listasDao listaDao = new listasDao();
        //ArrayList<genero> listaGeneros = listaDao.listarGeneros();
        //ArrayList<streaming> listaStreaming = listaDao.listarStraming();

        switch (action) {

            case "filtrar":
                String idGeneroStr = request.getParameter("genero");
                String idStreamingStr = request.getParameter("streaming");
                int idGenero = 0;
                int idStreaming = 0;

                if (idGeneroStr != null && !idGeneroStr.trim().isEmpty()) {
                    idGenero = Integer.parseInt(idGeneroStr);
                }

                if (idStreamingStr != null && !idStreamingStr.trim().isEmpty()) {
                    idStreaming = Integer.parseInt(idStreamingStr);
                }


                ArrayList<pelicula> listaPeliculasFiltradas = peliculaDao.listarPeliculasFiltradas(idGenero, idStreaming);
                ArrayList<genero> listaGeneros = listaDao.listarGeneros();
                ArrayList<streaming> listaStreaming = listaDao.listarStreaming();

                request.setAttribute("listaPeliculas", listaPeliculasFiltradas);
                request.setAttribute("listaGeneros", listaGeneros);
                request.setAttribute("listaStreaming", listaStreaming);
                request.setAttribute("generoSeleccionado", idGenero);
                request.setAttribute("streamingSeleccionado", idStreaming);

                RequestDispatcher viewFiltro = request.getRequestDispatcher("listaPeliculas.jsp");
                viewFiltro.forward(request,response);
                break;

            case "editar":


                idPelicula = Integer.parseInt(request.getParameter("idPelicula"));
                String titulo = request.getParameter("titulo");
                String director = request.getParameter("director");
                int anoPublicacion = Integer.parseInt(request.getParameter("anoPublicacion"));
                double rating = Double.parseDouble(request.getParameter("rating"));
                double boxOffice = Double.parseDouble(request.getParameter("boxOffice"));
                idGenero = Integer.parseInt(request.getParameter("genero"));
                idStreaming = Integer.parseInt(request.getParameter("streaming"));

                peliculaDao.editarPelicula(idPelicula, titulo, director, anoPublicacion, rating, boxOffice, idGenero, idStreaming);
                response.sendRedirect(request.getContextPath()+"/listaPeliculas?action=listar");
                break;

            case "borrar":

                idPelicula = Integer.parseInt(request.getParameter("idPelicula"));
                peliculaDao.borrarPelicula(idPelicula);
                response.sendRedirect(request.getContextPath() + "/listaPeliculas?action=listar&message=peliculaBorrada");
                break;


        }
    }




}
