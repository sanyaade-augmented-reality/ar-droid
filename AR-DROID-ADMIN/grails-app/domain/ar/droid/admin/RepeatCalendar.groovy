package ar.droid.admin

class RepeatCalendar {

	//java.sql.Date endDate /*finalizacion de la repetición*/
	Integer repeatEvery /**Cada cuantos dias se repite, o meses o semanas*/

	static constraints = {
		repeatEvery(range:1..30 )
	}

	
}
