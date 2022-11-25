package val.shop.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import val.shop.dao.CartDao;
import val.shop.dao.OrderDao;
import val.shop.model.Cart;
import val.shop.model.Order;
import val.shop.model.User;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
    private OrderDao orderDao;
    private CartDao cartDao;

    @Override
    public void init(ServletConfig config){
        ServletContext servletContext = config.getServletContext();
        orderDao = (OrderDao) servletContext.getAttribute("orderDao");
        cartDao = (CartDao) servletContext.getAttribute("cartDao");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DecimalFormat dcf = new DecimalFormat("#.##");
        request.setAttribute("dcf", dcf);
        User auth = (User) request.getSession().getAttribute("auth");
        List<Order> orders = null;
        if (auth != null) {
            request.setAttribute("person", auth);
            orders = orderDao.userOrders(auth.getId());
            ArrayList<Cart> cart_list = (ArrayList<Cart>) cartDao.userCart(auth.getId());
            if (cart_list != null) {
                request.setAttribute("cart_list", cart_list);
            }
        }
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("orders.jsp").forward(request,response);
    }
}
