package ar.droid.admin.survey.question;

import com.google.gson.annotations.Expose;

public abstract class Question {
	
	private  String question;
	
	@Expose
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	public abstract void onQuestion(IQuestionListener callback);

	
	
}
