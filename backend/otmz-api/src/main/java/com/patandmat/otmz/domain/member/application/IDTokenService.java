package com.patandmat.otmz.domain.member.application;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IDTokenService {
//    @Value("${oauth.google.clientid}")
//    String clientId;
//    public boolean checkToken(String idToken) throws GeneralSecurityException, IOException {
//        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
//                // Specify the CLIENT_ID of the app that accesses the backend:
//                .setAudience(Collections.singletonList(clientId))
//                // Or, if multiple clients access the backend:
//                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
//                .build();
//
//// (Receive idTokenString by HTTPS POST)
//        GoogleIdToken googleIdToken = verifier.verify(idToken);
//
//        if (googleIdToken != null) {
//            GoogleIdToken.Payload payload = googleIdToken.getPayload();
//            System.out.println("expiration time second: " + payload.getExpirationTimeSeconds());
//            payload.setExpirationTimeSeconds(16700000L);
//            // Print user identifier
//            String userId = payload.getSubject();
//            System.out.println("User ID: " + userId);
//            return true;
//        } else {
//            System.out.println("Invalid ID token.");
//            return false;
//        }
//    }

    public String refreshIdToken(String refreshToken) {
        String client_id;
        String client_secret;
        String refresh_token;
        String grant_type = "";
        return null;
    }

    private String getMeUrl(String baseUrl, Map<String,String> map) {
        String url = baseUrl + "?";
        for (String key : map.keySet()) {
            url += key + "=" + map.get(key) + "&";
        }
        System.out.println(url);
        return url;
    }
}
