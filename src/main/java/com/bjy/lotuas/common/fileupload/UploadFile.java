package com.bjy.lotuas.common.fileupload;

import java.io.File;

public class UploadFile {
	private File file;//上传文件
	private String fileOriginalNname;//文件原始名称
	
	public UploadFile() {
		// TODO Auto-generated constructor stub
	}
	
	
	public UploadFile(File file, String fileOriginalNname) {
		super();
		this.file = file;
		this.fileOriginalNname = fileOriginalNname;
	}


	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileOriginalNname() {
		return fileOriginalNname;
	}
	public void setFileOriginalNname(String fileOriginalNname) {
		this.fileOriginalNname = fileOriginalNname;
	}
	
}
