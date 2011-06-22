package ar.droid.admin.survey.response;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import ar.droid.admin.survey.question.Choice;
import ar.droid.admin.survey.question.MultipleChoiceQuestion;

public class MultipleChoiceResponse extends Response {
	
	@Expose
	private MultipleChoiceQuestion multipleChoiceQuestion;
	@Expose
	private List<Choice> options;
	
	public MultipleChoiceQuestion getMultipleChoiceQuestion() {
		return multipleChoiceQuestion;
	}
	public void setMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
		this.multipleChoiceQuestion = multipleChoiceQuestion;
	}
	public List<Choice> getOptions() {
		return options;
	}
	public void setOptions(List<Choice> options) {
		this.options = options;
	}
	
	public void addOption(Choice choice){
		if (options == null){
			options = new ArrayList<Choice>();
		}
		options.add(choice);
	}
	
	
	
}
