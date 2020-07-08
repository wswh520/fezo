package com.fz.enroll.dto.student;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.fz.common.util.Utils;
import com.fz.enroll.entity.student.StuApply;
import com.fz.enroll.enum0.StuApplyStatus;

public class StuApplyReview {

	public StuApply convert2Entity(){
		try{
			StuApply entity = new StuApply();
			entity.setId(Integer.valueOf(this.getId()));
			entity.setStatus(StuApplyStatus.valueOf(this.getStatus()).val());
			entity.setReviewer(Utils.emptyToNull(this.getReviewer()));
			entity.setDateOfReview(this.getTimeFromStr(this.getDateOfReview()));
			entity.setNote(Utils.emptyToNull(this.getNote()));
			entity.setMessage(Utils.emptyToNull(this.getMessage()));
			if(entity.getId()==0
					||(entity.getStatus()!=StuApplyStatus.REVIEW_PASS.val()
							&&entity.getStatus()!=StuApplyStatus.REVIEW_REFUSE.val()
							&&entity.getStatus()!=StuApplyStatus.REVIEW_WAITING.val())
					||entity.getReviewer()==null
					||entity.getDateOfReview()==0){
				return null;
			}
			
			return entity;
		} catch (Exception e) {
			return null;
		}
	}
	private long getTimeFromStr(String timeStr) throws ParseException{
		SimpleDateFormat sp = new SimpleDateFormat(StuApply.TIME_FORMAT);
		return sp.parse(timeStr).getTime();
	}
	
	private String id;
	private String status;
	private String reviewer;
	private String dateOfReview;
	private String note;
	private String message;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReviewer() {
		return reviewer;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	public String getDateOfReview() {
		return dateOfReview;
	}
	public void setDateOfReview(String dateOfReview) {
		this.dateOfReview = dateOfReview;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
