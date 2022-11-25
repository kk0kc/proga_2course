package val.shop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import val.shop.DataBaseConnection.PostgresConnectionToDataBase;
import val.shop.dao.OrderDao;
import val.shop.dao.UserDao;

@WebServlet("/cancel-order")
public class CancelOrderServlet extends HttpServlet {
	private OrderDao orderDao;

	@Override
	public void init(ServletConfig config){
		ServletContext servletContext = config.getServletContext();
		orderDao = (OrderDao) servletContext.getAttribute("orderDao");
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
			String id = request.getParameter("id");
			if(id != null) {
				orderDao.cancelOrder(Integer.parseInt(id));
			}
			response.sendRedirect("/orders");
	}
}
