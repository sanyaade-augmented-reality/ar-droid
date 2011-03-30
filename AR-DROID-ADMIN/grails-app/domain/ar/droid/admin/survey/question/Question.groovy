package ar.droid.admin.survey.question

import org.hamcrest.core.IsNull;

import ar.droid.admin.survey.SurveyTemplate;

class Question {

	String question
	SurveyTemplate surveyTemplate;
	
	static belongsTo = [surveyTemplate: SurveyTemplate]
	
	static constraints = {
		question(blank: false)
    }
}
