<%-- 
    Document   : index
    Created on : 5 mar 2026, 6:24:02 p.m.
    Author     : martinbl
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

    <head>
        <title>Nébula music</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/styles.css" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>

    <body>
        <%
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("views/auth/iniciar-sesion.jsp");
            }
        %>

        <%@include file="/WEB-INF/jsp/fragments/header.jspf" %>

        <c:if test="${sessionScope.usuario != null}">
            <h2>Bienvenido ${sessionScope.correo}</h2>
        </c:if>

        <header class="principal hero">
            <img src="${pageContext.request.contextPath}/assets/img/cover.jpg" />
            <h1>Tu música en la <span>nube</span> y en tu <span>espacio</span>.</h1>
        </header>

        <main>

            <section id="news" class="news-box">

                <article class="card">
                    <img
                        src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRfGRivZ1CFL4jhQXNbF9phy9h-CASW3TM9UQ&s" />
                    <h3>Taylor Swift – The Life of a Showgirl</h3>
                    <p>El nuevo álbum de Taylor Swift explora una etapa más madura de su carrera, combinando pop moderno con letras personales y emotivas.</p>
                    <p><a href="${pageContext.request.contextPath}/views/aplication/play-music.jsp">Escuchar ahora</a></p>
                </article>

                <article class="card">
                    <img
                        src="https://media.gq.com.mx/photos/66cdf89fe21e890818a07d6d/4:3/w_2248,h_1686,c_limit/Oasis.jpg" />
                    <div>
                        <h3>Oasis – Greatest Anthems</h3>
                        <p>Una recopilación con los himnos más icónicos de la banda británica.</p>
                    </div>
                    <p>Este álbum reúne algunos de los mayores éxitos de Oasis, con su característico sonido britpop y guitarras que marcaron toda una generación.</p>
                    <p><a href="${pageContext.request.contextPath}/views/aplication/play-music.jsp">Escuchar ahora</a></p>
                </article>

                <article class="card">
                    <img
                        src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRU2wpHalZ_btJnPiNpMLziQ2Pcu0BDxxJBVA&s" />
                    <h3>Los Reztos – Tributo a Marea</h3>
                    <p>Un álbum tributo que rinde homenaje al estilo único de Marea.</p>
                    <p>Los Reztos reinterpretan varios clásicos del rock español con su propio estilo, manteniendo la esencia rebelde y poética de la banda original.</p>
                    <p><a href="${pageContext.request.contextPath}/views/aplication/play-music.jsp">Escuchar ahora</a></p>
                </article>

            </section>

        </main>

        <%@include file="/WEB-INF/jsp/fragments/footer.jspf" %>

    </body>

</html>