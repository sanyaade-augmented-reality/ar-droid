

<%@ page import="ar.droid.admin.survey.question.Question" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:javascript src="viewparams.js" />
		<g:javascript library="prototype" />
		<g:javascript>
				document.observe("dom:loaded", function() {
					  showDataForQuestion($('typeQuestion'));
				});		
		</g:javascript>
        <g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
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
            <g:hasErrors bean="${questionInstance}">
            <div class="errors">
                <g:renderErrors bean="${questionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table id="tableQuestion">
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name" >
                                    <label for="surveyTemplate"><g:message code="question.surveyTemplate.label" default="Survey Template" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'surveyTemplate', 'errors')}">
                                    <g:select name="surveyTemplate.id" from="${ar.droid.admin.survey.SurveyTemplate.list()}" optionKey="id" value="${questionInstance?.surveyTemplate?.id}"  />
                                </td>
                                 <td valign="top" class="name">
                                    <label for="typeQuestion"><g:message code="question.typeQuestion.label" default="Type Question" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'question', 'errors')}">
                                    <g:select name="typeQuestion" from="${typeQuestions}" optionKey="key"  optionValue="value" value="${typeQuestion}"  onChange="showDataForQuestion(this);"/>
                                </td>
                            </tr>
                         	
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="question"><g:message code="question.question.label" default="Question" /></label>
                                </td>
                                <td colspan="3" valign="top" class="value ${hasErrors(bean: questionInstance, field: 'question', 'errors')}">
                                    <g:textField name="question" value="${questionInstance?.question}" />
                                </td>                                
                            </tr>
                            
                            <tr class="prop" id="trNumericType">
                                <td valign="top" class="name">
                                    <label for="limitTo"><g:message code="question.NumericValueQuestion.LimitTo.label" default="limit To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'limitTo', 'errors')}">
                                    <g:textField name="limitTo" value="${limitTo}" />
                                </td>
                                <td valign="top" class="name">
                                    <label for="limitFrom"><g:message code="question.NumericValueQuestion.LimitFrom.label" default="limit From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'limitFrom', 'errors')}">
                                    <g:textField name="limitFrom" value="${limitFrom}" />
                                </td>
                          </tr>
                          
                           <tr class="prop" id="trMultipleChoiceType">
                                <td valign="top" class="name">
                                    <label for="limitTo"><g:message code="question.MultipleChoiceQuestion.maxOptions.label" default="max Options" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'maxOptions', 'errors')}">
                                    <g:textField name="maxOptions" value="${maxOptions}" />
                                </td>
                                <td><input type="button" name="createChoice" id="createChoice" value="${message(code: 'default.button.create.Choice.label', default: 'Create Choice')}" onclick="createOption(this)"></td>
                                <td>&nbsp;</td>                                
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
