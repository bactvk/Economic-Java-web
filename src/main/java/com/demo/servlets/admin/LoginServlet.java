package com.demo.servlets.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.demo.DAO.AccountDAO;
import com.demo.models.Account;

@WebServlet(value={
	"/admin",
	"/admin/login",
	
})
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/admin/login/index.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username").trim();
		String password = req.getParameter("password").trim();
		AccountDAO accountdao = new AccountDAO();
		if(accountdao.login(username, password, 1) != null) //role_id = 1; //1:admin
		{
			HttpSession session = req.getSession();
			System.out.println(username);
			session.setAttribute("user_admin", username);
			resp.sendRedirect("dashboard");
		}else{
			System.out.println("no ok");
			req.setAttribute("error", "account invalid");
			req.getRequestDispatcher("/WEB-INF/views/admin/login/index.jsp").forward(req,resp);
		}
		
	}
}
