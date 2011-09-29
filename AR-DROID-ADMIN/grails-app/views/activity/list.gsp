<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.Activity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=CP-1250" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'activity.label', default: 'Activity')}" />
        <title>Actividades</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="create" action="create">Nueva Actividad</g:link></span>
        </div>
        <div class="body">
            <h1>Listar Actividades</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        	<g:sortableColumn property="name" title="Nombre" />
                            <g:sortableColumn property="url" title="Tipo de Actividad" />
                            <g:sortableColumn property="entity" title="Evento" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${activityInstanceList}" status="i" var="activityInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${activityInstance.id}">${fieldValue(bean: activityInstance, field: "name")}</g:link></td>
                        
                            <td>${fieldValue(bean: activityInstance, field: "typeActivity")}</td>
                        
                            <td>${fieldValue(bean: activityInstance, field: "event")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${activityInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
