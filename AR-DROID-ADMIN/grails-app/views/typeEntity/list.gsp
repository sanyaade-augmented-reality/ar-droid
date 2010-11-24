
<%@ page import="ar.droid.admin.TypeEntity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'typeEntity.label', default: 'TypeEntity')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
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
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'typeEntity.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'typeEntity.description.label', default: 'Description')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${typeEntityInstanceList}" status="i" var="typeEntityInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${typeEntityInstance.id}">${fieldValue(bean: typeEntityInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: typeEntityInstance, field: "description")}</td>
                        
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
