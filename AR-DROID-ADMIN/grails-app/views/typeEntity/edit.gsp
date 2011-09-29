<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.TypeEntity" %>
<html>
    <head>
        
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
            <h1>Editar Tipo de entidad</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${typeEntityInstance}">
            <div class="errors">
                <g:renderErrors bean="${typeEntityInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:uploadForm method="post" action="update">
                <g:hiddenField name="id" value="${typeEntityInstance?.id}" />
                <g:hiddenField name="version" value="${typeEntityInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description">Descripción</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: typeEntityInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${typeEntityInstance?.description}" />
                                </td>
                                
                                <td valign="top" class="name">
                                    <label for="description">Icono</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: typeEntityInstance, field: 'icon', 'errors')}">
                                    <input type="file" id="icon" name="icon" />
                                    <g:if test="${typeEntityInstance.icon}">
                                    	<br /><br />
  										<img class="avatar" src="${createLink(controller:'typeEntity', action:'showIcon', id:typeEntityInstance.id)}" />
									</g:if>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="Guardar" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="Eliminar" onclick="return confirm('Seguro de eliminar?');" /></span>
                </div>
            </g:uploadForm>
        </div>
    </body>
</html>
