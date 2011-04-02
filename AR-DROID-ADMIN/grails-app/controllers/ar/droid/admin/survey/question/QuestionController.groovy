package ar.droid.admin.survey.question


import ar.droid.admin.survey.question.Question;

class QuestionController {
	
	def questionService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [questionInstanceList: Question.list(params), questionInstanceTotal: Question.count()]
    }

    def create = {
		def questionInstance = new Question()
        questionInstance.properties = params		
        return [questionInstance: questionInstance,typeQuestions:questionService.getTypeQuestions()]
    }

    def save = {
		def questionInstance = questionService.saveQuestion(params)		
		if (questionInstance.hasErrors()) {
			request.limitTo = questionInstance.limitTo()
			request.limitFrom = questionInstance.limitFrom()
			request.maxOptions = questionInstance.maxOptions()
			render(view: "create", model: [questionInstance: questionInstance,typeQuestions:questionService.getTypeQuestions(),typeQuestion:params.typeQuestion])
	    }
        else {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'question.label', default: 'Question'), questionInstance.id])}"
			redirect(action: "show", id: questionInstance.id)
        }
    }

    def show = {
        def questionInstance = Question.get(params.id)
        if (!questionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), params.id])}"
            redirect(action: "list")
        }
        else {
			request.limitTo = questionInstance.limitTo()
			request.limitFrom = questionInstance.limitFrom()
			request.maxOptions = questionInstance.maxOptions()
			def val = questionService.getTypeQuestions().find{it.value.type == questionInstance.type}?.key
            [questionInstance: questionInstance,typeQuestion:val,options:questionInstance.getxxx()]
        }
    }

    def edit = {
        def questionInstance = Question.get(params.id)
        if (!questionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), params.id])}"
            redirect(action: "list")
        }
        else {
			request.limitTo = questionInstance.limitTo()
			request.limitFrom = questionInstance.limitFrom()
			request.maxOptions = questionInstance.maxOptions()
			def val = questionService.getTypeQuestions().find{it.value.type == questionInstance.type}?.key
			return [questionInstance: questionInstance,typeQuestion:val,typeQuestions:questionService.getTypeQuestions()]
        }
    }

    def update = {
		def questionInstance = questionService.updateQuestion(params)
		if (questionInstance.hasErrors()) {
			request.limitTo = questionInstance.limitTo()
			request.limitFrom = questionInstance.limitFrom()
			request.maxOptions = questionInstance.maxOptions()
			render(view: "edit", model: [questionInstance: questionInstance,typeQuestions:questionService.getTypeQuestions(),typeQuestion:params.typeQuestion])
		}
		else{
			flash.message = "${message(code: 'default.updated.message', args: [message(code: 'question.label', default: 'Question'), questionInstance.id])}"
			redirect(action: "show", id: questionInstance.id)
		}
        //def questionInstance = Question.get(params.id)
        /*if (questionInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (questionInstance.version > version) {
                    
                    questionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'question.label', default: 'Question')] as Object[], "Another user has updated this Question while you were editing")
                    render(view: "edit", model: [questionInstance: questionInstance])
                    return
                }
            }
            questionInstance.properties = params
            if (!questionInstance.hasErrors() && questionInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'question.label', default: 'Question'), questionInstance.id])}"
                redirect(action: "show", id: questionInstance.id)
            }
            else {
				request.limitTo = questionInstance.limitTo
				request.limitFrom = questionInstance.limitFrom
			    render(view: "edit", model: [questionInstance: questionInstance,typeQuestions:questionService.getTypeQuestions(),typeQuestion:params.typeQuestion])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), params.id])}"
            redirect(action: "list")
        }*/
    }

    def delete = {
        def questionInstance = Question.get(params.id)
        if (questionInstance) {
            try {
                questionInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'question.label', default: 'Question'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'question.label', default: 'Question'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), params.id])}"
            redirect(action: "list")
        }
    }
}
