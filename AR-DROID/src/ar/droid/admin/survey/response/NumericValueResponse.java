package ar.droid.admin.survey.response;

import com.google.gson.annotations.Expose;

import ar.droid.admin.survey.question.NumericValueQuestion;

public class NumericValueResponse extends Response {

	@Expose
	private Integer value;
	@Expose
	private NumericValueQuestion numericValueQuestion;
	
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public NumericValueQuestion getNumericValueQuestion() {
		return numericValueQuestion;
	}
	public void setNumericValueQuestion(NumericValueQuestion numericValueQuestion) {
		this.numericValueQuestion = numericValueQuestion;
	}
}
