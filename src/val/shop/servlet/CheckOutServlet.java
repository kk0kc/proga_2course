package val.shop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import val.shop.DataBaseConnection.PostgresConnectionToDataBase;
import val.shop.dao.CartDao;
import val.shop.dao.OrderDao;
import val.shop.model.*;


@WebServlet("/cart-check-out")
public class CheckOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
//			ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
			CartDao cartDao = new CartDao(PostgresConnectionToDataBase.getConnection());
		User auth = (User) request.getSession().getAttribute("auth");
			ArrayList<Cart> cart_list = (ArrayList<Cart>) cartDao.userCart(auth.getId());

			if(cart_list != null && auth!=null) {
				for(Cart c:cart_list) {
					Order order = new Order();
					order.setId(c.getId());
					order.setUid(auth.getId());
					order.setQunatity(c.getQuantity());
					order.setDate(formatter.format(date));
					
					OrderDao oDao = new OrderDao(PostgresConnectionToDataBase.getConnection());
					boolean result = oDao.insertOrder(order);
					boolean result2 = cartDao.insertCart(c); //?
					cartDao.cancelCart(c.getId());
					if(!result) break;
					if(!result2) break; //?
				}

				response.sendRedirect("orders.jsp");
			}else {
				if(auth==null) {
					response.sendRedirect("login.jsp");
				}
				response.sendRedirect("cart.jsp");
			}

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
