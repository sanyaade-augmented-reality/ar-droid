package ar.droid.admin.survey.response

import com.sun.tools.javac.main.JavacOption.Option;

import ar.droid.admin.survey.question.Choice;
import ar.droid.admin.survey.question.MultipleChoiceQuestion;

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
}
