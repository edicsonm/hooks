package au.com.billingbuddy.hook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import au.com.billingbuddy.vo.objects.MerchantDocumentVO;

import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.util.PortalUtil;


public class SaveFileMerchantAction extends BaseStrutsPortletAction {

	@Override
	public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		System.out.println("ejecutando esto ....1 SaveFileMerchantAction ");
//		Enumeration<String> enume = actionRequest.getParameterNames();
//		while (enume.hasMoreElements()) {
//			String valor = enume.nextElement();
//			System.out.println("Parametro1: " + valor + "-->"+ actionRequest.getParameter(valor));
//		}
		updatePortrait(actionRequest);
		
		super.processAction(portletConfig, actionRequest, actionResponse);
	}

//	@Override
//	public void processAction(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
//		System.out.println("ejecutando esto ....2 SaveFileMerchantAction");
//		Enumeration<String> enume = actionRequest.getParameterNames();
//		while (enume.hasMoreElements()) {
//			String valor = enume.nextElement();
//			System.out.println("Parametro2: " + valor + "-->"+ actionRequest.getParameter(valor));
//		}
//		super.processAction(originalStrutsPortletAction, portletConfig, actionRequest, actionResponse);
//	}

//	@override
//	public string render(portletconfig portletconfig, renderrequest renderrequest, renderresponse renderresponse) throws exception {
//		system.out.println("portletconfig: " + portletconfig);
//		system.out.println("renderrequest: " + renderrequest);
//		system.out.println("renderresponse: " + renderresponse);
//		renderrequest.setattribute("jsppage", "/jsp/carddetails.jsp");
//		renderrequest.setattribute("redirect", "/jsp/carddetails.jsp");
////		actionresponse.setrenderparameter("jsppage", "/jsp/view.jsp");
//		return super.render(portletconfig, renderrequest, renderresponse);
//	}

	@Override
	public String render(PortletConfig portletConfig, RenderRequest renderRequest, RenderResponse renderResponse) throws Exception {
		System.out.println("ejecutando esto ....3 SaveFileMerchantAction");
		renderRequest.setAttribute(WebKeys.PORTLET_DECORATE, Boolean.TRUE); 
		return "/jsp/addDocument.jsp";
	}
	
	protected void updatePortrait(ActionRequest actionRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(actionRequest);
		HttpSession session = request.getSession();
		ArrayList<MerchantDocumentVO> arrayListFiles = (ArrayList)session.getAttribute("arrayListFiles");
		if(arrayListFiles == null) arrayListFiles = new ArrayList<MerchantDocumentVO>();
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		
		User user = PortalUtil.getSelectedUser(uploadPortletRequest);
//		InputStream inputStream = uploadPortletRequest.getFileAsStream("fileName");
		if (uploadPortletRequest.getFileAsStream("fileName") == null) {
			throw new UploadException();
		}
//		byte[] bytes = FileUtil.getBytes(inputStream);
		
		MerchantDocumentVO merchantDocumentVO = new MerchantDocumentVO();
		merchantDocumentVO.setFile(new OutputBlob(new FileInputStream(uploadPortletRequest.getFile("fileName")), uploadPortletRequest.getFile("fileName").length()));
		merchantDocumentVO.setName(uploadPortletRequest.getFileName("fileName"));
		merchantDocumentVO.setSize(String.valueOf(uploadPortletRequest.getFile("fileName").length()));
		
		arrayListFiles.add(merchantDocumentVO);
		session.setAttribute("arrayListFiles", arrayListFiles);
		System.out.println("Tamaño del archivo" + uploadPortletRequest.getFile("fileName").length());
//		UserServiceUtil.updatePortrait(user.getUserId(), bytes);
	}

	
}
