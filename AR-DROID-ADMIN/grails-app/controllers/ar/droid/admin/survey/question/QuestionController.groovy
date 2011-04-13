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
			render(view: "create", model: getParameters(questionInstance,false))
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
			return getParameters(questionInstance,true)
        }
    }

    def edit = {
        def questionInstance = Question.get(params.id)
        if (!questionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), params.id])}"
            redirect(action: "list")
        }
        else {
			return getParameters(questionInstance,true)
        }
    }

    def update = {
		def questionInstance = questionService.updateQuestion(params)
		if (questionInstance.hasErrors()) {
			/*request.limitTo = questionInstance.limitTo()
			request.limitFrom = questionInstance.limitFrom()
			request.maxOptions = questionInstance.maxOptions()
			render(view: "edit", model: [questionInstance: questionInstance,typeQuestions:questionService.getTypeQuestions(),typeQuestion:params.typeQuestion])
			*/
			render(view: "edit", model: getParameters(questionInstance,false))
		}
		else{
			flash.message = "${message(code: 'default.updated.message', args: [message(code: 'question.label', default: 'Question'), questionInstance.id])}"
			redirect(action: "show", id: questionInstance.id)
		}
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
	
	def deletechoice = {
		def idchoice =  params.choicedelete
		println idchoice
		redirect(controller: "choice", action: "delete", id: idchoice,question:params.id)
	}
	
	def getParameters(questionInstance,pEval) { 
		request.limitTo = questionInstance.limitTo()
		request.limitFrom = questionInstance.limitFrom()
		request.maxOptions = questionInstance.maxOptions()
		def val = params.typeQuestion
		if (pEval){
			val = questionService.getTypeQuestions().find{it.value.type == questionInstance.type}?.key
		}
		return [questionInstance: questionInstance,typeQuestions:questionService.getTypeQuestions(),typeQuestion:val,options:questionInstance.getOptionsQuestion()]
	}
}
