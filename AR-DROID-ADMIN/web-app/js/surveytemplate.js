function createOption(button){
	var tr = new Element('tr', { 'class': 'prop','name':'trChoice'}).update("<tr><td><label for='choice'>Opcion</label></td><td><input type='text' name='choice' id='choice'></td><td><input type='button' name='deleteChoice' id='deleteChoice' value='Eliminar' onClick='deleteOption(this)'></td><td>&nbsp;</td></tr>");
	Element.insert($('tableQuestion'), tr);
}

function deleteOptions(){
	var trs = $('tableQuestion').getElementsBySelector('[name="trChoice"]');
	//alert($A(trs).length);
	trs.each(function(row){
		Element.remove(row);
	});
	
}

function deleteOption(button){
	var tr =  button.up(1);
	alert(tr.readAttribute('name'))
	Element.remove(tr);
}