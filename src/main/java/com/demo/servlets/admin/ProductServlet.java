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

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.demo.DAO.CategoryDAO;
import com.demo.DAO.ProductDAO;
import com.demo.models.Category;
import com.demo.models.Product;

@WebServlet(urlPatterns = "/admin/product/*")
@MultipartConfig
public class ProductServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	// location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "upload";
 
    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    
	
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
		
		//upload file
		DiskFileItemFactory factory = new DiskFileItemFactory();
        // sets memory threshold - beyond which files are stored in disk
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // sets temporary location to store files
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
        // sets maximum size of upload file
        upload.setFileSizeMax(MAX_FILE_SIZE);
        // sets maximum size of request (include file + form data)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        
        String uploadPath = getServletContext().getRealPath("")
                + File.separator + UPLOAD_DIRECTORY;
        
        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
 
        try {
            // parses the request's content to extract file data
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest((RequestContext) req);
 
            if (formItems != null && formItems.size() > 0) {
                // iterates over form's fields
                for (FileItem item : formItems) {
                    // processes only fields that are not form fields
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
 
                        // saves the file on disk
                        item.write(storeFile);
                        System.out.println("path: "+filePath);
                    }
                }
               
            }
        } catch (Exception ex) {
            req.setAttribute("message",
                    "There was an error: " + ex.getMessage());
        }
        //
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
	
	
}
