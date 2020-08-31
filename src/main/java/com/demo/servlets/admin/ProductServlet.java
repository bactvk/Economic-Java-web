package com.demo.servlets.admin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.demo.DAO.CategoryDAO;
import com.demo.DAO.ProductDAO;
import com.demo.models.Category;
import com.demo.models.Product;

@WebServlet(urlPatterns = "/admin/product/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
	maxFileSize = 1024 * 1024 * 10, // 10MB
	maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ProductServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	// location to store file uploaded
	public static final String SAVE_DIRECTORY = "assets/uploads/";
 
    
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getPathInfo();
		System.out.println(action);
		if(action.equals("/add")) {
			doGetAdd(req, resp);
		}else if(action.equals("/list")){
			doGetList(req,resp);
		}else if(action.equals("/delete")){
			doGetDelete(req,resp);
		}else if(action.equals("/edit")){
			doGetEdit(req,resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getPathInfo();
		if(action.equals("/add")) {
			doPostAdd(req,resp);
		}else if(action.equals("/edit")){
			doPostEdit(req,resp);
		}
	}
	protected void doGetAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		CategoryDAO categoryDAO = new CategoryDAO();
		
		List<Category> lists = categoryDAO.getAllParent();
		req.setAttribute("listCategory", lists);
		req.getRequestDispatcher("/WEB-INF/views/admin/product/add.jsp").forward(req, resp);
	}
	protected void doGetList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		ProductDAO productDAO = new ProductDAO();
		req.setAttribute("listProduct", productDAO.getAll());
		req.getRequestDispatcher("/WEB-INF/views/admin/product/list.jsp").forward(req, resp);	
	}
	protected void doGetEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		int id = Integer.parseInt(req.getParameter("id"));
		ProductDAO productDAO = new ProductDAO();
		Product product = productDAO.getProductByID(id);
		
		CategoryDAO categoryDAO = new CategoryDAO();
		List<Category> lists = categoryDAO.getAllParent();
		req.setAttribute("listCategory", lists);
		
		req.setAttribute("product", product);
		req.getRequestDispatcher("/WEB-INF/views/admin/product/edit.jsp").forward(req, resp);
		
	}
	protected void doGetDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		int id = Integer.parseInt(req.getParameter("id"));
		ProductDAO productDAO = new ProductDAO();
		productDAO.deleteProduct(id);
		HttpSession session = req.getSession();
		session.setAttribute("success", "Delete product successfuly");
		resp.sendRedirect("list");
	}
	protected void doPostAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String name = req.getParameter("name");
		int category_id = (!req.getParameter("category_id").equals(""))? Integer.parseInt(req.getParameter("category_id")):0;
		float price = (!req.getParameter("price").equals(""))?Float.parseFloat(req.getParameter("price")):0;
		int number = (!req.getParameter("number").equals(""))? Integer.parseInt(req.getParameter("number")):0;
		int sale = (!req.getParameter("sale").equals(""))? Integer.parseInt(req.getParameter("sale")):0;
		
		String fileName = "";
		//upload file
		
        String appPath = req.getServletContext().getRealPath("");
        appPath = appPath.replace('\\', '/');
        // Thư mục để save file tải lên.
        String fullSavePath = null;
        if (appPath.endsWith("/")) {
            fullSavePath = appPath + SAVE_DIRECTORY;
        } else {
            fullSavePath = appPath + "/" + SAVE_DIRECTORY;
        }
        // Danh mục các phần đã upload lên (Có thể là nhiều file).
        for (Part part : req.getParts()) {
            fileName = extractFileName(part);
            if (fileName != null && fileName.length() > 0) {
                String filePath = fullSavePath + fileName;
                System.out.println("Write attachment to file: " + filePath);

                // Ghi vào file.
                part.write(filePath);
                break;
            }
        }
        
        
		List<String> errors = new ArrayList<String>();
		if(category_id == 0) {
			errors.add("Please choose category");
		}
		if(name.equals("")) {
			errors.add("Please enter name product");
		}
		if(price == 0) {
			errors.add("Please enter price");
		}
		if(number == 0) {
			errors.add("Please enter number product");
		}
		
		if(!errors.isEmpty()) {
			CategoryDAO categoryDAO = new CategoryDAO();
			List<Category> lists = categoryDAO.getAllParent();
			req.setAttribute("listCategory", lists);
			req.setAttribute("errors",errors );
			req.getRequestDispatcher("/WEB-INF/views/admin/product/add.jsp").forward(req, resp);

		}else{
			Product product = new Product();
			product.setName(name);
			product.setCategory_id(category_id);
			product.setNumber(number);
			product.setPrice(price);
			product.setSale(sale);
			product.setImage(fileName);
			
			ProductDAO productDAO = new ProductDAO();
			productDAO.insertProduct(product);
			
			HttpSession session = req.getSession();
			session.setAttribute("success", "Adding product successfuly");
			resp.sendRedirect("list");
		}
	}
	
	protected void doPostEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		int id = Integer.parseInt(req.getParameter("id"));
		
		String name = req.getParameter("name");
		int category_id = (!req.getParameter("category_id").equals(""))? Integer.parseInt(req.getParameter("category_id")):0;
		float price = (!req.getParameter("price").equals(""))?Float.parseFloat(req.getParameter("price")):0;
		int number = (!req.getParameter("number").equals(""))? Integer.parseInt(req.getParameter("number")):0;
		int sale = (!req.getParameter("sale").equals(""))? Integer.parseInt(req.getParameter("sale")):0;
		
		List<String> errors = new ArrayList<String>();
		if(category_id == 0) {
			errors.add("Please choose category");
		}
		if(name.equals("")) {
			errors.add("Please enter name product");
		}
		if(price == 0) {
			errors.add("Please enter price");
		}
		if(number == 0) {
			errors.add("Please enter number product");
		}
		
		if(!errors.isEmpty()){
			CategoryDAO categoryDAO = new CategoryDAO();
			List<Category> lists = categoryDAO.getAllParent();
			req.setAttribute("listCategory", lists);
			req.setAttribute("errors",errors );
			req.getRequestDispatcher("/WEB-INF/views/admin/product/edit.jsp").forward(req, resp);
		}else{
			Product product = new Product();
			product.setName(name);
			product.setCategory_id(category_id);
			product.setNumber(number);
			product.setPrice(price);
			product.setSale(sale);
			
			ProductDAO productDAO = new ProductDAO();
			productDAO.updateProduct(product,id);
			
			HttpSession session = req.getSession();
			session.setAttribute("success", "Editing product successfuly");
			resp.sendRedirect("list");
		}
		
		
	}
	
	private String extractFileName (Part part) {
		String contentDisp = part.getHeader ("content-disposition");
		String [] items = contentDisp.split (";");
		for (String s: items) {
			if (s.trim (). startsWith ("filename")) {
				String clientFileName = s.substring (s.indexOf ("=") + 2, s.length () - 1);
				clientFileName = clientFileName.replace ("\\", "/");
				int i = clientFileName.lastIndexOf ('/');
				return clientFileName.substring (i + 1);
			}
		}
		return null;
	}
}
