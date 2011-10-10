<div>Respuestas a <strong>${question.question}</strong></div>
<br />
<div class="list">
<table>
	<thead>
		<tr>
			<th>Fecha</th>
			<g:sortableColumn property="comment"
				title="Comentarios" />
			<th>Acciones</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${responses}" status="i" var="responseInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>
					<g:formatDate format="dd/MM/yyyy HH:mm" date="${fieldValue(bean: responseInstance.first(), field: "fecha")}" />
				</td>
				<td>
					${fieldValue(bean: responseInstance.first(), field: "comment")}
				</td>
				<td>
					<g:link controller="stats" action="deleteResponse" id="${responseInstance.id}">Eliminar</g:link>
				</td>
			</tr>
		</g:each>
	</tbody>
</table>
</div>