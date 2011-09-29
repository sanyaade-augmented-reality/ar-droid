

<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.reader.ReaderActivity" %>
<html>
    <head>
        
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'readerActivity.label', default: 'ReaderActivity')}" />
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
            <g:hasErrors bean="${readerActivityInstance}">
            <div class="errors">
                <g:renderErrors bean="${readerActivityInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                            	<td valign="top" class="name">
                                    <label for="url"><g:message code="readerActivity.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: readerActivityInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${readerActivityInstance?.name}" />
                                </td>
                                
                                <td valign="top" class="name">
                                    <label for="url"><g:message code="readerActivity.url.label" default="Url" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: readerActivityInstance, field: 'url', 'errors')}">
                                    <g:textField name="url" value="${readerActivityInstance?.url}" />
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
