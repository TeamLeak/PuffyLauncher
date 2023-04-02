package com.github.leanfe.session;

import com.github.leanfe.file.FileManager;
import com.github.leanfe.util.Constants;
import org.apache.shiro.session.Session;

import java.io.File;

public class SessionManager {

    private SessionManagerHelper sessionManager;
    
    public SessionManager() {
        if (!Constants.useSessions) return;

        File sessionDir = FileManager.createSessionDir();
        
        sessionManager = new SessionManagerHelper(sessionDir);
    }

    public boolean load() {
        if (!Constants.useSessions) return false;
        return sessionManager.loadLastValidSession();
    }

    public Session createSession() {
        if (!Constants.useSessions) return null;

        Session session = sessionManager.createSession();
        // Do some work with the session
        sessionManager.runSession(session);

        return session;
    }

    public void killSession(Session session) {
        if (!Constants.useSessions) return;

        sessionManager.killSession(session);
    }
}
