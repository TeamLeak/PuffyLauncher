package com.github.leanfe.util;

import com.github.leanfe.file.FileManager;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogManipulation {

    private static String formatFilename() {
        LocalDateTime now = LocalDateTime.now();

        // Create a formatter for the desired output format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        // Format the current date and time as a string using the formatter
        return "%s_interface-log.log".formatted(now.format(formatter));
    }

    public static void redirectErrorsToFile() {
        var file = new File(FileManager.createLogDir(), formatFilename());
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Can't create log file!");
        }

        // Create a new PrintStream that writes to the specified file
        try {
            FileOutputStream fos = new FileOutputStream(file);
            PrintStream ps = new PrintStream(fos);

            // Redirect System.err to the new PrintStream
            System.setErr(ps);
        } catch (IOException e) {
            // Handle the exception
            System.err.println("Can't redirect System.err!");
        }

        var ref = new Object() {
            FileWriter writer = null;
        };

        try {
            ref.writer = new FileWriter(file, true);
        } catch (IOException e) {
            System.err.println("Can't create writer!");
        }

        // Install a new default uncaught exception handler
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            // Check if the exception message contains "JAVAFX" or "jfoenix"
            if (e.getMessage() != null && (e.getMessage().toLowerCase().contains("javafx") || e.getMessage().toLowerCase().contains("jfoenix"))) {
                // Write the exception message to the log file
                try {
                    ref.writer.write(e.getMessage() + "\n");
                    ref.writer.close();
                } catch (IOException ex) {
                    System.err.println("Can't write log!");
                }
            }
        });
    }

}
