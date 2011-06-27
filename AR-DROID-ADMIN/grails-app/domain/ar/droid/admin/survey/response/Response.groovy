package ar.droid.admin.survey.response

class Response {
	Date fecha
	SurveyResponse surveyResponse
	
	static belongsTo = [surveyResponse: SurveyResponse]

}