import java.util.Scanner;
import java.sql.ResultSet;
//import java.sql.SQLException;

public class UserInterface {
    private DatabaseManager dbManager;
    private PlaylistGenerator playlistGenerator;

    public UserInterface() throws Exception {
        dbManager = new DatabaseManager();
        playlistGenerator = new PlaylistGenerator();
    }

    public void start() throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Music Playlist Generator!");

        // User registration
        System.out.println("Enter your name:");
        String name = scanner.nextLine();

        System.out.println("Enter your email:");
        String email = scanner.nextLine();

        dbManager.insertUser(name, email);

        // Set user preferences
        System.out.println("Enter your preferred genre:");
        String genre = scanner.nextLine();

        System.out.println("Enter your preferred mood:");
        String mood = scanner.nextLine();

        // Fetch user ID and store preferences
        ResultSet userResult = dbManager.getUserByEmail(email);
        if (userResult.next()) {
            int userId = userResult.getInt("id");
            dbManager.insertUserPreferences(userId, genre, mood);
        }

        // Generate and show the playlist
        playlistGenerator.generatePlaylist(email);
    }

    public static void main(String[] args) throws Exception {
        UserInterface ui = new UserInterface();
        ui.start();
    }
}
