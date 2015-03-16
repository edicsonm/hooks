package com.proliferay.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.struts.BaseStrutsAction;

/**
 * 
 * @author Hamidul Islam
 *
 */
public class AddedAction extends BaseStrutsAction {
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return "/portal/proliferay.jsp";
	}

}
