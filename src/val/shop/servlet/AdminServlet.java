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
import val.shop.dao.ProductDao;
import val.shop.model.Cart;
import val.shop.model.Order;
import val.shop.model.Product;
import val.shop.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/adminka")
public class AdminServlet extends HttpServlet {
    private ProductDao pd;
    private CartDao cartDao;

    @Override
    public void init(ServletConfig config){
        ServletContext servletContext = config.getServletContext();
        pd = (ProductDao)  servletContext.getAttribute("productDao");
        cartDao = (CartDao) servletContext.getAttribute("cartDao");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User auth = (User) request.getSession().getAttribute("auth");
        List<Product> products = pd.getAllProducts();
        if (auth != null) {
            request.setAttribute("person", auth);
            ArrayList<Cart> cart_list = (ArrayList<Cart>) cartDao.userCart(auth.getId());
            if (cart_list != null) {
                request.setAttribute("cart_list", cart_list);
            }
        }
        request.setAttribute("products", products);
        request.getRequestDispatcher("adminka.jsp").forward(request, response);
    }
}
