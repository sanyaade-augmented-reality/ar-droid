
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
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                         	<td valign="top" class="name"><g:message code="question.surveyTemplate.label" default="Survey Template" /></td>
                            
                            <td valign="top" class="value"><g:link controller="surveyTemplate" action="show" id="${questionInstance?.surveyTemplate?.id}">${questionInstance?.surveyTemplate?.encodeAsHTML()}</g:link></td>
                           
                            <td valign="top" class="name"><g:message code="question.typeQuestion.label" default="Type Question" /></td>
                            
                            <td valign="top" class="value"><g:hiddenField name="typeQuestion" value="${typeQuestion}" />
                            ${fieldValue(bean: questionInstance, field: "type")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                         	<td valign="top" class="name"><g:message code="question.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: questionInstance, field: "id")}</td>
                            
                            <td valign="top" class="name"><g:message code="question.question.label" default="Question" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: questionInstance, field: "question")}</td>
                            
                        </tr>
                        <tr class="prop" id="trNumericType">
                                <td valign="top" class="name">
                                    <label for="limitTo"><g:message code="question.NumericValueQuestion.LimitTo.label" default="limit To" /></label>
                                </td>
                                <td valign="top" class="value">${limitTo}</td>
                                <td valign="top" class="name">
                                    <label for="limitFrom"><g:message code="question.NumericValueQuestion.LimitFrom.label" default="limitFrom" /></label>
                                </td>
                                <td valign="top" class="value">${limitFrom} </td>
                                
                        </tr>
                        
                        <tr class="prop" id="trMultipleChoiceType">
                                <td valign="top" class="name">
                                    <label for="limitTo"><g:message code="question.MultipleChoiceQuestion.maxOptions.label" default="max Options" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'maxOptions', 'errors')}">${maxOptions}</td>
                                <td>
                                	   <ul>
                               				 <g:each in="${options}" var="q">
                                    			<li>${q?.description}</li>
                                			</g:each>
                                	   </ul>
                                </td>
                                <td>&nbsp;</td>                                
                          </tr>
                     
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${questionInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
