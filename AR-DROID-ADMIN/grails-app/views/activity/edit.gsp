<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.Activity" %>
<html>
    <head>
        
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'activity.label', default: 'Activity')}" />
        <title>Actividades</title>
        
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />

		<g:javascript src="viewparams.js" />
		<g:javascript library="prototype" />
		
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="list" action="list">Volver al listado</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">Nueva Actividad</g:link></span>
        </div>
        <div class="body">
            <h1>Editar Actividad</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${activityInstance}">
            <div class="errors">
                <g:renderErrors bean="${activityInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post"  enctype="multipart/form-data">
                <g:hiddenField name="id" value="${activityInstance?.id}" />
                <g:hiddenField name="version" value="${activityInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                
                                <td valign="top" class="name">
                                    <label for="name">Nombre</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activityInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${activityInstance?.name}" />
                                </td>
                            
                                <td valign="top" class="name">
                                    <label for="event">Evento</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activityInstance, field: 'event', 'errors')}">
		                            <g:select name="event.id" from="${ar.droid.admin.Event.list()}" optionKey="id" value="${activityInstance?.event?.id}"  />
                                </td>
                                
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description">Descripción</label>
                                </td>
                                <td colspan="3" valign="top" class="value ${hasErrors(bean: activityInstance, field: 'description', 'errors')}">
                                    <g:textArea name="description" value="${activityInstance?.description}" class="w100" />
                                </td>
                                
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="typeActivity">Tipo de Actividad</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activityInstance, field: 'typeActivity', 'errors')}">
                                    <g:select name="typeActivity.id" from="${ar.droid.admin.TypeActivity.list()}" optionKey="id" value="${activityInstance?.typeActivity?.id}"  />
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
