<%@ page import="winner.picker.Winner" %>



<div class="fieldcontain ${hasErrors(bean: winnerInstance, field: 'alreadyAWinner', 'error')} ">
	<label for="alreadyAWinner">
		<g:message code="winner.alreadyAWinner.label" default="Already A Winner?" />
		
	</label>
	<g:checkBox name="alreadyAWinner" value="${winnerInstance?.alreadyAWinner}" />
</div>

<div class="fieldcontain ${hasErrors(bean: winnerInstance, field: 'firstName', 'error')} ">
	<label for="firstName">
		<g:message code="winner.firstName.label" default="First Name" />
		
	</label>
	<g:textField name="firstName" value="${winnerInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: winnerInstance, field: 'lastName', 'error')} ">
	<label for="lastName">
		<g:message code="winner.lastName.label" default="Last Name" />
		
	</label>
	<g:textField name="lastName" value="${winnerInstance?.lastName}"/>
</div>

