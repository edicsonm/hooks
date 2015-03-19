package au.com.billingbuddy.hook;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

public class CustomLoginAction extends BaseStrutsPortletAction {
	
	public void processAction(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		System.out.println("Ejecutando processAction en CustomLoginAction  antes ...");
        originalStrutsPortletAction.processAction(originalStrutsPortletAction, portletConfig, actionRequest, actionResponse);
	}

	public String render(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig, RenderRequest renderRequest, RenderResponse renderResponse) throws Exception {
		System.out.println("Ejecutando render en CustomLoginAction");
		return originalStrutsPortletAction.render(null, portletConfig, renderRequest, renderResponse);

	}

	public void serveResource(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig, ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		System.out.println("Ejecutando render en serveResource");
		originalStrutsPortletAction.serveResource(originalStrutsPortletAction, portletConfig, resourceRequest, resourceResponse);

	}
}
