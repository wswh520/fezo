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
import com.fz.enroll.dto.student.StuApplyAdd;
import com.fz.enroll.entity.student.StuApply;
import com.fz.enroll.enum0.AttOtype;
import com.fz.enroll.enum0.StuApplyStatus;
import com.fz.enroll.enum0.StuType;
import com.fz.enroll.file.service.AttachmentService;
import com.fz.enroll.student.service.StuApplyService;

@Controller
@RequestMapping("/stuApply")
public class StuApplyAction {
	
	@Autowired
	private StuApplyService stuApplyService;
	@Autowired
	private AttachmentService attachmentService;

	@ResponseBody
	@RequestMapping("load")
	public Response doLoad(String id) {
		Response res = stuApplyService.loadService(id);
		return res;
	}
	
	@ResponseBody
	@RequestMapping("save")
	public Response doSave(StuApplyAdd entity) {
//		Response res = stuApplyService.saveService(entity.convert2Entity());
//		return res;
		return new Response(ReturnCode.SERVER_INNER_ERROR);
	}
	
	@ResponseBody
	@RequestMapping("submit2Review")
	public Response doSubmit2Review(String id) {
		Response res = stuApplyService.submit2ReviewService(id, StuApplyStatus.SUBMIT_ONCE);
		return res;
	}
	
	@ResponseBody
	@RequestMapping("submit2ReviewTwice")
	public Response doSubmit2ReviewTwice(String id) {
		Response res = stuApplyService.submit2ReviewService(id, StuApplyStatus.SUBMIT_TWICE);
		return res;
	}
	
	@RequestMapping("download/{id}")
	public void doDownload(@PathVariable("id")String id,HttpServletRequest request,HttpServletResponse response) {
		stuApplyService.downloadStuApplyService(false,id,request,response);
	}
	
	@RequestMapping("downloadPdf/{id}")
	public void doDownloadPdf(@PathVariable("id")String id,HttpServletRequest request,HttpServletResponse response) {
		stuApplyService.downloadStuApplyService(true,id,request,response);
	}

	@RequestMapping("downloadTestPdf/{id}")
	public void downloadTestPdf(@PathVariable("id")String id,HttpServletRequest request,HttpServletResponse response) {
		stuApplyService.downloadStuApplyService2(true,id,request,response);
	}

	@ResponseBody
	@RequestMapping("historyList")
	public Response doGetHistoryList(String id){
		Response res = stuApplyService.getHistoryListService(id);
		return res;
	}

	@ResponseBody
	@RequestMapping("historyInfo")
	public Response doGetHistoryInfo(String id){
		Response res = stuApplyService.getHistoryInfoService(id);
		return res;
	}

	@RequestMapping("print/{id}")
	public String doPrint(@PathVariable("id")String id,Model model){
		model.addAttribute("id", id);
		return "student/print";
	}
	
	@ResponseBody
	@RequestMapping("initPinyin")
	public Response doInitPinyin() {
		Response res = stuApplyService.initPinyinService();
		return res;
	}
	/**************************附件相关**********************************/
	@ResponseBody
	@RequestMapping("/loadAtt")
	public Response doLoadAtt(Integer oid, AttOtype otype, StuType type){
		Response res =stuApplyService.loadAttService(oid, otype, type);
		return res;
	}
	/**
	 * 上传附件
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadAtt", method = RequestMethod.POST)
	public Response doUploadAtt(@RequestParam("file")MultipartFile file,String prefix, AttOtype otype, StuType type) {
		Response res = stuApplyService.loadService(null);
		if(res.getRetCode()!=ReturnCode.SUCCESS){
			return res;
		}
		int oid = ((StuApply)res.getData()).getId();
		res = stuApplyService.uploadService(file,prefix,oid,otype,type);
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
	/**
	 * 转级，将往年不予录取的儿童强制转到本年进行审核
	 * 三张表的状态均重置为未提交
	 * 登记信息 ：年份重置为本年，报名号重新生成
	 * 学籍信息：设为本年 级，班级置空，入学年月改为本年9月
	 * 登记的历史信息：重新生成json,暂时未做修改
	 * @return
	 */
	@ResponseBody
	@RequestMapping("forwardGrade")
	public Response doForwardGrade(Integer id) {
		Response res = stuApplyService.forwardGradeServiceT(id);
		return res;
	}
}
