package ar.droid.admin

import grails.converters.JSON;

import java.util.ArrayList

import ar.droid.admin.reader.*;

class EntityController {
	def entityService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [entityInstanceList: Entity.list(params), entityInstanceTotal: Entity.count()]
    }

    def create = {
        def entityInstance = new Entity()
		entityInstance.geoPoint = new GeoPoint()
        entityInstance.properties = params
			
        return [entityInstance: entityInstance]
    }

    def save = {		
		def entityInstance = entityService.saveEntity(params)	
		if(entityInstance.hasErrors()){
			request.readerActivity_select = entityInstance.readerActivity.class
			request.readerNews_select = entityInstance.readerNews.class
			render(view: "create", model: [entityInstance: entityInstance])
		}
		else{
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'entity.label', default: 'Entidad'), entityInstance.id])}"
			redirect(action: "show", id: entityInstance.id)
		}
    }

    def show = {
        def entityInstance = Entity.get(params.id)
        if (!entityInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entity.label', default: 'Entidad'), params.id])}"
            redirect(action: "list")
        }
        else {
            [entityInstance: entityInstance]
        }
    }

	def showImage ={
		def entityInstance = Entity.get(params.id)
		response.contentType = "image/jpeg"
		response.contentLength = entityInstance?.photo.length
		response.outputStream.write(entityInstance?.photo)
	}
	
    def edit = {		
        def entityInstance = Entity.get(params.id)
        if (!entityInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entity.label', default: 'Entidad'), params.id])}"
            redirect(action: "list")
        }
        else {
			request.readerActivity_select = entityInstance.readerActivity.class
			request.readerNews_select = entityInstance.readerNews.class
            return [entityInstance: entityInstance]
        }
    }

    def update = {
		def entityInstance = entityService.updateEntity(params)
		if(entityInstance.hasErrors()){
			request.readerActivity_select = entityInstance.readerActivity.class
			request.readerNews_select = entityInstance.readerNews.class
			render(view: "edit", model: [entityInstance: entityInstance])
		}
		else{
			flash.message = "${message(code: 'default.updated.message', args: [message(code: 'entity.label', default: 'Entidad'), entityInstance.id])}"
			redirect(action: "show", id: entityInstance.id)
		}
    }

    def delete = {
        def entityInstance = Entity.get(params.id)
        if (entityInstance) {
            try {
                entityInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'entity.label', default: 'Entidad'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'entity.label', default: 'Entidad'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entity.label', default: 'Entidad'), params.id])}"
            redirect(action: "list")
        }
    }
	
	def newevent = {
        def entityInstance = Entity.get(params.id)
		redirect(controller: "event", action: "create", entity: params.id)
	}
	
	def sinevent = {
		def entityInstance = Entity.get(params.id)
		entityService.synchronizeEvents(entityInstance, params);
		redirect(action: "show", id: params.id)
	}
	
	def sincronizeallvent = {
		Entity.list().each{ entityInstance ->
			entityService.synchronizeEvents(entityInstance, params)
		}
		redirect(action: "list")
	}
}
