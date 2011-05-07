import ar.droid.admin.reader.*
import ar.droid.admin.calendar.*
import ar.droid.admin.*
import ar.droid.admin.survey.*
import ar.droid.admin.survey.question.*

class BootStrap {
	
	def init = { servletContext ->
		servletContext.lsReaderNews = [new ReaderNewsNone(), new ReaderNewsRSS(), new ReaderNewsWeb(), new ReaderNewsFacebook()]
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
		
	}
	
	def destroy = {
	}
}
