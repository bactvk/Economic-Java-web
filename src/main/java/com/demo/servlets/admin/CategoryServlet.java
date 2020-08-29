package com.demo.servlets.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demo.DAO.CategoryDAO;
import com.demo.models.Category;
import com.sun.istack.internal.logging.Logger;

@WebServlet("/admin/category/*")
public class CategoryServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String action = req.getParameter("action");
		String action = req.getPathInfo();
		System.out.println("dispatch path: "+ req.getPathInfo());
		if(action.equals("/list")){ // list
			CategoryDAO categoryDAO = new CategoryDAO();
			List<Category> lists = categoryDAO.getAll();
			req.setAttribute("lists",lists);
			req.getRequestDispatcher("/WEB-INF/views/admin/category/list.jsp").forward(req, resp);
		}else if(action.equals("/add")){
			req.getRequestDispatcher("/WEB-INF/views/admin/category/add.jsp").forward(req, resp);
		}else if(action.equals("/delete")){
			int id = Integer.parseInt(req.getParameter("id"));
			doGetDelete(req,resp,id);
		}else if(action.equals("/edit")){
			int id = Integer.parseInt(req.getParameter("id"));
			doGetEdit(req,resp,id);
			
		}else if(action.equals("/addSub")){
			int id = Integer.parseInt(req.getParameter("id"));
			req.setAttribute("parent_id", id);
			req.getRequestDispatcher("/WEB-INF/views/admin/category/add.jsp").forward(req, resp);
		}
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String action = req.getParameter("action");
		String action = req.getPathInfo();
		if(action.equals("/add") || action.equals("/addSub")){
			doPostAdd(req,resp);
		}else if(action.equals("/edit")){
			int id = Integer.parseInt(req.getParameter("id"));
			doPostEdit(req,resp,id);
		}
	}
	
	public void doPostAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String name = req.getParameter("name");
		String error = "";
		String id = req.getParameter("parent_id");
		System.out.println("id: "+id);
		
		if(name.equals("")){
			error = "Please input category name";
			req.setAttribute("error", error);
			req.getRequestDispatcher("/WEB-INF/views/admin/category/add.jsp").forward(req, resp);
		}else{
			CategoryDAO categoryDAO = new CategoryDAO();
			if(id == null){ //add category
				
				categoryDAO.insertCategory(name);

			}else{ // add subcategory
				categoryDAO.insertSubCategory(name,Integer.parseInt(id));
			}
			HttpSession session = req.getSession();
			session.setAttribute("success", "Adding successfuly");
			resp.sendRedirect("list");
		}
		
	}
	public void doPostEdit(HttpServletRequest req, HttpServletResponse resp,int id) throws ServletException, IOException
	{
		String name = req.getParameter("name");
		String error = "";
		if(name.equals("")){
			error = "Please input category name";
			req.setAttribute("error", error);
			req.getRequestDispatcher("/WEB-INF/views/admin/category/edit.jsp").forward(req, resp);
		}else{
			CategoryDAO categoryDAO = new CategoryDAO();
			categoryDAO.editCategory(name, id);
			HttpSession session = req.getSession();
			session.setAttribute("success", "Edit category successfuly");
			resp.sendRedirect("list");
		}
	}
	
	public void doGetDelete(HttpServletRequest req, HttpServletResponse resp,int id) throws IOException
	{
		CategoryDAO categoryDAO = new CategoryDAO();
		categoryDAO.deleteCategory(id);
		HttpSession session = req.getSession();
		session.setAttribute("success", "Delete category success");
		resp.sendRedirect("list");
	}
	
	public void doGetEdit(HttpServletRequest req,HttpServletResponse resp,int id) throws ServletException, IOException
	{
		CategoryDAO categoryDAO = new CategoryDAO();
		req.setAttribute("category", categoryDAO.getCategoryByID(id));
		req.getRequestDispatcher("/WEB-INF/views/admin/category/edit.jsp").forward(req, resp);
	}
}
