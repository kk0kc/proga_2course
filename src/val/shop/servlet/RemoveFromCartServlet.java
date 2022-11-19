package val.shop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/remove-from-cart")
public class RemoveFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CartDao cartDao;
	@Override
	public void init(ServletConfig config){
		ServletContext servletContext = config.getServletContext();
		cartDao = (CartDao) servletContext.getAttribute("cartDao");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User auth = (User) request.getSession().getAttribute("auth");
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			String id =  request.getParameter("id");
			if (id != null) {
				ArrayList<Cart> cart_list = (ArrayList<Cart>) cartDao.userCart(auth.getId());
				if (cart_list != null) {
					for (Cart c : cart_list) {
						if (c.getId() == Integer.parseInt(id)) {
							cartDao.cancelCart(c.getId());
//							cart_list.remove(cart_list.indexOf(c));
						}
					}
				}
//				response.sendRedirect("cart.jsp");

			}
				response.sendRedirect("/cart");

		}
	}

}
