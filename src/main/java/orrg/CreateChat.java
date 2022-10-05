package orrg;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


@WebServlet(name = "CreateChat", urlPatterns = "/start")
public class CreateChat extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String dataId = req.getParameter("id");
        ArrayList<String> arr = SessionCreatedListener.rooms;
        for (String key : arr) {
            if (Objects.equals(key, dataId)) {
                req.getRequestDispatcher("/WEB-INF/already_have.jsp").forward(req, resp);
                return;
            }
        }
        SessionCreatedListener.rooms.add(dataId);
        req.getRequestDispatcher("/WEB-INF/id_chat.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/create_chat.jsp").forward(req, resp);
    }
}