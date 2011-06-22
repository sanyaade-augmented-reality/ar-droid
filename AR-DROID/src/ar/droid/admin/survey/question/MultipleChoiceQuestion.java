package ar.droid.admin.survey.question;

import java.util.List;

public class MultipleChoiceQuestion extends Question {
	
	
	private Integer maxOptions;
	
	private List<Choice> options;
	
	public List<Choice> getOptions() {
		return options;
	}

	public void setOptions(List<Choice> options) {
		this.options = options;
	}

	public Integer getMaxOptions() {
		return maxOptions;
	}

	public void setMaxOptions(Integer maxOptions) {
		this.maxOptions = maxOptions;
	}

	public String getType(){
		return "Múltiple opción";
	}

	
	public void onQuestion(IQuestionListener callback) {
		callback.doMultipleChoiceQuestion(this);		
	}
}
