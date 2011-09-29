<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.TypeEvent" %>
<html>
    <head>
        
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'typeEvent.label', default: 'TypeEvent')}" />
        <title>Tipos de eventos</title>
		<g:javascript library="prototype" />
		<g:javascript src="colorpicker.js" />
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="list" action="list">Volver al listado</g:link></span>
        </div>
        <div class="body">
            <h1>Nuevo Tipo de evento</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${typeEventInstance}">
            <div class="errors">
                <g:renderErrors bean="${typeEventInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Descripción</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: typeEventInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${typeEventInstance?.description}" />
                                </td>
                                
                                <td valign="top" class="name">
                                    <label for="color">Color</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: typeEventInstance, field: 'color', 'errors')}">
                                    <g:textField name="color" id="color" value="${typeEventInstance?.color}" />
									<g:javascript>
										var colorpicker = new colorPicker('color',{
											color:'#${typeEventInstance?.color}',
											previewElement: 'color'
										});
									</g:javascript>
                                </td>
                            </tr>
                        
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
