package com.github.leanfe.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthManager {

    public static boolean processLogin(String username, String password) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(getUriWithParams())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password)))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                // Authentication successful
                return true;
            } else if (statusCode == 401) {
                // Authentication failed
                return false;
            } else {
                // Other error occurred
                System.out.println("Server returned error: " + response.body());
                return false;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error sending request: " + e.getMessage());
            return false;
        }
    }

    private static URI getUriWithParams() {
        try {
            return new URI(Constants.AUTH_URL + "?" + "action=login");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}