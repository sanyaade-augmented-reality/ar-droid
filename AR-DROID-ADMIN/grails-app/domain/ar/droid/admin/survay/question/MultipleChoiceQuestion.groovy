package ar.droid.admin

class MultipleChoiceQuestion extends Question{

	Integer maxOptions
	
	static hasMany = [options:Choice]
           	
    static constraints = {
    }
	
	static mapping ={
		table 'chooseQuestion'		
   }
}