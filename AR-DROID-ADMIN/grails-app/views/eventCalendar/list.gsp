
<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.calendar.EventCalendar" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=CP-1250" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'eventCalendar.label', default: 'EventCalendar')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'eventCalendar.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="endDate" title="${message(code: 'eventCalendar.endDate.label', default: 'End Date')}" />
                        
                            <g:sortableColumn property="endTime" title="${message(code: 'eventCalendar.endTime.label', default: 'End Time')}" />
                        
                            <g:sortableColumn property="startDate" title="${message(code: 'eventCalendar.startDate.label', default: 'Start Date')}" />
                        
                            <g:sortableColumn property="startTime" title="${message(code: 'eventCalendar.startTime.label', default: 'Start Time')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${eventCalendarInstanceList}" status="i" var="eventCalendarInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${eventCalendarInstance.id}">${fieldValue(bean: eventCalendarInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${eventCalendarInstance.endDate}" /></td>
                        
                            <td><g:formatDate date="${eventCalendarInstance.endTime}" /></td>
                        
                            <td><g:formatDate date="${eventCalendarInstance.startDate}" /></td>
                        
                            <td><g:formatDate date="${eventCalendarInstance.startTime}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${eventCalendarInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
