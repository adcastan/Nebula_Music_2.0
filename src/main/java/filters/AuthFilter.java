/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package filters;

import com.mycompany.nubulamusicwebaplication.util.JWTUtil;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Adrián
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        String authHeader = req.getHeader("Authorization");
        boolean tokenValido = false;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String usuario = JWTUtil.validateToken(token);
                req.setAttribute("usuario", usuario);
                tokenValido = true;
            } catch (Exception e) {
                tokenValido = false;
            }
        }

        HttpSession sesion = req.getSession(false);
        boolean loggedIn = (sesion != null && sesion.getAttribute("usuario") != null);
        boolean loginRequest = path.contains("iniciar-sesion.jsp") || path.contains("registro.jsp") || path.contains("autenticacion") || path.contains("/api/");
        boolean apiRequest = path.contains("/api/");
        boolean resourceStaticRequest = path.contains("/assets/") || path.contains("/img/") || path.contains("/css/");
        boolean tyc = path.endsWith("tyc.jsp");

        if (loginRequest || resourceStaticRequest || tyc) {
            chain.doFilter(request, response);
            return;
        }
        
        if(apiRequest){
            if(!tokenValido){
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.getWriter().write("No autorizado");
                return;
            }
            
            chain.doFilter(request, response);
            return;
        }
        
        if (loggedIn) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + "/views/auth/iniciar-sesion.jsp");
        }
    }
}
