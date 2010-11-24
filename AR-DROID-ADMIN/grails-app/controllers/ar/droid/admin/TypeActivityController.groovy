package ar.droid.admin

class TypeActivityController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [typeActivityInstanceList: TypeActivity.list(params), typeActivityInstanceTotal: TypeActivity.count()]
    }

    def create = {
        def typeActivityInstance = new TypeActivity()
        typeActivityInstance.properties = params
        return [typeActivityInstance: typeActivityInstance]
    }

    def save = {
        def typeActivityInstance = new TypeActivity(params)
        if (typeActivityInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'typeActivity.label', default: 'TypeActivity'), typeActivityInstance.id])}"
            redirect(action: "show", id: typeActivityInstance.id)
        }
        else {
            render(view: "create", model: [typeActivityInstance: typeActivityInstance])
        }
    }

    def show = {
        def typeActivityInstance = TypeActivity.get(params.id)
        if (!typeActivityInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'typeActivity.label', default: 'TypeActivity'), params.id])}"
            redirect(action: "list")
        }
        else {
            [typeActivityInstance: typeActivityInstance]
        }
    }

    def edit = {
        def typeActivityInstance = TypeActivity.get(params.id)
        if (!typeActivityInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'typeActivity.label', default: 'TypeActivity'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [typeActivityInstance: typeActivityInstance]
        }
    }

    def update = {
        def typeActivityInstance = TypeActivity.get(params.id)
        if (typeActivityInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (typeActivityInstance.version > version) {
                    
                    typeActivityInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'typeActivity.label', default: 'TypeActivity')] as Object[], "Another user has updated this TypeActivity while you were editing")
                    render(view: "edit", model: [typeActivityInstance: typeActivityInstance])
                    return
                }
            }
            typeActivityInstance.properties = params
            if (!typeActivityInstance.hasErrors() && typeActivityInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'typeActivity.label', default: 'TypeActivity'), typeActivityInstance.id])}"
                redirect(action: "show", id: typeActivityInstance.id)
            }
            else {
                render(view: "edit", model: [typeActivityInstance: typeActivityInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'typeActivity.label', default: 'TypeActivity'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def typeActivityInstance = TypeActivity.get(params.id)
        if (typeActivityInstance) {
            try {
                typeActivityInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'typeActivity.label', default: 'TypeActivity'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'typeActivity.label', default: 'TypeActivity'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'typeActivity.label', default: 'TypeActivity'), params.id])}"
            redirect(action: "list")
        }
    }
}
