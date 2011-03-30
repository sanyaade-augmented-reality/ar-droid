package ar.droid.admin.survay.response

class SurveyResponse {
	String comment;

	static hasMany = [responses: Response]
}