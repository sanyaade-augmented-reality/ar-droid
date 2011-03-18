package ar.droid.admin.survay.response

abstract class SurveyResponse {
	String comment;

	static hasMany = [responses: Response]
}