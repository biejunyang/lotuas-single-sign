package com.bjy.lotuas.common.fileupload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bjy.lotuas.common.util.CollectionUtil;

/**
 * 文件上传帮助类
 * @author DELL
 *
 */
public final class LoadUtil
{
	
	
	
	public static List<String> uploadAndReturnPath(HttpServletRequest request,
			String fileName, String basePath) throws IOException
	{
		File uploadDir=new File(basePath);
		if(!uploadDir.exists()){
			uploadDir.mkdirs();
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> multipartFiles = multipartRequest.getFiles(fileName);
		List<String> filePaths = new ArrayList<String>();
		if (CollectionUtil.isNotNull(multipartFiles))
		{
			for (MultipartFile multipartFile : multipartFiles)
			{
				if(multipartFile.getSize()>0 && !multipartFile.isEmpty()){
					File file=new File(uploadDir, multipartFile.getOriginalFilename());
					FileCopyUtils.copy(multipartFile.getBytes(), file);
					filePaths.add(file.getAbsolutePath());
				}
			}
		}
		return filePaths;
	}

	
	public static List<String> uploadAndReturnUUIDPath(HttpServletRequest request,
			String fileName, String basePath) throws IOException
	{
		File uploadDir=new File(basePath);
		if(!uploadDir.exists()){
			uploadDir.mkdirs();
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> multipartFiles = multipartRequest
				.getFiles(fileName);
		List<String> filePaths = new ArrayList<String>();
		for (MultipartFile multipartFile : multipartFiles)
		{
			if(multipartFile.getSize()>0 && !multipartFile.isEmpty()){
				String fileNameSuffix = multipartFile.getOriginalFilename();
				String destFileName =getUUID()+ getFileSuffix(fileNameSuffix);
				File file=new File(uploadDir, destFileName);
				FileCopyUtils.copy(multipartFile.getBytes(), file);
				filePaths.add(file.getAbsolutePath());
			}
		}
		return filePaths;
	}
	
	
	/**
	 * 文件上传
	 * @param request：文件上传请求对象
	 * @param fileName：<input type="file">文件上传框的name属性名称
	 * @param basePath：上传到服务器的路径(绝对路径)
	 * @return：上传的绝对路径集合
	 * @throws IOException
	 */
	public static List<UploadFile> uploadFile(HttpServletRequest request,
			String fileName, String basePath) throws IOException
	{
		File uploadDir=new File(basePath);
		if(!uploadDir.exists()){
			uploadDir.mkdirs();
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> multipartFiles = multipartRequest
				.getFiles(fileName);
		List<UploadFile> uploadFiles = new ArrayList<UploadFile>();
		for (MultipartFile multipartFile : multipartFiles)
		{
			if(multipartFile.getSize()>0 && !multipartFile.isEmpty()){
				String fileNameSuffix = multipartFile.getOriginalFilename();
				String destFileName =getUUID()+ getFileSuffix(fileNameSuffix);
				File file=new File(uploadDir, destFileName);
				FileCopyUtils.copy(multipartFile.getBytes(), file);
				uploadFiles.add(new UploadFile(file, multipartFile.getOriginalFilename()));
			}
		}
		return uploadFiles;
	}
	
	
	/**
	 * 文件上传
	 * @param request：文件上传请求对象
	 * @param fileName：<input type="file">文件上传框的name属性名称
	 * @param basePath：上传到服务器的路径(绝对路径)
	 * @return：上传的绝对路径集合
	 * @throws IOException
	 */
	public static UploadResult uploadFileWithOriginName(HttpServletRequest request,
			String fileName, String basePath, UploadFilter filter) throws IOException
	{
		File uploadDir=new File(basePath);
		if(!uploadDir.exists()){
			uploadDir.mkdirs();
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> multipartFiles = multipartRequest
				.getFiles(fileName);
		List<UploadFile> uploadFiles = new ArrayList<UploadFile>();
		for (MultipartFile multipartFile : multipartFiles)
		{
			if(multipartFile.getSize()>0 && !multipartFile.isEmpty()){
				UploadResult result=filter.validate(multipartFile);
				if(result.isSuccess()){
					String fileNameSuffix = multipartFile.getOriginalFilename();
					String destFileName =UUID.randomUUID().toString()+fileNameSuffix;
					File file=new File(uploadDir, destFileName);
					FileCopyUtils.copy(multipartFile.getBytes(), file);
					uploadFiles.add(new UploadFile(file, multipartFile.getOriginalFilename()));
				}else{
					return result;
				}
			}
		}
		return new UploadResult(true, uploadFiles);
	}
	
	
	public static String getFileSuffix(String fileName)
	{
		if (fileName == null)
			return null;
		if (fileName.indexOf(".") == -1)
			return null;
		return fileName.substring(fileName.lastIndexOf("."));
	}

	private static String getUUID()
	{
		String s = UUID.randomUUID().toString();
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
				+ s.substring(19, 23) + s.substring(24);
	}

	public static String getFileNameFromAbsolutePath(String absolutePath)
	{
		return new File(absolutePath).getName();
	}

	public static String getFileNameFromAbsolutePath(File file)
	{
		return file.getName();
	}
	
	public static class ImgType{
		public static List<String> suffixs=Arrays.asList("JPG","JPEG","PNG","TIF");
		public static String errorMsg="上传文件必须是jpg、jpeg、png、tif文件!";
	}
	
	
//	
//	public static Object upload(HttpServletRequest request,
//			HttpServletResponse response, String fileName) throws IOException
//	{
//		String defaultBasePath = SystemContext.getFileUploadFileDir()
//				+ File.separator;
//		return upload(request, response, fileName, defaultBasePath);
//	}
//
//	public static Object upload(HttpServletRequest request,
//			HttpServletResponse response, String fileName, String basePath)
//			throws IOException
//	{
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		List<MultipartFile> multipartFiles = multipartRequest
//				.getFiles(fileName);
//		if (multipartFiles == null)
//		{
//			return new CommonResultVo(false, "文件不能为空！");
//		}
//		else
//		{
//			int i = 0;
//			for (MultipartFile multipartFile : multipartFiles)
//			{
//				FileCopyUtils.copy(multipartFile.getBytes(), new File(basePath
//						+ i + "_" + multipartFile.getOriginalFilename()));
//				i++;
//			}
//		}
//		return new CommonResultVo(true, "文件上传成功！");
//	}
//
	
//
//	public static List<String> uploadExcelFiles(HttpServletRequest request,
//			String fileName) throws IOException
//	{
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		List<MultipartFile> multipartFiles = multipartRequest
//				.getFiles(fileName);
//		List<String> filePaths = new ArrayList<String>();
//		String basePath = SystemContext.getFileUploadFileDir() + File.separator;
//		if (multipartFiles == null)
//		{
//			return null;
//		}
//		else
//		{
//			int i = 0;
//			for (MultipartFile multipartFile : multipartFiles)
//			{
//				String filePath = basePath + i + "_"
//						+ System.currentTimeMillis() + "_"
//						+ multipartFile.getOriginalFilename();
//				FileCopyUtils
//						.copy(multipartFile.getBytes(), new File(filePath));
//				filePaths.add(filePath);
//				i++;
//			}
//		}
//		return filePaths;
//	}
//
//	public static byte[] getImageFromTempDir(String fileName)
//			throws IOException
//	{
//		return getFileFromTempDir(new File(SystemContext.imageDir
//				+ File.separator + fileName));
//	}
//
//	public static byte[] getsampleAttachmentFromTempDir(String fileName)
//			throws IOException
//	{
//		return getFileFromTempDir(new File(SystemContext.sampleAttachmentDir
//				+ File.separator + fileName));
//	}
//
//	public static byte[] getFileFromTempDir(File file) throws IOException
//	{
//		return Files.toByteArray(file);
//	}
//
//	public static String saveFileReturnAndPath(HttpServletRequest request,
//			String fileName) throws IOException
//	{
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		List<MultipartFile> multipartFiles = multipartRequest
//				.getFiles(fileName);
//		if (multipartFiles == null || multipartFiles.size() == 0)
//		{
//			return null;
//		}
//		else
//		{
//			MultipartFile file = multipartFiles.get(0);
//			if (file == null || file.getBytes() == null
//					|| file.getBytes().length == 0)
//			{
//				return null;
//			}
//			String basePath = SystemContext.sampleAttachmentDir
//					+ File.separator;
//			String fileNameSuffix = file.getOriginalFilename();
//			String filePath = basePath + getUUID()
//					+ getFileSuffix(fileNameSuffix);
//			FileCopyUtils.copy(multipartFiles.get(0).getBytes(), new File(
//					filePath));
//			return filePath;
//		}
//	}

}
