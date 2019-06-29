package tmall.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.util.Page;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/categoryServlet")
public class CategoryServlet extends BaseBackServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String,String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);
		String name = params.get("name");
		Category c = new Category();
		c.setName(name);
		categoryDAO.add(c);//父类继承过来
		
		File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
		File file = new File(imageFolder,c.getId()+".jpg");
		try {
			if(is!=null && is.available()!=0){
				FileOutputStream fos = new FileOutputStream(file);
				byte b[] = new byte[1024*1024];
				int length = 0;
				while((length=is.read(b))!=-1){
					fos.write(b, 0, length);
					fos.flush();//刷新此输出流并强制写出所有缓冲的输出字节   保证缓存全部输出
					
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}
       
    

}
