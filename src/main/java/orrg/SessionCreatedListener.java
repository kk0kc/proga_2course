package orrg;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionListener;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class SessionCreatedListener implements HttpSessionListener {
    public static ArrayList<String> rooms = new ArrayList<>();
    public static List<MessageEntity> messageEntities = new LinkedList<>();
    public static ArrayList<String> users = new ArrayList<>();
}