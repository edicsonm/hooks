package au.com.billingbuddy.hook;


import java.util.Iterator;
import java.util.List;

import au.com.billingbuddy.business.objects.ProcesorFacade;
import au.com.billingbuddy.exceptions.objects.ProcesorFacadeException;
import au.com.billingbuddy.vo.objects.UserMerchantVO;

import com.liferay.portal.kernel.events.Action;
//import com.liferay.portal.struts.SimpleAction;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginPostAction extends Action {
    
	ProcesorFacade instance = ProcesorFacade.getInstance();
	public LoginPostAction() {
		 super();
	}
	public void run(HttpServletRequest request, HttpServletResponse response) {
        try {
        	User user = PortalUtil.getUser(request);
        	request.getSession().setAttribute("UserId", String.valueOf(user.getUserId()));
        	if(UserLocalServiceUtil.hasRoleUser(RoleLocalServiceUtil.getRole(user.getCompanyId(), "administrator").getRoleId(), user.getUserId())){
				UserMerchantVO userMerchantVO = new UserMerchantVO();
				userMerchantVO.setUserId(String.valueOf(user.getUserId()));
				instance.rechargeAdministratorAccess(userMerchantVO);
				System.out.println("Ejecuta actualizacion sobre tablas de usuariomerchant");
			}else{
				System.out.println("NO Ejecuta actualizacion sobre tablas de usuariomerchant");
			}
		} catch (SystemException | PortalException e) {
			e.printStackTrace();
		} catch (ProcesorFacadeException e) {
			e.printStackTrace();
		}
    }
}
