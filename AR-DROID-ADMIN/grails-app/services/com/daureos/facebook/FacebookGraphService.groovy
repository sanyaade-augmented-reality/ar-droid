package com.daureos.facebook


import org.apache.commons.codec.digest.DigestUtils
import org.codehaus.groovy.grails.web.json.JSONArray;

import grails.converters.JSON
import grails.converters.XML;

import org.springframework.web.context.request.RequestContextHolder

/**
 * See http://github.com/facebook/php-sdk
 * @author jesus.lanchas
 *
 */
class FacebookGraphService {

	// Injected by grails
	def grailsApplication

	boolean transactional = false
	
	/**
	 * The domains that can be used in this class.
	 */
	final static def DOMAIN_MAP = [
		api:'https://api.facebook.com/',
		api_read:'https://api-read.facebook.com/',
		graph:'https://graph.facebook.com/',
		www:'https://www.facebook.com/']

		
	/**
	 * This method validates a facebook session
	 */
	def validateSession(facebookData) {
		def sig, expectedSig
		def result
		
		if(!grailsApplication.config.facebook.applicationSecret) {
			log.error("facebook.applicationSecret not defined in the Config.groovy")
		} else {
			// make sure some essential fields exist
			if(facebookData.uid && facebookData.session_key && facebookData.secret &&
				facebookData.access_token && facebookData.sig) {
				
				log.debug("Facebook data exists in the session")
				
				sig = facebookData.remove('sig')
				expectedSig = generateSig(facebookData, grailsApplication.config.facebook.applicationSecret)
				
				log.debug("Expected sig: ${expectedSig}")
				
				if(expectedSig == sig) {
					// The session is valid
					result = facebookData
					result.sig = sig
					log.debug("The facebook data is valid")
				}
			}
		}
		
		return result
	}
	
	/**
	 * This method returns the information stored by Facebook of the session user.
	 * If the session user hasn't associated a facebook session this method returns
	 * null.
	 */
	def getFacebookProfile() {
		def result
		def facebookData = getFacebookData()
		
		log.debug("Facebook data: ${facebookData}")
		
		if(facebookData) {
			try {
				result = api("/me", facebookData)
			} catch (Exception e) {
				log.error(e.message)
			}
		}
		
		return result
	}
	
	/**
	 * This method publishes the message passed as parameter in the
	 * wall of the session user. If the session user hasn't associated
	 * a facebook session this method returns null.
	 *
	 * @param message The message to publish
	 */
	def publishWall(message) {
		def result
		def facebookData = getFacebookData()
		
		log.debug("Facebook data: ${facebookData}")
		
		if(facebookData) {
			try {
				result = api("/me/feed", facebookData, [message:message], 'POST')
			} catch (Exception e) {
				log.error(e)
			}
		}
		
		return result
	}
	
	/**
	 * This method returns the list of friends in Facebook of the session user.
	 * If the session user hasn't associated a facebook session this method returns null.
	 */
	def getFriends() {
		def result
		def facebookData = getFacebookData()
		
		log.debug("Facebook data: ${facebookData}")
		
		if(facebookData) {
			try {
				result = api("/me/friends", facebookData)
			} catch (Exception e) {
				log.error(e)
			}
		}
		
		return result
	}
	
	/**
	 * This method returns the String that can be used by an authorized user
	 * as the url of the profile image of the facebook user whose id is passed
	 * as parameter.
	 */
	def getProfilePhotoSrc(facebookUserId) {
		"${DOMAIN_MAP.graph}/${facebookUserId}/picture"
	}
	

	
	/**
	 * Invoke the Graph API.
	 *
	 * @param String $path the path (required)
	 * @param String $method the http method (default 'GET')
	 * @param Array $params the query/post data
	 * @return the decoded response object
	 * @throws FacebookGraphException
	 */
	def api(path, facebookData, params = [:], method = 'GET',apiName='graph') {
		def exception
		def result
		params.method = method // method override as we always do a POST

		if(facebookData) { // without a facebook session we'll return null
			result = oauthRequest(getUrl(apiName, path), params, facebookData)
			if(!result) throw new FacebookGraphException()
			else result = JSON.parse(result)
			
			log.error("Result: ${result}")
			
			def error = null
			if (result instanceof JSONArray){
				error = result.error[0]
			}		
			else error = result.error
						
			// results are returned, errors are thrown
			if (error) {
				exception = new FacebookGraphException(error)
				if (exception.type == 'OAuthException') {
					invalidateFacebookData()
				}
				throw exception
			}
		}
		
		return result
	}

	/**
	 * Make a OAuth Request
	 *
	 * @param path the path (required)
	 * @param params the query/post data
	 * @return the decoded response object
	 */
	private def oauthRequest(url, params, facebookData) {
		if (!params['access_token']) {
			params['access_token'] = facebookData['access_token'];
		}

		// json_encode all params values that are not strings
		params.each{key,value ->
			if(!(value instanceof String)) params[key] = value as JSON
		}
		
		return makeRequest(url, params)
	}

	/**
	 * Makes an HTTP request.
	 *
	 * @param urlAsString the URL to make the request to
	 * @param params the parameters to use for the POST body
	 * @return String the response text
	 */
	private def makeRequest(urlAsString, params) {
		def resp
		def encodedParams = ""
		URL url
		Writer writer
		URLConnection connection
		def method  =params.method
		
		params.remove("method")
		// Encoding the params...
		if (params) {
			params.each{k,v->
				encodedParams += k.encodeAsURL() + '=' + v.encodeAsURL() + '&'
			}
			encodedParams = encodedParams[0..-2]
		}
		
		try {
			// Making the request
			
			switch(method) {
				case "GET":
					if(encodedParams) urlAsString += '?' + encodedParams
					url = new URL(urlAsString)
					println "URL " + url
					resp = url.text
				break;
				case "POST":
					url = new URL(urlAsString)
					println "URL " + url
					connection = url.openConnection()
					connection.setRequestMethod("POST")
					connection.doOutput = true
					
					writer = new OutputStreamWriter(connection.outputStream)
					writer.write(encodedParams)
					writer.flush()
					writer.close()
					connection.connect()
					
					if (connection.responseCode == 200 || connection.responseCode == 201)
						resp = connection.content.text
	
				break;
			}
		} catch(Exception e) {
			// resp will be null, nothing to do...
			log.error(e)
		}

		return resp
	}
 
	/**
	 * Build the URL for given domain alias, path and parameters.
	 *
	 * @param name the name of the domain
	 * @param path optional path (without a leading slash)
	 * @param params optional query parameters
	 * @return String the URL for the given parameters
	 */
	private def getUrl(name, path = '', params = [:]) {
		def url = DOMAIN_MAP[name]
		if (path) {
			if (path[0] == '/') {
				path = path.substring(1)
			}
			url += path
		}
		if (params) {
			url += '?'
			params.each{k,v->
				url += k.encodeAsURL() + '=' + v.encodeAsURL()
			}
		}
		return url
	}
	
	/**
	 * Generate a signature for the given params and secret.
	 *
	 * @param facebookData the parameters to sign
	 * @param secret the secret to sign with
	 * @return String the generated signature
	 */
	private def generateSig(facebookData, secret) {
		def base = ''
		// work with sorted data
		facebookData = facebookData as TreeMap

		facebookData.each {key,value ->
			base += key + '=' + value
		}
		
		base += secret
		
		return DigestUtils.md5Hex(base)
	}
	
	/**
	 * This method set null to the facebookData attribute stored in the
	 * session object associated with the session user.
	 */
	private void invalidateFacebookData() {
		def session = RequestContextHolder.currentRequestAttributes().getSession()
		if(session) session.facebookData = null
	}
	
	/**
	 * This method returns the facebookData stored in the session
	 * associated with the user
	 */
	private def getFacebookData() {
		def session = RequestContextHolder.currentRequestAttributes().getSession()
		return session?.facebook
	}
}
