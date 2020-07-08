package com.fz.enroll.student.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fz.base.action.QueryBaseAction;
import com.fz.base.service.QueryBaseService;
import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.common.security.ThreadLocalUtils;
import com.fz.enroll.entity.student.GraStuInfo;
import com.fz.enroll.enum0.UserType;
import com.fz.enroll.student.service.StuGraInfoListService;


@Controller
@RequestMapping("/stuGraListInfo")
public class StuGraInfoListAction extends QueryBaseAction<GraStuInfo>{
	@Autowired
	private StuGraInfoListService stuGraInfoListService;
	/**
	 * 毕业生信息导入
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public String doImport(@RequestParam("file")MultipartFile file,Integer year,Model model){
		if(ThreadLocalUtils.getCurrentUser()==null){
			model.addAttribute("retCode", ReturnCode.NOT_LOGIN);
			return "student/importFinish";
		}else if(ThreadLocalUtils.getCurrentUser().getType()!=UserType.ADMIN){
			model.addAttribute("retCode", ReturnCode.UNAUTHORIZED);
			return "student/importFinish";
		}
		Response res = stuGraInfoListService.importService(file,year);
		model.addAttribute("retCode", res.getRetCode());
		model.addAttribute("msg", res.getErrorMsg());
		return "student/importFinish";
	}
	/**
	 * 毕业生信息导出
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void doExport(@RequestParam("file")MultipartFile file,HttpServletRequest request,HttpServletResponse response){
		stuGraInfoListService.exportService(file,request,response);
	}
	
	@Override
	protected QueryBaseService<GraStuInfo> getService() {
		return this.stuGraInfoListService;
	}

	@Override
	protected Map<String, Object> getParams(HttpServletRequest request) {
		String keyword = request.getParameter("keyword");
		String sylb = request.getParameter("sylb");
		String xb = request.getParameter("xb");
		String bj = request.getParameter("bj");
		String status = request.getParameter("status");
		String year = request.getParameter("year");
		Map<String, Object> map=this.stuGraInfoListService.createQueryParams(keyword,sylb,xb,bj,status,year);
		return map;
	}
}
