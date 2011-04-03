package ar.droid.admin.survey.question

class ChoiceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "GET"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [choiceInstanceList: Choice.list(params), choiceInstanceTotal: Choice.count()]
    }

    def create = {
        def choiceInstance = new Choice()
        choiceInstance.properties = params
        return [choiceInstance: choiceInstance]
    }

    def save = {
        def choiceInstance = new Choice(params)
        if (choiceInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'choice.label', default: 'Choice'), choiceInstance.id])}"
            redirect(action: "show", id: choiceInstance.id)
        }
        else {
            render(view: "create", model: [choiceInstance: choiceInstance])
        }
    }

    def show = {
        def choiceInstance = Choice.get(params.id)
        if (!choiceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'choice.label', default: 'Choice'), params.id])}"
            redirect(action: "list")
        }
        else {
            [choiceInstance: choiceInstance]
        }
    }

    def edit = {
        def choiceInstance = Choice.get(params.id)
        if (!choiceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'choice.label', default: 'Choice'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [choiceInstance: choiceInstance]
        }
    }

    def update = {
        def choiceInstance = Choice.get(params.id)
        if (choiceInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (choiceInstance.version > version) {
                    
                    choiceInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'choice.label', default: 'Choice')] as Object[], "Another user has updated this Choice while you were editing")
                    render(view: "edit", model: [choiceInstance: choiceInstance])
                    return
                }
            }
            choiceInstance.properties = params
            if (!choiceInstance.hasErrors() && choiceInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'choice.label', default: 'Choice'), choiceInstance.id])}"
                redirect(action: "show", id: choiceInstance.id)
            }
            else {
                render(view: "edit", model: [choiceInstance: choiceInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'choice.label', default: 'Choice'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def choiceInstance = Choice.get(params.id)
        if (choiceInstance) {
            try {
                choiceInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'choice.label', default: 'Choice'), params.id])}"
				println  params.question
				redirect(controller:"question",action: "edit", id: params.question)
				
		    }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'choice.label', default: 'Choice'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'choice.label', default: 'Choice'), params.id])}"
            redirect(action: "list")
        }
    }
}
