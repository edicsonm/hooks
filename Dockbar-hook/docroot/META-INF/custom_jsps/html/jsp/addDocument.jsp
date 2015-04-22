
<%@ include file="init.jsp" %>
<%@ page import="com.liferay.portal.kernel.util.ListUtil" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="javax.portlet.PortletURL" %>
<%@ page import="com.liferay.portlet.PortletURLUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ListUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.portlet.ActionRequest" %>

<%@ page import="com.liferay.portal.kernel.upload.UploadException" %>
<%@ page import="java.nio.file.NoSuchFileException" %>

<%@ page import="au.com.billingbuddy.vo.objects.RefundVO" %>
<%@ page import="au.com.billingbuddy.vo.objects.MerchantDocumentVO" %>

<%@ page import="java.util.ArrayList" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />
<fmt:setBundle basename="Language"/>

<liferay-ui:error exception="<%= NoSuchFileException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />
<liferay-ui:error exception="<%= UploadException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />

<%
	ArrayList<MerchantDocumentVO> arrayListFiles = (ArrayList)session.getAttribute("arrayListFiles");
	System.out.print("Ejecutando esto ...");
	if(arrayListFiles == null) arrayListFiles = new ArrayList<MerchantDocumentVO>();
	out.print("Time " + Calendar.getInstance().getTimeInMillis());
%>

<fieldset class="fieldset">
	<legend class="fieldset-legend">
		<span class="legend"><fmt:message key="label.attachDocument"/></span>
		<span class="legend"><fmt:message key="business-information"/> </span>
	</legend>
</fieldset>

<%-- <portlet:actionURL var='uploadURL' name="upload" /> --%>
<portlet:actionURL var="uploadURL">
	<portlet:param name="struts_action" value="/ir" />
</portlet:actionURL>
 
<aui:form name="fm" action="<%=uploadURL.toString()%>" method="post" enctype="multipart/form-data">
    <span class="legend"><fmt:message key="business-information"/> </span>
    
    <aui:input name="fileName" id="fileName" type="file" />
    <aui:button type="submit" value="Upload" />
    
    <liferay-ui:search-container emptyResultsMessage="There is no data to display">
	    <liferay-ui:search-container-results
	        results="<%= new ArrayList(ListUtil.subList(arrayListFiles, searchContainer.getStart(), searchContainer.getEnd()))%>"
	        total="<%= arrayListFiles.size() %>" />
	    <liferay-ui:search-container-row className="au.com.billingbuddy.vo.objects.MerchantDocumentVO" modelVar="merchantDocumentVO">
	        
	        <%-- <portlet:resourceURL var="viewURL">
	            <portlet:param name="dataId" value="<%=String.valueOf(aBlobDemo.getBlobId())%>" />
	        </portlet:resourceURL> --%>
	         
	         <liferay-ui:search-container-column-text name="label.name" property="name" orderable="false"/>
	         
	        <%-- <liferay-ui:search-container-column-text value="<%=String.valueOf(row.getPos() + 1)%>" name="Serial No" />
	        <liferay-ui:search-container-column-text href="<%=viewURL.toString() %>" property="name" name="File Name"  /> --%>
	        <%-- <liferay-ui:search-container-column-jsp path="/action.jsp" align="right" /> --%>
	 
	    </liferay-ui:search-container-row>
	 
	    <liferay-ui:search-iterator />
	</liferay-ui:search-container>
    
</aui:form>


