<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ar.droid.admin.Entity" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'entity.label', default: 'Entity')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />

		<g:javascript src="js?sensor=false" base="http://maps.google.com/maps/api/" />
		<g:javascript src="maps.js" />
		<g:javascript library="prototype" />
		<g:javascript>
			Event.observe(window, 'load', initialize, true);
			
			function initialize() {
				initializeMap('map_canvas', $('latitude').value, $('longitude').value, 12);
				
				google.maps.event.addListener(mapInstance, 'click', function(event){
  					placeMarker(event.latLng, event.latLng.lat() + '@' + event.latLng.lng(), true);
  					var clickedLocation = new google.maps.LatLng(event.latLng);
  					
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
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${entityInstance}">
            <div class="errors">
                <g:renderErrors bean="${entityInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save"  enctype="multipart/form-data">
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="entity.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${entityInstance?.name}" />
                                </td>
                                
                                <td valign="top" class="name">
                                    <label for="url"><g:message code="entity.url.label" default="Url" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'url', 'errors')}">
                                    <g:textField name="url" value="${entityInstance?.url}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="entity.description.label" default="Description" /></label>
                                </td>
                                <td colspan="3" valign="top" class="value ${hasErrors(bean: entityInstance, field: 'description', 'errors')}">
                                    <g:textArea name="description" value="${entityInstance?.description}" class="w100" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="geoPoint"><g:message code="entity.geoPoint.label" default="Geo Point" /></label>
                                </td>
                                <td colspan="3" valign="top" class="value ${hasErrors(bean: entityInstance, field: 'geoPoint', 'errors')}">
                                    <div id="map_canvas" style="width: 100%; height: 250px;"></div>
                                    <g:hiddenField  name="latitude" value="${entityInstance?.geoPoint.latitude}" />
                                    <g:hiddenField  name="longitude" value="${entityInstance?.geoPoint.longitude}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="typeEntity"><g:message code="entity.typeEntity.label" default="Type Entity" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'typeEntity', 'errors')}">
                                    <g:select name="typeEntity.id" from="${ar.droid.admin.TypeEntity.list()}" optionKey="id" value="${entityInstance?.typeEntity?.id}"  />
                                </td>
                                
                                <td valign="top" class="name">
                                    <label for="photo"><g:message code="entity.photo.label" default="Photo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'photo', 'errors')}">
                                    <input type="file" id="photo" name="photo" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="readerActivity"><g:message code="entity.readerActivity.label" default="Reader Activity" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'readerActivity', 'errors')}">
                                    <g:select name="readerActivity.id" from="${ar.droid.admin.ReaderActivity.list()}" optionKey="id" value="${entityInstance?.readerActivity?.id}"  />
                                </td>
                                
                                <td valign="top" class="name">
                                    <label for="readerNews"><g:message code="entity.readerNews.label" default="Reader News" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: entityInstance, field: 'readerNews', 'errors')}">
                                    <g:select name="readerNews.id" from="${ar.droid.admin.ReaderNews.list()}" optionKey="id" value="${entityInstance?.readerNews?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
