<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.Event" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=CP-1250" />
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
        <title>Eventos</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="list" action="list">Volver al lista</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">Nuevo evento</g:link></span>
        </div>
        <div class="body">
            <h1>Evento</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name">ID</td>
                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "id")}</td>
                            
                            <td valign="top" class="name">EID</td>
                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "eid")}</td>
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name">Título</td>
                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "title")}</td>
                            
                            <td valign="top" class="name">Tipo de Evento</td>
                            
                            <td valign="top" class="value"><g:link controller="typeEvent" action="show" id="${eventInstance?.typeEvent?.id}">${eventInstance?.typeEvent?.encodeAsHTML()}</g:link></td>
                       
                            
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
                            
                            <td valign="top" class="value"><g:link controller="entity" action="show" id="${eventInstance?.entity?.id}">${eventInstance?.entity?.encodeAsHTML()}</g:link></td>
                            
                            <td valign="top" class="name">Plantilla de Encuesta</td>
                            
                            <td valign="top" class="value"><g:link controller="surveyTemplate" action="show" id="${eventInstance?.surveyTemplate?.id}">${eventInstance?.surveyTemplate?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name">Periodicidad</td>
                            <td valign="top" class="value">${eventInstance?.eventCalendar?.encodeAsHTML()}</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            
                        </tr>
                          
                         <tr class="prop">
                            <td valign="top" class="name">Actividades</td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${eventInstance.activities}" var="a">
                                    <li><g:link controller="activity" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
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
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="Editar" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="Eliminar" onclick="return confirm('Seguro de eliminar?');" /></span>
                    <span class="button"><g:link class="stats" controller="stats" action="show" id="${eventInstance?.id}">Estad&iacute;sticas</g:link></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
