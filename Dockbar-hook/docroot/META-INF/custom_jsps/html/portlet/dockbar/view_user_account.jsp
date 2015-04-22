<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

<!-- Esta parte fue incluidad para evitar que apareciera la opcion administracion y la opcion de sitios web a usuarios diferentes al administrador -->
<c:if test="<%= RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), \"administrator\", true) %>">
	<c:if test="<%= themeDisplay.isSignedIn() && user.isSetupComplete() %>">
	
		<%
		request.setAttribute(WebKeys.RENDER_PORTLET_BOUNDARY, false);
		%>
	
		<liferay-portlet:runtime portletName="2_WAR_notificationsportlet" />
	
		<%
		request.removeAttribute(WebKeys.RENDER_PORTLET_BOUNDARY);
		%>
	
		<aui:nav-item cssClass="divider-vertical"></aui:nav-item>
	</c:if>
</c:if>
<!-- La modificacion termina aca, si se quiere volver al estado inicial de la configuracion se debe eliminar SOLAMENTE el if -->
<%@ include file="/html/portlet/dockbar/view_user_account.portal.jsp" %>