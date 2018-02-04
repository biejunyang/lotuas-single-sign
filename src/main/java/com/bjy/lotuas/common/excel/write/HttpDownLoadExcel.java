package com.bjy.lotuas.common.excel.write;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@SuppressWarnings({"rawtypes"})
public class HttpDownLoadExcel extends AbstractWriteExcel {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected String fileTitle;

	public HttpDownLoadExcel(String fileTitle,List<String> titles,HttpServletRequest request,
			HttpServletResponse response, List<List<ExcelHead>> excelHeadsList,
			List<Collection> datasetList, String pattern,int currentIndex) {
		super(titles,excelHeadsList, datasetList, pattern, currentIndex);
		this.fileTitle = fileTitle;
		this.request = request;
		this.response = response;
	}
	
	public HttpDownLoadExcel(String fileTitle,HttpServletRequest request,
			HttpServletResponse response, List<ExcelHead> excelHeads, Collection dataset) {
		super(fileTitle,excelHeads, dataset);
		this.fileTitle = fileTitle;
		this.request = request;
		this.response = response;
	}

	@Override
	public void doExport() {
		OutputStream out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + this.getEncodeFileName());
			out = response.getOutputStream();
			this.workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private final String getEncodeFileName()
			throws UnsupportedEncodingException {
		String fileName = this.fileTitle;
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {// firefox浏览器
			fileName = new String(this.fileTitle.getBytes("UTF-8"), "ISO8859-1");
		} else if (request.getHeader("User-Agent").toUpperCase()
				.indexOf("MSIE") > 0) {// IE浏览器
			fileName = URLEncoder.encode(this.fileTitle, "UTF-8");
		} else {
			fileName = this.toUtf8String(this.fileTitle);
		}
		return fileName.indexOf(".xls") > -1 ? fileName : fileName + ".xls";
	}

	private final String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b = null;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
}
