package val.shop.listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import val.shop.DataBaseConnection.PostgresConnectionToDataBase;
//import val.shop.dao.PostsRepository;
import val.shop.dao.*;
//import val.shop.dao.impl.FilesRepositoryImpl;
//import val.shop.dao.impl.PostsRepositoryImpl;
import val.shop.dao.impl.UsersRepositoryImpl;
import val.shop.services.*;
import val.shop.services.impl.*;
import val.shop.services.validation.Validator;

import java.sql.Connection;

@WebListener
public class CustomContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        Connection connection = PostgresConnectionToDataBase.getConnection();

//        FilesRepository filesRepository = new FilesRepositoryImpl(dataSource);
        OrderDao orderDao = new OrderDao(connection);
        ProductDao productDao = new ProductDao(connection);
        UserDao userDao = new UserDao(connection);
        CartDao cartDao = new CartDao(connection);
        UsersRepository usersRepository = new UsersRepositoryImpl(new PostgresConnectionToDataBase());
//        FilesService filesService = new FilesServiceImpl(IMAGES_STORAGE_PATH, filesRepository, usersRepository);
        PasswordEncoder passwordEncoder = new PasswordEncoderImpl();
//        SignInService signInService = new SignInServiceImpl(usersRepository, passwordEncoder);
        Validator validator = new ValidatorImpl(usersRepository);
        SignUpService signUpService = new SignUpServiceImpl(usersRepository, passwordEncoder, validator);
//        PostsRepository postsRepository = new PostsRepositoryImpl(dataSource);
//        PostsService postsService = new PostsServiceImpl(postsRepository);
//        ObjectMapper objectMapper = new ObjectMapper();

//        servletContext.setAttribute("filesService", filesService);
//        servletContext.setAttribute("signInService", signInService);
        servletContext.setAttribute("productDao", productDao);
        servletContext.setAttribute("orderDao", orderDao);
        servletContext.setAttribute("cartDao", cartDao);
        servletContext.setAttribute("userDao", userDao);
        servletContext.setAttribute("signUpService", signUpService);
//        servletContext.setAttribute("postsService", postsService);
//        servletContext.setAttribute("passwordEncoder", passwordEncoder);
//        servletContext.setAttribute("objectMapper", objectMapper);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
