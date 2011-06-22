import java.sql.Date;

import org.codehaus.groovy.grails.commons.spring.GrailsApplicationContext;

import ar.droid.admin.reader.*
import ar.droid.admin.calendar.*
import ar.droid.admin.*
import ar.droid.admin.survey.*
import ar.droid.admin.survey.question.*
//import grails.converters.JSON;
//import .*;

class BootStrap {
	
	def init = { servletContext ->
		servletContext.lsReaderNews = [new ReaderNewsNone(), new ReaderNewsRSS(), new ReaderNewsWeb(), new ReaderNewsFacebook(),new ReaderNewsTwitter()]
		servletContext.lsReaderActivities = [new ReaderActivityNone(), new ReaderActivityFacebook(), new ReaderActivityApi()]
		servletContext.lsEventCalendars = [new Unique(), new Daily(), new Weekly(), new Monthly()]
		servletContext.mpDayOfTheWeek = ["0": "Domingo", "1": "Lunes", "2": "Martes", "3": "M�ercoles", "4": "Jueves", "5": "Viernes", "6": "Sabado"]
		
		// tendria que crear si no existe el tipo de evento 1-ninguno
		// template te gusta? si/no
		
		def typeEvent = TypeEvent.get(1)
		if (typeEvent == null){
			typeEvent = new TypeEvent(name:"Sin Clasificaci�n")
			typeEvent.save(flush:true)
		}
		
		def template = SurveyTemplate.get(1)
		if (template == null){
			template = new SurveyTemplate(description:"Est�ndar AR-DROID")
			def question = new MultipleChoiceQuestion(question:"Te gusta?",surveyTemplate:template,maxOptions:1)
			def ch1 = new Choice(description:"Si",multipleChoiceQuestion:question)
			def ch2 = new Choice(description:"No",multipleChoiceQuestion:question)
			template.questions = []
			template.questions.add(question)
			question.options = []
			question.options.add(ch1)
			question.options.add(ch2)
			template.save(flush:true)
			log.error(template.errors)
		}
		
				
		grails.converters.JSON.registerObjectMarshaller(Entity) {
			def returnArray = [:]
			returnArray['id'] = it.id
			returnArray['name'] = it.name
			returnArray['description'] = it.description
			returnArray['url'] = it.url
			//returnArray['photoUrl'] = "/request/showImage/"+it.id
			returnArray['typeEntity'] = it.typeEntity
		    returnArray['geoPoint'] = it.geoPoint
			returnArray['readerNews'] = it.readerNews
			//returnArray['readerActivity'] = it.readerActivity
			return returnArray
		}
		
		grails.converters.JSON.registerObjectMarshaller(Event) {
			def returnArray = [:]
			returnArray['id'] = it.id
			returnArray['title'] = it.title
			returnArray['description'] = it.description
			returnArray['typeEvent'] = it.typeEvent
			returnArray['geoPoint'] = it.geoPoint
			returnArray['eventCalendar'] = it.eventCalendar
			returnArray['surveyTemplate'] = it.surveyTemplate			
			return returnArray
		}
		
		grails.converters.JSON.registerObjectMarshaller(TypeEntity) {
			def returnArray = [:]
			returnArray['id'] = it.id
			returnArray['description'] = it.description
			//returnArray['iconUrl'] = "/typeEntity/showIcon/"+it.id
			return returnArray
		}
		
		grails.converters.JSON.registerObjectMarshaller(SurveyTemplate) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['description'] = it.description			
			returnArray['questions'] = it.questions
			return returnArray
		}
		
		grails.converters.JSON.registerObjectMarshaller(NumericValueQuestion) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['question'] = it.question
			returnArray['limitTo'] = it.limitTo
			returnArray['limitFrom'] = it.limitFrom
						
			return returnArray
		}
		
		grails.converters.JSON.registerObjectMarshaller(MultipleChoiceQuestion) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['question'] = it.question
			returnArray['maxOptions'] = it.maxOptions
			returnArray['options'] = it.options
			return returnArray
		}
		
		grails.converters.JSON.registerObjectMarshaller(TextValueQuestion) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['question'] = it.question
			return returnArray
		}
		
		grails.converters.JSON.registerObjectMarshaller(Choice) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['description'] = it.description
			return returnArray
		}
		grails.converters.JSON.registerObjectMarshaller(java.util.Date) {
			return  it?.format("yyyy-MM-dd'T'HH:mm:ss'Z'")
		
		}
		
		
	}
	
	
	def destroy = {
	}
}
