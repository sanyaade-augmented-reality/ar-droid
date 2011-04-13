package ar.droid.admin.survey.question

import org.hamcrest.core.IsNull;
import org.hibernate.mapping.Set;

import ar.droid.admin.survey.SurveyTemplate;

class Question {

	String question
	SurveyTemplate surveyTemplate;
	
	def messageSource
	
	static transients = ['type']
	
	static belongsTo = [surveyTemplate: SurveyTemplate]
	
	static constraints = {
		question(blank: false)
    }
	
	@Override
	public String toString() {
		return getType();
	}
	
	public Integer limitTo(){
		return null;
	}
	
	public Integer limitFrom(){
		return null;
	}
	
	public Integer maxOptions(){
		return null;
	}
	
	public void addOption(Choice choice){
	}

	public java.util.Set getOptionsQuestion(){
		return []
	}
	

	
	
	
}
