package ar.droid.admin

class TypeEntityController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [typeEntityInstanceList: TypeEntity.list(params), typeEntityInstanceTotal: TypeEntity.count()]
    }

    def create = {
        def typeEntityInstance = new TypeEntity()
        typeEntityInstance.properties = params
        return [typeEntityInstance: typeEntityInstance]
    }

    def save = {
        def typeEntityInstance = new TypeEntity(params)
        if (typeEntityInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'typeEntity.label', default: 'TypeEntity'), typeEntityInstance.id])}"
            redirect(action: "show", id: typeEntityInstance.id)
        }
        else {
            render(view: "create", model: [typeEntityInstance: typeEntityInstance])
        }
    }

    def show = {
        def typeEntityInstance = TypeEntity.get(params.id)
        if (!typeEntityInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'typeEntity.label', default: 'TypeEntity'), params.id])}"
            redirect(action: "list")
        }
        else {
            [typeEntityInstance: typeEntityInstance]
        }
    }

    def edit = {
        def typeEntityInstance = TypeEntity.get(params.id)
        if (!typeEntityInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'typeEntity.label', default: 'TypeEntity'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [typeEntityInstance: typeEntityInstance]
        }
    }

    def update = {
        def typeEntityInstance = TypeEntity.get(params.id)
        if (typeEntityInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (typeEntityInstance.version > version) {
                    
                    typeEntityInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'typeEntity.label', default: 'TypeEntity')] as Object[], "Another user has updated this TypeEntity while you were editing")
                    render(view: "edit", model: [typeEntityInstance: typeEntityInstance])
                    return
                }
            }
			
			def icon = typeEntityInstance.icon;
			typeEntityInstance.properties = params;
			if (params.get("icon").size == 0){
				typeEntityInstance.icon = icon;
			}
            if (!typeEntityInstance.hasErrors() && typeEntityInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'typeEntity.label', default: 'TypeEntity'), typeEntityInstance.id])}"
                redirect(action: "show", id: typeEntityInstance.id)
            }
            else {
                render(view: "edit", model: [typeEntityInstance: typeEntityInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'typeEntity.label', default: 'TypeEntity'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def typeEntityInstance = TypeEntity.get(params.id)
        if (typeEntityInstance) {
            try {
                typeEntityInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'typeEntity.label', default: 'TypeEntity'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'typeEntity.label', default: 'TypeEntity'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'typeEntity.label', default: 'TypeEntity'), params.id])}"
            redirect(action: "list")
        }
    }
	
	def showIcon = {
		def typeEntityInstance = TypeEntity.get(params.id)
		response.contentType = "image/jpeg"
		response.contentLength = typeEntityInstance?.icon.length
		response.outputStream.write(typeEntityInstance?.icon)
	}
}
