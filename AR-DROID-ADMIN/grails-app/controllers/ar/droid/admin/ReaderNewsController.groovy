package ar.droid.admin

class ReaderNewsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [readerNewsInstanceList: ReaderNews.list(params), readerNewsInstanceTotal: ReaderNews.count()]
    }

    def create = {
        def readerNewsInstance = new ReaderNews()
        readerNewsInstance.properties = params
        return [readerNewsInstance: readerNewsInstance]
    }

    def save = {
        def readerNewsInstance = new ReaderNews(params)
        if (readerNewsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'readerNews.label', default: 'ReaderNews'), readerNewsInstance.id])}"
            redirect(action: "show", id: readerNewsInstance.id)
        }
        else {
            render(view: "create", model: [readerNewsInstance: readerNewsInstance])
        }
    }

    def show = {
        def readerNewsInstance = ReaderNews.get(params.id)
        if (!readerNewsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'readerNews.label', default: 'ReaderNews'), params.id])}"
            redirect(action: "list")
        }
        else {
            [readerNewsInstance: readerNewsInstance]
        }
    }

    def edit = {
        def readerNewsInstance = ReaderNews.get(params.id)
        if (!readerNewsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'readerNews.label', default: 'ReaderNews'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [readerNewsInstance: readerNewsInstance]
        }
    }

    def update = {
        def readerNewsInstance = ReaderNews.get(params.id)
        if (readerNewsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (readerNewsInstance.version > version) {
                    
                    readerNewsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'readerNews.label', default: 'ReaderNews')] as Object[], "Another user has updated this ReaderNews while you were editing")
                    render(view: "edit", model: [readerNewsInstance: readerNewsInstance])
                    return
                }
            }
            readerNewsInstance.properties = params
            if (!readerNewsInstance.hasErrors() && readerNewsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'readerNews.label', default: 'ReaderNews'), readerNewsInstance.id])}"
                redirect(action: "show", id: readerNewsInstance.id)
            }
            else {
                render(view: "edit", model: [readerNewsInstance: readerNewsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'readerNews.label', default: 'ReaderNews'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def readerNewsInstance = ReaderNews.get(params.id)
        if (readerNewsInstance) {
            try {
                readerNewsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'readerNews.label', default: 'ReaderNews'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'readerNews.label', default: 'ReaderNews'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'readerNews.label', default: 'ReaderNews'), params.id])}"
            redirect(action: "list")
        }
    }
}
