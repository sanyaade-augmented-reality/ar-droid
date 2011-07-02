<div>Respuestas a <strong>${question.question}</strong></div>
<div class="list">
<table>
	<thead>
		<tr>
			<th>Fecha</th>
			<g:sortableColumn property="comment"
				title="${message(code: 'response.comment.label', default: 'Comentarios')}" />
			<th>Acciones</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${responses}" status="i" var="responseInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>
				${fieldValue(bean: responseInstance, field: "fecha")}
				</td>
				<td>
				${fieldValue(bean: responseInstance, field: "comment")}
				</td>
				<td><g:link controller="stats" action="deleteResponse"
					id="${responseInstance.id}">
					${fieldValue(bean: responseInstance, field: "id")}
				</g:link></td>
			</tr>
		</g:each>
	</tbody>
</table>
</div>