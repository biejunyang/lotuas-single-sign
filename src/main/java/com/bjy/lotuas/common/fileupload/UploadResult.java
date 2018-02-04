package com.bjy.lotuas.common.fileupload;

import java.util.List;

public class UploadResult {
	private boolean success;
	private String message;
	private List<UploadFile> uploadFiles;
	
	
	public UploadResult() {
		// TODO Auto-generated constructor stub
	}
	
	public UploadResult(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public UploadResult(boolean success, List<UploadFile> uploadFiles) {
		super();
		this.success = success;
		this.uploadFiles = uploadFiles;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<UploadFile> getUploadFiles() {
		return uploadFiles;
	}
	public void setUploadFiles(List<UploadFile> uploadFiles) {
		this.uploadFiles = uploadFiles;
	}
	
}
