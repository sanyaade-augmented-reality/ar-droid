package ar.droid.admin

import ar.droid.admin.survey.question.Choice;
import ar.droid.admin.survey.question.MultipleChoiceQuestion;
import ar.droid.admin.survey.question.NumericValueQuestion;
import ar.droid.admin.survey.question.Question;
import ar.droid.admin.survey.question.TextValueQuestion;

class QuestionService {

    static transactional = true

	def getTypeQuestions =  {
		return ["1":new TextValueQuestion(),"2":new NumericValueQuestion(),"3":new MultipleChoiceQuestion()]
    }
	
	def Question saveQuestion(params) {
		def questionInstance = getTypeQuestions().getAt(params.typeQuestion)
		addChoices(questionInstance,params)
		questionInstance.properties = params
		questionInstance.save(flush: true)
		return questionInstance
	}
	
	private def addChoices(questionInstance,params){
		def choices = params.choice
		if (choices instanceof java.lang.String){
			choices = []
			choices.add(params.choice)
		}
		for (i in choices) {
			def choice = new Choice(description:i)
			choice.multipleChoiceQuestion = questionInstance
			questionInstance.addOption(choice)
		}
	}
	
	def Question updateQuestion(params) {
		def questionInstance = Question.get(params.id)
		addChoices(questionInstance,params)
        if (questionInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (questionInstance.version > version) {
                    questionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'question.label', default: 'Question')] as Object[], "Another user has updated this Question while you were editing")
                    return questionInstance
                }
            }
            questionInstance.properties = params
			questionInstance.save(flush: true)
        }
		return questionInstance;
	}
	
	
}
