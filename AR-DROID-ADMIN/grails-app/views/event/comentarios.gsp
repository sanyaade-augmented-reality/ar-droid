<%@ page contentType="text/html;charset=UTF-8" import="ar.droid.admin.Event" %>
<html>
    <head>
        
        <meta name="layout" content="empty" />
     	<g:javascript src="js?sensor=false" base="http://maps.google.com/maps/api/" />
		<g:javascript src="maps.js" />
		<g:javascript library="prototype" />
        <g:set var="entityName" value="${message(code: 'event.label', default: 'Event')}" />
    </head>
    <body>
        <div class="body">
            <div class="dialog">
                <table>
                    <tbody>
                        
                        <tr>
                            <td valign="top" class="name" >Titulo</td>
                            
                            <td valign="top" class="value"><strong>${fieldValue(bean: eventInstance, field: "title")}</strong></td>
                            
                            <td valign="top" class="name">Tipo de Evento</td>
                            
                            <td valign="top" class="value">${eventInstance?.typeEvent?.encodeAsHTML()}</td>
                       
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name">Descripción</td>
                            
                            <td colspan="3" valign="top" class="value">${fieldValue(bean: eventInstance, field: "description")}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name">Comentarios</td>
                            
                            <td colspan="3" valign="top" class="value">
                            	<table width="100%">
                            	<g:each in="${comentarios}" status="i" var="comentario">
			                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			                            <td width="20%">${fieldValue(bean: comentario, field: "fecha")}</td>
			                            <td width="80%">${fieldValue(bean: comentario, field: "comment")}</td>
			                        </tr>
			                    </g:each>
                            	</table>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
