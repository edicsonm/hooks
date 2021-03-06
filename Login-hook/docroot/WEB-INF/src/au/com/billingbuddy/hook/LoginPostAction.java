package au.com.billingbuddy.hook;

import au.com.billingbuddy.business.objects.ProcesorFacade;
import au.com.billingbuddy.exceptions.objects.ProcesorFacadeException;
import au.com.billingbuddy.vo.objects.UserMerchantVO;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginPostAction extends Action {
    
	ProcesorFacade instance = ProcesorFacade.getInstance();
	
	public LoginPostAction() {
		 super();
	}
	
	public void run(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		try {
        	User user = PortalUtil.getUser(request);
        	request.getSession().setAttribute("UserId", String.valueOf(user.getUserId()));
        	if(UserLocalServiceUtil.hasRoleUser(RoleLocalServiceUtil.getRole(user.getCompanyId(), "BillingBuddyAdministrator").getRoleId(), user.getUserId())){
        		UserMerchantVO userMerchantVO = new UserMerchantVO();
				userMerchantVO.setUserId(String.valueOf(user.getUserId()));
				instance.rechargeAdministratorAccess(userMerchantVO);
				System.out.println("Ejecuta actualizacion sobre tablas de usuariomerchant");
			}else{
				UserMerchantVO userMerchantVO = new UserMerchantVO();
				userMerchantVO.setUserId(String.valueOf(user.getUserId()));
				userMerchantVO = instance.searchMerchantUser(userMerchantVO);
				session.setAttribute("userMerchantVOAuthenticated", userMerchantVO);
				System.out.println("NO Ejecuta actualizacion sobre tablas de usuariomerchant " + userMerchantVO.getMerchantId());
			}
		} catch (SystemException | PortalException e) {
			e.printStackTrace();
		} catch (ProcesorFacadeException e) {
			e.printStackTrace();
		}
    }
}
