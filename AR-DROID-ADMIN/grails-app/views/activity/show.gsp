<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.Activity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=CP-1250" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'activity.label', default: 'Activity')}" />
        <title>Actividades</title>
        
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
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link class="list" action="list">Volver al listado</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">Nueva Actividad</g:link></span>
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
                            <td valign="top" class="name">ID</td>
                            <td valign="top" class="value">${fieldValue(bean: activityInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Nombre</td>                            
                            <td valign="top" class="value">${fieldValue(bean: activityInstance, field: "name")}</td>
                            <td valign="top" class="name">Evento</td>                            
                            <td valign="top" class="value"><g:link controller="event" action="show" id="${activityInstance?.event?.id}">${fieldValue(bean: activityInstance, field: "event")}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Descripción</td>
                            
                            <td colspan="3" valign="top" class="value">${fieldValue(bean: activityInstance, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                        	<td valign="top" class="name">
                                <label for="geoPoint">Ubicación</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: activityInstance, field: 'geoPoint', 'errors')}">
                                <div id="map_canvas" style="width: 500px; height: 250px;"></div>
                                <g:hiddenField  name="latitude" value="${activityInstance?.geoPoint.latitude}" />
                                <g:hiddenField  name="longitude" value="${activityInstance?.geoPoint.longitude}" />
                            </td>
                            
                            <td valign="top" class="name">Tipo de Actividad</td>
                            
                            <td valign="top" class="value"><g:link controller="typeActivity" action="show" id="${activityInstance?.typeActivity?.id}">${activityInstance?.typeActivity?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${activityInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="Editar" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="Eliminar" onclick="return confirm('Seguro de eliminar?');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
