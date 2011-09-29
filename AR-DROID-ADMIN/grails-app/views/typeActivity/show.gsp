<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.TypeActivity" %>
<html>
    <head>
        
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'typeActivity.label', default: 'TypeActivity')}" />
        <title>Tipos de actividades</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="list" action="list">Volver al listado</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">Nuevo Tipo de actividad</g:link></span>
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
                            <td valign="top" class="name">Descripción</td>
                            <td valign="top" class="value">${fieldValue(bean: typeActivityInstance, field: "description")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${typeActivityInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="Editar" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="Eliminar" onclick="return confirm('Seguro de eliminar?');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
