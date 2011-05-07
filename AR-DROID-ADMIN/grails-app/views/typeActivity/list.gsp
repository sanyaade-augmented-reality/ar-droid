<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.TypeActivity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'typeActivity.label', default: 'TypeActivity')}" />
        <title>Tipos de actividades</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="create" action="create">Nuevo Tipo de actividad</g:link></span>
        </div>
        <div class="body">
             <h1>Listar Tipos de actividades</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="description" title="Descripci&oacute;n" />
                            <g:sortableColumn property="color" title="Color" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${typeActivityInstanceList}" status="i" var="typeActivityInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${typeActivityInstance.id}">${fieldValue(bean: typeActivityInstance, field: "description")}</g:link></td>
                            <td><span style="background-color: ${fieldValue(bean: typeActivityInstance, field: "color")}; width: 10px; height: 10px; border: 1px solid gray;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> #${fieldValue(bean: typeActivityInstance, field: "color")}</td>
                        
                        </tr> 
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${typeActivityInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
