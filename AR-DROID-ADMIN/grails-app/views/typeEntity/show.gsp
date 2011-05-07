<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.TypeEntity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'typeEntity.label', default: 'TypeEntity')}" />
        <title>Tipos de entidades</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="list" action="list">Volver al listado</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">Nuevo Tipo de entidad</g:link></span>
        </div>
        <div class="body">
            <h1>Tipo de Entidad</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name">ID</td>
                            <td valign="top" class="value">${fieldValue(bean: typeEntityInstance, field: "id")}</td>
                            
                            <td valign="top" class="name">Descripción</td>
                            <td valign="top" class="value">${fieldValue(bean: typeEntityInstance, field: "description")}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name">Icono</td>
                            <td valign="top" class="value">
                            	<g:if test="${typeEntityInstance.icon}">
  									<img class="avatar" src="${createLink(controller:'typeEntity', action:'showIcon', id:typeEntityInstance.id)}" />
								</g:if>
                            </td>
                                                        
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${typeEntityInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('Seguro de eliminar?');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
