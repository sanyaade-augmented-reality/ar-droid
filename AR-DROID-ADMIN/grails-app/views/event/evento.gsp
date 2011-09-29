<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.Event" %>
<html>
    <head>
        
        <meta name="layout" content="empty" />
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
    </head>
    <body>
        <div class="body">
            <div class="dialog">
                <table>
                    <tbody>
                        
                        <tr class="prop">
                            <td valign="top" class="name">Título</td>
                            
                            <td valign="top" class="value"><strong>${fieldValue(bean: eventInstance, field: "title")}</strong></td>
                            
                            <td valign="top" class="name">Tipo de Evento</td>
                            
                            <td valign="top" class="value">${eventInstance?.typeEvent?.encodeAsHTML()}</td>
                       
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name">Descripción</td>
                            
                            <td colspan="3" valign="top" class="value">${fieldValue(bean: eventInstance, field: "description")}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name">Lugar</td>
                            
                            <td colspan="3" valign="top" class="value">${fieldValue(bean: eventInstance, field: "place")}</td>
                            
                        </tr>
                    
                          <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="geoPoint">Ubicación</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'geoPoint', 'errors')}">
                                	<div id="map_canvas" style="width: 100%; height: 250px;"></div>
                                    <g:hiddenField name="latitude" value="${eventInstance?.geoPoint.latitude}" />
                                    <g:hiddenField name="longitude" value="${eventInstance?.geoPoint.longitude}" />
                                </td>
                                
                                <td valign="top" class="name">
                                    <label for="photo">Foto</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: eventInstance, field: 'photo', 'errors')}">
                                    <g:if test="${eventInstance.photo}">
                                    	<br /><br />
  										<img class="avatar" src="${createLink(controller:'event', action:'showImage', id:eventInstance.id)}" />
									</g:if>
                                </td>
                            </tr>
                        
                    
                        <tr class="prop">
                           <td valign="top" class="name">Entidad</td>                            
                            <td valign="top" class="value"><strong>${eventInstance?.entity?.encodeAsHTML()}</strong></td>
                            
                            <td valign="top" class="name">Periodicidad</td>
                            <td valign="top" class="value">${eventInstance?.eventCalendar?.encodeAsHTML()}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            
                        </tr>
                          
                         <tr class="prop">
                            <td valign="top" class="name">Actividades</td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${eventInstance.activities}" var="a">
                                    <li>${a?.encodeAsHTML()}</li>
                                </g:each>
                                </ul>
                            </td>
                        </tr>
                    
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
