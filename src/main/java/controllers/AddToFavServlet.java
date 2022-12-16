package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Fav;
import models.Post;
import models.User;
import services.FavService;
import services.PostService;
import services.UserService;

import javax.swing.text.html.HTMLEditorKit;
import java.io.IOException;

@WebServlet("/add_fav")
public class AddToFavServlet extends HttpServlet {
    private final FavService favService = new FavService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long postId = Long.valueOf(request.getParameter("id"));
        User user = UserService.getAuthUser();
        Fav fav = Fav.builder()
                .userID(user.getId())
                .postID(postId)
                .build();
        favService.saveFav(fav);

        response.sendRedirect("/posts/"+postId);
    }
}
