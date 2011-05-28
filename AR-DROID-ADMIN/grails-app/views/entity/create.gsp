<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.Entity" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'entity.label', default: 'Entity')}" />
        <title>Entidades</title>
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
  				
  				// asignar parametros de lectores
  				viewParams($('readerNews_select'));
  				viewParams($('readerActivity_select'));
  			}
  			
		</g:javascript>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>            
            <span class="menuButton"><g:link class="list" action="list">Volver al listado </g:link></span>
        </div>
        <div class="body">
            <h1>Nueva Entidad</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${entityInstance}">
            <div class="errors">
                <g:renderErrors bean="${entityInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:uploadForm action="save">
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Nombre</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${entityInstance?.name}" />
                                </td>
                                
                                <td valign="top" class="name">
                                    <label for="url">Url</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'url', 'errors')}">
                                    <g:textField name="url" value="${entityInstance?.url}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description">Descripción</label>
                                </td>
                                <td colspan="3" valign="top" class="value ${hasErrors(bean: entityInstance, field: 'description', 'errors')}">
                                    <g:textArea name="description" value="${entityInstance?.description}" class="w100" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="geoPoint">Ubicación</label>
                                </td>
                                <td colspan="3" valign="top" class="value ${hasErrors(bean: entityInstance, field: 'geoPoint', 'errors')}">
                                    <div id="map_canvas" style="width: 100%; height: 250px;"></div>
                                    <g:hiddenField  name="latitude" value="${entityInstance?.geoPoint.latitude}" />
                                    <g:hiddenField  name="longitude" value="${entityInstance?.geoPoint.longitude}" />
                                    <g:hiddenField  name="altitude" value="${entityInstance?.geoPoint.altitude}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="typeEntity">Tipo de Entidad</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'typeEntity', 'errors')}">
                                    <g:select name="typeEntity.id" from="${ar.droid.admin.TypeEntity.list()}" optionKey="id" value="${entityInstance?.typeEntity?.id}"  />
                                </td>
                                
                                <td valign="top" class="name">
                                    <label for="photo">Imagen</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'photo', 'errors')}">
                                    <input type="file" id="photo" name="photo" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="readerActivity">Lector de actividades</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'readerActivity', 'errors')}">
                                    <g:select name="readerActivity_select" from="${application.lsReaderActivities}" optionKey="class" value="${request.readerActivity_select}" onchange="viewParams(this);" />
                                    <div style="display: none" id="readerActivity_parameter"><div class="subtitle2"><g:message code="entity.param.label" default="Parameter" /></div><g:textField class="input_param2" name="readerActivity.parameter" id="readerActivity.parameter" value="${entityInstance?.readerActivity?.parameter}" /></div>
                                </td>
                                
                                <td valign="top" class="name">
                                    <label for="readerNews">Lector de noticias</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'readerNews', 'errors')}">
                                    <g:select name="readerNews_select" from="${application.lsReaderNews}" optionKey="class" value="${request.readerNews_select}" onchange="viewParams(this);" />
                                    <div style="display: none" id="readerNews_parameter"><div class="subtitle2"><g:message code="entity.param.label" default="Parameter" /></div><g:textField class="input_param2" name="readerNews.parameter" id="readerNews.parameter" value="${entityInstance?.readerNews?.parameter}" /></div>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="Crear" /></span>
                </div>
            </g:uploadForm>
        </div>
    </body>
</html>
