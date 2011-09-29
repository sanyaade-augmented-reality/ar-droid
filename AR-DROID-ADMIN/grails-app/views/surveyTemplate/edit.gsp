<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.survey.SurveyTemplate" %>
<html>
    <head>
        
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'surveyTemplate.label', default: 'SurveyTemplate')}" />
        <title>Plantillas de encuestas</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="list" action="list">Volver al listado</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">Nueva Plantilla</g:link></span>
        </div>
        <div class="body">
            <h1>Editar Plantilla</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${surveyTemplateInstance}">
            <div class="errors">
                <g:renderErrors bean="${surveyTemplateInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${surveyTemplateInstance?.id}" />
                <g:hiddenField name="version" value="${surveyTemplateInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description">Descripción</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: surveyTemplateInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${surveyTemplateInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="questions"><g:message code="surveyTemplate.questions.label" default="Questions" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: surveyTemplateInstance, field: 'questions', 'errors')}">
                                    
<ul>
<g:each in="${surveyTemplateInstance?.questions?}" var="q">
    <li><g:link controller="question" action="show" id="${q.id}">${q?.type}: ${q?.question}</g:link></li>
</g:each>
</ul>


                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="Guardar" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="Eliminar" onclick="return confirm('Seguro de eliminar?');" /></span>
                    <span class="button"><g:actionSubmit class="add" action="addnewquestion" params="['surveyTemplate.id': surveyTemplateInstance?.id]" value="Agregar pregunta" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
