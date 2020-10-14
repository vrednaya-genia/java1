package ru.progwards.java1.lessons.datetime;

import java.time.LocalDateTime;
import java.util.Random;

public class UserSession {
    private int sessionHandle;
    private String userName;
    private LocalDateTime lastAccess;

    public UserSession(String userName) {
        this.userName = userName;
        sessionHandle = new Random().nextInt();
        lastAccess = LocalDateTime.now();
    }

    int getSessionHandle() {
        return sessionHandle;
    }

    String getUserName() {
        return userName;
    }

    LocalDateTime getLastAccess() {
        return lastAccess;
    }

    void updateLastAccess() { // обновляет время доступа к сессии
        lastAccess = LocalDateTime.now();
    }
}
