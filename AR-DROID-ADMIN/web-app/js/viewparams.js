function viewParams(input){
	var value = input.value;
	switch(input.name){
		case 'readerActivity_select':
			if(value == 'class ar.droid.admin.reader.ReaderActivityNone'){
				input.value = '';
				$('readerActivity_parameter').hide();
			}
			else
				$('readerActivity_parameter').show();
		break;
		
		case 'readerNews_select':
			if(value == 'class ar.droid.admin.reader.ReaderNewsNone'){
				input.value = '';
				$('readerNews_parameter').hide();
			}
			else
				$('readerNews_parameter').show();
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

function showDataForQuestion(input){
	var value = input.value;
	switch(value){
		case '3':
			$('trMultipleChoiceType').show();
			$('trNumericType').hide();
			break;
		case '2':
			$('trMultipleChoiceType').hide();
			$('trNumericType').show();
			deleteOptions();
			break;
		case '1':
			$('trMultipleChoiceType').hide();
			$('trNumericType').hide();
			deleteOptions();
			break;
	}
}

function createOption(button){
	var tr = new Element('tr', { 'class': 'prop','name':'trChoice'}).update("<tr><td><label for='choice'>Opcion</label></td><td><input type='text' name='choice' id='choice'></td><td>&nbsp;</td><td>&nbsp;</td></tr>");
	Element.insert($('tableQuestion'), tr);
}

function deleteOptions(){
	var trs = $('tableQuestion').getElementsBySelector('[name="trChoice"]');
	//alert($A(trs).length);
	trs.each(function(row){
		Element.remove(row);
	});
	
}