

<%@ page import="ar.droid.admin.Activity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'activity.label', default: 'Activity')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
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
                                    <label for="description"><g:message code="activity.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activityInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${activityInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="entity"><g:message code="activity.entity.label" default="Entity" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activityInstance, field: 'entity', 'errors')}">
                                    <g:select name="entity.id" from="${ar.droid.admin.Entity.list()}" optionKey="id" value="${activityInstance?.entity?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="activity.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activityInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${activityInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="photo"><g:message code="activity.photo.label" default="Photo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activityInstance, field: 'photo', 'errors')}">
                                    <input type="file" id="photo" name="photo" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="typeActivity"><g:message code="activity.typeActivity.label" default="Type Activity" /></label>
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
