package com.demo.servlets.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demo.DAO.AccountDAO;

@WebServlet("/admin/profile")
public class ProfileServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("user_admin");
		AccountDAO accountdao = new AccountDAO();
		req.setAttribute("account", accountdao.findAccount(username, 1) ); // 1 = admin
		req.getRequestDispatcher("/WEB-INF/views/admin/profile/edit.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String check_change_pass = req.getParameter("check_change_pass");
		String password = req.getParameter("password");
		
		List<String> errors = new ArrayList<String>();
		if(username.equals("")){
			errors.add("Please input username");
		}
		if(email.equals("")){
			errors.add("Please input email");
		}
		
		if(check_change_pass!=null){
			if(password.equals("")){
				errors.add("Please input password");
			}
		}
		if(!errors.isEmpty()){
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/WEB-INF/views/admin/profile/edit.jsp").forward(req, resp);
		}else{
			AccountDAO  accountdao = new AccountDAO();
			accountdao.UpdateInfoAccount(username,password,email);
			req.setAttribute("success", "update successfuly");
//			req.getRequestDispatcher("/WEB-INF/views/admin/profile/edit.jsp").forward(req, resp);
			resp.sendRedirect("profile");
		}
		
		
	}
}
