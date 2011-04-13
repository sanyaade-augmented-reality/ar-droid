

<%@ page import="ar.droid.admin.Event" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:javascript src="js?sensor=false" base="http://maps.google.com/maps/api/" />
		<g:javascript src="maps.js" />
		<g:javascript src="viewparams.js" />
		<g:javascript library="prototype" />
		<g:javascript>
			Event.observe(window, 'load', initialize, true);
			
			function initialize() {
				initializeMap('map_canvas', $('latitude').value, $('longitude').value, 12);
				
				google.maps.event.addListener(mapInstance, 'click', function(event){
  					placeMarker(event.latLng, event.latLng.lat() + '@' + event.latLng.lng(), true);
  					var clickedLocation = new google.maps.LatLng(event.latLng);
  					
  					$('latitude').value = event.latLng.lat();
  					$('longitude').value = event.latLng.lng();
  				});
  				
  				if($('latitude').value != '' && $('longitude').value != ''){
  					var latlng = new google.maps.LatLng($('latitude').value, $('longitude').value);
  					placeMarker(latlng, $('latitude').value + '@' + $('longitude').value, true);
  				}
  			}
  			
		</g:javascript>
        
        <g:set var="entityName" value="${message(code: 'event.label', default: 'Event')}" />
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
            <g:hasErrors bean="${eventInstance}">
            <div class="errors">
                <g:renderErrors bean="${eventInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post"  enctype="multipart/form-data">
                <g:hiddenField name="id" value="${eventInstance?.id}" />
                <g:hiddenField name="version" value="${eventInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="title"><g:message code="event.title.label" default="Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'title', 'errors')}">
                                    <g:textField name="title" value="${eventInstance?.title}" />
                                </td>
                                 <td valign="top" class="name">
                                  <label for="entity"><g:message code="event.entity.label" default="Entity" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'entity', 'errors')}">
                                    <g:select name="entity.id" from="${ar.droid.admin.Entity.list()}" optionKey="id" value="${eventInstance?.entity?.id}"  />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="event.description.label" default="Description" /></label>
                                </td>
                                <td colspan="3" valign="top" class="value ${hasErrors(bean: eventInstance, field: 'description', 'errors')}">
                                     <g:textArea name="description" value="${eventInstance?.description}" />                                  
                                </td>
                            </tr>
                            
                             <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="geoPoint"><g:message code="event.geoPoint.label" default="Geo Point" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'geoPoint', 'errors')}">
                                	<div id="map_canvas" style="width: 100%; height: 250px;"></div>
                                    <g:hiddenField name="latitude" value="${eventInstance?.geoPoint.latitude}" />
                                    <g:hiddenField name="longitude" value="${eventInstance?.geoPoint.longitude}" />
                                </td>
                                
                               
                                 <td valign="top" class="name">
                                    <label for="photo"><g:message code="entity.photo.label" default="Photo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'photo', 'errors')}">
                                    <input type="file" id="photo" name="photo" />
                                    <g:if test="${eventInstance.photo}">
                                    	<br /><br />
  										<img class="avatar" src="${createLink(controller:'event', action:'showImage', id:eventInstance.id)}" />
									</g:if>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="eid"><g:message code="event.eid.label" default="Eid" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'eid', 'errors')}">
                                    <g:textField name="eid" value="${eventInstance?.eid}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="typeEvent"><g:message code="event.typeEvent.label" default="Type Event" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'typeEvent', 'errors')}">
                                    <g:select name="typeEvent.id" from="${ar.droid.admin.TypeEvent.list()}" optionKey="id" value="${eventInstance?.typeEvent?.id}"  />
                                </td>
                                <td valign="top" class="name">
                                  <label for="surveyTemplate"><g:message code="event.surveyTemplate.label" default="Survey Template" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'surveyTemplate', 'errors')}">
                                    <g:select name="surveyTemplate.id" from="${ar.droid.admin.survey.SurveyTemplate.list()}" optionKey="id" value="${eventInstance?.surveyTemplate?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                           <tr class="prop">                                
                                <td valign="top" class="name">
                                    <label for="surveyTemplate"><g:message code="event.eventCalendar.label" default="Event Calendar" /></label>
                                </td>
                                <td valign="top" class="date value ${hasErrors(bean: eventInstance, field: 'eventCalendar', 'errors')}">
                                    <g:select name="eventCalendar_select" from="${application.lsEventCalendars}" optionKey="class" value="${request.eventCalendar_select}" onchange="viewParams(this);" class="w100" />
                                    <div><div class="dates"><g:message code="event.eventCalendar.startDate" default="Init" /></div><g:datePicker name="event.eventCalendar.startDate" value="${eventInstance?.eventCalendar?.startDate}" years="${2011..2020}" /></div>
                                    <div><div class="dates"><g:message code="event.eventCalendar.endDate" default="End" /></div><g:datePicker name="event.eventCalendar.endDate" value="${eventInstance?.eventCalendar?.endDate}" years="${2011..2020}" /></div>
                                    
                                    <div style="display: none" id="dayOfTheWeek"><div class="dates2"><g:message code="event.eventCalendar.dayOfTheWeek" default="Day of the Week" /></div><g:select name="eventCalendar.dayOfWeek" optionKey="key" optionValue="value" from="${application.mpDayOfTheWeek}" /></div>
                                    <div style="display: none" id="dayOfTheMonth"><div class="dates2"><g:message code="event.eventCalendar.dayOfTheMonth" default="Day of the Month" /></div><g:select name="eventCalendar.dayOfMonth" from="${1..31}" /></div>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activities"><g:message code="event.activities.label" default="Activities" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'activities', 'errors')}">                                    
								<ul>
									<g:each in="${eventInstance?.activities?}" var="a">
    									<li><g:link controller="activity" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
									</g:each>
								</ul>
						        </td>
						        <td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                        
                               <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="responses"><g:message code="event.responses.label" default="Responses" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'responses', 'errors')}">
                                 <ul>
									<g:each in="${eventInstance?.responses?}" var="r">
    									<li><g:link controller="surveyResponse" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
									</g:each>
								</ul>
                                </td>
                                 <td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><g:actionSubmit class="add" action="newactivity" value="${message(code: 'default.add.label', args: [message(code: 'activity.label', default: 'Activity')])}" /></span>
                
                </div>
            </g:form>
        </div>
    </body>
</html>
