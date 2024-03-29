package tmall.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import tmall.bean.OrderItem;
import tmall.bean.User;
import tmall.dao.OrderItemDAO;

public class ForeAuthFilter implements Filter{
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		//获取根路径     /tmall
		String contextPath=request.getServletContext().getContextPath();
		//定义不需要登录就可以访问的路径
		String[] noNeedAuthPage = new String[]{
				"home",
				"checkLogin",
				"register",
				"loginAjax",
				"login",
				"product",
				"category",
				"search",
				"yzm"};
		//获取uri地址   如： /tmall/forehome
		String uri = request.getRequestURI();
		uri =StringUtils.remove(uri, contextPath);  //获取请求路径如forehome
		if(uri.startsWith("/fore")&&!uri.startsWith("/foreServlet")){
			String method = StringUtils.substringAfterLast(uri,"/fore" );
			//判断该访问路径是否需要登录     如果不包含可访问路径，执行以下代码   否则正常执行
			if(!Arrays.asList(noNeedAuthPage).contains(method)){
				User user =(User) request.getSession().getAttribute("user");
				if(null==user){
					response.sendRedirect("login.jsp");
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	
}
