package tmall.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

 
public class BackServletFilter implements Filter {
 
    public void destroy() {
         
    }
 
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    		throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        //获取请求路径的截取部分
        String contextPath=request.getServletContext().getContextPath();
        //获取请求路径的资源部分
        String uri = request.getRequestURI();
        //借助StringUtils.remove方法截取contextPath
        uri =StringUtils.remove(uri, contextPath);
      //判断uri是否以/admin开头
        if(uri.startsWith("/admin_")){  
        	//取出请求资源的 _ 之间的字符串并拼接上Servlet
            String servletPath = StringUtils.substringBetween(uri,"_", "_") + "Servlet";
            String method = StringUtils.substringAfterLast(uri,"_" );
            //发送请求
            request.setAttribute("method", method);
            //  请求转发跳转到   “/***” 的servlet
            req.getRequestDispatcher("/" + servletPath).forward(request, response);
            return;
        }
        //执行下一个过滤器
        chain.doFilter(request, response);
    }
 
    public void init(FilterConfig arg0) throws ServletException {
     
    }
}