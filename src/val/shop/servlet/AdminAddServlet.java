package val.shop.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import val.shop.dao.ProductDao;
import val.shop.model.Product;
import val.shop.model.User;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


@WebServlet("/add-product")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class AdminAddServlet extends HttpServlet {
    private ProductDao productDao;
    @Override
    public void init(ServletConfig config){
        ServletContext servletContext = config.getServletContext();
        productDao = (ProductDao) servletContext.getAttribute("productDao");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User auth = (User) request.getSession().getAttribute("auth");
        if (auth != null) {
            Part filePart = request.getPart("file");
            String fileName = filePart.getSubmittedFileName();
            FileOutputStream fos = new FileOutputStream("C:\\Users\\1\\Desktop\\eShop-Website-main\\WebContent\\includes\\product-image\\" + fileName);
            InputStream ips = filePart.getInputStream();
            byte[] data = new byte[ips.available()];
            ips.read(data);
            fos.write(data);
            ips.close();
            fos.close();
            Product cartModel = new Product();
            cartModel.setName(request.getParameter("name"));
            cartModel.setCategory(request.getParameter("category"));
            cartModel.setYear(Integer.parseInt(request.getParameter("year")));
            cartModel.setImdb(Double.parseDouble(request.getParameter("imdb")));
            cartModel.setImage(fileName);
            cartModel.setGif(request.getParameter("gif"));
            productDao.insertNewProduct(cartModel);
            response.sendRedirect("/adminka");
        }
    }
}
