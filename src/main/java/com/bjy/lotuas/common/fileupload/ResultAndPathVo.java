package com.bjy.lotuas.common.fileupload;

import java.util.List;

public class ResultAndPathVo {
	private boolean success;
	private String message;
	private List<String> filePaths = null;

	public ResultAndPathVo() {
		super();
	}

	public ResultAndPathVo(boolean success, String message,
			List<String> filePaths) {
		super();
		this.success = success;
		this.message = message;
		this.filePaths = filePaths;
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

	public List<String> getFilePaths() {
		return filePaths;
	}

	public void setFilePaths(List<String> filePaths) {
		this.filePaths = filePaths;
	}
}
