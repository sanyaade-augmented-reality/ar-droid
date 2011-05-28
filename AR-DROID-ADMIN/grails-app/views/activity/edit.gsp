<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.Activity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'activity.label', default: 'Activity')}" />
        <title>Actividades</title>
        
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
  			}
  			
		</g:javascript>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="list" action="list">Volver al listado</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">Nueva Actividad</g:link></span>
        </div>
        <div class="body">
            <h1>Editar Actividad</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${activityInstance}">
            <div class="errors">
                <g:renderErrors bean="${activityInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post"  enctype="multipart/form-data">
                <g:hiddenField name="id" value="${activityInstance?.id}" />
                <g:hiddenField name="version" value="${activityInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                
                                <td valign="top" class="name">
                                    <label for="name">Nombre</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activityInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${activityInstance?.name}" />
                                </td>
                            
                                <td valign="top" class="name">
                                    <label for="event">Evento</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activityInstance, field: 'event', 'errors')}">
		                            <g:select name="event.id" from="${ar.droid.admin.Event.list()}" optionKey="id" value="${activityInstance?.event?.id}"  />
                                </td>
                                
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description">Descripción</label>
                                </td>
                                <td colspan="3" valign="top" class="value ${hasErrors(bean: activityInstance, field: 'description', 'errors')}">
                                    <g:textArea name="description" value="${activityInstance?.description}" class="w100" />
                                </td>
                                
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="geoPoint">Ubicación</label>
                                </td>
                                <td colspan="3" valign="top" class="value ${hasErrors(bean: activityInstance, field: 'geoPoint', 'errors')}">
                                    <div><g:checkBox class="check" checked="true" name="positionSameAsEvent" value="1" onclick="displayMap('map_canvas', !this.checked);" /> Utilizar la misma ubicación del evento</div>
                                    <div id="map_canvas" style="width: 100%; height: 250px; visibility: hidden;"></div>
                                    <g:hiddenField  name="latitude" value="${activityInstance?.geoPoint.latitude}" />
                                    <g:hiddenField  name="longitude" value="${activityInstance?.geoPoint.longitude}" />
                                    <g:hiddenField  name="altitude" value="${activityInstance?.geoPoint.altitude}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="typeActivity">Tipo de Actividad</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: activityInstance, field: 'typeActivity', 'errors')}">
                                    <g:select name="typeActivity.id" from="${ar.droid.admin.TypeActivity.list()}" optionKey="id" value="${activityInstance?.typeActivity?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="Guardar" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="Eliminar" onclick="return confirm('Seguro de eliminar?');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
