package val.shop.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import val.shop.dao.CartDao;
import val.shop.dao.ProductDao;
import val.shop.model.Cart;
import val.shop.model.Product;
import val.shop.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/remove-admin")
public class AdminRemoveServlet extends HttpServlet {
    private ProductDao productDao;
    @Override
    public void init(ServletConfig config){
        ServletContext servletContext = config.getServletContext();
        productDao = (ProductDao) servletContext.getAttribute("productDao");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User auth = (User) request.getSession().getAttribute("auth");
        String id =  request.getParameter("id");
        if (id != null) {
            ArrayList<Product> products_list = (ArrayList<Product>) productDao.getAllProducts();
            if (products_list != null) {
                for (Product p : products_list) {
                    if (p.getId() == Integer.parseInt(id)) {
                        productDao.removeProduct(p.getId());
                    }
                }
            }
        }
        response.sendRedirect("/adminka");
    }

}
