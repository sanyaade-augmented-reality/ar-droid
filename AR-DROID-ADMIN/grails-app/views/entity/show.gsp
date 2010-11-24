
<%@ page import="ar.droid.admin.Entity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'entity.label', default: 'Entity')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
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
                            <td valign="top" class="name"><g:message code="entity.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: entityInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="entity.url.label" default="Url" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: entityInstance, field: "url")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="entity.activities.label" default="Activities" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${entityInstance.activities}" var="a">
                                    <li><g:link controller="activity" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="entity.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: entityInstance, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="entity.geoPoint.label" default="Geo Point" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: entityInstance, field: "geoPoint")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="entity.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: entityInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="entity.photo.label" default="Photo" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: entityInstance, field: "photo")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="entity.readerActivity.label" default="Reader Activity" /></td>
                            
                            <td valign="top" class="value"><g:link controller="readerActivity" action="show" id="${entityInstance?.readerActivity?.id}">${entityInstance?.readerActivity?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="entity.readerNews.label" default="Reader News" /></td>
                            
                            <td valign="top" class="value"><g:link controller="readerNews" action="show" id="${entityInstance?.readerNews?.id}">${entityInstance?.readerNews?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="entity.typeEntity.label" default="Type Entity" /></td>
                            
                            <td valign="top" class="value"><g:link controller="typeEntity" action="show" id="${entityInstance?.typeEntity?.id}">${entityInstance?.typeEntity?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${entityInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
