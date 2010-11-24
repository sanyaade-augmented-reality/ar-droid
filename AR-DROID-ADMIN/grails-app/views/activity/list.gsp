
<%@ page import="ar.droid.admin.Activity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'activity.label', default: 'Activity')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'activity.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="url" title="${message(code: 'activity.url.label', default: 'Url')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'activity.description.label', default: 'Description')}" />
                        
                            <th><g:message code="activity.entity.label" default="Entity" /></th>
                        
                            <g:sortableColumn property="name" title="${message(code: 'activity.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="photo" title="${message(code: 'activity.photo.label', default: 'Photo')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${activityInstanceList}" status="i" var="activityInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${activityInstance.id}">${fieldValue(bean: activityInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: activityInstance, field: "url")}</td>
                        
                            <td>${fieldValue(bean: activityInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: activityInstance, field: "entity")}</td>
                        
                            <td>${fieldValue(bean: activityInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: activityInstance, field: "photo")}</td>
                        
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
