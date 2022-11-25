package val.shop.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import val.shop.DataBaseConnection.PostgresConnectionToDataBase;
import val.shop.dao.CartDao;
import val.shop.dao.ProductDao;
import val.shop.model.Cart;
import val.shop.model.User;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private ProductDao pDao;
    private CartDao cartDao;

    @Override
    public void init(ServletConfig config){
        ServletContext servletContext = config.getServletContext();
        pDao = (ProductDao) servletContext.getAttribute("productDao");
        cartDao = (CartDao) servletContext.getAttribute("cartDao");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DecimalFormat dcf = new DecimalFormat("#.##");
        request.setAttribute("dcf", dcf);
        User auth = (User) request.getSession().getAttribute("auth");
        List<Cart> cartProduct = null;
        ArrayList<Cart> cart_list = null;
        if (auth != null) {
            request.setAttribute("person", auth);
            cartProduct = cartDao.userCart(auth.getId());
            cart_list = (ArrayList<Cart>) cartDao.userCart(auth.getId());
        }
        if (cart_list != null) {
            cartProduct = pDao.getCartProducts(cart_list);
            double total = pDao.getTotalCartPrice(cart_list);
            request.setAttribute("total", total);
            request.setAttribute("cart_list", cart_list);
        }
        request.setAttribute("cartProduct", cartProduct);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
}
