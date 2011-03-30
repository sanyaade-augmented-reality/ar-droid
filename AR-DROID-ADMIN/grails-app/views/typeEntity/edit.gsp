

<%@ page import="ar.droid.admin.TypeEntity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'typeEntity.label', default: 'TypeEntity')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
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
                                  <label for="description"><g:message code="typeEntity.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: typeEntityInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${typeEntityInstance?.description}" />
                                </td>
                                
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="typeEntity.icon.label" default="Icon" /></label>
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
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:uploadForm>
        </div>
    </body>
</html>
