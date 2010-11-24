package ar.droid.admin

import com.sun.tools.internal.xjc.api.Mapping;


class MultipleChoiceQuestion extends Question{

	Integer maxOptions
	
	static hasMany = [options:Choice]
           	
    static constraints = {
    }
	
	static mapping ={
		table 'chooseQuestion'		
   }
}