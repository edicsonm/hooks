package au.com.billingbuddy.hook;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;

public class dos extends BaseStrutsPortletAction {
	
	public void processAction(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		System.out.println("Ejecutando processAction en dos  antes ...");
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        Long currentuser = themeDisplay.getUserId();

        if (currentuser != null) {
            System.out.println("Wrapped /login/ action2" + currentuser);
        }else{
        	System.out.println("Usuario con valor null .... " );
        }
		originalStrutsPortletAction.processAction(originalStrutsPortletAction, portletConfig, actionRequest, actionResponse);
		System.out.println("Ejecutando processAction en dos  despues ...");
		User user = themeDisplay.getUser();
		System.out.println("user: " + user);
	}

	public String render(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig, RenderRequest renderRequest, RenderResponse renderResponse) throws Exception {
		System.out.println("Ejecutando render en dos");
		return originalStrutsPortletAction.render(null, portletConfig, renderRequest, renderResponse);

	}

	public void serveResource(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig, ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		System.out.println("Ejecutando serveResource en dos");
		originalStrutsPortletAction.serveResource(originalStrutsPortletAction, portletConfig, resourceRequest, resourceResponse);

	}
}
