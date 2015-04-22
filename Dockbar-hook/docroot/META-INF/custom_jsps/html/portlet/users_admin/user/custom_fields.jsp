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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%@ page import="au.com.billingbuddy.vo.objects.MerchantVO" %>
<%@ page import="au.com.billingbuddy.vo.objects.CountryVO" %>
<%@ page import="au.com.billingbuddy.vo.objects.BusinessTypeVO" %>
<%@ page import="au.com.billingbuddy.vo.objects.IndustryVO" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	User selUser = (User)request.getAttribute("user.selUser");
%>
<%		 
	MerchantVO merchantVO = (MerchantVO)session.getAttribute("merchantVO");
	ArrayList<CountryVO> listCountries = (ArrayList<CountryVO>)session.getAttribute("listCountries");
	ArrayList<BusinessTypeVO> listBusinessTypes = (ArrayList<BusinessTypeVO>)session.getAttribute("listBusinessTypes");
	ArrayList<IndustryVO> listIndustries = (ArrayList<IndustryVO>)session.getAttribute("listIndustries");
%>

<%-- <aui:fieldset>
	<liferay-ui:custom-attribute-list
		className="com.liferay.portal.model.User"
		classPK="<%= (selUser != null) ? selUser.getUserId() : 0 %>"
		editable="<%= true %>"
		label="<%= true %>"	/>
</aui:fieldset> --%>

<%-- <div class="exp-ctrl-holder">
	<liferay-ui:custom-attribute className="<%= User.class.getName() %>" classPK="<%= 0 %>" editable="<%= true %>" label="<%= true %>" name="CompanyName"/>
</div> --%>

<portlet:defineObjects />


<portlet:renderURL var="saveDocument" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"> --%>
	<portlet:param name="struts_action" value="/ir" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<portlet:renderURL var="simpleDialogExample"
	windowState="<%=LiferayWindowState.EXCLUSIVE.toString()%>">
	<portlet:param name="mvcPath"
		value="/html/alloyuidialog/simple_alloyui_dialog-content.jsp" />
	<portlet:param name="message" value="Hello welcome" />
</portlet:renderURL>

<aui:script>
AUI().use('aui-base',
'aui-io-plugin-deprecated',
'liferay-util-window',
function(A) {
A.one('#<portlet:namespace />simple-dialog-example').on('click', function(event){
var popUpWindow=Liferay.Util.Window.getWindow(
{
dialog: {
centered: true,
constrain2view: true,
<!-- //cssClass: 'yourCSSclassName', -->
modal: true,
resizable: false,
width: 475
}
}
).plug(
A.Plugin.IO,
{
autoLoad: false
}).render();
popUpWindow.show();
popUpWindow.titleNode.html("Liferay 6.2 Dialog Window");
popUpWindow.io.set('uri','<%=simpleDialogExample%>');
popUpWindow.io.start();

});
});
</aui:script>

<aui:script use="aui-node,aui-base,aui-modal">
window.showDetailsCard = 
    function(url) {
        var portletURL="<%=themeDisplay.getURLCurrent().toString()%>";
        var dialog = new A.Modal(
            {
            	bodyContent: 'Modal body',
                centered: true,
                modal: true,
                resizable: false,
                render: '#modalCards',
                width: 450,
                height: 300
            }
        ).plug(A.Plugin.IO, { uri: url }).render();
    }
<!-- showDetailsCard.show(); -->  
    
</aui:script>

<liferay-ui:error key="ProcessorMDTR.updateMerchant.MerchantDAOException" message="error.ProcessorMDTR.updateMerchant.MerchantDAOException" />

<portlet:actionURL var='uploadURL' name="upload">
	<portlet:param name="action" value="ENVIANDO INFORMACION"/>
</portlet:actionURL>

<a href="<portlet:renderURL />">&laquo;Home</a>
<div class="separator"></div>
<div>
	<h1>Simple AUI Dialog Please click on button and see</h1>
	<br />
	<aui:button name="simple-dialog-example" id="simple-dialog-example"
		value="Click Here See Simple Allou UI Dialog">
	</aui:button>
</div>

<div class="form-section tab-pane active" role="tabpanel">
	<%  
		if(portletName.equals(PortletKeys.MY_ACCOUNT)){
	%>
		<h3><liferay-ui:message key="merchant-information"/></h3>
	<%}else{%>
		<h3><liferay-ui:message key="custom-fields"/></h3>
	<%}%>

	<div class="row-fluid"> 
		<fieldset class="fieldset span6" id=""> 
			<div class="control-group">
				<aui:input label="label.name" helpMessage="help.name" name="name" value="${merchantVO.name}" showRequiredLabel="false" type="text" required="false" >
				</aui:input>
			</div>
			<div class="control-group">
				<aui:input label="label.tradingName" helpMessage="help.tradingName" name="tradingName" value="${merchantVO.tradingName}" showRequiredLabel="false" type="text" required="false" >
				</aui:input>
			</div>
			<div class="control-group">
				<aui:input label="label.legalPhysicalAddress" helpMessage="help.legalPhysicalAddress" name="legalPhysicalAddress" value="${merchantVO.legalPhysicalAddress}" showRequiredLabel="false" type="text" required="false" >
				</aui:input>
			</div>
			<div class="control-group">
				<aui:input name="statementAddress"  label="label.statementAddress" helpMessage="help.statementAddress" value="${merchantVO.statementAddress}" showRequiredLabel="false" type="text" required="false">
				</aui:input>
			</div>
			
			<div class="control-group">
				<aui:input label="label.taxFileNumber" helpMessage="help.taxFileNumber" name="taxFileNumber" value="${merchantVO.taxFileNumber}" showRequiredLabel="false" type="text" required="false" >
				</aui:input>
			</div>
			<div class="control-group">
				<aui:input label="label.cityBusinessInformation" helpMessage="help.cityBusinessInformation" name="cityBusinessInformation" value="${merchantVO.cityBusinessInformation}" showRequiredLabel="false" type="text" required="false" >
				</aui:input>
			</div>
			<div class="control-group">
				<aui:input label="label.postCodeBusinessInformation" helpMessage="help.postCodeBusinessInformation" name="postCodeBusinessInformation" value="${merchantVO.postCodeBusinessInformation}" showRequiredLabel="false" type="text" required="false" >
				</aui:input>
			</div>
			<div class="control-group">
				<aui:select name="countryBusinessInformation" helpMessage="help.countryBusinessInformation"  label="label.countryBusinessInformation" id="countryBusinessInformation">
					<c:forEach var="countryVO" items="${listCountries}">
						<aui:option value="${countryVO.numeric}" label="${countryVO.name}" selected="${countryVO.numeric==merchantVO.countryNumericMerchant}"/>
					</c:forEach>
				</aui:select>
			</div>
			<div class="control-group">
				<aui:select name="businessType" helpMessage="help.businessType"  label="label.businessType" id="businessType">
					<c:forEach var="businessTypeVO" items="${listBusinessTypes}">
						<aui:option value="${businessTypeVO.id}" label="${businessTypeVO.description}" selected="${businessTypeVO.id==merchantVO.businessTypeId}"/>
					</c:forEach>
				</aui:select>
			</div>
		</fieldset>
		
		<fieldset class="fieldset span6"> 
		
			<div class="control-group">
				<aui:select name="industry" helpMessage="help.industry"  label="label.industry" id="industry">
					<c:forEach var="industryVO" items="${listIndustries}">
						<aui:option value="${industryVO.id}" label="${industryVO.description}" selected="${industryVO.id==merchantVO.industryId}"/>
					</c:forEach>
				</aui:select>
			</div>
			
			<div class="control-group">
				<aui:input label="label.issuedBusinessID" helpMessage="help.issuedBusinessID" name="issuedBusinessID" value="${merchantVO.issuedBusinessID}" showRequiredLabel="false" type="text" required="false" >
				</aui:input>
			</div>
			
			<div class="control-group">
				<aui:input label="label.issuedPersonalID" helpMessage="help.issuedPersonalID" name="issuedPersonalID" value="${merchantVO.issuedPersonalID}" showRequiredLabel="false" type="text" required="false" >
				</aui:input>
			</div>
			
			<div class="control-group">
				<aui:input label="label.typeAccountApplication" helpMessage="help.typeAccountApplication" name="typeAccountApplication" value="${merchantVO.typeAccountApplication}" showRequiredLabel="false" type="text" required="false" >
				</aui:input>
			</div>
			
			<div class="control-group">
				<aui:input label="label.estimatedAnnualSales" helpMessage="help.estimatedAnnualSales" name="estimatedAnnualSales" value="${merchantVO.estimatedAnnualSales}" showRequiredLabel="false" type="text" required="false" >
				</aui:input>
			</div>
			
			<div class="control-group">
				<aui:input label="label.averageTicketSize" helpMessage="help.averageTicketSize" name="averageTicketSize" value="${merchantVO.estimatedAnnualSales}" showRequiredLabel="false" type="text" required="false" >
				</aui:input>
			</div>
			
			<div class="control-group">
				<aui:input label="label.monthlyProcessingVolume" helpMessage="help.monthlyProcessingVolume" name="monthlyProcessingVolume" value="${merchantVO.estimatedAnnualSales}" showRequiredLabel="false" type="text" required="false" >
				</aui:input>
			</div>
			
			<div class="control-group">
				<aui:input name="check" label="business-information-first-question" type="checkbox" value="1"></aui:input>
			</div>
			
			<div class="control-group">
				<aui:input name="check" label="business-information-second-question" type="checkbox" value="1"></aui:input>
			</div>
			
			<div class="control-group">
				<aui:input name="check" label="business-information-third-question" type="checkbox" value="1"></aui:input>
			</div>
			
			
			<!-- <div class="control-group">
				<aui:input name="check" label="business-information-fourth-question" type="checkbox" value="1"></aui:input>
			</div>
			
			<div class="control-group">
				<aui:input name="check" label="business-information-fifth-question" type="checkbox" value="1"></aui:input>
			</div> -->
			
			
			
		</fieldset>
	</div>
	<div class="row-fluid">
		
		<aui:fieldset cssClass="span5">
			
			<a onclick="showDetailsCard('<%= saveDocument.toString() %>')" href="#">Cargar Archivos 1</a>
			<a onclick="showPopup()" href="#">Cargar Archivos 2</a>
			<div>
				<c:if test="<%= selUser != null %>">
					<c:choose>
						<c:when test='<%= UsersAdminUtil.hasUpdateFieldPermission(permissionChecker, user, selUser, "portrait") %>'>
							<portlet:renderURL var="saveDocument" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
								<portlet:param name="struts_action" value="/ir" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
							</portlet:renderURL>
							ja ja ja ja
							<liferay-ui:logo-selector
								currentLogoURL="<%= selUser.getPortraitURL(themeDisplay) %>"
								defaultLogoURL="<%= UserConstants.getPortraitURL(themeDisplay.getPathImage(), selUser.isMale(), 0) %>"
								editLogoURL="<%= saveDocument %>"
								imageId="<%= selUser.getPortraitId() %>"
								logoDisplaySelector=".user-logo"/>
						</c:when>
						<c:otherwise>
							<img src="<%= selUser.getPortraitURL(themeDisplay) %>" />
						</c:otherwise>
					</c:choose>
				</c:if>
			</div>
		</aui:fieldset>
	</div>
	
</div>