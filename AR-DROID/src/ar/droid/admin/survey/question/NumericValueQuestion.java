package ar.droid.admin.survey.question;


public class NumericValueQuestion extends Question {
	
	private Integer limitTo;
	private Integer limitFrom;
	
	public Integer getLimitTo() {
		return limitTo;
	}
	public void setLimitTo(Integer limitTo) {
		this.limitTo = limitTo;
	}
	public Integer getLimitFrom() {
		return limitFrom;
	}
	public void setLimitFrom(Integer limitFrom) {
		this.limitFrom = limitFrom;
	}
	
	public String getType(){
		return "Numérico";
	}
	@Override
	public void onQuestion(IQuestionListener callback) {
		callback.doNumericValueQuestion(this);		
	}
}
