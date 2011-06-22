package ar.droid.admin.survey.response

import ar.droid.admin.survey.question.NumericValueQuestion;

class NumericValueResponse extends Response{
	Integer value
	NumericValueQuestion numericValueQuestion
	
	static mapping = {
		tablePerHierarchy false
	}
	
    static constraints = {
		value(nullable:true)
		numericValueQuestion(nullable:true)
    }
	
	
	def createResponseFromJSON(objetJson){
		numericValueQuestion = NumericValueQuestion.get(objetJson.numericValueQuestion.id)
		value = objetJson.value
	}
}
