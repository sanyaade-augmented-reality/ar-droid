package ar.droid.admin.survey.question;

public interface IQuestionListener {
	
	public void doMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion);
	public void doNumericValueQuestion(NumericValueQuestion numericValueQuestion);
	public void doTextValueQuestion(TextValueQuestion textValueQuestion);
	

}
