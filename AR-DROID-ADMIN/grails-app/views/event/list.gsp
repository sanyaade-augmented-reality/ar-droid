<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.Event" %>
<html>
    <head>
        
        <meta name="layout" content="main" />
        <g:javascript src="js?sensor=false" base="http://maps.google.com/maps/api/" />
		<g:javascript library="prototype" />
		<g:javascript src="maps.js" />
		<g:javascript>
				document.observe("dom:loaded", function() {
					  tableToDirection('tableDir',2);  	
				});		
		</g:javascript>
        <g:set var="entityName" value="${message(code: 'event.label', default: 'Event')}" />
        <title>Eventos</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="create" action="create">Nuevo Evento</g:link></span>
        </div>
        <div class="body">
            <h1>Listar Eventos</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table id="tableDir">
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="title" title="${message(code: 'event.title.label', default: 'T&iacute;tulo')}" />
                        
                            <th><g:message code="event.entity.label" default="Entidad" /></th>
                        
                            <th><g:message code="event.geoPoint.label" default="Ubicaci&oacute;n" /></th>
                        
                            <th><g:message code="event.surveyTemplate.label" default="Plantilla de Encuesta" /></th>
                        	
                        	<th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${eventInstanceList}" status="i" var="eventInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${eventInstance.id}">${fieldValue(bean: eventInstance, field: "title")}</g:link></td>
                        
                            <td>${fieldValue(bean: eventInstance, field: "entity")}</td>
                        
                            <td>${fieldValue(bean: eventInstance, field: "geoPoint")}</td>
                        
                            <td>${fieldValue(bean: eventInstance, field: "surveyTemplate")}</td>
                        
                        	<td>
                        		<g:if test="${eventInstance.surveyTemplate != null}">
                        			<g:link controller="stats" action="show" id="${eventInstance.id}">Estadísticas</g:link>
                        		</g:if>
                        	</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${eventInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
