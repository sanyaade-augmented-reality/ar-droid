<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.Entity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'entity.label', default: 'Entity')}" />
        <title>Entidades</title>
        
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
            <span class="menuButton"><g:link class="create" action="create">Nueva Entidad</g:link></span>
        </div>
        <div class="body">
            <h1>Editar Entidad</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${entityInstance}">
            <div class="errors">
                <g:renderErrors bean="${entityInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:uploadForm method="post">
                <g:hiddenField name="id" value="${entityInstance?.id}" />
                <g:hiddenField name="version" value="${entityInstance?.version}" />
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
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'geoPoint', 'errors')}">
                                    <div id="map_canvas" style="width: 500px; height: 250px;"></div>
                                    <g:hiddenField  name="latitude" value="${entityInstance?.geoPoint.latitude}" />
                                    <g:hiddenField  name="longitude" value="${entityInstance?.geoPoint.longitude}" />
                                    <g:hiddenField  name="altitude" value="${entityInstance?.geoPoint.altitude}" />
                                </td>
                                
                                <td valign="top" class="name">
                                  <label for="photo">Foto</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'photo', 'errors')}">
                                    <input type="file" id="photo" name="photo" />
                                    <g:if test="${entityInstance.photo}">
                                    	<br /><br />
  										<img class="avatar" src="${createLink(controller:'entity', action:'showImage', id:entityInstance.id)}" />
									</g:if>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="typeEntity">Tipo de entidad</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'typeEntity', 'errors')}">
                                    <g:select name="typeEntity.id" from="${ar.droid.admin.TypeEntity.list()}" optionKey="id" value="${entityInstance?.typeEntity?.id}"  />
                                </td>
                                <td>&nbsp;</td> 
                                <td>&nbsp;</td>
                               
                            </tr>
                            
                             <tr class="prop">
                                
                                <td valign="top" class="name">
                                  <label for="readerActivity">Lector de actividades</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'readerNews', 'errors')}">
                                    <g:select name="readerActivity_select" from="${application.lsReaderActivities}" optionKey="class" value="${request.readerActivity_select}" onchange="viewParams(this);" />
                                    <div style="display: none" id="readerActivity_parameter"><div class="subtitle2"><g:message code="entity.parameter.label" default="Parameter" /></div><g:textField class="input_param2" name="readerActivity.parameter" value="${entityInstance?.readerActivity?.parameter}" /></div>
                                </td>
                                
                                 <td valign="top" class="name">
                                  <label for="readerNews">Lector de noticias</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'readerActivity', 'errors')}">
                                    <g:select name="readerNews_select" from="${application.lsReaderNews}" optionKey="class" value="${request.readerNews_select}" onchange="viewParams(this);" />
                                    <div style="display: none" id="readerNews_parameter"><div class="subtitle2"><g:message code="entity.parameter.label" default="Parameter" /></div><g:textField class="input_param2" name="readerNews.parameter" value="${entityInstance?.readerNews?.parameter}" /></div>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="events">Eventos</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'activities', 'errors')}">
                                    <ul>
									<g:each in="${entityInstance?.events?}" var="a">
   										<li><g:link controller="activity" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
									</g:each>
									</ul>
                                </td>                                
                                <td>&nbsp;</td> 
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="Guardar" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="Eliminar" onclick="return confirm('Seguro de eliminar?');" /></span>
                    <span class="button"><g:actionSubmit class="add" action="newevent" params="['entity.id': entityInstance?.id]" value="Agregar evento" /></span>
                    <span class="button"><g:actionSubmit class="sinc" action="sinevent" value="Sincronizar eventos" /></span>
                </div>
            </g:uploadForm>
        </div>
    </body>
</html>
