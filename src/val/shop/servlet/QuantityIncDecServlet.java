package val.shop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.util.ArrayList;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import val.shop.DataBaseConnection.PostgresConnectionToDataBase;
import val.shop.dao.CartDao;
import val.shop.dao.OrderDao;
import val.shop.model.Cart;
import val.shop.model.User;

@WebServlet("/quantity-inc-dec")
public class QuantityIncDecServlet extends HttpServlet {
	@Serial
	private static final long serialVersionUID = 1L;
	private CartDao cartDao;

	@Override
	public void init(ServletConfig config){
		ServletContext servletContext = config.getServletContext();
		cartDao = (CartDao) servletContext.getAttribute("cartDao");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			String action = request.getParameter("action");
			int id = Integer.parseInt(request.getParameter("id"));
//			ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
			User auth = (User) request.getSession().getAttribute("auth");
			ArrayList<Cart> cart_list = (ArrayList<Cart>) cartDao.userCart(auth.getId());


			if (action != null && id >= 1) {
				if (action.equals("inc")) {
					for (Cart c : cart_list) {
						if (c.getId() == id) {
							int quantity = c.getQuantity();
							quantity++;
							cartDao.updateQuantityOfCart(id, quantity);
						}
					}
				}

				if (action.equals("dec")) {
					for (Cart c : cart_list) {
						if (c.getId() == id && c.getQuantity() > 1) {
							int quantity = c.getQuantity();
							quantity--;
							cartDao.updateQuantityOfCart(id, quantity);
						}
					}
//					response.sendRedirect("cart.jsp");
				}
			}
			response.sendRedirect("/cart");
	}

}
