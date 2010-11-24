package ar.droid.admin

class ReaderActivityController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [readerActivityInstanceList: ReaderActivity.list(params), readerActivityInstanceTotal: ReaderActivity.count()]
    }

    def create = {
        def readerActivityInstance = new ReaderActivity()
        readerActivityInstance.properties = params
        return [readerActivityInstance: readerActivityInstance]
    }

    def save = {
        def readerActivityInstance = new ReaderActivity(params)
        if (readerActivityInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'readerActivity.label', default: 'ReaderActivity'), readerActivityInstance.id])}"
            redirect(action: "show", id: readerActivityInstance.id)
        }
        else {
            render(view: "create", model: [readerActivityInstance: readerActivityInstance])
        }
    }

    def show = {
        def readerActivityInstance = ReaderActivity.get(params.id)
        if (!readerActivityInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'readerActivity.label', default: 'ReaderActivity'), params.id])}"
            redirect(action: "list")
        }
        else {
            [readerActivityInstance: readerActivityInstance]
        }
    }

    def edit = {
        def readerActivityInstance = ReaderActivity.get(params.id)
        if (!readerActivityInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'readerActivity.label', default: 'ReaderActivity'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [readerActivityInstance: readerActivityInstance]
        }
    }

    def update = {
        def readerActivityInstance = ReaderActivity.get(params.id)
        if (readerActivityInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (readerActivityInstance.version > version) {
                    
                    readerActivityInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'readerActivity.label', default: 'ReaderActivity')] as Object[], "Another user has updated this ReaderActivity while you were editing")
                    render(view: "edit", model: [readerActivityInstance: readerActivityInstance])
                    return
                }
            }
            readerActivityInstance.properties = params
            if (!readerActivityInstance.hasErrors() && readerActivityInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'readerActivity.label', default: 'ReaderActivity'), readerActivityInstance.id])}"
                redirect(action: "show", id: readerActivityInstance.id)
            }
            else {
                render(view: "edit", model: [readerActivityInstance: readerActivityInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'readerActivity.label', default: 'ReaderActivity'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def readerActivityInstance = ReaderActivity.get(params.id)
        if (readerActivityInstance) {
            try {
                readerActivityInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'readerActivity.label', default: 'ReaderActivity'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'readerActivity.label', default: 'ReaderActivity'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'readerActivity.label', default: 'ReaderActivity'), params.id])}"
            redirect(action: "list")
        }
    }
}
