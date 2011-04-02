package ar.droid.admin.survey.question


class Choice {
	String description
			
    static constraints = {
		description(blank: false)
    }
	
	static belongsTo = [multipleChoiceQuestion: MultipleChoiceQuestion]
}
