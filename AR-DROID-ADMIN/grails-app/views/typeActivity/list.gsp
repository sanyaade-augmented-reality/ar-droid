
<%@ page import="ar.droid.admin.TypeActivity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'typeActivity.label', default: 'TypeActivity')}" />
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
                            <g:sortableColumn property="description" title="${message(code: 'typeActivity.description.label', default: 'Description')}" />
                            <g:sortableColumn property="color" title="${message(code: 'typeActivity.color.label', default: 'Color')}" />
                        
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
