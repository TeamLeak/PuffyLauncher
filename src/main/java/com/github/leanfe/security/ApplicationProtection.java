package com.github.leanfe.security;

import java.security.Permission;
import java.util.PropertyPermission;

public class ApplicationProtection {

    /**
     * Enables protection against injection of .dll files and other unwanted behavior by setting a security manager.
     */
    public static void enableSecurityManager() {
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkRead(String file) {
                if (isUnsafeFile(file)) {
                    throw new SecurityException("Reading from unsafe file is not allowed");
                }
            }

            @Override
            public void checkWrite(String file) {
                if (isUnsafeFile(file)) {
                    throw new SecurityException("Writing to unsafe file is not allowed");
                }
            }

            @Override
            public void checkExec(String cmd) {
                throw new SecurityException("Executing commands is not allowed");
            }

            @Override
            public void checkPermission(Permission perm) {
                // Only allow a few selected permissions
                if (perm instanceof PropertyPermission && ("user.home".equals(perm.getName()) || "java.io.tmpdir".equals(perm.getName()))) {
                    return;
                }
                throw new SecurityException("Permission denied: " + perm);
            }

            private boolean isUnsafeFile(String file) {
                return file != null && (file.toLowerCase().endsWith(".dll") || file.toLowerCase().endsWith(".so"));
            }
        });
    }

    /**
     * Limits access to certain resources by setting system properties and environment variables.
     */
    public static void limitAccessToResources() {
        System.setProperty("java.home", "");
        System.setProperty("java.class.path", "");
        System.setProperty("java.ext.dirs", "");
        System.setProperty("user.home", "");
        System.setProperty("user.dir", "");
        System.setProperty("user.name", "");

        System.getenv().keySet().forEach(envVar -> {
            if (envVar.contains("PATH")) {
                System.clearProperty(envVar);
            } else {
                System.setProperty(envVar, "");
            }
        });
    }

    /**
     * Secures the environment by disabling debugging options and setting a secure random number generator.
     */
    public static void secureEnvironment() {
        System.setProperty("java.security.debug", "");
        System.setProperty("java.security.egd", "file:/dev/urandom");
    }

    /**
     * Monitors the application by adding a shutdown hook that logs the shutdown event.
     */
    public static void monitorApplication() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Application is shutting down")));
    }
}