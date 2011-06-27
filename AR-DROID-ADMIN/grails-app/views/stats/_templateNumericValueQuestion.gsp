<script type="text/javascript">

	function drawChart(){
		var data = new google.visualization.DataTable();
		data.addColumn('string', 'Opcion');
        data.addColumn('number', 'Valor');
		
        data.addRows(${question.maxOptions});

        <g:set var="c" value="${0}" />
        <g:while test="c < question.maxOptions">
        	var opt${c} = 0;
        	<g:set var="c" value="${c + 1}" />
        </g:while>
        
        <g:each in="${responses}" var="r">
        	opt${r.value}++;
        </g:each>
        
        <g:each in="${question.options}" status="i" var="option_d">
        	data.setValue(${i}, 0, '${option_d.description}');
        	data.setValue(${i}, 1, opt${option_d.id});
        </g:each>

		var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, {width: 450, height: 300});
	}

	google.setOnLoadCallback(drawChart);
</script>
<div>Respuestas a <strong>${question.question}</strong></div>
<div style="text-align: center;" id="chart_div"></div>