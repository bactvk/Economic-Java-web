package com.demo.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.demo.connnection.MyConnection;
import com.demo.models.Account;

public class AccountDAO {
	
//	private Account account;
	
	

	public Account login(String username,String password,int role_id)
	{
		Account account = null;
		try {
			Connection conn = MyConnection.getConnection();
			System.out.println(conn);
			String sql = "SELECT * FROM account WHERE username=? AND role_id = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, username);
			pstm.setInt(2, role_id); // role_id = 1 -> admin
			
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				// admin : $2a$12$/LXzIVd3EudfdaDwhDPDzuVETi6xytLxhRxG4qmuUE/y8mv/5ek.W
//				account = new Account(1, username, password, role_id);
//				System.out.println(account.getPassword());
//				System.out.println(account);
//				BCrypt.hashpw(password, BCrypt.gensalt(15));
//				if(BCrypt.checkpw(password,account.getPassword())){
//					
//					System.out.println(account);
//					return account;
//				}
//				account.setUsername(rs.getString("username"));
				account = new Account(1, rs.getString("username"), password, role_id);
				System.out.println(account);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return account;
	}
}
