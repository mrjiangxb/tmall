package tmall.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchWantServlet")
public class SearchWantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchWantServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String name = request.getParameter("name");
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl", "scott", "admin");
			
			String sql="select * from myproduct where 1=1 ";
			
			/*if(name!=null && !name.equals("")){
				sql+=" and name=? ";
			}*/
			PreparedStatement psmt = conn.prepareStatement(sql);
			
			/*if(name!=null && !name.equals("")){
				psmt.setObject(1, name);
			}*/
			
			ResultSet rs = psmt.executeQuery();
			List pros = new ArrayList();
			while(rs.next()){
				int idnum = rs.getInt(1); 
				String namenum = rs.getString(2);
				
				product pro = new product();
				pro.setId(idnum);
				pro.setName(namenum );
				
				pros.add(pro);
			}
			
			request.setAttribute("pros", pros);
			request.getRequestDispatcher("collection.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
