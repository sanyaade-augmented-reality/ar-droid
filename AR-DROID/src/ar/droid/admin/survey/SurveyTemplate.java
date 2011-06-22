package ar.droid.admin.survey;

import java.util.List;

import ar.droid.admin.survey.question.Question;

public class SurveyTemplate {
	private String description;
	private Long id;
	private List<Question> questions;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
