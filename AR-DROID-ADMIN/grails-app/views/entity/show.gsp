<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.Entity" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=CP-1250" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'entity.label', default: 'Entity')}" />
        <title>Entidades</title>
        
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
            <span class="menuButton"><g:link class="list" action="list">Volver al listado </g:link></span>
            <span class="menuButton"><g:link class="create" action="create">Nueva Entidad</g:link></span>
        </div>
        <div class="body">
            <h1>Entidad</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">                        
                            <td valign="top" class="name">Nombre</td>                            
                            <td valign="top" class="value">${fieldValue(bean: entityInstance, field: "name")}</td>
                            
                            <td valign="top" class="name">Url</td>                            
                            <td valign="top" class="value">${fieldValue(bean: entityInstance, field: "url")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Descripción</td>
                            <td valign="top" colspan="3" class="value">${fieldValue(bean: entityInstance, field: "description")}</td>
                        </tr>
                    
                    
                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="geoPoint">Ubicación</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'geoPoint', 'errors')}">
                                <div id="map_canvas" style="width: 500px; height: 250px;"></div>
                                <g:hiddenField  name="latitude" value="${entityInstance?.geoPoint.latitude}" />
                                <g:hiddenField  name="longitude" value="${entityInstance?.geoPoint.longitude}" />
                            </td>
                                
                          	<td valign="top" class="name">Foto</td>
                            
                            	<td valign="top" class="value">
                            	<g:if test="${entityInstance.photo}">
  									<img class="avatar" src="${createLink(controller:'entity', action:'showImage', id:entityInstance.id)}" />
								</g:if>
       						</td>
                        </tr>
                    
                        <tr class="prop">
                        	<td valign="top" class="name">Tipo de entidad</td>
                       	     <td valign="top" class="value"><g:link controller="typeEntity" action="show" id="${entityInstance?.typeEntity?.id}">${entityInstance?.typeEntity?.encodeAsHTML()}</g:link></td>
                       	     
                            <td valign="top" class="name"><g:message code="entity.readerNews.label" default="Reader News" /></td>
                            <td valign="top" class="value">${entityInstance?.readerNews?.encodeAsHTML()} (${entityInstance?.readerNews?.parameter})</td>
                        </tr>
                    
                        <tr class="prop">
                        	<td valign="top" class="name">Eventos</td>
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${entityInstance.events}" var="a">
                                    <li><g:link controller="event" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                            <td valign="top" class="name">Lector de actividades</td>
                            <td valign="top" class="value">${entityInstance?.readerActivity?.encodeAsHTML()} (${entityInstance?.readerActivity?.parameter})</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${entityInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="Editar" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="Eliminar" onclick="return confirm('Seguro de eliminar?');" /></span>
                    <span class="button"><g:actionSubmit class="add" action="newevent" params="['entity.id': entityInstance?.id]" value="Agregar evento" /></span>
                    <span class="button"><g:actionSubmit class="sinc" action="sinevent" value="Sincronizar eventos" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
