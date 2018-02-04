package com.bjy.lotuas.common.fileupload;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;


public interface UploadFilter {
	public UploadResult validate(MultipartFile file) throws IOException ;
}
