package ar.droid.admin

import grails.converters.JSON

class RequestController {
	
	def typeEntities = {
		render TypeEntity.list() as JSON
	}
	
	def typeActivities = {
		render TypeActivity.list() as JSON
	}

    def typeEvents = {
		render TypeEvent.list() as JSON
	}

    def entities = {
		render Entity.list() as JSON
	}
}
