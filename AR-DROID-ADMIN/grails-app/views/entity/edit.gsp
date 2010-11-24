

<%@ page import="ar.droid.admin.Entity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'entity.label', default: 'Entity')}" />
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
            <g:hasErrors bean="${entityInstance}">
            <div class="errors">
                <g:renderErrors bean="${entityInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post"  enctype="multipart/form-data">
                <g:hiddenField name="id" value="${entityInstance?.id}" />
                <g:hiddenField name="version" value="${entityInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="url"><g:message code="entity.url.label" default="Url" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'url', 'errors')}">
                                    <g:textField name="url" value="${entityInstance?.url}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activities"><g:message code="entity.activities.label" default="Activities" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'activities', 'errors')}">
                                    
<ul>
<g:each in="${entityInstance?.activities?}" var="a">
    <li><g:link controller="activity" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="activity" action="create" params="['entity.id': entityInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'activity.label', default: 'Activity')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="entity.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${entityInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="geoPoint"><g:message code="entity.geoPoint.label" default="Geo Point" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'geoPoint', 'errors')}">
                                    
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="entity.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${entityInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="photo"><g:message code="entity.photo.label" default="Photo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'photo', 'errors')}">
                                    <input type="file" id="photo" name="photo" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="readerActivity"><g:message code="entity.readerActivity.label" default="Reader Activity" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'readerActivity', 'errors')}">
                                    <g:select name="readerActivity.id" from="${ar.droid.admin.ReaderActivity.list()}" optionKey="id" value="${entityInstance?.readerActivity?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="readerNews"><g:message code="entity.readerNews.label" default="Reader News" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'readerNews', 'errors')}">
                                    <g:select name="readerNews.id" from="${ar.droid.admin.ReaderNews.list()}" optionKey="id" value="${entityInstance?.readerNews?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="typeEntity"><g:message code="entity.typeEntity.label" default="Type Entity" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'typeEntity', 'errors')}">
                                    <g:select name="typeEntity.id" from="${ar.droid.admin.TypeEntity.list()}" optionKey="id" value="${entityInstance?.typeEntity?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
