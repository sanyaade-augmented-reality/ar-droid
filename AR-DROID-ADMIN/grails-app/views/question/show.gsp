<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.survey.question.Question" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=CP-1250" />
        <meta name="layout" content="main" />
         <g:javascript src="viewparams.js" />
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
            <span class="menuButton"><g:link class="create" action="create">Nueva Pregunta</g:link></span>
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
                        	<td valign="top" class="name">ID</td>
                            
                            <td valign="top" class="value">${fieldValue(bean: questionInstance, field: "id")}</td>
                            
                         	<td valign="top" class="name">Plantilla</td>
                            
                            <td valign="top" class="value"><g:link controller="surveyTemplate" action="show" id="${questionInstance?.surveyTemplate?.id}">${questionInstance?.surveyTemplate?.encodeAsHTML()}</g:link></td>

                        </tr>
                    
                        <tr class="prop">
                         	<td valign="top" class="name">Tipo de pregunta</td>
                            
                            <td valign="top" class="value"><g:hiddenField name="typeQuestion" value="${typeQuestion}" />
                            ${fieldValue(bean: questionInstance, field: "type")}</td>
                            
                            <td valign="top" class="name">Pregunta</td>
                            
                            <td valign="top" class="value">${fieldValue(bean: questionInstance, field: "question")}</td>
                            
                        </tr>
                        <tr class="prop" id="trNumericType">
                                <td valign="top" class="name">
                                    <label for="limitTo">Limite hasta</label>
                                </td>
                                <td valign="top" class="value">${limitTo}</td>
                                <td valign="top" class="name">
                                    <label for="limitFrom">Limite desde</label>
                                </td>
                                <td valign="top" class="value">${limitFrom} </td>
                                
                        </tr>
                        
                        <tr class="prop" id="trMultipleChoiceType">
                                <td valign="top" class="name">
                                    <label for="limitTo">Cantidad de opciones</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'maxOptions', 'errors')}">${maxOptions}</td>
                                 <td valign="top" class="name">Opciones</td>
                                <td>
                                	   <ul >
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
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="Editar" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="Eliminar" onclick="return confirm('Seguro de eliminar?');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
