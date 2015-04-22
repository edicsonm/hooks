package au.com.billingbuddy.hook;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import au.com.billingbuddy.business.objects.ProcesorFacade;
import au.com.billingbuddy.exceptions.objects.ProcesorFacadeException;
import au.com.billingbuddy.vo.objects.BusinessTypeVO;
import au.com.billingbuddy.vo.objects.CountryVO;
import au.com.billingbuddy.vo.objects.IndustryVO;
import au.com.billingbuddy.vo.objects.MerchantVO;
import au.com.billingbuddy.vo.objects.UserMerchantVO;

import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;

public class CustomViewAction extends BaseStrutsPortletAction {
	
	private ProcesorFacade procesorFacade = ProcesorFacade.getInstance();
	/**
	 * This is the custom process action
	 * 
	 * In this process action we are reading custom field which is entered from
	 * create account form
	 * 
	 * After reading the custom field we are calling the original process action
	 */
	public void processAction(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
//		String CompanyName = (String) PortalUtil.getExpandoValue(actionRequest, "ExpandoAttribute--" + "CompanyName" + "--", 
//				ExpandoColumnConstants.STRING,
//				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX);
//		if (!Validator.isNotNull(CompanyName)) {
//			SessionErrors.add(actionRequest, "wrong-CompanyName");
//			return;
//		}
		
		System.out.println("Ejecutando esto .... processAction .... ");
		
		Enumeration<String> enume = actionRequest.getParameterNames();
		while (enume.hasMoreElements()) {
			String valor = enume.nextElement();
			System.out.println("Parametro: " + valor + "-->"+ actionRequest.getParameter(valor));
		}
		
		originalStrutsPortletAction.processAction(originalStrutsPortletAction, portletConfig, actionRequest, actionResponse);
		
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
		
		System.out.println("Ejecuta el metodo render .... CustomViewAction .... ");
		HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
		HttpSession session = request.getSession();
//		String UserId = (String)PortalSessionThreadLocal.getHttpSession().getAttribute("UserId");
		try {
			
//			ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
//			User user = themeDisplay.getRealUser();
			
//			ArrayList<MerchantVO> listMerchants = procesorFacade.listAllMerchants(new MerchantVO(String.valueOf(PortalUtil.getUserId(request))));
//			session.setAttribute("listMerchants", listMerchants);
			/**/
			
			UserMerchantVO userMerchantVO = (UserMerchantVO)session.getAttribute("userMerchantVOAuthenticated");
			if(userMerchantVO == null) {
					userMerchantVO = new UserMerchantVO();
					userMerchantVO.setMerchantId("-1");
			}
			MerchantVO merchantVO = new MerchantVO();
			merchantVO.setId(userMerchantVO.getMerchantId());
			merchantVO = procesorFacade.searchMerchantDetailsUpdateProfile(merchantVO);
			System.out.println("merchantVO.getIndustryId(): " + merchantVO.getIndustryId());
			session.setAttribute("merchantVO", merchantVO);
			
			ArrayList<CountryVO> listCountries = procesorFacade.listCountries();
			session.setAttribute("listCountries", listCountries);
			
			ArrayList<BusinessTypeVO> listBusinessTypes = procesorFacade.listBusinessTypes();
			session.setAttribute("listBusinessTypes", listBusinessTypes);
			
			ArrayList<IndustryVO> listIndustries = procesorFacade.listIndustries();
			session.setAttribute("listIndustries", listIndustries);
			/**/
			
		} catch (ProcesorFacadeException e) {
			e.printStackTrace();
			LiferayPortletConfig liferayPortletConfig = (LiferayPortletConfig) portletConfig;
			SessionMessages.add(renderRequest, liferayPortletConfig.getPortletId() + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
			SessionErrors.add(renderRequest,e.getErrorCode());
			System.out.println("e.getMessage(): " + e.getMessage());
			System.out.println("e.getErrorMenssage(): " + e.getErrorMenssage());
			System.out.println("e.getErrorCode(): " + e.getErrorCode());
		}
		
		
		return originalStrutsPortletAction.render(originalStrutsPortletAction, portletConfig, renderRequest, renderResponse);

	}

	@Override
	public void serveResource(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws Exception {
		System.out.println("serveResource .... ");
		super.serveResource(originalStrutsPortletAction, portletConfig, resourceRequest, resourceResponse);
	}

}
