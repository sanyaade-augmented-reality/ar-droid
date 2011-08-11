package ar.droid.admin.survey.question

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
	
}
