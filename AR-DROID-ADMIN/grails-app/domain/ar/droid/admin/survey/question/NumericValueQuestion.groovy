package ar.droid.admin.survey.question

class NumericValueQuestion extends Question{
	Integer limitTo
	Integer limitFrom
	
	static mapping = {
		tablePerHierarchy false
	}
	
	static constraints = {
		limitTo(nullable:true)
		limitFrom(nullable:true)
    }
	
	
}
