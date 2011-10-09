package ar.droid.admin.survey.question

import java.sql.Date;
import java.sql.Timestamp

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
		
		def dueDateFromTimestamp = {
			assert it instanceof java.sql.Timestamp
			['DD/MM/YY': [String.format('%td', it), String.format('%tm', it), String.format('%tY', it)].join("-") ]
		}
		
		for (SurveyResponse sr : responses) {
			TextValueResponse tvr = sr.first()
			Calendar date = new GregorianCalendar()
			date.setTimeInMillis(tvr.surveyResponse.fecha.getTime())
			commentarios.add(date.getAt(Calendar.DAY_OF_MONTH) + "/" + (date.getAt(Calendar.MONTH)+1) + "/" +  date.getAt(Calendar.YEAR).toString().substring(2) + ": " + tvr.getComment() + "\n\r")
		}
		return ["rating":-1,"description": responses.size() + " comentarios","comments":commentarios]
	}
	
}
