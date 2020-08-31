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
import com.demo.models.Product;


public class ProductDAO {
	public void insertProduct(Product p)
	{
		try {
			Connection conn = MyConnection.getConnection();
			String sql = "INSERT INTO products (`name`,`price`,`category_id`,`number`,`sale`,`image`,`content`) VALUES(?,?,?,?,?,?,?)";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1,p.getName());
			pstm.setFloat(2, p.getPrice());
			pstm.setInt(3,p.getCategory_id());
			pstm.setInt(4, p.getNumber());
			pstm.setInt(5, p.getSale());
			pstm.setString(6,p.getImage());
			pstm.setString(7, p.getContent());
			
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public List<Product> getAll()
	{
		List<Product> list = new ArrayList<Product>();
		
		
		try {
			Connection conn = MyConnection.getConnection();
			String sql = "SELECT p.id,p.name,p.number,p.price,p.sale,p.image,c.name as category_name FROM products p LEFT JOIN category c "
					+ "ON p.category_id = c.id"
					+ " WHERE p.deleted_at IS NULL";
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
//				product.setCategory_id(rs.getInt("category_id"));
				product.setCategory_name(rs.getString("category_name"));
				product.setNumber(rs.getInt("number"));
				product.setPrice(rs.getFloat("price"));
				product.setSale(rs.getInt("sale"));
				product.setImage(rs.getString("image"));
				list.add(product);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	public void deleteProduct(int id)
	{
		try {
			Connection conn = MyConnection.getConnection();
			
			String sql = "UPDATE products SET deleted_at = ? WHERE id=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
			String date = formatter.format(new Date());
			pstm.setString(1,date);
			pstm.setInt(2, id);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public Product getProductByID(int id)
	{
		Product product = new Product();
		try {
			Connection conn = MyConnection.getConnection();
			String sql = "SELECT * FROM products WHERE id=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				product.setCategory_id(rs.getInt("category_id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getFloat("price"));
				product.setNumber(rs.getInt("number"));
				product.setSale(rs.getInt("sale"));
				
				return product;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public void updateProduct(Product p,int id)
	{
		try {
			Connection conn = MyConnection.getConnection();
			String sql = "UPDATE products SET category_id=?, name = ?, price = ?, number = ?, sale = ? WHERE id=? ";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, p.getCategory_id());
			pstm.setString(2, p.getName());
			pstm.setFloat(3, p.getPrice());
			pstm.setInt(4, p.getNumber());
			pstm.setInt(5, p.getSale());
			pstm.setInt(6, id);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
