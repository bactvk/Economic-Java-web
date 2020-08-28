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

@WebServlet("/admin/category")
public class CategoryServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if(action == null){ // list
			CategoryDAO categoryDAO = new CategoryDAO();
			List<Category> lists = categoryDAO.getAll();
			req.setAttribute("lists",lists);
			req.getRequestDispatcher("/WEB-INF/views/admin/category/list.jsp").forward(req, resp);
		}else if(action.equals("add")){
			req.getRequestDispatcher("/WEB-INF/views/admin/category/add.jsp").forward(req, resp);
		}else if(action.equals("delete")){
			int id = Integer.parseInt(req.getParameter("id"));
			doGetDelete(req,resp,id);
		}else if(action.equals("edit")){
			int id = Integer.parseInt(req.getParameter("id"));
			doGetEdit(req,resp,id);
			
		}
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if(action.equals("add")){
			doPostAdd(req,resp);
		}else if(action.equals("edit")){
			int id = Integer.parseInt(req.getParameter("id"));
			doPostEdit(req,resp,id);
		}
	}
	
	public void doPostAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String name = req.getParameter("name");
		String error = "";
		if(name.equals("")){
			error = "Please input category name";
			req.setAttribute("error", error);
			req.getRequestDispatcher("/WEB-INF/views/admin/category/add.jsp").forward(req, resp);
		}else{
			CategoryDAO categoryDAO = new CategoryDAO();
			if(categoryDAO.insertCategory(name) == 1){
				HttpSession session = req.getSession();
				session.setAttribute("success", "Add category successfuly");
			}
			resp.sendRedirect("category");
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
			resp.sendRedirect("category");
		}
	}
	
	public void doGetDelete(HttpServletRequest req, HttpServletResponse resp,int id) throws IOException
	{
		CategoryDAO categoryDAO = new CategoryDAO();
		categoryDAO.deleteCategory(id);
		HttpSession session = req.getSession();
		session.setAttribute("success", "Delete category success");
		resp.sendRedirect("category");
	}
	
	public void doGetEdit(HttpServletRequest req,HttpServletResponse resp,int id) throws ServletException, IOException
	{
		CategoryDAO categoryDAO = new CategoryDAO();
		req.setAttribute("category", categoryDAO.getCategoryByID(id));
		req.getRequestDispatcher("/WEB-INF/views/admin/category/edit.jsp").forward(req, resp);
	}
}
