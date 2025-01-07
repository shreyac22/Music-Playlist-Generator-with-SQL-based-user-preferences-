import java.sql.ResultSet;

public class PlaylistGenerator {
    private DatabaseManager dbManager;
    private SpotifyAPI spotifyAPI;

    public PlaylistGenerator() throws Exception {
        dbManager = new DatabaseManager();
        spotifyAPI = new SpotifyAPI();
    }

    public void generatePlaylist(String email) throws Exception {
        // Fetch user ID by email
        ResultSet userResult = dbManager.getUserByEmail(email);
        if (userResult.next()) {
            int userId = userResult.getInt("id");

            // Fetch user preferences
            ResultSet preferences = dbManager.getUserPreferences(userId);
            if (preferences.next()) {
                String genre = preferences.getString("genre");
                String mood = preferences.getString("mood");

                // Fetch recommended songs from Spotify API
                String recommendations = spotifyAPI.searchSong(genre, mood);
                System.out.println("Recommended songs: " + recommendations);
            } else {
                System.out.println("User preferences not found.");
            }
        } else {
            System.out.println("User not found.");
        }
    }
}
