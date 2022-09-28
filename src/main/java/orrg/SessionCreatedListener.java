package orrg;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionListener;

import java.util.*;

@WebListener
public class SessionCreatedListener implements HttpSessionListener {
    public static ArrayList<String> rooms = new ArrayList<>();
    public static List<MessageEntity> messageEntities = new LinkedList<>();
}