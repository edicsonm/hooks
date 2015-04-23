package au.com.billingbuddy.hook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import au.com.billingbuddy.business.objects.ProcesorFacade;
import au.com.billingbuddy.vo.objects.MerchantDocumentVO;
import au.com.billingbuddy.vo.objects.UserMerchantVO;

import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;


public class SaveFileMerchantAction extends BaseStrutsPortletAction {
	
	private ProcesorFacade procesorFacade = ProcesorFacade.getInstance();
	
	@Override
	public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
//		System.out.println("ejecutando esto ....1 SaveFileMerchantAction ");
//		Enumeration<String> enume = actionRequest.getParameterNames();
//		while (enume.hasMoreElements()) {
//			String valor = enume.nextElement();
//			System.out.println("Parametro1: " + valor + "-->"+ actionRequest.getParameter(valor));
//		}
		addFile(actionRequest);
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
//		System.out.println("Ejecuta render en SaveFileMerchantAction ... " + renderRequest.getParameter("action"));
		HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
		HttpSession session = request.getSession();
		if(renderRequest.getParameter("action") != null && renderRequest.getParameter("action").equalsIgnoreCase("removeElement")){
			ArrayList<MerchantDocumentVO> arrayListFiles = (ArrayList)session.getAttribute("arrayListFiles");
			MerchantDocumentVO merchantDocumentVO = (MerchantDocumentVO)arrayListFiles.get(Integer.parseInt(renderRequest.getParameter("indice")));
			procesorFacade.deleteMerchantDocument(merchantDocumentVO);
			if(merchantDocumentVO.getStatus().equalsIgnoreCase("success")) {
				UserMerchantVO userMerchantVO = (UserMerchantVO)session.getAttribute("userMerchantVOAuthenticated");
				merchantDocumentVO.setMerchantId(userMerchantVO.getMerchantId());
				ArrayList<MerchantDocumentVO> listMerchantDocuments = procesorFacade.listMerchantDocuments(merchantDocumentVO);
				session.setAttribute("arrayListFiles", listMerchantDocuments);
			}else{
				LiferayPortletConfig liferayPortletConfig = (LiferayPortletConfig) portletConfig;
				SessionMessages.add(renderRequest, liferayPortletConfig.getPortletId() + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
				SessionErrors.add(renderRequest, "error");
				SessionErrors.add(renderRequest,merchantDocumentVO.getMessage());
				session.setAttribute("merchantDocumentVO", merchantDocumentVO);
			}
		}else{
			UserMerchantVO userMerchantVO = (UserMerchantVO)session.getAttribute("userMerchantVOAuthenticated");
			if(userMerchantVO != null && userMerchantVO.getMerchantId() != null){
				MerchantDocumentVO merchantDocumentVO = new MerchantDocumentVO();
				merchantDocumentVO.setMerchantId(userMerchantVO.getMerchantId());
				ArrayList<MerchantDocumentVO> listMerchantDocuments = procesorFacade.listMerchantDocuments(merchantDocumentVO);
				session.setAttribute("arrayListFiles", listMerchantDocuments);
			}
		}
		renderRequest.setAttribute(WebKeys.PORTLET_DECORATE, Boolean.TRUE); 
		return "/jsp/addDocument.jsp";
	}
	
	protected void addFile(ActionRequest actionRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(actionRequest);
		HttpSession session = request.getSession();
		ArrayList<MerchantDocumentVO> arrayListFiles = (ArrayList)session.getAttribute("arrayListFiles");
		if(arrayListFiles == null) arrayListFiles = new ArrayList<MerchantDocumentVO>();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		User user = PortalUtil.getSelectedUser(uploadPortletRequest);
		
		if (uploadPortletRequest.getFileAsStream("fileName") == null) {
//			throw new UploadException();
			PortletConfig portletConfig = (PortletConfig)actionRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
			LiferayPortletConfig liferayPortletConfig = (LiferayPortletConfig) portletConfig;
			SessionMessages.add(actionRequest, liferayPortletConfig.getPortletId() + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
			SessionErrors.add(actionRequest, "error");
			SessionErrors.add(actionRequest,"file.UploadException");
		}else{
			UserMerchantVO userMerchantVO = (UserMerchantVO)session.getAttribute("userMerchantVOAuthenticated");
			MerchantDocumentVO merchantDocumentVO = new MerchantDocumentVO();
			merchantDocumentVO.setFile(new OutputBlob(new FileInputStream(uploadPortletRequest.getFile("fileName")), uploadPortletRequest.getFile("fileName").length()));
			merchantDocumentVO.setName(uploadPortletRequest.getFileName("fileName"));
			merchantDocumentVO.setDescription(actionRequest.getParameter("description"));
			
			merchantDocumentVO.setSize(String.valueOf(uploadPortletRequest.getFile("fileName").length()));
			merchantDocumentVO.setMerchantId(userMerchantVO.getMerchantId());
			procesorFacade.saveMerchantDocument(merchantDocumentVO);
			if(merchantDocumentVO.getStatus().equalsIgnoreCase("success")) {
				merchantDocumentVO.setMerchantId(userMerchantVO.getMerchantId());
				ArrayList<MerchantDocumentVO> listMerchantDocuments = procesorFacade.listMerchantDocuments(merchantDocumentVO);
				session.setAttribute("arrayListFiles", listMerchantDocuments);
			}else{
				PortletConfig portletConfig = (PortletConfig)actionRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
				LiferayPortletConfig liferayPortletConfig = (LiferayPortletConfig) portletConfig;
				SessionMessages.add(actionRequest, liferayPortletConfig.getPortletId() + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
				SessionErrors.add(actionRequest, "error");
				SessionErrors.add(actionRequest,merchantDocumentVO.getMessage());
				session.setAttribute("merchantDocumentVO", merchantDocumentVO);
			}
		}
	}

	@Override
	public void serveResource(PortletConfig portletConfig, ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
//		System.out.println("Ejecuta serveResource en SaveFileMerchantAction ... ");
		HttpServletRequest request = PortalUtil.getHttpServletRequest(resourceRequest);
		HttpSession session = request.getSession();
		try {
			int indice = ParamUtil.getInteger(resourceRequest, "indice");
			ArrayList<MerchantDocumentVO> arrayListFiles = (ArrayList)session.getAttribute("arrayListFiles");
			MerchantDocumentVO merchantDocumentVO = (MerchantDocumentVO)arrayListFiles.get(indice);
			merchantDocumentVO = procesorFacade.listMerchantDocument(merchantDocumentVO);
			byte[] data = merchantDocumentVO.getFile().getBytes(1,(int)merchantDocumentVO.getFile().length());
			if (data != null) {
                resourceResponse.setContentType("application/application-download");
                resourceResponse.setProperty("Content-disposition","attachement; filename=" + merchantDocumentVO.getName().replace(" ", "_")+"");
                OutputStream o = resourceResponse.getPortletOutputStream();
                o.write(data);
                o.flush();
                o.close();
                resourceResponse.flushBuffer();
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
		super.serveResource(portletConfig, resourceRequest, resourceResponse);
	}

	
}
