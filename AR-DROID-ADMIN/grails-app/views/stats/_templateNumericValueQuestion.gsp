<script type="text/javascript">

	function drawChart(){
		var data = new google.visualization.DataTable();
		data.addColumn('string', 'Opcion');
        data.addColumn('number', 'Valor');
		
        data.addRows(${(question.limitFrom - question.limitTo) + 1});

        <g:set var="c" value="${question.limitTo}" />
        <g:while test="${c <= question.limitFrom}" >
        	var opt${c}_0value = 0;
        	<% c++ %>
        </g:while>
        
        <g:each in="${responses}" var="r">
        	<g:if test="${r.first().value.toFloat() <= question.limitFrom.toFloat()}">
        		opt${r.first().value.toString().replace('.','_')}value++;
        	</g:if>
        </g:each>
        
        <g:set var="i" value="${question.limitTo}" />
        <g:while test="${i <= question.limitFrom}" >
    		data.setValue(${i-1}, 0, 'Valor ${i}');
    		data.setValue(${i-1}, 1, opt${i}_0value);
    		<% i++ %>
    	</g:while>

		var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
        chart.draw(data, {width: 450, height: 300});
	}

	google.setOnLoadCallback(drawChart);
</script>
<div>Respuestas a <strong>${question.question}</strong></div>
<div style="text-align: center;" id="chart_div"></div>