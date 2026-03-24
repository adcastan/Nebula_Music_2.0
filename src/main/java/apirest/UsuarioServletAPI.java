/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package apirest;

import DTOs.ResponseMessageDTO;
import DTOs.UsuarioDTO;
import com.mycompany.nubulamusicwebaplication.model.Usuario;
import com.mycompany.nubulamusicwebaplication.service.IUsuarioService;
import com.mycompany.nubulamusicwebaplication.service.UsuarioService;
import com.mycompany.nubulamusicwebaplication.util.JSONMapper;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Adrián
 */
@WebServlet(name = "UsuariosServletAPI", urlPatterns = {"/api/usuario/*"})
public class UsuarioServletAPI extends HttpServlet {

    private IUsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //GET www.myservidor.com/api/usuario //EL CLIENTE QUIERE OBTENER A TODOS
        //GET www.myservidor.com/api/usuario?id=1 //EL CLIENTE QUIERE OBTENER Al usuario 1
        //GET www.myservidor.com/api/usuario?nombre=pepe //EL CLIENTE QUIERE OBTENER A TODOS LOS PEPES

        try {

            String pathInfo = request.getPathInfo();
            List<Usuario> usuarios = usuarioService.listarTodos();

            if (pathInfo == null || pathInfo.equals("/")) {

                List<UsuarioDTO> usuariosDTO = usuarios.stream().map((u) -> {
                    UsuarioDTO dto = new UsuarioDTO();
                    dto.setId(u.getId());
                    dto.setNombre(u.getNombre());
                    dto.setCorreo(u.getCorreo());
                    dto.setPseudonimo(u.getPseudonimo());
                    return dto;
                }).collect(Collectors.toList());
                JSONMapper.mapper.writeValue(response.getWriter(), usuariosDTO);

            } else {
                Long id = Long.parseLong(pathInfo.substring(1));
                Usuario usuario = usuarioService.buscarPorId(id);
                if (usuario == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);

                    ResponseMessageDTO mensaje = new ResponseMessageDTO();

                    mensaje.setSuccess(false);
                    mensaje.setMessage("Mo se encontro el usuario buscado.");

                    JSONMapper.mapper.writeValue(response.getWriter(), id);
                    return;
                }

                    UsuarioDTO dto = new UsuarioDTO();
                    dto.setId(usuario.getId());
                    dto.setNombre(usuario.getNombre());
                    dto.setCorreo(usuario.getCorreo());
                    dto.setPseudonimo(usuario.getPseudonimo());
                JSONMapper.mapper.writeValue(response.getWriter(), dto);

            }

        } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);

                ResponseMessageDTO mensaje = new ResponseMessageDTO();

                mensaje.setSuccess(false);
                mensaje.setMessage("Mo se encontro el usuario buscado.");

                JSONMapper.mapper.writeValue(response.getWriter(), mensaje);
            }

        }
    

        @Override
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        }

        @Override
        protected void doPut
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        }

        @Override
        protected void doDelete
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
