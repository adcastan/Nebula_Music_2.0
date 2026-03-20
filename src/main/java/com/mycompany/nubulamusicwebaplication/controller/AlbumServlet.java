/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.nubulamusicwebaplication.controller;

import com.mycompany.nubulamusicwebaplication.model.Album;
import com.mycompany.nubulamusicwebaplication.model.Usuario;
import com.mycompany.nubulamusicwebaplication.service.AlbumService;
import com.mycompany.nubulamusicwebaplication.service.IAbumService;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.rmi.ServerException;
import java.util.List;

/**
 *
 * @author Adrián
 */
@WebServlet(name = "AlbumServlet", urlPatterns = {"/albums"})
public class AlbumServlet extends HttpServlet {

    private final IAbumService albumService = new AlbumService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("accion");

        HttpSession session = request.getSession(false);

        Usuario usuario = (Usuario) session.getAttribute("Usuario");

        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "new":
                    request.getRequestDispatcher("/views/admin/album-form.jsp").forward(request, response);
                    break;
                case "edit":
                    Long id = Long.parseLong(request.getParameter("id"));

                    Album album = albumService.obtenerAlbum(id, usuario.getId());

                    request.setAttribute("album", album);
                    request.getRequestDispatcher("/views/admin/album-form.jsp").forward(request, response);
                    break;
                case "delete":
                    Long deleteId = Long.parseLong(request.getParameter("id"));

                    albumService.eliminarAlbum(deleteId, usuario.getId());

                    response.sendRedirect("/albums");

                default:
                    List<Album> albums = albumService.obtenerAlbumsUsuario(usuario.getId());

                    request.setAttribute("albums", albums);

                    request.getRequestDispatcher("/views/admin/mis-albums.jsp").forward(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(e);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("accion");

        HttpSession session = request.getSession(false);

        Usuario usuario = (Usuario) session.getAttribute("Usuario");

        String idParam = request.getParameter("id");
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");

        Part filePart = request.getPart("imagen");

        String filename = filePart.getSubmittedFileName();

        if (!filename.endsWith(".png")) {
            throw new ServerException("Solo se permiten imagenes en formato .PNG");
        }

        String newFileName = System.currentTimeMillis() + "_ " + filename;

        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String filePath = uploadPath + File.separator + newFileName;

        filePart.write(filePath);

        String imageUrl = "uploads/" + newFileName;

        Album album = new Album();

        album.setTitulo(titulo);
        album.setDescripcion(descripcion);
        album.setImageUrl(imageUrl);
        album.setUsuario(usuario);

        try {
            if (idParam == null || idParam.isEmpty()) {
                albumService.crearAlbum(album);
            } else {
                album.setId(Long.parseLong(idParam));
                albumService.actualizarAlbum(album, usuario.getId());
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/admin/album-form.jsp").forward(request, response);

        }
    }

}
