package com.fz.enroll.student.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.common.util.Utils;
import com.fz.enroll.dto.student.GraStuInfoAdd;
import com.fz.enroll.entity.student.GraStuInfo;
import com.fz.enroll.enum0.AttOtype;
import com.fz.enroll.file.service.AttachmentService;
import com.fz.enroll.student.service.StuGraInfoService;


@Controller
@RequestMapping("/stuGraInfo")
public class StuGraInfoAction {
	@Autowired
	private StuGraInfoService stuGraInfoService;
	@Autowired
	private AttachmentService attachmentService;
	
	@ResponseBody
	@RequestMapping("load")
	public Response doLoad(String id) {
		Response res = stuGraInfoService.loadService(id);
		return res;
	}
	
	@ResponseBody
	@RequestMapping("verify")
	public Response doVerify(GraStuInfoAdd entity) {
		Response res = stuGraInfoService.verifyServiceT(entity.convert2Entity());
		return res;
	}
	
	@ResponseBody
	@RequestMapping("review")
	public Response doReview(GraStuInfoAdd entity) {
		Response res = stuGraInfoService.reviewServiceT(entity.convert2Entity());
		return res;
	}
	
	@ResponseBody
	@RequestMapping("submit")
	public Response doSubmit(String id) {
		Response res = stuGraInfoService.submitServiceT(id);
		return res;
	}
	
	@ResponseBody
	@RequestMapping("refuse")
	public Response doRefuse(String id) {
		Response res = stuGraInfoService.refuseServiceT(id);
		return res;
	}
	
	@ResponseBody
	@RequestMapping("historyList")
	public Response doGetHistoryList(String id){
		Response res = stuGraInfoService.getHistoryListService(id);
		return res;
	}

	@ResponseBody
	@RequestMapping("historyInfo")
	public Response doGetHistoryInfo(String id){
		Response res = stuGraInfoService.getHistoryInfoService(id);
		return res;
	}

	@RequestMapping("download/{id}")
	public void doDownload(@PathVariable("id")String id,HttpServletRequest request,HttpServletResponse response) {
		stuGraInfoService.downloadStuInfoService(false,id,request,response);
	}
	
	@RequestMapping("downloadPdf/{id}")
	public void doDownloadPdf(@PathVariable("id")String id,HttpServletRequest request,HttpServletResponse response) {
		stuGraInfoService.downloadStuInfoService(true,id,request,response);
	}

	@RequestMapping("print/{id}")
	public String doPrint(@PathVariable("id")String id,Model model){
		model.addAttribute("id", id);
		return "student/print2";
	}
	
	/**************************附件相关**********************************/
	@ResponseBody
	@RequestMapping("/loadAtt")
	public Response doLoadAtt(Integer oid){
		Response res =attachmentService.loadService(oid,AttOtype.TYPE_GRASTUINFO);
		return res;
	}
	/**
	 * 上传附件
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadAtt", method = RequestMethod.POST)
	public Response doUploadAtt(@RequestParam("file")MultipartFile file,String prefix) {
		Response res = stuGraInfoService.loadService(null);
		if(res.getRetCode()!=ReturnCode.SUCCESS){
			return res;
		}
		int oid = ((GraStuInfo)res.getData()).getId();
		res = attachmentService.uploadService(file,Utils.connectString("【",prefix,"】"),oid,AttOtype.TYPE_GRASTUINFO.val());
		return res;
	}
	/**
	 * 下载附件
	 */
	@RequestMapping("/downloadAtt/{hash}/{id}")
	public void doDownloadAtt(@PathVariable("id")int id, @PathVariable("hash")String hash,
			HttpServletRequest request, HttpServletResponse response) {
		attachmentService.downloadService(id,hash,request,response);
	}
	/**
	 * 删除附件
	 */
	@ResponseBody
	@RequestMapping("/delAtt")
	public Response doDelAtt(Integer id){
		Response res = attachmentService.delService(id);
		return res;
	}
}
