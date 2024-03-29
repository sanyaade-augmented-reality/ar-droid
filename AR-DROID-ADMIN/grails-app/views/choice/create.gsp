

<%@ page import="ar.droid.admin.survey.question.Choice" %>
<html>
    <head>
        
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'choice.label', default: 'Choice')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${choiceInstance}">
            <div class="errors">
                <g:renderErrors bean="${choiceInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="choice.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: choiceInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${choiceInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="multipleChoiceQuestion"><g:message code="choice.multipleChoiceQuestion.label" default="Multiple Choice Question" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: choiceInstance, field: 'multipleChoiceQuestion', 'errors')}">
                                    <g:select name="multipleChoiceQuestion.id" from="${ar.droid.admin.survey.question.MultipleChoiceQuestion.list()}" optionKey="id" value="${choiceInstance?.multipleChoiceQuestion?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
