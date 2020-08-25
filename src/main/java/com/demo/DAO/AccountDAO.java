package com.demo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.demo.connnection.MyConnection;
import com.demo.models.Account;

public class AccountDAO {
	
	public Account login(String username,String password,int role_id)
	{
		Account account = null;
		try {
			Connection conn = MyConnection.getConnection();
			String sql = "SELECT * FROM username=? AND role_id = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, username);
			pstm.setInt(2, 1); // role_id = 1 -> admin
			
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				// admin : $2a$12$/LXzIVd3EudfdaDwhDPDzuVETi6xytLxhRxG4qmuUE/y8mv/5ek.W
				account.setUsername(rs.getString("username"));
				account.setPassword(rs.getString("password"));
				
				if(BCrypt.checkpw(password,account.getPassword())){
					return account;
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
