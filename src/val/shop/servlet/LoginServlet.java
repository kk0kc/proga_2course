package val.shop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import val.shop.dao.*;
import val.shop.model.*;

@WebServlet("/user-login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao udao;

	@Override
	public void init(ServletConfig config){
		ServletContext servletContext = config.getServletContext();
		udao = (UserDao) servletContext.getAttribute("userDao");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String email = request.getParameter("login-email");
		String password = request.getParameter("login-password");
		String status;
		if (request.getParameter("status") != null) {
			status = "admin";
		} else {
			status = "user";
		}
		User user = udao.userLogin(email, password, status);
		if (user != null && user.getStatus().equals("user")) {
			request.getSession().setAttribute("auth", user);
			response.sendRedirect("/");
		} else if (user != null && user.getStatus().equals("admin")) {
			request.getSession().setAttribute("auth", user);
			response.sendRedirect("/adminka");
		} else {
			response.sendRedirect("/error-log");
//			request.getRequestDispatcher("error_log.jsp").forward(request, response);
		}
	}
}
