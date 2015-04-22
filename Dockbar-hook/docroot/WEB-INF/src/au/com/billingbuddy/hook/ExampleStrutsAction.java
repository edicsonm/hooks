package au.com.billingbuddy.hook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;

public class ExampleStrutsAction extends BaseStrutsAction {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("Ejecuta 1 en ExampleStrutsAction");
		return "/portal/sample.jsp";
//		return super.execute(request, response);
	}

//	@Override
//	public String execute(StrutsAction originalStrutsAction,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//			System.out.println("Ejecuta 2 en ExampleStrutsAction");
//		return super.execute(originalStrutsAction, request, response);
//	}

}
