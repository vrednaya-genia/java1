package ru.progwards.java1.lessons.datetime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class SessionManager {
    private Collection<UserSession> sessions;
    private int sessionValid;

    public SessionManager(int sessionValid) {
        sessions = new ArrayList<>();
        this.sessionValid = sessionValid;
    }// sessionValid - период валидности сессии в секундах.

    public void add(UserSession userSession) {
        userSession.updateLastAccess();
        sessions.add(userSession);
    }

    public UserSession find(String userName) {
        Iterator<UserSession> it = sessions.iterator();
        while (it.hasNext()) {
            UserSession temp = it.next();
            if (userName.equals(temp.getUserName())) {
                LocalDateTime AccessExp = temp.getLastAccess().plusSeconds(sessionValid);
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
            if (sessionHandle == temp.getSessionHandle()) {
                LocalDateTime AccessExp = temp.getLastAccess().plusSeconds(sessionValid);
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
            if (sessionHandle == temp.getSessionHandle()) {
                it.remove();
                break;
            }
        }
    }

    public void deleteExpired() {
        Iterator<UserSession> it = sessions.iterator();
        while (it.hasNext()) {
            UserSession temp = it.next();
            LocalDateTime AccessExp = temp.getLastAccess().plusSeconds(sessionValid);
            if (AccessExp.isBefore(LocalDateTime.now())) {
                it.remove();
            }
        }
    }

    public static void main(String[] args)  {
        SessionManager t = new SessionManager(5);
        UserSession newOne = new UserSession("Eugenia");

        if (t.find("Eugenia")==null) {
            t.add(newOne);
        }
        t.get(newOne.getSessionHandle());
        t.get(newOne.getSessionHandle());
        try {
            Thread.sleep(6000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(t.get(newOne.getSessionHandle()));

        UserSession newOne1 = new UserSession("Eugenia1");
        t.add(newOne1);
        try {
            Thread.sleep(2500);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        UserSession newOne2 = new UserSession("Eugenia2");
        t.add(newOne2);
        try {
            Thread.sleep(2500);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        t.deleteExpired();
        System.out.println(t.sessions);

        t.delete(newOne2.getSessionHandle());
        System.out.println(t.sessions);
    }
}
