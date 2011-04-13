
<%@ page import="ar.droid.admin.Event" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
     	<g:javascript src="js?sensor=false" base="http://maps.google.com/maps/api/" />
		<g:javascript src="maps.js" />
		<g:javascript library="prototype" />
		<g:javascript>
			Event.observe(window, 'load', initialize, true);
			
			function initialize() {
				initializeMap('map_canvas', $('latitude').value, $('longitude').value, 12);
  				if($('latitude').value != '' && $('longitude').value != ''){
  					var latlng = new google.maps.LatLng($('latitude').value, $('longitude').value);
  					placeMarker(latlng, $('latitude').value + '@' + $('longitude').value, true);
  				}
  			}
  			
		</g:javascript>
        
        <g:set var="entityName" value="${message(code: 'event.label', default: 'Event')}" />
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
                            <td valign="top" class="name"><g:message code="event.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "id")}</td>
                            
                            <td valign="top" class="name"><g:message code="event.eid.label" default="Eid" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "eid")}</td>
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.title.label" default="Title" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "title")}</td>
                            
                            <td valign="top" class="name"><g:message code="event.typeEvent.label" default="Type Event" /></td>
                            
                            <td valign="top" class="value"><g:link controller="typeEvent" action="show" id="${eventInstance?.typeEvent?.id}">${eventInstance?.typeEvent?.encodeAsHTML()}</g:link></td>
                       
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.description.label" default="Description" /></td>
                            
                            <td colspan="3" valign="top" class="value">${fieldValue(bean: eventInstance, field: "description")}</td>
                            
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
                                    <g:if test="${eventInstance.photo}">
                                    	<br /><br />
  										<img class="avatar" src="${createLink(controller:'event', action:'showImage', id:eventInstance.id)}" />
									</g:if>
                                </td>
                            </tr>
                        
                    
                        <tr class="prop">
                           <td valign="top" class="name"><g:message code="event.entity.label" default="Entity" /></td>
                            
                            <td valign="top" class="value"><g:link controller="entity" action="show" id="${eventInstance?.entity?.id}">${eventInstance?.entity?.encodeAsHTML()}</g:link></td>
                            
                            <td valign="top" class="name"><g:message code="event.surveyTemplate.label" default="Survey Template" /></td>
                            
                            <td valign="top" class="value"><g:link controller="surveyTemplate" action="show" id="${eventInstance?.surveyTemplate?.id}">${eventInstance?.surveyTemplate?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.eventCalendar.label" default="Event Calendar" /></td>
                            <td valign="top" class="value">${eventInstance?.eventCalendar?.encodeAsHTML()}</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            
                        </tr>
                          
                         <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.activities.label" default="Activities" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${eventInstance.activities}" var="a">
                                    <li><g:link controller="activity" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                      
                    

                    
                       
                
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.responses.label" default="Responses" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${eventInstance.responses}" var="r">
                                    <li><g:link controller="surveyResponse" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                  
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${eventInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
