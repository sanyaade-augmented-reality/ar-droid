<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ar.droid.admin.Event" %>
<%@ page import="ar.droid.admin.survey.question.*" %>
<%@ page import="ar.droid.admin.survey.response.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Estadísticas de evento</title>
    	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
    	<script type="text/javascript">
    		google.load("visualization", "1", {packages:["corechart"]});
    	</script>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Inicio</a></span>
            <span class="menuButton"><g:link controller="event" class="show" action="show" id="${eventInstance.id}">Volver al evento</g:link></span>
        </div>
        <div class="body">
            <h1>Estadísticas del evento <strong>${eventInstance.title}</strong></h1>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                        	<td valign="top" class="name">Visitas Cliente</td>                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "clientVisits")}</td>
                         	<td valign="top" class="name">Visitas Web</td>                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "webVisits")}</td>
                        </tr>
                    	
                    	<tr>
                    		<td colspan="4">&nbsp;</td>
                    	</tr>
                    	
                    	<tr>
                    		<td colspan="4">
                    			<g:render template="template${question.getClass().getSimpleName()}" model="[responses: responses, question: question]" />
                    		</td>
                    	</tr>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
