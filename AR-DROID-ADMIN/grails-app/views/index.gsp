<%@ page contentType="text/html;charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://www.facebook.com/2008/fbml">
    <head>
        <title>Administración de AR-DROID</title>
        <meta name="layout" content="main" />
		<script src="http://connect.facebook.net/en_US/all.js"></script>
        <style type="text/css" media="screen">

        #nav {
            margin-top:20px;
            margin-left:30px;
            width:228px;
            float:left;

        }
        .homePagePanel * {
            margin:0px;
        }
        .homePagePanel .panelBody ul {
            list-style-type:none;
            margin-bottom:10px;
        }
        .homePagePanel .panelBody h1 {
            text-transform:uppercase;
            font-size:1.1em;
            margin-bottom:10px;
        }
        .homePagePanel .panelBody {
            background: url(images/leftnav_midstretch.png) repeat-y top;
            margin:0px;
            padding:15px;
        }
        .homePagePanel .panelBtm {
            background: url(images/leftnav_btm.png) no-repeat top;
            height:20px;
            margin:0px;
        }

        .homePagePanel .panelTop {
            background: url(images/leftnav_top.png) no-repeat top;
            height:11px;
            margin:0px;
        }
        h2 {
            margin-top:15px;
            margin-bottom:15px;
            font-size:1.2em;
        }
        #pageBody {
            margin-left:280px;
            margin-right:20px;
        }
        </style>
    </head>
    <body>
        <div id="nav">
            <div class="homePagePanel">
                <div class="panelTop"></div>
                <div class="panelBody">
                    <h1>Sobre la Aplicación</h1>
                    <ul>
                        <li>App version: <g:meta name="app.version"></g:meta></li>
                        <li>Grails version: <g:meta name="app.grails.version"></g:meta></li>
                        <li>Groovy version: ${org.codehaus.groovy.runtime.InvokerHelper.getVersion()}</li>
                        <li>JVM version: ${System.getProperty('java.version')}</li>
                        <li>Controllers: ${grailsApplication.controllerClasses.size()}</li>
                        <li>Domains: ${grailsApplication.domainClasses.size()}</li>
                        <li>Services: ${grailsApplication.serviceClasses.size()}</li>
                        <li>Tag Libraries: ${grailsApplication.tagLibClasses.size()}</li>
                    </ul>
                    <h1>Plugins Instalados</h1>
                    <ul>
                        <g:set var="pluginManager"
                               value="${applicationContext.getBean('pluginManager')}"></g:set>

                        <g:each var="plugin" in="${pluginManager.allPlugins}">
                            <li>${plugin.name} - ${plugin.version}</li>
                        </g:each>

                    </ul>
                </div>
                <div class="panelBtm"></div>
            </div>
        </div>
        <div id="pageBody">
            <h1>Administración AD-DROID</h1>
            <p>Bienvenido a la aplicación de administración del sistema <b>AR-DROID</b>. Desde aquí podrá administrar todo
            	referente a eventos y actividades de las entidades definidas.</p>
            <p>Por favor, seleccione una de las opciones definidas mas abajo para empezar.</p>
			<br />
            
            <g:link class="index_link" controller="typeEntity">Tipos de entidades</g:link>
            <g:link class="index_link" controller="typeEvent">Tipos de eventos</g:link>
            <g:link class="index_link" controller="typeActivity">Tipos de actividades</g:link>
            <br />
            <g:link class="index_link" controller="entity">Entidades</g:link>
            <g:link class="index_link" controller="event">Eventos</g:link>
            <g:link class="index_link" controller="activity">Actividades</g:link>
            <br />
            <g:link class="index_link" controller="surveyTemplate">Plantillas</g:link>
            <g:link class="index_link" controller="question">Preguntas</g:link>
            <br />
            <g:link class="index_link" controller="entity" action="sincronizeallvent">Sinronizar eventos</g:link><br /><br />

            <g:link class="index_link icon icon_cross" controller="auth" action="logout">Logout</g:link>
                        	
            <fbg:resources locale="${Locale.getDefault()}" />
			<script>
					FB.init({appId:'122540491154119', cookie: true, xfbml: true, status: true});				
					function facebookLogin() {
						FB.getLoginStatus(function(response) {
						if (response.session) {
							// logged in and connected user, someone you know
							//window.location = "${createLink(controller:'auth', action:'facebookLogin')}";
						}
					});
					}
						
			</script>
			<fb:login-button perms="email,publish_stream" onlogin="facebookLogin();" size="large">
				<g:message code="Autorizar aplicaciones Facebook"/>
			</fb:login-button>
			<!-- 
			<div id="controllerList" class="dialog">	
                <h2>Administrar:</h2>
                <ul>
                    <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                        <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
                    </g:each>
                </ul>
            </div>
             -->
             <br />
             <br />
             <a href="client/AR-DROID.apk" style="font-size: 20px; font-weight: bold; text-align: right;">DESCARGAR APLICACIÓN CLIENTE</a>
        </div>
    </body>
</html>
