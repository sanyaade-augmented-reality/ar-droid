<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.Event" %>
<html>
    <head>
        
        <meta name="layout" content="main" />
        <g:javascript src="js?sensor=false" base="http://maps.google.com/maps/api/" />
		<g:javascript src="maps.js" />
		<g:javascript src="viewparams.js" />
		<g:javascript library="prototype" />
		<g:javascript>
			Event.observe(window, 'load', initialize, true);
			var elevator = new google.maps.ElevationService();
			
			function initialize() {
				initializeMap('map_canvas', $('latitude').value, $('longitude').value, 12);
				
				google.maps.event.addListener(mapInstance, 'click', function(event){
  					placeMarker(event.latLng, event.latLng.lat() + '@' + event.latLng.lng(), true);
  					var clickedLocation = new google.maps.LatLng(event.latLng);
  					
  					var locations = [];
  					locations.push(event.latLng);
					var positionalRequest = {
    					'locations': locations
  					}
  
  					// traer altitud
  					elevator.getElevationForLocations(positionalRequest, function(results, status){
						if (status == google.maps.ElevationStatus.OK){
							if (results[0])
								$('altitude').value = results[0].elevation.toFixed(2);
							else
								$('altitude').value = '0';
						}
					});
					
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
        <title>Eventos</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="list" action="list">Volver al lista</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">Nuevo evento</g:link></span>
        </div>
        <div class="body">
            <h1>Editar Evento</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${eventInstance}">
            <div class="errors">
                <g:renderErrors bean="${eventInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:uploadForm action="save">
                <g:hiddenField name="id" value="${eventInstance?.id}" />
                <g:hiddenField name="version" value="${eventInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="title">Título</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'title', 'errors')}">
                                    <g:textField name="title" value="${eventInstance?.title}" />
                                    <g:hiddenField name="eid" value="${eventInstance?.eid}" />
                                </td>
                                 <td valign="top" class="name">
                                  <label for="entity">Entidad</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'entity', 'errors')}">
                                    <g:select name="entity.id" from="${ar.droid.admin.Entity.list()}" optionKey="id" value="${eventInstance?.entity?.id}"  />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description">Descripción</label>
                                </td>
                                <td colspan="3" valign="top" class="value ${hasErrors(bean: eventInstance, field: 'description', 'errors')}">
                                     <g:textArea name="description" value="${eventInstance?.description}" />                                  
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="title">Lugar</label>
                                </td>
                                <td colspan="3" valign="top" class="value ${hasErrors(bean: eventInstance, field: 'place', 'errors')}">
                                    <g:textField name="place" value="${eventInstance?.place}" />
                                </td>
                            </tr>
                                    
                            
                             <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="geoPoint">Ubicación</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'geoPoint', 'errors')}">
                                	<div><g:checkBox class="check" checked="false" name="positionSameAsEntity" value="1" onclick="displayMap('map_canvas', !this.checked);" /> Utilizar la misma ubicación de la entidad</div>
                                	<div id="map_canvas" style="width: 100%; height: 250px;"></div>
                                    <g:hiddenField name="latitude" value="${eventInstance?.geoPoint.latitude}" />
                                    <g:hiddenField name="longitude" value="${eventInstance?.geoPoint.longitude}" />
                                    <g:hiddenField  name="altitude" value="${eventInstance?.geoPoint.altitude}" />
                                </td>
                                
                               
                                 <td valign="top" class="name">
                                    <label for="photo">Foto</label>
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
                                  <label for="typeEvent">Tipo de Evento</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'typeEvent', 'errors')}">
                                    <g:select name="typeEvent.id" from="${ar.droid.admin.TypeEvent.list()}" optionKey="id" value="${eventInstance?.typeEvent?.id}"  />
                                </td>
                                <td valign="top" class="name">Plantilla de Encuesta</td>
                            
                                <td valign="top" class="value"><g:link controller="surveyTemplate" action="show" id="${eventInstance?.surveyTemplate?.id}">${eventInstance?.surveyTemplate?.encodeAsHTML()}</g:link></td>
                            
                            </tr>
                        
                           <tr class="prop">                                
                                <td valign="top" class="name">
                                    <label for="surveyTemplate">Periodicidad</label>
                                </td>
                                <td valign="top" class="date value ${hasErrors(bean: eventInstance, field: 'eventCalendar', 'errors')}">
                                    <g:select name="eventCalendar_select" from="${application.lsEventCalendars}" optionKey="class" value="${request.eventCalendar_select}" onchange="viewParams(this);" class="w100" />
                                    <div><div class="dates"><g:message code="event.eventCalendar.startDate" default="Init" /></div><g:datePicker name="event.eventCalendar.startDate" value="${eventInstance?.eventCalendar?.startDate}" years="${2011..2020}" /></div>
                                    <div><div class="dates"><g:message code="event.eventCalendar.endDate" default="End" /></div><g:datePicker name="event.eventCalendar.endDate" value="${eventInstance?.eventCalendar?.endDate}" years="${2011..2020}" /></div>
                                    
                                    <div style="display: none" id="dayOfTheWeek"><div class="dates2">Día de la semana</div><g:select name="event.eventCalendar.dayOfWeek" optionKey="key" optionValue="value" from="${application.mpDayOfTheWeek}" /></div>
                                    <div style="display: none" id="dayOfTheMonth"><div class="dates2">Día del mes</div><g:select name="event.eventCalendar.dayOfMonth" from="${1..31}" /></div>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activities">Actividades</label>
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
                        
                               
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><g:actionSubmit class="add" action="newactivity" value="${message(code: 'default.add.label', args: [message(code: 'activity.label', default: 'Activity')])}" /></span>
                
                </div>
            </g:uploadForm>
        </div>
    </body>
</html>
