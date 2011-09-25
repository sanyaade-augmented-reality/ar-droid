package ar.droid.admin.survey.question

import java.util.List
import java.util.Map;
import java.util.HashMap;


import org.hibernate.mapping.Set

import ar.droid.admin.survey.response.MultipleChoiceResponse;
import ar.droid.admin.survey.response.SurveyResponse;


class MultipleChoiceQuestion extends Question{
	Integer maxOptions
	
	static mapping = {
		tablePerHierarchy false
	}
	
	static hasMany = [options:Choice]
           	
    static constraints = {
		maxOptions(nullable: true)
    }
	
	public String getType(){
		return "Múltiples opciones"
	}
	
	public Integer maxOptions(){
		return maxOptions;
	}
	
	public void addOption(Choice choice){
		if (options == null)
			options = [];
		options.add(choice)
	}
	
	public java.util.Set getOptionsQuestion(){
		return options
	}
		
	def getSummary (responses){
		double rating = 0;
		Map<Choice,Integer> result = new HashMap<Choice,Integer>();
		for (SurveyResponse sr : responses) {
			MultipleChoiceResponse mcr = sr.first()
			for (Choice c : mcr.options) {
				Integer total = 1;
				if (result.get(c)!=null){
					total = result.get(c);
					total++;
				}
				result.put(c,total);
			}
		}
		String desc ="";
		for (Choice c : result.keySet()) {
			Integer total =  result.get(c);
			desc = desc + c.getDescription() + "(" + total + ")/" 
			//esto ta mal pero
			if (rating == 0){
				rating = rating + (5*total/responses.size());
			}
			else{
				rating = rating - (5*total/responses.size());
			}
			
		}
		rating = Math.abs(rating);
		desc = desc.substring(0,desc.length()-1);
		return ["rating":rating,"description":desc,"comments":[]]
	}
	

}