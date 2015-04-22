package au.com.billingbuddy.hook;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import au.com.billingbuddy.business.objects.ProcesorFacade;
import au.com.billingbuddy.exceptions.objects.ProcesorFacadeException;
import au.com.billingbuddy.vo.objects.MerchantVO;
import au.com.billingbuddy.vo.objects.UserMerchantVO;

import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.util.PortalUtil;

public class CustomEditUserAction extends BaseStrutsPortletAction {
	
	ProcesorFacade procesorFacade = ProcesorFacade.getInstance();
	/**
	 * This is the custom process action
	 * 
	 * In this process action we are reading custom field which is entered from
	 * create account form
	 * 
	 * After reading the custom field we are calling the original process action
	 */
	public void processAction(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(actionRequest);
		HttpSession session = request.getSession();
//		String CompanyName = (String) PortalUtil.getExpandoValue(actionRequest, "ExpandoAttribute--" + "CompanyName" + "--", 
//				ExpandoColumnConstants.STRING,
//				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX);
//		if (!Validator.isNotNull(CompanyName)) {
//			SessionErrors.add(actionRequest, "wrong-CompanyName");
//			return;
//		}
		
//		if(actionRequest.getParameter("cmd") != null && actionRequest.getParameter("cmd") == add_temp)
		
		System.out.println("Ejecutando esto .... CustomEditUserAction .... ");
		
		Enumeration<String> enume = actionRequest.getParameterNames();
		while (enume.hasMoreElements()) {
			String valor = enume.nextElement();
			System.out.println("Parametro: " + valor + "-->"+ actionRequest.getParameter(valor));
		}
		
		/*############################################*/
		UserMerchantVO userMerchantVO = (UserMerchantVO)session.getAttribute("userMerchantVOAuthenticated");
//		userMerchantVO.setUserId(String.valueOf(user.getUserId()));
//		userMerchantVO = instance.searchMerchantUser(userMerchantVO);
		if(userMerchantVO != null) {
			MerchantVO merchantVO = (MerchantVO)session.getAttribute("merchantVO");
			if(merchantVO == null) merchantVO = new MerchantVO();
	
			merchantVO.setId(userMerchantVO.getMerchantId());
			
			merchantVO.setName(actionRequest.getParameter("name"));
			merchantVO.setCountryNumericMerchant(actionRequest.getParameter("countryBusinessInformation"));
			merchantVO.setTradingName(actionRequest.getParameter("tradingName"));
			merchantVO.setLegalPhysicalAddress(actionRequest.getParameter("legalPhysicalAddress"));
			merchantVO.setStatementAddress(actionRequest.getParameter("statementAddress"));
			merchantVO.setTaxFileNumber(actionRequest.getParameter("taxFileNumber"));
			merchantVO.setCityBusinessInformation(actionRequest.getParameter("cityBusinessInformation"));
			merchantVO.setPostCodeBusinessInformation(actionRequest.getParameter("postCodeBusinessInformation"));
			merchantVO.setCountryNumericMerchant(actionRequest.getParameter("countryBusinessInformation"));
			merchantVO.setBusinessTypeId(actionRequest.getParameter("businessType"));
			merchantVO.setIndustryId(actionRequest.getParameter("industry"));
			merchantVO.setIssuedBusinessID(actionRequest.getParameter("issuedBusinessID"));
			merchantVO.setIssuedPersonalID(actionRequest.getParameter("issuedPersonalID"));
			merchantVO.setTypeAccountApplication(actionRequest.getParameter("typeAccountApplication"));
			merchantVO.setEstimatedAnnualSales(actionRequest.getParameter("estimatedAnnualSales"));
			
			session.setAttribute("merchantVO", merchantVO);
			System.out.println("merchantVO.getId(): " + merchantVO.getId());
			try {
				procesorFacade.updateMerchant(merchantVO);
				if(merchantVO.getStatus().equalsIgnoreCase("success")) {
					
					System.out.println("Datos actualizados satifactoriamente ... ");
					
					ArrayList<MerchantVO> listMerchants = procesorFacade.listAllMerchants(new MerchantVO(String.valueOf(PortalUtil.getUserId(request))));
					session.setAttribute("listMerchants", listMerchants);
					SessionMessages.add(actionRequest, "merchantSavedSuccessfully");
	//				 try {
	//					InternetAddress fromAddress = new InternetAddress("pepitoperez@billingbuddy.com"); // from address
	//					InternetAddress toAddress = new InternetAddress("edicson@billingbuddy.com");  // to address
	//		            
	//					// email body , here we are getting email structure creating the content folder in 
	//					//the src and create the file with the extension as tmpl.
	//					String body = ContentUtil.get("/templates/sample.tmpl", true);  
	//					String subject = "subject"; // email subject
	//					body = StringUtil.replace(body, new String []{"[$NAME$]","[$DESC$]"}, new String []{"Name","Description"}); // replacing the body with our content.
	//					MailMessage mailMessage = new MailMessage();
	//					mailMessage.setTo(toAddress);
	//					mailMessage.setFrom(fromAddress);
	//					mailMessage.setSubject(subject);
	//					mailMessage.setBody(body);
	//					mailMessage.setHTMLFormat(true);
	//					MailServiceUtil.sendEmail(mailMessage); // Sending message
	//					
	//				} catch (AddressException e1) {
	//					e1.printStackTrace();
	//				}
	//				actionResponse.setRenderParameter("jspPage", "/jsp/view.jsp");
				} else {
					System.out.println("Datos NO actualizados satifactoriamente ... ");
					LiferayPortletConfig liferayPortletConfig = (LiferayPortletConfig) portletConfig;
					SessionMessages.add(actionRequest, liferayPortletConfig.getPortletId() + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
					SessionErrors.add(actionRequest, "error");
					SessionErrors.add(actionRequest,merchantVO.getMessage());
					session.setAttribute("merchantVO", merchantVO);
	//				actionResponse.setRenderParameter("jspPage", "/jsp/newMerchant.jsp");
				}
			} catch (ProcesorFacadeException e) {
	//			e.printStackTrace();
				LiferayPortletConfig liferayPortletConfig = (LiferayPortletConfig) portletConfig;
				SessionMessages.add(actionRequest, liferayPortletConfig.getPortletId() + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
				SessionErrors.add(actionRequest,e.getErrorCode());
				System.out.println("e.getMessage(): " + e.getMessage());
				System.out.println("e.getErrorMenssage(): " + e.getErrorMenssage());
				System.out.println("e.getErrorCode(): " + e.getErrorCode());
				session.setAttribute("merchantVO", merchantVO);
	//			actionResponse.setRenderParameter("jspPage", "/jsp/newMerchant.jsp");
			}
		}
		/*############################################*/
		
		originalStrutsPortletAction.processAction(originalStrutsPortletAction, portletConfig, actionRequest, actionResponse);
		
		if(SessionErrors.isEmpty(actionRequest)){
			System.out.println("Entra por el if ... ");
		}else{
			System.out.println("Entra por el else ... ");
		}
		
//		if(SessionErrors.isEmpty(actionRequest)){
//			System.out.println("Crea usuario");
//			ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
//			long UID = UserLocalServiceUtil.getUserIdByEmailAddress(themeDisplay.getCompanyId(), ParamUtil.getString(actionRequest, "emailAddress"));
//	//		User user = UserLocalServiceUtil.getUserByEmailAddress(themeDisplay.getCompanyId(), ParamUtil.getString(actionRequest, "emailAddress"));
//			
//			UserMerchantVO userMerchantVO = new UserMerchantVO();
//			userMerchantVO.setUserId(String.valueOf(UID));
//			userMerchantVO.setMerchantVO(new MerchantVO());
//			userMerchantVO.getMerchantVO().setName(CompanyName);
//			userMerchantVO.getMerchantVO().setFirstName(ParamUtil.getString(actionRequest, "firstName"));
//			userMerchantVO.getMerchantVO().setLastName(ParamUtil.getString(actionRequest, "lastName"));
//			userMerchantVO.getMerchantVO().setEmailAddress(ParamUtil.getString(actionRequest, "emailAddress"));
//			instance.saveUserMerchant(userMerchantVO);
//		}else{
//			System.out.println("NO Crea usuario");
//		}
	}

	/**
	 * After process action this method is invoked
	 * 
	 * From inside of this method we are again calling the original render
	 * method
	 */
	public String render(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig, RenderRequest renderRequest, RenderResponse renderResponse) throws Exception {
		System.out.println("Ejecuta el metodo render .... CustomEditUserAction .... ");
		return originalStrutsPortletAction.render(originalStrutsPortletAction, portletConfig, renderRequest, renderResponse);

	}

}
