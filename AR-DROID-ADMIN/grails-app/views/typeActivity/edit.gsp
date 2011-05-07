<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.TypeActivity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'typeActivity.label', default: 'TypeActivity')}" />
        <title>Tipos de actividades</title>
		<g:javascript library="prototype" />
		<g:javascript src="colorpicker.js" />
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="list" action="list">Volver al listado</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">Nuevo Tipo de actividad</g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${typeActivityInstance}">
            <div class="errors">
                <g:renderErrors bean="${typeActivityInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${typeActivityInstance?.id}" />
                <g:hiddenField name="version" value="${typeActivityInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description">Descripción</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: typeActivityInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${typeActivityInstance?.description}" />
                                </td>
                                
                                <td valign="top" class="name">
                                    <label for="color">Color</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: typeActivityInstance, field: 'color', 'errors')}">
                                    <g:textField name="color" value="${typeActivityInstance?.color}" />
									<g:javascript>
										var colorpicker = new colorPicker('color',{
											color:'#${typeActivityInstance?.color}',
											previewElement: 'color'
										});
									</g:javascript>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="Guardar" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="Eliminar" onclick="return confirm('Seguro de eliminar?');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
