/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.nubulamusicwebaplication.controller;

import DTOs.ResponseMessageDTO;
import DTOs.UsuarioAuthDTO;
import com.mycompany.nubulamusicwebaplication.model.Usuario;
import com.mycompany.nubulamusicwebaplication.service.IUsuarioService;
import com.mycompany.nubulamusicwebaplication.service.UsuarioService;
import com.mycompany.nubulamusicwebaplication.util.JSONMapper;
import com.mycompany.nubulamusicwebaplication.util.JWTUtil;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Adrián
 */
@WebServlet(name = "AuthServletAPI", urlPatterns = {"/api/auth/*"})
public class AuthServletAPI extends HttpServlet {

    private IUsuarioService usuarioService = new UsuarioService();

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UsuarioAuthDTO req = JSONMapper.mapper
                .readValue(request.getInputStream(),UsuarioAuthDTO.class);
        
        Usuario usuario = usuarioService.autenticar(req.getCorreo(),req.getContrasenia());
        
        if(usuario == null){
            response.setStatus(401);
            return;
        }
        
        String token = JWTUtil.generarToken(usuario.getCorreo());
        
        ResponseMessageDTO mensaje = new ResponseMessageDTO();
        
        mensaje.setSuccess(true);
        mensaje.setMessage(token);
        
        response.setContentType("application/json");
        JSONMapper.mapper.writeValue(response.getWriter(),mensaje);
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
