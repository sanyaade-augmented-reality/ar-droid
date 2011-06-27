<script type="text/javascript">

	function drawChart(){
		var data = new google.visualization.DataTable();
		data.addColumn('string', 'Opcion');
        data.addColumn('number', 'Valor');
		
        data.addRows(${questions.size()+1});
        
        <g:each in="${question.options}" var="option_c">
        	var opt${option_c.id} = 0;
        </g:each>
        
        <g:each in="${responses}" var="r">
        	opt${r.first().first().id}++;
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