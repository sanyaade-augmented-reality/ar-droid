<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.Activity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'activity.label', default: 'Activity')}" />
        <title>Actividades</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="list" action="list">Volver al listado</g:link></span>
        </div>
        <div class="body">
            <h1>Nueva Actividad</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${activityInstance}">
            <div class="errors">
                <g:renderErrors bean="${activityInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save"  enctype="multipart/form-data">
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
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
