package val.shop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import val.shop.DataBaseConnection.PostgresConnectionToDataBase;
import val.shop.dao.CartDao;
import val.shop.dao.OrderDao;
import val.shop.dao.UserDao;
import val.shop.model.*;


@WebServlet(name = "AddToCartServlet", urlPatterns = "/add-to-cart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        User auth = (User) request.getSession().getAttribute("auth");
//        if (auth != null) {
//
//            try (PrintWriter out = response.getWriter()) {
////        	out.print("add to cart servlet");
//
//                ArrayList<Cart> cartList = new ArrayList<>();
//                int id = Integer.parseInt(request.getParameter("id"));
//                Cart cm = new Cart();
//                cm.setId(id);
//                cm.setUid(auth.getId());
//                cm.setQuantity(1);
//                HttpSession session = request.getSession();
////            ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
//                CartDao cartDao = new CartDao();
//                ArrayList<Cart> cart_list = (ArrayList<Cart>) cartDao.userCart(id);
//                if (cart_list == null) {
//
////                cartList.add(cm);
//                    cartDao.insertCart(cm);
////                session.setAttribute("cart-list", cartList);
//                    response.sendRedirect("index.jsp");
//                } else {
//                    cartList = cart_list;
//
//                    boolean exist = false;
//                    for (Cart c : cart_list) {
//                        if (c.getId() == id) {
//                            exist = true;
//                            out.println("<h3 style='color:crimson; text-align: center'>Item Already in Cart. <a href='cart.jsp'>GO to Cart Page</a></h3>");
//                        }
//                    }
//
//                    if (!exist) {
//                        cartDao.insertCart(cm);
////                    cartList.add(cm);
//                        response.sendRedirect("index.jsp");
//                    }
//                }
//            }
//        }
//        else {
//            response.sendRedirect("login.jsp");
//        }
//    }
    private CartDao cartDao;
    @Override
    public void init(ServletConfig config)  {
        ServletContext servletContext = config.getServletContext();
        cartDao = (CartDao) servletContext.getAttribute("cartDao");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        User auth = (User) request.getSession().getAttribute("auth");

        if (auth != null) {
            String productId = request.getParameter("id");
            boolean exist = false;
            Cart cartModel = new Cart();
            cartModel.setId(Integer.parseInt(productId));
            cartModel.setUid(auth.getId());
            cartModel.setQuantity(1);
            ArrayList<Cart> cart_list = (ArrayList<Cart>) cartDao.userCart(auth.getId());
            for(Cart c: cart_list) {
                if (c.getId() == Integer.parseInt(productId)) {
                    exist = true;
                    out.println("<h3 style='color:crimson; text-align: center'>Item Already in Cart. <a href='cart.jsp'>GO to Cart Page</a></h3>");
                }
            }
            if(!exist) {
                boolean result = cartDao.insertCart(cartModel);
                response.sendRedirect("/");
            }

        }
//        else {
//            response.sendRedirect("login.jsp");
//        }
    }

}
