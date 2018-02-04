package com.bjy.lotuas.common.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bjy.lotuas.common.fileupload.LoadUtil;
import com.bjy.lotuas.common.util.DownEncodeUtil;
import com.bjy.lotuas.common.util.StringUtil;
import com.bjy.lotuas.common.vo.CommonResultVo;
import com.bjy.lotuas.config.SystemContext;



@Controller
@RequestMapping(value="/common")
public class CommonController
{

	


	
	/**
	 * 文件下载
	 * @param fullPath：文件全路径
	 * @throws IOException 
	 */
	@RequestMapping(value="downloadFile")
	public void downloadFile(String fullPath, String fileName, HttpServletResponse resp, HttpServletRequest req) throws IOException{
		File file=new File(fullPath);
		String attachmentName=LoadUtil.getFileNameFromAbsolutePath(file);
		if(StringUtil.isNotNull(fileName)){
			attachmentName=fileName;
		}
		resp.setHeader("content-disposition","attachment;filename="+DownEncodeUtil.getEncodeFileName(attachmentName, req));
		OutputStream out = resp.getOutputStream();
		Files.copy(file.toPath(), out);
		out.flush();
		out.close();
	}
	
	
	@RequestMapping(value="/uploadPhotoWithPix", method=RequestMethod.POST)  
	@ResponseBody
	public CommonResultVo uploadPhotoWithPix(MultipartFile uploadFile,int photoWidth,int photoHeight, HttpServletRequest request) throws IOException{  
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
			if(uploadFile.getSize()>50000){
				return new CommonResultVo(false, "上传文件不能大于50kb!");				
			}			
			
			//判断是否是pdf文件				
			
			if(!uploadFile.getOriginalFilename().endsWith(".jpg")&&!uploadFile.getOriginalFilename().endsWith(".png")){							
				return new CommonResultVo(false, "上传文件必须是jpg、或png文件!");						
			}
			
			
			
			//转为图片，打印图片的宽高
			BufferedImage sourceImg =ImageIO.read(uploadFile.getInputStream());			
			System.out.println(String.format("%.1f",uploadFile.getSize()/1024.0));
			System.out.println(sourceImg.getWidth());
			System.out.println(sourceImg.getHeight());
			
			
			
			
			if(sourceImg.getWidth()!=photoWidth){
				
				return new CommonResultVo(false, "上传图片的宽度与类型定义的宽度不一致，上传失败!");
			}
			
			if(sourceImg.getHeight()!=photoHeight){
				return new CommonResultVo(false, "上传图片的高度与类型定义的高度不一致，上传失败!");
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
	
}
