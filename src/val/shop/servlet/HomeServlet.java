package val.shop.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import val.shop.DataBaseConnection.PostgresConnectionToDataBase;
import val.shop.dao.CartDao;
import val.shop.dao.ProductDao;
import val.shop.model.Cart;
import val.shop.model.Product;
import val.shop.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User auth = (User) request.getSession().getAttribute("auth");
        if (auth != null) {
            request.setAttribute("person", auth);
            CartDao cartDao = new CartDao(PostgresConnectionToDataBase.getConnection());
            ArrayList<Cart> cart_list = (ArrayList<Cart>) cartDao.userCart(auth.getId());
            if (cart_list != null) {
                request.setAttribute("cart_list", cart_list);
            }
        }
        ProductDao pd = new ProductDao(PostgresConnectionToDataBase.getConnection());
        List<Product> products = pd.getAllProducts();
        request.setAttribute("products", products);
        HttpSession session = request.getSession();
//        ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
