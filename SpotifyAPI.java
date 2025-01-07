/*
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class SpotifyAPI {
    private static final String CLIENT_ID = "2de5b8ade6d744cc8f4635487f653268";
    private static final String CLIENT_SECRET = "d317eaaec13d4edeb5af31cae73de0fd";

    // Obtain access token from Spotify
    public String getAccessToken() throws IOException, InterruptedException {
        String authUrl = "https://accounts.spotify.com/api/token";
        String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(authUrl))
                .header("Authorization", "Basic " + encodedCredentials)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Extract and return the access token from the response
        String accessToken = response.body().split("\"access_token\":\"")[1].split("\"")[0];
        return accessToken;
    }

    // Search songs based on genre and mood
    public String searchSong(String genre, String mood) throws IOException, InterruptedException {
        String accessToken = getAccessToken();
        String searchUrl = String.format("https://api.spotify.com/v1/recommendations?seed_genres=%s&target_valence=%s", genre, mood);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(searchUrl))
                .header("Authorization", "Bearer " + accessToken)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();  // Return song recommendations
    }
}

 */

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SpotifyAPI {
    private static final String CLIENT_ID = "2de5b8ade6d744cc8f4635487f653268";
    private static final String CLIENT_SECRET = "d317eaaec13d4edeb5af31cae73de0fd";

    // Mapping for mood to target valence values
    private static final Map<String, String> moodToValenceMap = new HashMap<>();

    static {
        moodToValenceMap.put("happy", "0.9");
        moodToValenceMap.put("sad", "0.2");
        moodToValenceMap.put("energetic", "0.8");
        moodToValenceMap.put("calm", "0.4");
    }

    // Obtain access token from Spotify
    public String getAccessToken() throws IOException, InterruptedException {
        String authUrl = "https://accounts.spotify.com/api/token";
        String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(authUrl))
                .header("Authorization", "Basic " + encodedCredentials)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String accessToken = response.body().split("\"access_token\":\"")[1].split("\"")[0];
        return accessToken;
    }

    // Search songs based on genre and mood
    public String searchSong(String genre, String mood) throws IOException, InterruptedException {
        String valence = moodToValenceMap.getOrDefault(mood.toLowerCase(), "0.5");  // Default to neutral if mood is unknown
        String accessToken = getAccessToken();

        // Construct the search URL
        String searchUrl = String.format("https://api.spotify.com/v1/recommendations?seed_genres=%s&target_valence=%s", genre, valence);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(searchUrl))
                .header("Authorization", "Bearer " + accessToken)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();  // Return song recommendations
    }
}
