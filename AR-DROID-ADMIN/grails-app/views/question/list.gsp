<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.survey.question.Question" %>
<html>
    <head>
        
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
        <title>Preguntas</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="create" action="create">Nueva Pregunta</g:link></span>
        </div>
        <div class="body">
            <h1>Preguntas</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="question" title="Pregunta" />
                            
                            <g:sortableColumn property="type" title="Tipo de pregunta" />
                        
                            <th>Plantilla</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${questionInstanceList}" status="i" var="questionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${questionInstance.id}">${fieldValue(bean: questionInstance, field: "question")}</g:link></td>
                            
                            <td>${questionInstance?.type}</td>
                        
                            <td>${fieldValue(bean: questionInstance, field: "surveyTemplate")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${questionInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
