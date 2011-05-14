<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.TypeEvent" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'typeEvent.label', default: 'TypeEvent')}" />
        <title>Tipos de eventos</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="list" action="list">Volver al listado</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">Nuevo Tipo de evento</g:link></span>
        </div>
        <div class="body">
            <h1>Editar Tipo de evento</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${typeEventInstance}">
            <div class="errors">
                <g:renderErrors bean="${typeEventInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${typeEventInstance?.id}" />
                <g:hiddenField name="version" value="${typeEventInstance?.version}" />
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
