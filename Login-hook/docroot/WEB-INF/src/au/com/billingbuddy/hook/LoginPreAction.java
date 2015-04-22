package au.com.billingbuddy.hook;


import au.com.billingbuddy.business.objects.ProcesorFacade;

import com.liferay.portal.kernel.events.Action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginPreAction extends Action {
    
	ProcesorFacade instance = ProcesorFacade.getInstance();
	
	public LoginPreAction() {
		 super();
	}
	
	public void run(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("Ejecuta run en LoginPreAction");
	}
}
