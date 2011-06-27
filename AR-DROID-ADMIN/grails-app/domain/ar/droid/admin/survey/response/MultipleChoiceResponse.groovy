package ar.droid.admin.survey.response

import ar.droid.admin.survey.question.*

class MultipleChoiceResponse extends Response {
	MultipleChoiceQuestion multipleChoiceQuestion
	
	static hasMany =[options:Choice]
	
	static mapping = {
		tablePerHierarchy false
	}
	
    static constraints = {
		multipleChoiceQuestion(nullable:true)
    }
	
	def createResponseFromJSON(objetJson){		
		multipleChoiceQuestion = MultipleChoiceQuestion.get(objetJson.multipleChoiceQuestion.id)
		options = []
		objetJson.options.each {c->
			options.add (Choice.get(c.id))
		}
		
	}
	
	Choice first() {
		def choice
		for(o in options){
			choice = o
			break
		}
		choice
	}
}
