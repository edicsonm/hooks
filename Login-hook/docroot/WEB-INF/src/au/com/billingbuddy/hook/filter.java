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

public class filter extends BaseStrutsPortletAction {
	
	public void processAction(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		System.out.println("Ejecutando processAction en uno  filter ...");
		
		originalStrutsPortletAction.processAction(originalStrutsPortletAction, portletConfig, actionRequest, actionResponse);
		
		System.out.println("Ejecutando processAction en uno  filter ...");
	}

	public String render(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig, RenderRequest renderRequest, RenderResponse renderResponse) throws Exception {
		System.out.println("Ejecutando render en filter");
		return originalStrutsPortletAction.render(null, portletConfig, renderRequest, renderResponse);

	}

	public void serveResource(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig, ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		System.out.println("Ejecutando serveResource en filter");
		originalStrutsPortletAction.serveResource(originalStrutsPortletAction, portletConfig, resourceRequest, resourceResponse);

	}
}
