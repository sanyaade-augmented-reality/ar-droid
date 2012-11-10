grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: [
		'text/html',
		'application/xhtml+xml'
	],
	xml: [
		'text/xml',
		'application/xml'
	],
	text: 'text/plain',
	js: 'text/javascript',
	rss: 'application/rss+xml',
	atom: 'application/atom+xml',
	css: 'text/css',
	csv: 'text/csv',
	all: '*/*',
	json: [
		'application/json',
		'text/json'
	],
	form: 'application/x-www-form-urlencoded',
	multipartForm: 'multipart/form-data'
]
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
grails.views.gsp.sitemesh.preprocess = true
grails.scaffolding.templates.domainSuffix = 'Instance'

grails.json.legacy.builder = false
grails.enable.native2ascii = true
grails.logging.jul.usebridge = true
grails.spring.bean.packages = []

grails.views.javascript.library = "prototype"

// set per-environment serverURL stem for creating absolute links
environments {
	production { grails.serverURL = "http://www.gabrielnegri.com.ar:8080/server" }
	development { grails.serverURL = "http://www.gabrielnegri.com.ar:8080/server" }
	test { grails.serverURL = "http://www.gabrielnegri.com.ar:8080/server" }

}

// log4j configuration
log4j = {
	rootLogger="debug,stdout"

	appenders {
		console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
	}

	info  'org.codehaus.groovy.grails.web.servlet',  //  controllers
			'org.codehaus.groovy.grails.web.pages', //  GSP
			'org.codehaus.groovy.grails.web.sitemesh', //  layouts
			'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
			'org.codehaus.groovy.grails.web.mapping', // URL mapping
			'org.codehaus.groovy.grails.commons', // core / classloading
			'org.codehaus.groovy.grails.plugins', // plugins
			'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
			'org.springframework',
			'org.hibernate',
			'net.sf.ehcache.hibernate', 'org.mortbay.log'
}

facebook.applicationSecret='a511dae2169d13558351bd2aa4ff2966'
facebook.applicationId='122540491154119'
facebook.secure=false
