package au.com.billingbuddy.hook;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;

public class CustomCreateAccountAction extends BaseStrutsPortletAction {

	/**
	 * This is the custom process action
	 * 
	 * In this process action we are reading custom field which is entered from
	 * create account form
	 * 
	 * After reading the custom field we are calling the original process action
	 */
	public void processAction(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		String ssnValue = (String) PortalUtil.getExpandoValue(actionRequest,
				"ExpandoAttribute--" + "CompanyName" + "--",
				ExpandoColumnConstants.STRING,
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX);
		System.out.println("##################ssnValue###########################################" + ssnValue);
//		if (!Validator.isNumber(ssnValue)) {
//			SessionErrors.add(actionRequest, "wrong-CompanyName");
//			return;
//		}
		if (!Validator.isNotNull(ssnValue)) {
			SessionErrors.add(actionRequest, "wrong-CompanyName");
			return;
		}
		originalStrutsPortletAction.processAction(originalStrutsPortletAction, portletConfig, actionRequest, actionResponse);
	}

	/**
	 * After process action this method is invoked
	 * 
	 * From inside of this method we are again calling the original render
	 * method
	 */
	public String render(StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse) throws Exception {

		return originalStrutsPortletAction.render(originalStrutsPortletAction,
				portletConfig, renderRequest, renderResponse);

	}

}
