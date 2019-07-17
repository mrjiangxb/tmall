package tmall.servlet;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.util.ImageUtil;
import tmall.util.Page;

public class CategoryServlet extends BaseBackServlet {
	
	/*public void showCategoryImg (HttpServletRequest request, HttpServletResponse response, Page page) {
		try {
			String id = request.getParameter("id");
			//读取本地图片输入流
			FileInputStream is = new FileInputStream("E:/project/img/category/"+id+".jpg");
			int i = is.available();
			//byte[]数组存放图片字节数据
			byte[] buff = new byte[i];
			is.read(buff);
			is.close();
			
			//设置发送到客户端的响应内容类型
			response.setContentType("img/*");
			OutputStream os = response.getOutputStream();
			os.write(buff);
			os.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMiddleImg (HttpServletRequest request, HttpServletResponse response, Page page) {
		try {
			String id = request.getParameter("id");
			//读取本地图片输入流
			FileInputStream is = new FileInputStream("E:/project/img/productSingle_middle/"+id+".jpg");
			int i = is.available();
			//byte[]数组存放图片字节数据
			byte[] buff = new byte[i];
			is.read(buff);
			is.close();
			
			//设置发送到客户端的响应内容类型
			response.setContentType("img/*");
			OutputStream os = response.getOutputStream();
			os.write(buff);
			os.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showSingleImg (HttpServletRequest request, HttpServletResponse response, Page page) {
		try {
			String id = request.getParameter("id");
			//读取本地图片输入流
			FileInputStream is = new FileInputStream("E:/project/img/productSingle/"+id+".jpg");
			int i = is.available();
			//byte[]数组存放图片字节数据
			byte[] buff = new byte[i];
			is.read(buff);
			is.close();
			
			//设置发送到客户端的响应内容类型
			response.setContentType("img/*");
			OutputStream os = response.getOutputStream();
			os.write(buff);
			os.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showDetailImg (HttpServletRequest request, HttpServletResponse response, Page page) {
		try {
			String id = request.getParameter("id");
			//读取本地图片输入流
			FileInputStream is = new FileInputStream("E:/project/img/productDetail/"+id+".jpg");
			int i = is.available();
			//byte[]数组存放图片字节数据
			byte[] buff = new byte[i];
			is.read(buff);
			is.close();
			
			//设置发送到客户端的响应内容类型
			response.setContentType("img/*");
			OutputStream os = response.getOutputStream();
			os.write(buff);
			os.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showSmallImg (HttpServletRequest request, HttpServletResponse response, Page page) {
		try {
			String id = request.getParameter("id");
			//读取本地图片输入流
			FileInputStream is = new FileInputStream("E:/project/img/productSingle_small/"+id+".jpg");
			int i = is.available();
			//byte[]数组存放图片字节数据
			byte[] buff = new byte[i];
			is.read(buff);
			is.close();
			
			//设置发送到客户端的响应内容类型
			response.setContentType("img/*");
			OutputStream os = response.getOutputStream();
			os.write(buff);
			os.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String,String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);
		
		String name= params.get("name");
		Category c = new Category();
		c.setName(name);
		categoryDAO.add(c);
		
		File  imageFolder= new File(request.getSession().getServletContext().getRealPath("img/category"));
		File file = new File(imageFolder,c.getId()+".jpg");
		
		try {
			if(null!=is && 0!=is.available()){
			    try(FileOutputStream fos = new FileOutputStream(file)){
			        byte b[] = new byte[1024 * 1024];
			        int length = 0;
			        while (-1 != (length = is.read(b))) {
			            fos.write(b, 0, length);
			        }
			        fos.flush();
			        //通过如下代码，把文件保存为jpg格式
			        BufferedImage img = ImageUtil.change2jpg(file);
			        ImageIO.write(img, "jpg", file);		
			    }
			    catch(Exception e){
			    	e.printStackTrace();
			    }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return "@admin_category_list";
	}

	
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		categoryDAO.delete(id);
		return "@admin_category_list";
	}

	
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Category c = categoryDAO.get(id);
		request.setAttribute("c", c);
		return "admin/editCategory.jsp";		
	}

	
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String,String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);
		String name= params.get("name");
		int id = Integer.parseInt(params.get("id"));

		Category c = new Category();
		c.setId(id);
		c.setName(name);
		categoryDAO.update(c);
		
		File  imageFolder= new File("E:/project/img/category");
		File file = new File(imageFolder,c.getId()+".jpg");
		file.getParentFile().mkdirs();
		
		try {
			if(null!=is && 0!=is.available()){
			    try(FileOutputStream fos = new FileOutputStream(file)){
			        byte b[] = new byte[1024 * 1024];
			        int length = 0;
			        while (-1 != (length = is.read(b))) {
			            fos.write(b, 0, length);
			        }
			        fos.flush();
			        //通过如下代码，把文件保存为jpg格式
			        BufferedImage img = ImageUtil.change2jpg(file);
			        ImageIO.write(img, "jpg", file);		
			    }
			    catch(Exception e){
			    	e.printStackTrace();
			    }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "@admin_category_list";

	}

	
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		List<Category> cs = categoryDAO.list(page.getStart(),page.getCount());
		
		int total = categoryDAO.getTotal();
		page.setTotal(total);
		
		request.setAttribute("thecs", cs);
		request.setAttribute("page", page);
		
		return "admin/listCategory.jsp";
	}
}
