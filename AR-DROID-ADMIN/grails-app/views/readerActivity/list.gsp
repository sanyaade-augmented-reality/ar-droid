
<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.reader.ReaderActivity" %>
<html>
    <head>
        
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'readerActivity.label', default: 'ReaderActivity')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'readerActivity.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="url" title="${message(code: 'readerActivity.url.label', default: 'Url')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${readerActivityInstanceList}" status="i" var="readerActivityInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${readerActivityInstance.id}">${fieldValue(bean: readerActivityInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: readerActivityInstance, field: "url")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${readerActivityInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
