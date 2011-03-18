package ar.droid.admin.survay.question

import ar.droid.admin.survay.Choice;

class MultipleChoiceQuestion extends Question{

	Integer maxOptions
	
	static hasMany = [options:Choice]
           	
    static constraints = {
    }
	
	static mapping ={
		table 'chooseQuestion'		
   }
}