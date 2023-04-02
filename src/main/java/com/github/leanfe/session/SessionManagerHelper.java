package com.github.leanfe.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;

import java.io.*;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class SessionManagerHelper {

    private final SessionDAO sessionDAO;
    private final File currentSessionFile;

    public SessionManagerHelper(File sessionDir) {
        this.currentSessionFile = new File(sessionDir, "current_session.session");
        this.sessionDAO = new FileSessionDAO(sessionDir);
    }

    public Session createSession() {
        Session session = new DefaultSessionManager().start(null);
        saveCurrentSession(session.getId().toString());
        return session;
    }

    public static boolean isDate14DaysOld(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -14); // Subtract 14 days from current date
        Date fourteenDaysAgo = calendar.getTime();

        return date.before(fourteenDaysAgo);
    }

    public boolean isValidSession(Session session) {
        try {
            return session != null && session.getId() != null && sessionDAO.readSession(session.getId()) != null && isDate14DaysOld(session.getLastAccessTime());
        } catch (Exception e) {
            return false;
        }
    }

    public Session getSession(String sessionId) {
        return sessionDAO.readSession(sessionId);
    }

    public void runSession(Session session) {
        session.touch();
        sessionDAO.update(session);
    }

    public void killSession(Session session) {
        session.stop();
        sessionDAO.delete(session);
        deleteCurrentSession();
    }

    public boolean loadLastValidSession() {
        String currentSessionId = loadCurrentSession();
        if (currentSessionId != null) {
            Session session = getSession(currentSessionId);
            if (isValidSession(session)) {
                runSession(session);
                return true;
            } else {
                deleteCurrentSession();
            }
        }
        return false;
    }

    public boolean downloadSession(File sessionFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(sessionFile))) {
            Session session = (Session) ois.readObject();
            sessionDAO.create(session);
            saveCurrentSession(session.getId().toString());
            return true;
        } catch (Exception e) {
            System.err.printf("Can't load session! %s", e.getMessage());
            return false;
        }
    }

    private void saveCurrentSession(String sessionId) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentSessionFile))) {
            writer.write(sessionId);
        } catch (IOException e) {
            System.err.print("Can't write current session!");
        }
    }

    private String loadCurrentSession() {
        try (BufferedReader reader = new BufferedReader(new FileReader(currentSessionFile))) {
            return reader.readLine();
        } catch (IOException e) {
            System.err.printf("Can't load current session! %s", e.getMessage());
            return null;
        }
    }

    private void deleteCurrentSession() {
        currentSessionFile.delete();
    }

    private record FileSessionDAO(File sessionDir) implements SessionDAO {

            private FileSessionDAO(File sessionDir) {
                this.sessionDir = new File(sessionDir, "session");
                if (!this.sessionDir.exists()) {
                    this.sessionDir.mkdir();
                }
            }

            @Override
            public void update(Session session) throws UnknownSessionException {
                saveSession(session);
            }

            @Override
            public void delete(Session session) {
                File sessionFile = getSessionFile(session.getId().toString());
                sessionFile.delete();
            }

            @Override
            public Collection<Session> getActiveSessions() {
                return null;
            }

            @Override
            public Serializable create(Session session) {
                saveSession(session);
                return session.getId();
            }

            @Override
            public Session readSession(Serializable sessionId) throws UnknownSessionException {
                File sessionFile = getSessionFile(sessionId.toString());
                if (sessionFile.exists()) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(sessionFile))) {
                        return (Session) ois.readObject();
                    } catch (Exception e) {
                        System.err.printf("Can't read session! %s", e.getMessage());
                        return null;
                    }
                } else {
                    return null;
                }
            }

            private File getSessionFile(String sessionId) {
                return new File(sessionDir, sessionId + ".session");
            }

            private void saveSession(Session session) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getSessionFile(session.getId().toString())))) {
                    oos.writeObject(session);
                } catch (Exception e) {
                    System.err.printf("Can't save session! %s", e.getMessage());
                }
            }
    }
}