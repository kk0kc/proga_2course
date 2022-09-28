package orrg;

public class MessageEntity {
    private String login;
    private String userMessage;

    public MessageEntity(String login, String userMessage) {
        this.login = login;
        this.userMessage = userMessage;
    }

    public String getLogin() {
        return login;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
