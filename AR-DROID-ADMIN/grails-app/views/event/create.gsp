<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.Event" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'event.label', default: 'Event')}" />
        <title>Eventos</title>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />

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
  				
  				// cargar parametros de calendar
  				viewParams($('eventCalendar_select'));
  			}
  			
		</g:javascript>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="list" action="list">Volver al lista</g:link></span>
        </div>
        <div class="body">
            <h1>Nuevo Evento</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${eventInstance}">
            <div class="errors">
                <g:renderErrors bean="${eventInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="title">Tìtulo</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'title', 'errors')}">
                                    <g:textField name="title" value="${eventInstance?.title}" />
                                </td>
                                
                                <td valign="top" class="name">
                                    <label for="e">Entidad</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'entity', 'errors')}">
                                    <g:select name="entity.id" from="${ar.droid.admin.Entity.list()}" optionKey="id" value="${eventInstance?.entity?.id}"  />
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
                                    <label for="description">Descripción</label>
                                </td>
                                <td colspan="3" valign="top" class="value ${hasErrors(bean: eventInstance, field: 'description', 'errors')}">
                                    <g:textArea name="description" value="${eventInstance?.description}" />
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
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'photo', 'errors')}">
                                    <input type="file" id="photo" name="photo" />
                                </td>
                            </tr>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="surveyTemplate">Tipo de Evento</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'surveyTemplate', 'errors')}">
                                    <g:select name="typeEvent.id" from="${ar.droid.admin.TypeEvent.list()}" optionKey="id" value="${eventInstance?.typeEvent?.id}" />
                                </td>
                                
                                <td valign="top" class="name">
                                    <label for="surveyTemplate">Plantilla de encuesta</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'surveyTemplate', 'errors')}">
                                    <g:select name="surveyTemplate.id" from="${ar.droid.admin.survey.SurveyTemplate.list()}" optionKey="id" value="${eventInstance?.surveyTemplate?.id}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">                                
                                <td valign="top" class="name">
                                    <label for="surveyTemplate">Periodicidad</label>
                                </td>
                                <td valign="top" class="date value ${hasErrors(bean: eventInstance, field: 'eventCalendar', 'errors')}">
                                    <g:select name="eventCalendar_select" from="${application.lsEventCalendars}" optionKey="class" value="${request.eventCalendar_select}" onchange="viewParams(this);" class="w100" />
                                    <div><div class="dates"><g:message code="event.eventCalendar.startDate" default="Init" /></div><g:datePicker name="event.eventCalendar.startDate" value="${new Date()}" years="${2011..2020}" /></div>
                                    <div><div class="dates"><g:message code="event.eventCalendar.endDate" default="End" /></div><g:datePicker name="event.eventCalendar.endDate" value="${new Date()}" years="${2011..2020}" /></div>
                                    
                                    <div style="display: none" id="dayOfTheWeek" >Día de la semana<g:select name="eventCalendar.dayOfWeek" optionKey="key" optionValue="value" from="${application.mpDayOfTheWeek}" /></div>
                                    <div style="display: none" id="dayOfTheMonth">Día del mes<g:select name="eventCalendar.dayOfMonth" from="${1..31}" /></div>
                                </td>
                            </tr>
 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="Crear" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
