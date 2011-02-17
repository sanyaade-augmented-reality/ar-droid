package ar.droid.admin

import java.util.ArrayList;
import grails.converters.JSON

class EntityController {

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
	
	/*de prueba para conexion desde android*/
	def entityJson ={
		def entityInstance1 = new Entity(name:"1",description:"entidad 1",url:"http:\\marisa.com")
		def entityInstance2 = new Entity(name:"2",description:"entidad 2",url:"http:\\marisa.com")
		List ls = new ArrayList<Entity>();
		ls.add entityInstance1;
		ls.add entityInstance2;
		def mapEnt = ["entities":ls]
		//render TypeActivity.list() as JSON
		render mapEnt as JSON
	}

    def save = {
		def gphoto = request.getFile('photo')
		def entityInstance = new Entity(params)
		entityInstance.photo = gphoto.getBytes();
		
		// crear y asignar punto
		def point = new GeoPoint()
		
		if(params.latitude == null || ''.equals(params.latitude) || params.longitude == null || ''.equals(params.longitude)){
             entityInstance.errors.rejectValue('geoPoint', 'Debe seleccionar un punto')
			 entityInstance.geoPoint = point
			 render(view: "create", model: [entityInstance: entityInstance])
		}        
        else {
		
			point.latitude = new BigDecimal(params.latitude)
			point.longitude = new BigDecimal(params.longitude)
			entityInstance.geoPoint = point
		
			if (entityInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'entity.label', default: 'Entity'), entityInstance.id])}"
				redirect(action: "show", id: entityInstance.id)
			}
			else {
				render(view: "create", model: [entityInstance: entityInstance])
			}
        }
    }

    def show = {
        def entityInstance = Entity.get(params.id)
        if (!entityInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entity.label', default: 'Entity'), params.id])}"
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
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entity.label', default: 'Entity'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [entityInstance: entityInstance]
        }
    }

    def update = {
        def entityInstance = Entity.get(params.id)
        if (entityInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (entityInstance.version > version) {
                    
                    entityInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'entity.label', default: 'Entity')] as Object[], "Another user has updated this Entity while you were editing")
                    render(view: "edit", model: [entityInstance: entityInstance])
                    return
                }
            }
            entityInstance.properties = params
            if (!entityInstance.hasErrors() && entityInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'entity.label', default: 'Entity'), entityInstance.id])}"
                redirect(action: "show", id: entityInstance.id)
            }
            else {
                render(view: "edit", model: [entityInstance: entityInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entity.label', default: 'Entity'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def entityInstance = Entity.get(params.id)
        if (entityInstance) {
            try {
                entityInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'entity.label', default: 'Entity'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'entity.label', default: 'Entity'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entity.label', default: 'Entity'), params.id])}"
            redirect(action: "list")
        }
    }
}
