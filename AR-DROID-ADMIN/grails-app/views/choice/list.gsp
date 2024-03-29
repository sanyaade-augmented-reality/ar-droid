
<%@ page import="ar.droid.admin.survey.question.Choice" %>
<html>
    <head>
        
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'choice.label', default: 'Choice')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'choice.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'choice.description.label', default: 'Description')}" />
                        
                            <th><g:message code="choice.multipleChoiceQuestion.label" default="Multiple Choice Question" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${choiceInstanceList}" status="i" var="choiceInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${choiceInstance.id}">${fieldValue(bean: choiceInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: choiceInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: choiceInstance, field: "multipleChoiceQuestion")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${choiceInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
