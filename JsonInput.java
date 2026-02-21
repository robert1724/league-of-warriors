import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class JsonInput {
    public static ArrayList<Account> deserializeAccounts() {
        String accountPath = "accounts.json";
        try {
            // Read the JSON file content
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(accountPath);
            JSONObject obj = (JSONObject) parser.parse(reader);

            JSONArray accountsArray = (JSONArray) obj.get("accounts");

            ArrayList<Account> accounts = new ArrayList<>();
            for (Object accountObj : accountsArray) {
                JSONObject accountJson = (JSONObject) accountObj;
                // name, country, games_number
                String name = (String) accountJson.get("name");
                String country = (String) accountJson.get("country");
                int gamesNumber = Integer.parseInt((String) accountJson.get("maps_completed"));

                // Credentials
                Credentials credentials = null;
                try {
                    JSONObject credentialsJson = (JSONObject) accountJson.get("credentials");
                    String email = (String) credentialsJson.get("email");
                    String password = (String) credentialsJson.get("password");

                    credentials = new Credentials(email, password);
                } catch (Exception e) {
                    System.out.println("! This account doesn't have all credentials !");
                }

                // Favorite games
                SortedSet<String> favoriteGames = new TreeSet<>();
                try {
                    JSONArray games = (JSONArray) accountJson.get("favorite_games");
                    for (Object game : games) {
                        favoriteGames.add((String) game);
                    }
                } catch (Exception e) {
                    System.out.println("! This account doesn't have favorite games !");
                }

                // Characters
                ArrayList<Figure> characters = new ArrayList<Figure>();
                try {
                    JSONArray charactersListJson = (JSONArray) accountJson.get("characters");

                    for (Object charObj : charactersListJson) {
                        JSONObject charJson = (JSONObject) charObj;

                        String cname = (String) charJson.get("name");
                        String profession = (String) charJson.get("profession");
                        String level = (String) charJson.get("level");
                        int lvl = Integer.parseInt(level);
                        int experience = 0; // Default experience value if parsing fails

                        Object experienceObj = charJson.get("experience");
                        if (experienceObj instanceof String) {
                            try {
                                experience = Integer.parseInt((String) experienceObj);
                                System.out.println("Experience parsed: " + experience);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid experience value for character: " + cname + ", skipping this character.");
                            }
                        } else if (experienceObj instanceof Long) {
                            experience = ((Long) experienceObj).intValue();  // Convert Long to int
                        } else {
                            System.out.println("Experience field is not valid for character: " + cname);
                        }

                        // Create the character object based on profession
                        Figure newCharacter = null;
                        if (profession.equals("Warrior")) {
                            newCharacter = new Warrior(cname, experience, lvl);
                        } else if (profession.equals("Rogue")) {
                            newCharacter = new Rogue(cname, experience, lvl);
                        } else if (profession.equals("Mage")) {
                            newCharacter = new Mage(cname, experience, lvl);
                        }

                        // Check if the character was created and add it
                        if (newCharacter != null) {
                            characters.add(newCharacter);
                        } else {
                            System.out.println("Invalid profession for character: " + cname);
                        }
                    }
                } catch (Exception e) {
                    // If there is any error in processing characters (other than experience), print it
                    System.out.println("Error while processing characters for account: " + accountJson.get("name"));
                    e.printStackTrace();
                }

                Account.Information information = new Account.Information(credentials, favoriteGames, name, country);
                Account account = new Account(information, gamesNumber, characters);
                accounts.add(account);
            }
            return accounts;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
