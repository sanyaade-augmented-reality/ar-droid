<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.survey.question.Question" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=CP-1250" />
        <meta name="layout" content="main" />
        <g:javascript src="viewparams.js" />
        <g:javascript src="surveytemplate.js" />
		<g:javascript library="prototype" />
		<g:javascript>
				document.observe("dom:loaded", function() {
					  showDataForQuestion($('typeQuestion'));
				});		
		</g:javascript>
        <g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
        <title>Preguntas</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="list" action="list">Volver al listado</g:link></span>
        </div>
        <div class="body">
            <h1>Crear Pregunta</h1>
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
                                    <label for="surveyTemplate">Plantilla</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'surveyTemplate', 'errors')}">
                                    <g:select name="surveyTemplate.id" from="${ar.droid.admin.survey.SurveyTemplate.list()}" optionKey="id" value="${questionInstance?.surveyTemplate?.id}"  />
                                </td>
                                 <td valign="top" class="name">
                                    <label for="typeQuestion">Tipo de pregunta</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'question', 'errors')}">
                                    <g:select name="typeQuestion" from="${typeQuestions}" optionKey="key"  optionValue="value" value="${typeQuestion}"  onChange="showDataForQuestion(this);"/>
                                </td>
                            </tr>
                         	
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="question">Pregunta</label>
                                </td>
                                <td colspan="3" valign="top" class="value ${hasErrors(bean: questionInstance, field: 'question', 'errors')}">
                                    <g:textField name="question" value="${questionInstance?.question}" />
                                </td>                                
                            </tr>
                            
                            <tr class="prop" id="trNumericType">
                                <td valign="top" class="name">
                                    <label for="limitTo">Limite hasta</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'limitTo', 'errors')}">
                                    <g:textField name="limitTo" value="${limitTo}" />
                                </td>
                                <td valign="top" class="name">
                                    <label for="limitFrom">Limite desde</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'limitFrom', 'errors')}">
                                    <g:textField name="limitFrom" value="${limitFrom}" />
                                </td>
                          </tr>
                          
                           <tr class="prop" id="trMultipleChoiceType">
                                <td valign="top" class="name">
                                    <label for="limitTo">Cantidad de opciones</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'maxOptions', 'errors')}">
                                    <g:textField name="maxOptions" value="${maxOptions}" />
                                </td>
                                <td><input type="button" name="createChoice" id="createChoice" value="Crear opción" onclick="createOption(this)"></td>
                                <td>&nbsp;</td>                                
                          </tr>
                          
                           <g:each in="${options}" var="q">
                                  <tr>
                                 	 <td><label for='choice'>Opción</label></td>
                                  	 <td><input type='text' name='choice' id='choice' value="${q?.description}"></td>
                                  	 <td><input type='button' name='deleteChoice' id='deleteChoice' value='Eliminar' onClick='deleteOption(this)'></td>
                                 	 <td>&nbsp;</td>
                                </tr>
                           </g:each>
                                              
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="Crear" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
