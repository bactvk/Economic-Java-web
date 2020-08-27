package com.demo.servlets.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demo.DAO.AccountDAO;

@WebServlet(urlPatterns={
	"/admin",
	"/admin/login",
	
})
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if(action == null){
			req.getRequestDispatcher("/WEB-INF/views/admin/login/index.jsp").forward(req, resp);
		}
		else if(action.equalsIgnoreCase("logout")){
			doGetLogout(req,resp);
		}else if(action.equals("profile")){
			doGetProfile(req,resp);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username").trim();
		String password = req.getParameter("password").trim();
		AccountDAO accountdao = new AccountDAO();
		if(accountdao.login(username, password, 1) != null) //role_id = 1; //1:admin
		{
			HttpSession session = req.getSession();
			session.setAttribute("user_admin", username);
			resp.sendRedirect("dashboard");
		}else{
			req.setAttribute("error", "account invalid");
			req.getRequestDispatcher("/WEB-INF/views/admin/login/index.jsp").forward(req,resp);
		}
		
	}
	protected void doGetLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		HttpSession session = req.getSession();
		session.removeAttribute("user_admin");
		resp.sendRedirect("login");
	}
	protected void doGetProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("user_admin");
		AccountDAO accountdao = new AccountDAO();
		req.setAttribute("account", accountdao.findAccount(username, 1) ); // 1 = admin
		req.getRequestDispatcher("/WEB-INF/views/admin/profile/edit.jsp").forward(req, resp);
	}
}
