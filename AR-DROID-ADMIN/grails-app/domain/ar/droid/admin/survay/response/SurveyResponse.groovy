package ar.droid.admin

abstract class SurveyResponse {
	String comment;

	static hasMany = [responses: Response]
}