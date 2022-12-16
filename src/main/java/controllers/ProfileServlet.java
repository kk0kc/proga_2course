package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Fav;
import models.Post;
import models.User;
import services.FavService;
import services.PostService;
import services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "ProfileServlet", value = "/profile")
public class ProfileServlet extends HttpServlet {

    private final UserService userService  = new UserService();
    private final PostService postService  = new PostService();
    private final FavService favService  = new FavService();
    private User user;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.user = UserService.getAuthUser();
        List<Fav> favs = favService.getAllPosts();
        List<Post> posts = postService.getAllPosts();


            favs = favs.stream().filter(fav ->  fav.getUserID().equals(user.getId())).collect(Collectors.toList());

        request.setAttribute("favs", favs);
        request.setAttribute("posts", posts);

        request.setAttribute("login", user.getLogin());
        request.setAttribute("info", user.getInfo());

        request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String info = request.getParameter("info");
        this.user.setLogin(login);
        this.user.setInfo(info);

        userService.updateUser(this.user);
        response.sendRedirect("/profile");
    }
}
