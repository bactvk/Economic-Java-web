package com.demo.servlets.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.demo.DAO.CategoryDAO;
import com.demo.models.Category;

@WebServlet(urlPatterns = "/admin/product/*")
@MultipartConfig
public class ProductServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getPathInfo();
		System.out.println(action);
		if(action.equals("/add")) {
			doGetAdd(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getPathInfo();
		if(action.equals("/add")) {
			doPostAdd(req,resp);
		}
	}
	protected void doGetAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		CategoryDAO categoryDAO = new CategoryDAO();
		
		List<Category> lists = categoryDAO.getAllParent();
		req.setAttribute("listCategory", lists);
		req.getRequestDispatcher("/WEB-INF/views/admin/product/add.jsp").forward(req, resp);
	}
	
	protected void doPostAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String category = req.getParameter("category_id");
		String name = req.getParameter("name");
		String price = req.getParameter("price");
		String number = req.getParameter("number");
		String sale = req.getParameter("sale");
		
		//upload file
		ServletFileUpload uploadFile = new ServletFileUpload(new DiskFileItemFactory());
		//List<FileItem> items = uploadFile.parseRequest(req);
		

		List<String> errors = new ArrayList<String>();
		if(category.equals("")) {
			errors.add("Please choose category");
		}
		if(name.equals("")) {
			errors.add("Please enter name product");
		}
		if(price.equals("")) {
			errors.add("Please enter price");
		}
		if(number.equals("")) {
			errors.add("Please enter number product");
		}

		if(!errors.isEmpty()) {
			req.setAttribute("errors",errors );
			req.getRequestDispatcher("/WEB-INF/views/admin/product/add.jsp").forward(req, resp);
		}
	}
}
