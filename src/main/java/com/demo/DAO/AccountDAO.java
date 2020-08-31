package com.demo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.demo.connnection.MyConnection;
import com.demo.models.Account;

public class AccountDAO {
	
	public Account findAccount(String username,int role_id)
	{
		Account account = new Account();
		try {
			Connection conn = MyConnection.getConnection();
			String sql = "SELECT * FROM account WHERE username = ? AND role_id = ? LIMIT 1";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, username);
			pstm.setInt(2, role_id);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				account.setUsername(username);
				account.setEmail(rs.getString("email"));
				return account;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Account login(String username,String password,int role_id)
	{
		Account account = new Account();
		try {
			Connection conn = MyConnection.getConnection();
			System.out.println(conn);
			String sql = "SELECT * FROM account WHERE username=? AND role_id = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, username);
			pstm.setInt(2, role_id); // role_id = 1 -> admin

			
			ResultSet rs = pstm.executeQuery();
//			System.out.println(BCrypt.hashpw("admin", BCrypt.gensalt())); // admin = $2a$10$UEj1gtgKhvAYyRgV1awNE.Vc9XfBhbdPWyPQh2OA7vZ/cVhK8CHrC
			if(rs.next()){
				account.setUsername(rs.getString("username"));
				account.setPassword(rs.getString("password"));
				
				if(BCrypt.checkpw(password, account.getPassword())){
					return account;
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void UpdateInfoAccount(String username,String password  ,String email)
	{
		try {
			Connection conn = MyConnection.getConnection();
			String sql = "";
			if(password.equals("")){
				sql = "UPDATE account SET username='" +username+ "'," + "email='" + email + "'" ;
			}else{
				String new_pass = BCrypt.hashpw(password, BCrypt.gensalt());
				sql = "UPDATE account SET username='" +username+ "'," + "email='" + email + "'" + ",password='" + new_pass + "'";
			}
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void UpdateInfoAccount(String username,String email)
	{
		UpdateInfoAccount(username,"",email);
	}
}
