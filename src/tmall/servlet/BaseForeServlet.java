package tmall.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.dao.CategoryDAO;
import tmall.dao.OrderDAO;
import tmall.dao.OrderItemDAO;
import tmall.dao.ProductDAO;
import tmall.dao.ProductImageDAO;
import tmall.dao.PropertyDAO;
import tmall.dao.PropertyValueDAO;
import tmall.dao.ReviewDAO;
import tmall.dao.UserDAO;
import tmall.util.Page;

public class BaseForeServlet extends HttpServlet {
	protected CategoryDAO categoryDAO = new CategoryDAO();
	protected OrderDAO orderDAO = new OrderDAO();
	protected OrderItemDAO orderItemDAO = new OrderItemDAO();
	protected ProductDAO productDAO = new ProductDAO();
	protected ProductImageDAO productImageDAO = new ProductImageDAO();
	protected PropertyDAO propertyDAO = new PropertyDAO();
	protected PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
	protected ReviewDAO reviewDAO = new ReviewDAO();
	protected UserDAO userDAO = new UserDAO();
	
	private static final long serialVersionUID = 1L;
       
    public BaseForeServlet() {
        super();
    }
    
    
    public void service(HttpServletRequest request, HttpServletResponse response){
    	int start = 0;
    	int count = 10;
    	start = Integer.parseInt(request.getParameter("page.start"));
    	count = Integer.parseInt(request.getParameter("page.count"));
    	
    	Page page = new Page(start,count);
    	String method = (String) request.getAttribute("method");
    	
				try {
					Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,
							javax.servlet.http.HttpServletResponse.class,Page.class);
					String redirect = m.invoke(this, request, response, page).toString();
					
					if(redirect.startsWith("@")){
						response.sendRedirect(redirect.substring(1));
					}else if(redirect.startsWith("%")){
						response.getWriter().print(redirect.substring(1));
					}else{
						request.getRequestDispatcher(redirect).forward(request, response);
					}
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | IOException | ServletException e) {
					e.printStackTrace();
				}
		
    }

}
