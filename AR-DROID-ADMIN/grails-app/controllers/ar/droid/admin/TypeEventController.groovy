package ar.droid.admin

class TypeEventController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [typeEventInstanceList: TypeEvent.list(params), typeEventInstanceTotal: TypeEvent.count()]
    }

    def create = {
        def typeEventInstance = new TypeEvent()
        typeEventInstance.properties = params
        return [typeEventInstance: typeEventInstance]
    }

    def save = {
        def typeEventInstance = new TypeEvent(params)
        if (typeEventInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'typeEvent.label', default: 'typeEvent'), typeEventInstance.id])}"
            redirect(action: "show", id: typeEventInstance.id)
        }
        else {
            render(view: "create", model: [typeEventInstance: typeEventInstance])
        }
    }

    def show = {
        def typeEventInstance = TypeEvent.get(params.id)
        if (!typeEventInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'typeEvent.label', default: 'typeEvent'), params.id])}"
            redirect(action: "list")
        }
        else {
            [typeEventInstance: typeEventInstance]
        }
    }

    def edit = {
        def typeEventInstance = TypeEvent.get(params.id)
        if (!typeEventInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'typeEvent.label', default: 'typeEvent'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [typeEventInstance: typeEventInstance]
        }
    }

    def update = {
        def typeEventInstance = TypeEvent.get(params.id)
        if (typeEventInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (typeEventInstance.version > version) {
                    
                    typeEventInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'typeEvent.label', default: 'typeEvent')] as Object[], "Another user has updated this typeEvent while you were editing")
                    render(view: "edit", model: [typeEventInstance: typeEventInstance])
                    return
                }
            }
			
			def icon = typeEventInstance.icon;
			typeEventInstance.properties = params;
			if (params.get("icon").size == 0){
				typeEventInstance.icon = icon;
			}
            if (!typeEventInstance.hasErrors() && typeEventInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'typeEvent.label', default: 'typeEvent'), typeEventInstance.id])}"
                redirect(action: "show", id: typeEventInstance.id)
            }
            else {
                render(view: "edit", model: [typeEventInstance: typeEventInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'typeEvent.label', default: 'typeEvent'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def typeEventInstance = TypeEvent.get(params.id)
        if (typeEventInstance) {
            try {
                typeEventInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'typeEvent.label', default: 'typeEvent'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'typeEvent.label', default: 'typeEvent'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'typeEvent.label', default: 'typeEvent'), params.id])}"
            redirect(action: "list")
        }
    }
}
