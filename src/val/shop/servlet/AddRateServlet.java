package val.shop.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import val.shop.DataBaseConnection.PostgresConnectionToDataBase;
import val.shop.dao.OrderDao;
import val.shop.model.Cart;
import val.shop.model.Order;
import val.shop.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

@WebServlet("/add-rate")
public class AddRateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.getRequestDispatcher("orders.jsp").forward(request,response);

		String id = request.getParameter("id");
		String rate = request.getParameter("output");
		String rate2 = request.getParameter("rate");
		Enumeration param = request.getParameterNames();
		if(id != null) {
			OrderDao orderDao = new OrderDao(PostgresConnectionToDataBase.getConnection());
			User auth = (User) request.getSession().getAttribute("auth");
			ArrayList<Order> order_list = (ArrayList<Order>) orderDao.userOrders(auth.getId());
			for (Order o : order_list) {
				if (o.getOrderId() == Integer.parseInt(id)) {
					orderDao.updateRate(Integer.parseInt(id), Integer.parseInt(rate));
				}
			}
		}
		response.sendRedirect("orders.jsp");
	}

//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		response.setContentType("text/html;charset=UTF-8");
//
//			String id = request.getParameter("id");
//			String rate = request.getParameter("rate");
//			if(id != null) {
//				OrderDao orderDao = new OrderDao(PostgresConnectionToDataBase.getConnection());
//				User auth = (User) request.getSession().getAttribute("auth");
//				ArrayList<Order> order_list = (ArrayList<Order>) orderDao.userOrders(auth.getId());
//				for (Order o : order_list) {
//					if (o.getOrderId() == Integer.parseInt(id)) {
//						orderDao.updateRate(Integer.parseInt(id), Integer.parseInt(rate));
//					}
//				}
//			}
//			response.sendRedirect("orders.jsp");
//
//	}

}
