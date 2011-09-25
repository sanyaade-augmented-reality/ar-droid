package ar.droid.admin.survey.question

import ar.droid.admin.survey.response.NumericValueResponse;
import ar.droid.admin.survey.response.SurveyResponse;

class NumericValueQuestion extends Question{
	Integer limitTo
	Integer limitFrom
	
	static mapping = {
		tablePerHierarchy false
	}
	
	static constraints = {
		limitTo(nullable: true)
		limitFrom(nullable: true)
    }
	
	public String getType(){
		return "Valor numérico"
	}
	
	public Integer limitTo(){
		return limitTo;
	}
	
	public Integer limitFrom(){
		return limitFrom;
	}
	
	def getSummary (responses){
		def summary = [];
		double rating = 0;
		for (SurveyResponse sr : responses) {
			NumericValueResponse rn = sr.first()
			rating = rating + rn.value;
		}
		if (rating != 0){
			rating = rating/responses.size()
		}		
		return ["rating":rating,"description":"("+responses.size() + " votos)","comments":[]]
	}

	
}
