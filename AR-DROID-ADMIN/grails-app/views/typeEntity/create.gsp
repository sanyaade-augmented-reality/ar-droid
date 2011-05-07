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
        </div>
        <div class="body">
            <h1>Nuevo Tipo de entidad</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${typeEntityInstance}">
            <div class="errors">
                <g:renderErrors bean="${typeEntityInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:uploadForm action="save">
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
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="Crear" /></span>
                </div>
            </g:uploadForm>
        </div>
    </body>
</html>
