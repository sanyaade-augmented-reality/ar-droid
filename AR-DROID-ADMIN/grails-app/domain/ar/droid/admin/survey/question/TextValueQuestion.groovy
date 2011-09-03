package ar.droid.admin.survey.question

import org.springframework.context.i18n.LocaleContextHolder;

import ar.droid.admin.survey.response.SurveyResponse;
import ar.droid.admin.survey.response.TextValueResponse;

class TextValueQuestion extends Question {
	
	static mapping = {
		tablePerHierarchy false
	}
	
    static constraints = {
    }

	public String getType(){
		return "Texto"
	}
	
	def getSummary (responses){
		def commentarios = []
		for (SurveyResponse sr : responses) {
			TextValueResponse tvr = sr.first()
			commentarios.add(">> " + tvr.getComment() + ". \n\r")
		}
		return ["rating":-1,"description":responses.size() + " commentarios","comments":commentarios]
	}
	
}
