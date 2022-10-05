package orrg;

public class MessageEntity {
    private String login;
    private String user;
    private String userMessage;
    public MessageEntity(String login, String user, String userMessage) {
        this.login = login;
        this.userMessage = userMessage;
        this.user = user;
    }
    public String getUser() {
        return user;
    }
    public String getLogin() {
        return login;
    }
    public String getUserMessage() {
        return userMessage;
    }
}
