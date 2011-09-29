<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.Entity" %>
<html>
    <head>
        
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'entity.label', default: 'Entity')}" />
        <title>Entidades</title>
        <g:javascript src="js?sensor=false" base="http://maps.google.com/maps/api/" />
		<g:javascript library="prototype" />
		<g:javascript src="maps.js" />
		<g:javascript>
				document.observe("dom:loaded", function() {
					  tableToDirection('tableDir',3);  	
				});		
		</g:javascript>
	 </head>
    <body >
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="create" action="create">Nueva Entidad</g:link></span>
        </div>
        <div class="body">
            <h1>Listar Entidades</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table id="tableDir">
                    <thead>
                        <tr>
                        	<g:sortableColumn property="name" title="${message(code: 'entity.name.label', default: 'Nombre')}" />
                            <g:sortableColumn property="typeEntity" title="${message(code: 'entity.typeEntity.label', default: 'Tipo de Entidad')}" />
                            <g:sortableColumn property="url" title="${message(code: 'entity.url.label', default: 'Url')}" />
                            <th>Ubicación</th>
                        </tr>
                    </thead>
                    <tbody>
                    
                    <g:each in="${entityInstanceList}" status="i" var="entityInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${entityInstance.id}">${fieldValue(bean: entityInstance, field: "name")}</g:link></td>
                            <td>${fieldValue(bean: entityInstance, field: "typeEntity")}</td>
                            <td>${fieldValue(bean: entityInstance, field: "url")}</td>
                        	<td >${fieldValue(bean: entityInstance, field: "geoPoint")}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${entityInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
