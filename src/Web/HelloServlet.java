package Web;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/")
public class HelloServlet extends HttpServlet {

		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException{
			resp.setContentType("text/html");
			PrintWriter pw = resp.getWriter();
			String name = req.getParameter("name");
			if (name == null) {
				name = "world";
			}
			pw.format("<h1>Hello, " + name + "!</h1>");
			pw.flush();
		}
}
