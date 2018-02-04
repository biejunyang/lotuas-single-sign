package com.bjy.lotuas.config.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name="welcomeServlet", urlPatterns="/welcome")
public class WeclomeServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("name", "张三");
		req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/samples/welcome.jsp").forward(req, resp);
	}
	
	
	
}
