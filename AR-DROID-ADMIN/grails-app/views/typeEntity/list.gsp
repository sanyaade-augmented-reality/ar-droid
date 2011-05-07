<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.TypeEntity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'typeEntity.label', default: 'TypeEntity')}" />
        <title>Tipos de entidades</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="create" action="create">Nuevo Tipo de entidad</g:link></span>
        </div>
        <div class="body">
            <h1>Lista de Tipos de entidades</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="description" title="Descripci&oacute;n" />
                            <g:sortableColumn  property="icon" title="Icono" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${typeEntityInstanceList}" status="i" var="typeEntityInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${typeEntityInstance.id}">${fieldValue(bean: typeEntityInstance, field: "description")}</g:link></td>
                        	<td>
                        		<g:if test="${typeEntityInstance.icon}">
  									<img width="16" class="avatar" src="${createLink(controller:'typeEntity', action:'showIcon', id:typeEntityInstance.id)}" />
								</g:if>
                        	</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${typeEntityInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
