
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
<liferay-ui:error key="file.UploadException" message="error.file.UploadException" />
<%
	ArrayList<MerchantDocumentVO> arrayListFiles = (ArrayList)session.getAttribute("arrayListFiles");
	if(arrayListFiles == null) arrayListFiles = new ArrayList<MerchantDocumentVO>();
/* 	out.print("Time " + Calendar.getInstance().getTimeInMillis()); */
%>

<portlet:actionURL var="uploadURL">
	<portlet:param name="struts_action" value="/ir" />
</portlet:actionURL>
 
<aui:form name="fm" action="<%=uploadURL.toString()%>" method="post" enctype="multipart/form-data">
	
	<div class="control-group">
		<aui:input label="label.document" required="true" helpMessage="help.document"  name="fileName" id="fileName" type="file" />
	</div>
    
     <div class="control-group">
		<aui:input label="label.description" value="" helpMessage="help.description" name="description" id="description" showRequiredLabel="false" type="text" required="true" >
		</aui:input>
	</div>
    
    <button type="submit" class="btn btn-primary" /><liferay-ui:message key="label.upload"/></button>
    
    <liferay-ui:search-container emptyResultsMessage="There is no data to display">
	    <liferay-ui:search-container-results
	        results="<%= new ArrayList(ListUtil.subList(arrayListFiles, searchContainer.getStart(), searchContainer.getEnd()))%>"
	        total="<%= arrayListFiles.size() %>" />
	    <liferay-ui:search-container-row className="au.com.billingbuddy.vo.objects.MerchantDocumentVO" indexVar="indice" modelVar="merchantDocumentVO">
	         
	         <portlet:resourceURL  var="imageResourceURL">
	         	<portlet:param name="struts_action" value="/ir"/>
				<portlet:param name="operacion" value="cargarImagen"/>
				<portlet:param name="indice" value="<%=String.valueOf(indice)%>"/>
			</portlet:resourceURL>
			
			 <liferay-ui:search-container-column-text  href="<%=imageResourceURL.toString() %>" property="name" name="label.name"/>
			 <liferay-ui:search-container-column-text name="label.size" property="size" orderable="false"/>
	         <liferay-ui:search-container-column-text name="label.description" value="${Utils:printString(merchantDocumentVO.description)}" orderable="false"/>
	         
	         <liferay-ui:search-container-column-text name="Accion">
				<liferay-ui:icon-menu>
				<liferay-portlet:renderURL varImpl="removeDocument">
					<portlet:param name="struts_action" value="/ir"/>
					<portlet:param name="indice" value="<%=String.valueOf(indice)%>"/>
					<portlet:param name="action" value="removeElement"/>
				</liferay-portlet:renderURL>
				<liferay-ui:icon image="delete" onClick="return confirm('Are you sure do you want to remove this file?')" message="label.remove" url="<%=removeDocument.toString()%>" />
					
				</liferay-ui:icon-menu>
			</liferay-ui:search-container-column-text>
	         
	    </liferay-ui:search-container-row>
	    <liferay-ui:search-iterator paginate="false"/>
	</liferay-ui:search-container>
    
</aui:form>


