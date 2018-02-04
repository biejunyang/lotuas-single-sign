package com.bjy.lotuas.common.fileupload;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bjy.lotuas.common.util.DownEncodeUtil;
import com.bjy.lotuas.common.vo.CommonResultVo;
import com.bjy.lotuas.config.SystemContext;


@Controller
@RequestMapping(value="/commonFile")
public class CommonFile
{
	/**
	 * 
	 * @param fileName
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/view")  
	@ResponseBody
	public String download(String fileName, HttpServletRequest request, HttpServletResponse response) { 
		/** 
         *  用getBytes(encoding)：返回字符串的一个byte数组 
         *  当b[0]为  63时，应该是转码错误 
         *  A、不乱码的汉字字符串： 
         *  1、encoding用GB2312时，每byte是负数； 
         *  2、encoding用ISO8859_1时，b[i]全是63。 
         *  B、乱码的汉字字符串： 
         *  1、encoding用ISO8859_1时，每byte也是负数； 
         *  2、encoding用GB2312时，b[i]大部分是63。 
         *  C、英文字符串 
         *  1、encoding用ISO8859_1和GB2312时，每byte都大于0； 
         *  <p/> 
         *  总结：给定一个字符串，用getBytes("iso8859_1") 
         *  1、如果b[i]有63，不用转码；  A-2 
         *  2、如果b[i]全大于0，那么为英文字符串，不用转码；  B-1 
         *  3、如果b[i]有小于0的，那么已经乱码，要转码。  C-1 
         */ 
		byte[] bytes = null;
		boolean needConvertEncode = false;
		try {
			bytes = fileName.getBytes("ISO-8859-1");
			for (byte b : bytes) {
				if(b < 0){
					needConvertEncode = true;
					break;
				}
			}
			if(needConvertEncode){
				fileName = new String(fileName.getBytes("ISO-8859-1"),"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/");
		String realPath = SystemContext.getSystemInfo("System.fileManage.dir");
		String downLoadPath = realPath +"/"+ fileName;   
		//System.out.println(downLoadPath);  
		OutputStream out = null;
		try{
		File contractFile=new File(downLoadPath);
		fileName = fileName.substring(36);
		response.setHeader("content-disposition","attachment;filename="+DownEncodeUtil.getEncodeFileName(fileName, request));
		out = response.getOutputStream();
		FileUtils.copyFile(contractFile, out);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(out);
		}
		return null;
	}  
	
	/**
	 * 上传合同文件
	 * @param contractFile
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/upload", method=RequestMethod.POST)  
	@ResponseBody
	public CommonResultVo upload(MultipartFile uploadFile, HttpServletRequest request) throws IOException{  
		//如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解  
		//如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解  
		//并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件  
		String fileName = null;
		if(uploadFile.isEmpty()){  
			return new CommonResultVo(false, "文件未选择");
		}else{  
			/*if(!"application/msword".equals(contractFile.getContentType())){
				return new CommonResultVo(false, "只能上传word文档");
			}*/
			System.out.println("文件长度: " + uploadFile.getSize());  
			System.out.println("文件类型: " + uploadFile.getContentType());  
			System.out.println("文件名称: " + uploadFile.getName());  
			System.out.println("文件原名: " + uploadFile.getOriginalFilename());  
			System.out.println("========================================");  
			//如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
			//String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload"); 
			
			//判断文件的大小			
			if(uploadFile.getSize()>10000000){
				return new CommonResultVo(false, "上传文件不能大于10M!");				
			}			
			
			//判断是否是pdf文件				
			
			if(!uploadFile.getOriginalFilename().endsWith(".pdf")&&!uploadFile.getOriginalFilename().endsWith(".doc")&&!uploadFile.getOriginalFilename().endsWith(".docx")&&!uploadFile.getOriginalFilename().endsWith(".xlsx")
					&&!uploadFile.getOriginalFilename().endsWith(".jpg")&&!uploadFile.getOriginalFilename().endsWith(".bmp")){							
				return new CommonResultVo(false, "上传文件必须是pdf、doc、docx、jpg、或bmp文件!");						
			}
			
			
			String realPath = SystemContext.getSystemInfo("System.fileManage.dir");
			File uploadDir=new File(realPath);
			if(!uploadDir.exists()){
				uploadDir.mkdirs();
			}
			//这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的  
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			
			String uuid=UUID.randomUUID().toString();	
			
			//为了去掉swf中文名
			fileName =uuid+ uploadFile.getOriginalFilename();
			File newFile=new File(realPath, fileName); 
			FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), newFile); 		
		
			return new CommonResultVo(true, fileName);							
		}  
	
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)  
	@ResponseBody
	public CommonResultVo deleteSupplier(String fileName, HttpServletRequest request) throws IOException{  
		//String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/");
		String realPath = SystemContext.getSystemInfo("System.fileManage.dir");
		String filePath = realPath +"/"+ fileName;
		if(deleteFile(filePath)){
			return new CommonResultVo(true, "删除成功");
		}
		return new CommonResultVo(false, "删除失败");
	}
	
	public boolean deleteFile(String filePath){
		File file = new File(filePath);
		if(file.exists()){
			return file.delete();
		} else {
			return true;
		}
//		return false;
	}
		
	/**
	 * 文件下载
	 * @param fullPath：文件全路径
	 * @throws IOException 
	 */
	@RequestMapping(value="downloadFile")
	public void downloadFile(String fullPath, HttpServletResponse resp, HttpServletRequest req) throws IOException{
		File file=new File(fullPath);
		String attachmentName=LoadUtil.getFileNameFromAbsolutePath(file);
		attachmentName = attachmentName.substring(attachmentName.indexOf("_")+1);
		resp.setHeader("content-disposition","attachment;filename="+DownEncodeUtil.getEncodeFileName(attachmentName, req));
		OutputStream out = resp.getOutputStream();
		Files.copy(file.toPath(), out);
		out.flush();
		out.close();
	}
	
	
	
	
}
