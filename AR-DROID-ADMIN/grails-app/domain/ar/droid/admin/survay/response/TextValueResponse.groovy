package ar.droid.admin.survay.response

import ar.droid.admin.survay.question.TextValueQuestion;

class TextValueResponse extends Response{
	String value
	
	TextValueQuestion  textValueQuestion
	
    static constraints = {
    }

}
