<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.util.StringUtil" %>

<liferay-util:buffer var="html">
    <liferay-util:include page="/html/portlet/blogs/search.portal.jsp" />
</liferay-util:buffer>

<%
html = StringUtil.add( html, "Didn't find what you were looking for? Refine your search and " + "try again!",  "\n");
%>

<%= html %>