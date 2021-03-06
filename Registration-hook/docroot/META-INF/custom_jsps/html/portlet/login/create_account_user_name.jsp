<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<div class="exp-ctrl-holder">
	<liferay-ui:custom-attribute className="<%= User.class.getName() %>" classPK="<%= 0 %>" editable="<%= true %>" label="<%= true %>" name="CompanyName"/>
</div>

<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" model="<%= User.class %>" name="firstName" />
<%-- <aui:input model="<%= User.class %>" name="middleName" /> --%>
<aui:input model="<%= User.class %>" name="lastName">
	<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_LAST_NAME_REQUIRED, PropsValues.USERS_LAST_NAME_REQUIRED) %>">
		<aui:validator name="required" />
	</c:if>
</aui:input>
