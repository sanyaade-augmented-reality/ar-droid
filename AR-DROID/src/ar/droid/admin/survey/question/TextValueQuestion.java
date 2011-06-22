package ar.droid.admin.survey.question;

public class TextValueQuestion extends Question {

	public String getType(){
		return "Texto";
	}

	@Override
	public void onQuestion(IQuestionListener callback) {
		callback.doTextValueQuestion(this);
	}
}
