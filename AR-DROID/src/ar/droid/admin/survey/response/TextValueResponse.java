package ar.droid.admin.survey.response;

import com.google.gson.annotations.Expose;

import ar.droid.admin.survey.question.TextValueQuestion;

public class TextValueResponse extends Response {
	@Expose
	private String comment;
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Expose
	private TextValueQuestion textValueQuestion;
	
	public TextValueQuestion getTextValueQuestion() {
		return textValueQuestion;
	}

	public void setTextValueQuestion(TextValueQuestion textValueQuestion) {
		this.textValueQuestion = textValueQuestion;
	}

	
	
}
