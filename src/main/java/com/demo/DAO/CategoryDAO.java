package com.demo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.demo.connnection.MyConnection;
import com.demo.models.Category;

public class CategoryDAO {
	
	public List<Category> getAll()
	{
		List<Category> lists = new ArrayList<Category>();
		try {
			Connection conn = MyConnection.getConnection();
			String sql = "SELECT c2.id,c2.name,c2.parent_id FROM category c1"
					+ " LEFT JOIN category c2 ON (c1.id = c2.id OR c1.id = c2.parent_id) "
					+ " WHERE c1.parent_id IS NULL AND c1.deleted_at IS NULL AND c2.deleted_at IS NULL "
					+ " ORDER BY c1.id,c2.id";
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				Category category = new Category();
				category.setName(rs.getString("name"));
				category.setId(rs.getInt("id"));
				category.setParent_id(rs.getInt("parent_id"));
				lists.add(category);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lists;
	}
	
	public List<Category> getAllParent()
	{
		List<Category> lists = new ArrayList<Category>();
		try {
			Connection conn = MyConnection.getConnection();
			String sql = "SELECT * FROM category WHERE deleted_at IS NULL";
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				Category category = new Category();
				category.setName(rs.getString("name"));
				category.setId(rs.getInt("id"));
				
				lists.add(category);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lists;
	}
	
	public int insertCategory(String name)
	{
		int status = 0;
		try {
			Connection conn = MyConnection.getConnection();
			String sql = "INSERT INTO category (`name`) VALUES(?)";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, name);
			pstm.executeUpdate();
			status = 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	public int insertSubCategory(String name,int id)
	{
		int status = 0;
		try {
			Connection conn = MyConnection.getConnection();
			String sql = "INSERT INTO category (`name`,`parent_id`) VALUES(?,?)";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, name);
			pstm.setInt(2, id);
			pstm.executeUpdate();
			status = 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	public int deleteCategory(int id)
	{
		int status = 0;
		try {
			Connection conn = MyConnection.getConnection();
			String sql = "UPDATE category SET deleted_at=? WHERE id=?";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
			String date = formatter.format(new Date());
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1,date);
			pstm.setInt(2, id);
			status = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
	public int editCategory(String name,int id)
	{
		int status = 0;
		try {
			Connection conn = MyConnection.getConnection();
			String sql = "UPDATE category SET name=? WHERE id=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1,name);
			pstm.setInt(2, id);
			status = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
	
	public Category getCategoryByID(int id)
	{
		Category category = new Category();
		try {
			Connection conn = MyConnection.getConnection();
			String sql = "SELECT * FROM category WHERE id=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1,id);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				category.setName(rs.getString("name"));
				return category;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
