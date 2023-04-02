package com.github.leanfe.util;

import com.github.leanfe.file.FileManager;

import java.awt.*;
import java.io.*;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class Utils {

    public static void closeSilently(final Closeable closeable) {
        if(closeable != null)
            try {
                closeable.close();
            }
            catch(final IOException ignored) {}
    }

    public static void openURL(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException e) {
            System.err.printf("Error, when open URL! LOG: %s", e.getMessage());
        } catch (URISyntaxException e) {
            System.err.printf("Error! URL Syntax error! LOG: %s", e.getMessage());
        }
    }

    public static String getMD5(final File file) {
        DigestInputStream stream = null;
        try {
            stream = new DigestInputStream(new FileInputStream(file), MessageDigest.getInstance("MD5"));
            final byte[] buffer = new byte[65536];

            int read = stream.read(buffer);
            while(read >= 1)
                read = stream.read(buffer);
        }
        catch(final Exception ignored) {
            return null;
        }
        finally {
            closeSilently(stream);
        }

        return String.format("%1$032x", new BigInteger(1, stream.getMessageDigest().digest()));
    }


    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static String readJsonFromUrl(String url) throws IOException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return readAll(rd);
        }
    }


    public static void openFolder() {
        try {
            Desktop.getDesktop().open(FileManager.initializeApplicationFolder());
        } catch (IOException e) {
            System.err.printf("Error, when open Folder! LOG: %s", e.getMessage());
        }
    }
}
