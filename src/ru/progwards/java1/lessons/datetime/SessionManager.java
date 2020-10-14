package ru.progwards.java1.lessons.datetime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class SessionManager {
    private Collection<UserSession> sessions;
    private int sessionValid;

    public SessionManager(int sessionValid) {
        sessions = new ArrayList<>();
        this.sessionValid = sessionValid;
    }// sessionValid - период валидности сессии в секундах.

    class UserSession {
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

    public void add(UserSession userSession) {
        userSession.updateLastAccess();
        sessions.add(userSession);
    }

    public UserSession find(String userName) {
        Iterator<UserSession> it = sessions.iterator();
        while (it.hasNext()) {
            UserSession temp = it.next();
            if (userName.equals(temp.userName)) {
                LocalDateTime AccessExp = temp.lastAccess.plusSeconds(sessionValid);
                if (AccessExp.isAfter(LocalDateTime.now())) {
                    temp.updateLastAccess();
                    return temp;
                } else {
                    break;
                }
            }
        }
        return null;
    }

    public UserSession get(int sessionHandle) {
        Iterator<UserSession> it = sessions.iterator();
        while (it.hasNext()) {
            UserSession temp = it.next();
            if (sessionHandle == temp.sessionHandle) {
                LocalDateTime AccessExp = temp.lastAccess.plusSeconds(sessionValid);
                if (AccessExp.isAfter(LocalDateTime.now())) {
                    temp.updateLastAccess();
                    return temp;
                } else {
                    break;
                }
            }
        }
        return null;
    }

    public void delete(int sessionHandle) {
        Iterator<UserSession> it = sessions.iterator();
        while (it.hasNext()) {
            UserSession temp = it.next();
            if (sessionHandle == temp.sessionHandle) {
                it.remove();
                break;
            }
        }
    }

    public void deleteExpired() {
        Iterator<UserSession> it = sessions.iterator();
        while (it.hasNext()) {
            UserSession temp = it.next();
            LocalDateTime AccessExp = temp.lastAccess.plusSeconds(sessionValid);
            if (AccessExp.isBefore(LocalDateTime.now())) {
                it.remove();
            }
        }
    }

    public static void main(String[] args)  {
        SessionManager t = new SessionManager(5);
        UserSession newOne = t.new UserSession("Eugenia");

        if (t.find("Eugenia")==null) {
            t.add(newOne);
        }
        t.get(newOne.sessionHandle);
        t.get(newOne.sessionHandle);
        try {
            Thread.sleep(6000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(t.get(newOne.sessionHandle));

        UserSession newOne1 = t.new UserSession("Eugenia1");
        t.add(newOne1);
        try {
            Thread.sleep(2500);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        UserSession newOne2 = t.new UserSession("Eugenia2");
        t.add(newOne2);
        try {
            Thread.sleep(2500);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        t.deleteExpired();
        System.out.println(t.sessions);

        t.delete(newOne2.sessionHandle);
        System.out.println(t.sessions);
    }
}
