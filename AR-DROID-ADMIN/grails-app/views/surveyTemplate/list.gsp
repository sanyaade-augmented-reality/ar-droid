<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.survey.SurveyTemplate" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=CP-1250" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'surveyTemplate.label', default: 'SurveyTemplate')}" />
        <title>Plantillas de encuestas</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new2.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>Plantillas de encuestas</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="description" title="Nombre" />
                        
                            <g:sortableColumn property="questions" title="Preguntas" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${surveyTemplateInstanceList}" status="i" var="surveyTemplateInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${surveyTemplateInstance.id}">${fieldValue(bean: surveyTemplateInstance, field: "description")}</g:link></td>
                        
                            <td>${surveyTemplateInstance?.questions}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${surveyTemplateInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
