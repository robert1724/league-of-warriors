import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Test {
    static Grid grid;
    ArrayList<Account> accounts;
    Account currentAccount;
    int sem = 1;

    public Test() {
        accounts = new ArrayList<>();
    }

    public void run() {

        accounts = JsonInput.deserializeAccounts();

        while (true) {

            int character_to_use;
            Scanner scanner = new Scanner(System.in);
            if(sem == 1) {
                show_instructions();
            }
            sem = 0;
            System.out.println("Insert your email: ");
            String email = scanner.nextLine();
            System.out.println("Insert your password: ");
            String password = scanner.nextLine();
            currentAccount = null;

            for (Account account : accounts) {
                if (account.account_exists(email, password)) {
                    currentAccount = account;
                    break;
                }
            }

            if (currentAccount == null)
                System.out.println("Account does not exist!");

            else {
                choose_character(currentAccount);
                break;
            }
        }
    }

    public void choose_character(Account account) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to your account, choose your character: ");
        for (Figure figure : account.game_characters) {
            System.out.println("Character: " + figure.name + ", Level: " + figure.level + ", Experience: " + figure.experience + " Immunities to fire/ice/earth: [" + figure.immune_to_fire + ", " + figure.immune_to_ice + ", " + figure.immune_to_earth + "]");
        }

        System.out.println("Press 1 for Warrior, 2 for Mage, 3 for Rogue");

        int ok = 1;
        while (ok == 1) {
            String input = scanner.nextLine();
            try {
                int input_number = Integer.parseInt(input); //incearca sa il converteasca la numar. nu reuseste => exceptie
                if (input_number >= 1 && input_number <= 3) {
                    grid.currentCharacter = account.game_characters.get(input_number - 1);
                    System.out.println("Your character: " + grid.currentCharacter.name);
                    System.out.println("From now on you can press 'exit' anytime you like to finish the game! ( not in a fight, what would that say about you? )");
                    System.out.println("You can also press 'damage' if you want to remember how spells work");
                    ok = 0;
                } else {
                    throw new InvalidChoiceException("Please press 1 for Warrior, 2 for Mage, 3 for Rogue");
                }
            } catch (InvalidChoiceException e) {
                System.out.println("Please press 1 for Warrior, 2 for Mage, 3 for Rogue");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }

    public void show_instructions() {
        System.out.println();
        System.out.println("Welcome to the game!");
        System.out.println("YOUR GOAL: Find the portal to advance to the next level");
        System.out.println("You can reach 3 other places:");
        System.out.println("1)Desert: Boring, nothing happens here");
        System.out.println("2)Sanctuary: Heal your health and mana");
        System.out.println("3)Enemy: You have to defeat him to continue your search for the portal");
        System.out.println("You can move up, down, left and right");
        System.out.println("You can attack with:");
        System.out.println("1)Normal attack (does not cost mana)");
        System.out.println("2)Ability attack : Fire/Ice/Earth (randomly generated each fight to a maximum of 6, each one costing random amount of mana)");
        System.out.println("Normal attack deals 10-19 damage without costing mana. 50% chance the damage doubles!");
        System.out.println("Fire adds 31-40 damage with a mana cost of 10-15.");
        System.out.println("Ice adds 21-30 damage with a mana cost of 5-10.");
        System.out.println("Earth adds 26-35 damage with a mana cost of 8-12.");
        System.out.println("Damages can double if you are lucky");
        System.out.println("Both you and your enemy have a 50% chance of blocking the attack!");
        System.out.println("Earn 50 experience to level up and boost your attributes!");
        System.out.println("The portal is hidden! Find it! But first, introduce your credentials..");
        System.out.println();
    }

    public static void show_map(ArrayList<ArrayList<Cell>> game_map) {

        if(game_map == null || game_map.isEmpty()){
            System.out.println("Game map is empty or not initialized!");
            return;
        }

        int grid_length = game_map.size();
        int grid_width = game_map.getFirst().size();

        for (int i = 0; i < grid_length; i++) {
            for (int j = 0; j < grid_width; j++) {
                //e vizitat si nu e player-ul pe celula
                if (game_map.get(i).get(j).isVisited() && game_map.get(i).get(j).getType() != CellEntityType.PLAYER)
                    System.out.print("V ");
                else if (game_map.get(i).get(j).getType() == CellEntityType.PLAYER)
                    System.out.print("P ");
                else if (!game_map.get(i).get(j).isVisited())
                    System.out.print("N ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        int grid_length = 5;
        int grid_width = 5;
        Test.grid = Grid.getInstance(grid_length, grid_width);
        ArrayList<ArrayList<Cell>> game_map_hard = Grid.map_generator_hardcoded(grid_length, grid_width);
        for (int i = 0; i < grid_length; i++) {
            for (int j = 0; j < grid_width; j++) {
                if(game_map_hard.get(i).get(j).getType() == CellEntityType.PLAYER)
                    grid.currentCell = game_map_hard.get(i).get(j);
            }
        }

        Test test = new Test();
        test.run();

        int levels_completed = 1;
        int ok = 1;

        System.out.println("Welcome to level 1!");

        while (ok == 1) {

            show_map(game_map_hard);

            System.out.println("Press 'w' to move up, 'a' to move left, 'd' to move right, 's' to move down, 'exit' to leave the game!");
            try {
                String move = scanner.nextLine();
                if (!move.equals("w") && !move.equals("a") && !move.equals("d") && !move.equals("s") && !move.equals("exit"))
                    throw new InvalidChoiceException("Please enter a valid move");
                else {
                    if(grid.currentCell == null) {
                        System.out.println("Grid not initialized yet!");
                        return;
                    }
                }

                if (move.equals("w"))
                    grid.goNorth(game_map_hard);
                else if (move.equals("a"))
                    grid.goWest(game_map_hard);
                else if (move.equals("d"))
                    grid.goEast(game_map_hard);
                else if (move.equals("s"))
                    grid.goSouth(game_map_hard);

                else if (move.equals("exit")){
                    ok = 0;
                    System.out.println("Exiting game..");
                    break;
                }
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }

            //daca jucatorul ajunge pe sanctuar
            if (grid.currentCell.getType() == CellEntityType.SANCTUARY) {
                System.out.println("You've reached a sanctuary! Both your health and mana will regenerate!");
                Figure player = grid.currentCharacter;
                System.out.println("Player stats before healing: Health = " + player.currentHealth + " ,Mana = " + player.currentMana);
                int regenerate_health_random = rand.nextInt(100);
                int regenerate_mana_random = rand.nextInt(100);

                System.out.println("Player will be healed with: Health = " + regenerate_health_random + " ,Mana = " + regenerate_mana_random);

                player.regenerate_health(player.currentHealth + regenerate_health_random);

                if(player.currentHealth > player.maxHealth)
                    player.currentHealth = player.maxHealth;

                player.regenerate_mana(player.currentMana + regenerate_mana_random);
                if(player.currentMana > player.maxMana)
                    player.currentMana = grid.currentCharacter.maxMana;

                System.out.println("Player stats after healing: Health = " + player.currentHealth + " ,Mana = " + player.currentMana);

                grid.currentCell.setType(CellEntityType.PLAYER);
                grid.currentCell.setVisited(false);
            }

            if (grid.currentCell.getType() == CellEntityType.VOID) {
                System.out.println("You reached a desert! Stay as long as you like");
                grid.currentCell.setType(CellEntityType.PLAYER);
                grid.currentCell.setVisited(false);
            }

            //am ajuns la batalie
            if (grid.currentCell.getType() == CellEntityType.ENEMY) {
                System.out.println("You've reached an enemy! You have to fight!");
                Enemy enemy = grid.currentCell.getEnemy();
                Figure player = grid.currentCharacter;

                while (player.currentHealth > 0 && enemy.currentHealth > 0) {

                    System.out.println();
                    System.out.println("Your stats:\nHealth = " + player.currentHealth + ", Mana = " + player.currentMana);
                    System.out.println("Enemy stats:\nHealth = " + enemy.currentHealth + ", Mana = " + enemy.currentMana);
                    System.out.println();

                    System.out.println("Your turn to attack!");
                    player.choose_abilities(enemy);

                    System.out.println();

                    //daca enemy inca e in viata va selecta si el o abilitate random din lista si o va face
                    if(enemy.currentHealth > 0) {
                        System.out.println("Enemy turn to attack!");
                        enemy.choose_random_ability(player);
                    }

                    if(player.currentHealth <= 0) {
                        System.out.println("You died!");
                        System.out.println("Maybe you should choose another character :)");
                        test.run();
                        System.out.println("Welcome to level 1!");
                        levels_completed = 1;
                        grid_length = rand.nextInt(8) + 3; //intre 3 si 10
                        grid_width = rand.nextInt(8) + 3;
                        Game.grid = Grid.getInstance(grid_length, grid_width);
                        ArrayList<ArrayList<Cell>> game_map_loss = Grid.map_generator(grid_length, grid_width);
                        for (int i = 0; i < grid_length; i++) {
                            for (int j = 0; j < grid_width; j++) {
                                game_map_loss.get(i).get(j).setVisited(false);
                            }
                        }

                        for (int i = 0; i < grid_length; i++) {
                            for (int j = 0; j < grid_width; j++) {
                                if(game_map_loss.get(i).get(j).getType() == CellEntityType.PLAYER) {
                                    grid.currentCell = game_map_loss.get(i).get(j);
                                }
                            }
                        }

                        grid.currentCharacter = grid.currentCharacter;

                        game_map_hard = game_map_loss;
                    }

                    if(enemy.currentHealth <= 0) {
                        System.out.println("You won! Congratulations!");
                        System.out.println("Your health will double and you will have max Mana!");
                        System.out.println("Your stats before healing:\nHealth = " + player.currentHealth + ", Mana = " + player.currentMana);

                        player.regenerate_health(player.currentHealth * 2);
                        if(player.currentHealth > player.maxHealth)
                            player.currentHealth = player.maxHealth;
                        player.regenerate_mana(player.maxMana);



                        System.out.println("Your stats after healing:\nHealth = " + player.currentHealth + ", Mana = " + player.currentMana);

                        int random_experience = rand.nextInt(10) + 1;

                        player.experience += random_experience;
                        player.check_level_up(player);

                        player.regenerate_abilities();

                        grid.currentCell.setType(CellEntityType.PLAYER);
                        grid.currentCell.setVisited(false);
                        break;
                    }
                }
            }

            if(grid.currentCell.getType() == CellEntityType.PORTAL) {
                System.out.println("You found the portal! Well done,you finished the level!");
                levels_completed++;
                grid.currentCharacter.experience += levels_completed * 5;
                grid.currentCharacter.check_level_up(grid.currentCharacter);
                System.out.println("Generating map for a new level..");
                grid_length = rand.nextInt(8) + 3;
                grid_width = rand.nextInt(8) + 3;

                ArrayList<ArrayList<Cell>> new_game_map = Grid.map_generator(grid_length, grid_width);

                for (int i = 0; i < grid_length; i++) {
                    for (int j = 0; j < grid_width; j++) {
                        new_game_map.get(i).get(j).setVisited(false);  // Resetăm celulele
                    }
                }

                for (int i = 0; i < grid_length; i++) {
                    for (int j = 0; j < grid_width; j++) {
                        if(new_game_map.get(i).get(j).getType() == CellEntityType.PLAYER) {
                            grid.currentCell = new_game_map.get(i).get(j);
                        }
                    }
                }

                grid.currentCharacter = grid.currentCharacter;

                game_map_hard = new_game_map;

                System.out.println("Welcome to level " + levels_completed + "!");
            }
        }
    }
}