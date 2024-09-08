package com.smartvizz.expenses.backend.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.AccessToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

public class GoogleOAuthUtil {

    private static final String GMAIL_SCOPE = "https://mail.google.com/";

    public static AccessToken getAccessToken() throws IOException {
        // Load credentials from a file
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                    new FileInputStream("gmail-oauth-credentials-dev.json")
                )
                .createScoped(Collections.singleton(GMAIL_SCOPE));

        // Refresh and get an access token
        credentials.refreshIfExpired();
        return credentials.getAccessToken();
    }
}
