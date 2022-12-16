package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Comment;
import models.Fav;
import models.Post;
import models.User;
import services.CommentService;
import services.FavService;
import services.PostService;
import services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet(name = "ProfileServlet", value = "/profile")
public class ProfileServlet extends HttpServlet {

    private final UserService userService  = new UserService();
    private final PostService postService  = new PostService();
    private final FavService favService  = new FavService();
    private final CommentService commentService = new CommentService();
    private User user;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.user = UserService.getAuthUser();
        List<Fav> favs = favService.getAllFavs();
        List<Post> posts = postService.getAllPosts();


        favs = favs.stream().filter(fav -> Objects.equals(fav.getUserID(), user.getId())).collect(Collectors.toList());

        request.setAttribute("favs", favs);
        request.setAttribute("posts", posts);

        request.setAttribute("login", user.getLogin());
        request.setAttribute("info", user.getInfo());



        try {
//            Long postID = Long.valueOf(request.getPathInfo().substring(1));

            request.setAttribute("comments", commentService.getAllComments().stream()
                    .filter(comment -> comment.getUserID().equals(user.getId())).collect(Collectors.toList())
            );
            request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }

        request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String info = request.getParameter("info");
        this.user.setLogin(login);
        this.user.setInfo(info);

//        Long postID = Long.valueOf(request.getPathInfo().substring(1));
        User user = UserService.getAuthUser();
        String text = request.getParameter("text");
        Comment comment = Comment.builder()
                .text(text)
                .userID(user.getId())
                .postID(1L)
                .build();
        commentService.saveComment(comment);

        userService.updateUser(this.user);
        response.sendRedirect("/profile");
    }
}
