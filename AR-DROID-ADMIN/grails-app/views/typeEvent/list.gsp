<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.TypeEvent" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'typeEvent.label', default: 'TypeEvent')}" />
        <title>Tipos de eventos</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="create" action="create">Nuevo Tipo de evento</g:link></span>
        </div>
        <div class="body">
            <h1>Listar Tipos de eventos</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="name" title="Descripci&oacute;n" />
                            <g:sortableColumn property="color" title="Color" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${typeEventInstanceList}" status="i" var="typeEventInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${typeEventInstance.id}">${fieldValue(bean: typeEventInstance, field: "description")}</g:link></td>
                        	<td><span style="background-color: ${fieldValue(bean: typeEventInstance, field: "color")}; width: 10px; height: 10px; border: 1px solid gray;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> #${fieldValue(bean: typeEventInstance, field: "color")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${typeEventInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
