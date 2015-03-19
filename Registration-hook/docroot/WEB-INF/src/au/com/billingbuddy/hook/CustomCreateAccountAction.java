package au.com.billingbuddy.hook;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.billingbuddy.business.objects.ProcesorFacade;
import au.com.billingbuddy.vo.objects.MerchantVO;
import au.com.billingbuddy.vo.objects.UserMerchantVO;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;

public class CustomCreateAccountAction extends BaseStrutsPortletAction {
	ProcesorFacade instance = ProcesorFacade.getInstance();
	/**
	 * This is the custom process action
	 * 
	 * In this process action we are reading custom field which is entered from
	 * create account form
	 * 
	 * After reading the custom field we are calling the original process action
	 */
	public void processAction(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		String CompanyName = (String) PortalUtil.getExpandoValue(actionRequest,
				"ExpandoAttribute--" + "CompanyName" + "--",
				ExpandoColumnConstants.STRING,
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX);
		if (!Validator.isNotNull(CompanyName)) {
			SessionErrors.add(actionRequest, "wrong-CompanyName");
			return;
		}
		
		originalStrutsPortletAction.processAction(originalStrutsPortletAction, portletConfig, actionRequest, actionResponse);
		
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long UID = UserLocalServiceUtil.getUserIdByEmailAddress(themeDisplay.getCompanyId(), ParamUtil.getString(actionRequest, "emailAddress"));
//		User user = UserLocalServiceUtil.getUserByEmailAddress(themeDisplay.getCompanyId(), ParamUtil.getString(actionRequest, "emailAddress"));
		
		UserMerchantVO userMerchantVO = new UserMerchantVO();
		userMerchantVO.setUserId(String.valueOf(UID));
		userMerchantVO.setMerchantVO(new MerchantVO());
		userMerchantVO.getMerchantVO().setName(CompanyName);
		userMerchantVO.getMerchantVO().setFirstName(ParamUtil.getString(actionRequest, "firstName"));
		userMerchantVO.getMerchantVO().setLastName(ParamUtil.getString(actionRequest, "lastName"));
		userMerchantVO.getMerchantVO().setEmailAddress(ParamUtil.getString(actionRequest, "emailAddress"));
		System.out.println("CompanyName" + ParamUtil.getString(actionRequest, "CompanyName"));
		
		instance.saveUserMerchant(userMerchantVO);
	}

	/**
	 * After process action this method is invoked
	 * 
	 * From inside of this method we are again calling the original render
	 * method
	 */
	public String render(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig, RenderRequest renderRequest, RenderResponse renderResponse) throws Exception {
		return originalStrutsPortletAction.render(originalStrutsPortletAction, portletConfig, renderRequest, renderResponse);

	}

}
