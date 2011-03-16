
<%@ page import="ar.droid.admin.Entity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'entity.label', default: 'Entity')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <g:javascript src="js?sensor=false" base="http://maps.google.com/maps/api/" />
		<g:javascript library="prototype" />
		<g:javascript src="maps.js" />
	 </head>
    <body >
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table id="tableDir">
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'entity.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="url" title="${message(code: 'entity.url.label', default: 'Url')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'entity.description.label', default: 'Description')}" />
                        
                            <th><g:message code="entity.geoPoint.label" default="Geo Point" /></th>
                         
                            <g:sortableColumn property="name" title="${message(code: 'entity.name.label', default: 'Name')}" />
                        
                            
                        
                        </tr>
                    </thead>
                    <tbody>
                    
                    <g:each in="${entityInstanceList}" status="i" var="entityInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${entityInstance.id}">${fieldValue(bean: entityInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: entityInstance, field: "url")}</td>
                        
                            <td>${fieldValue(bean: entityInstance, field: "description")}</td>
                        	
                        	<td >${fieldValue(bean: entityInstance, field: "geoPoint")}</td>
                            
                            <td>${fieldValue(bean: entityInstance, field: "name")}</td>
                        
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${entityInstanceTotal}" />
            </div>
            <g:javascript >
            	tableToDirection('tableDir');  	
            </g:javascript>
        </div>
    </body>
</html>
