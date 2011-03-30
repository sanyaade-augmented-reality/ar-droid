function viewParams(input){
	var value = input.value;
	switch(input.name){
		case 'readerActivity_select':
			if(value == 'class ar.droid.admin.reader.ReaderActivityNone'){
				input.value = '';
				$('readerActivity_parameter').style.display = 'none';
			}
			else
				$('readerActivity_parameter').style.display = '';
		break;
		
		case 'readerNews_select':
			if(value == 'class ar.droid.admin.reader.ReaderNewsNone'){
				input.value = '';
				$('readerNews_parameter').style.display = 'none';
			}
			else
				$('readerNews_parameter').style.display = '';
		break;
		
		case 'eventCalendar_select':
			$('dayOfTheWeek').style.display = 'none';
			$('dayOfTheMonth').style.display = 'none';
			if(value == 'class ar.droid.admin.calendar.Weekly')
				$('dayOfTheWeek').style.display = '';
			else if(value == 'class ar.droid.admin.calendar.Monthly')
				$('dayOfTheMonth').style.display = '';
		break;
	}
}