import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class Account {

    //lista cu toate personajele contului
    public Information information;
    public int nr_of_games;
    public ArrayList<Figure> game_characters;

    public Account(Information information, int nr_of_games, ArrayList<Figure> game_characters) {
        this.information = information;
        this.nr_of_games = nr_of_games;
        this.game_characters = game_characters;
    }

    //inner class
    static class Information {
        //o colectie care contine jocurile preferate
        public SortedSet<String> fav_games = new TreeSet<>();
        public Credentials credentials;
        public String country;
        public String username;

        public Information(Credentials credentials, SortedSet<String> fav_games, String country, String username) {
            this.credentials = credentials;
            this.fav_games = fav_games;
            this.country = country;
            this.username = username;
        }
    }

    public boolean account_exists(String email, String password) {
        if(this.information.credentials.getEmail().equals(email) && this.information.credentials.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

}

