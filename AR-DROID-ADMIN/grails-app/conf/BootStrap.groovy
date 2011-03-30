import ar.droid.admin.reader.*
import ar.droid.admin.calendar.*

class BootStrap {
	
	def init = { servletContext ->
		servletContext.lsReaderNews = [new ReaderNewsNone(), new ReaderNewsRSS(), new ReaderNewsWeb(), new ReaderNewsFacebook()]
		servletContext.lsReaderActivities = [new ReaderActivityNone(), new ReaderActivityFacebook(), new ReaderActivityApi()]
		servletContext.lsEventCalendars = [new Unique(), new Daily(), new Weekly(), new Monthly()]
		servletContext.mpDayOfTheWeek = ["0": "Sunday", "1": "Monday", "2": "Tuesday", "3": "Wednesday", "4": "Thursday", "5": "Friday", "6": "Saturday"]
	}
	
	def destroy = {
	}
}
