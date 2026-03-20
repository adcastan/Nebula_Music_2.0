<%-- 
    Document   : about
    Created on : 5 mar 2026, 6:21:18 p.m.
    Author     : martinbl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <title>Nosotros - Nébula</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/styles.css" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

    </head>

    <body>
        <%@include file="/WEB-INF/jsp/fragments/header.jspf" %>
        <header class="about-header">
            <img src="${pageContext.request.contextPath}/assets/img/header2.jpg" />
            <h1>Mis Álbums</h1>
        </header>
        <main class="about-main">
            <c:if test="${album == null}">
                Nuevo albúm
            </c:if>
            <c:if test="${album != null}">
                Editar álbúm
            </c:if>
            <c:if test="${error != null}">
                <p style="color:red">${error}</p>

            </c:if>
            <c:if test="${album == null}">
                Nuevo album
            </c:if>
            <form action="albums" method="post" enctype="multipart/form-data">
                <input type="hidden" name="id" value="${album.id}"></input>
                <label>Titulo</label>
                <br><br>
                <input type="text" name="titulo" value="${album.titulo}"></input>

                <label>Descripcion</label>
                <br><br>
                <textarea type="text" name="descripcion" required="${album.descripcion}"></textarea>

                <label>Portada</label>
                <br><br>
                <input type="file" name="imagen" accept="/image/png"></input>
            </form>
        </main>
        <%@include file="/WEB-INF/jsp/fragments/footer.jspf" %>
    </body>

</html>