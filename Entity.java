import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

//fiind clasa abstracta i se permite sa nu implementeze metodele interfetei daca subclasele le implementeaza
//Entity(abstract) implementeaza Battle => nu implementez metodele interfetei
//Figure(abstract) extinde Entity => nu implementez metodele
//RAZBOINICI = Warrior/Mage/Rogue extind Figure => aici le implementez

public abstract class Entity implements Battle {
    ArrayList<String> abilities = new ArrayList<>(); //lista de abilitati
    int currentHealth;
    int maxHealth;
    int currentMana;
    int maxMana;
    boolean immune_to_fire;
    boolean immune_to_ice;
    boolean immune_to_earth;
    boolean show = false;

    public Entity(int maxHealth, int maxMana, boolean immune_to_earth, boolean immune_to_fire, boolean immune_to_ice) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.immune_to_fire = immune_to_fire;
        this.immune_to_ice = immune_to_ice;
        this.immune_to_earth = immune_to_earth;
    }

    public void regenerate_health(int new_health) {
        currentHealth = new_health;
    }

    public void regenerate_mana(int new_mana) {
        currentMana = new_mana;
    }

    public void regenerate_abilities(){
        Random rand = new Random();

        ArrayList<String> possible_abilities_regen = new ArrayList<>();
        possible_abilities_regen.add("Fire");
        possible_abilities_regen.add("Ice");
        possible_abilities_regen.add("Earth");


        abilities.clear();
        abilities.add("Normal");
        abilities.add("Fire");
        abilities.add("Ice");
        abilities.add("Earth");
        System.out.println("Abilities have been regenerated");

        int number_of_random_abilities = rand.nextInt(4);  //0,1,2,3 abilitati in plus

        while(abilities.size() < number_of_random_abilities + 4) {
            String duplicate_ability = possible_abilities_regen.get(rand.nextInt(possible_abilities_regen.size()));
            abilities.add(duplicate_ability);
        }

    }

    public void choose_abilities(Entity player_damaged) {
        //LISTA DE ABILITATI (vf daca exista si alte abilitati in afara de normal attack)

        System.out.println("Available abilities:");
        for (int i = 0; i < abilities.size(); i++) {
            System.out.println(i + ". " + abilities.get(i));
        }
        System.out.println();

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Choose an ability: Write 'Fire', 'Ice', 'Earth', 'Normal'. Write 'Damage' for spell details");
            String ability = scanner.nextLine();

            if(ability.equals("Damage") || ability.equals("damage"))
                show_how_spells_work();
            else if (abilities.contains(ability)) {
                System.out.println("Ability chosen: " + ability);
                //o sterg din lista doar daca e abilitate speciala. Normal attack e valabil oricand
                if (ability.equals("Fire") || ability.equals("Ice") || ability.equals("Earth")) {
                    abilities.remove(ability);
                }
                do_the_ability_player(ability, player_damaged);
                break;
            }
            else if(ability.equals("normal")){
                System.out.println("Ability chosen: Normal");
                abilities.remove(ability);
                do_the_ability_player(ability, player_damaged);
                break;
            }
            else if(ability.equals("earth")){
                System.out.println("Ability chosen: Earth");
                abilities.remove(ability);
                do_the_ability_player(ability, player_damaged);
                break;
            }
            else if(ability.equals("ice")){
                System.out.println("Ability chosen: Ice");
                abilities.remove(ability);
                do_the_ability_player(ability, player_damaged);
                break;
            }
            else if(ability.equals("fire")){
                System.out.println("Ability chosen: Fire");
                abilities.remove(ability);
                do_the_ability_player(ability, player_damaged);
                break;
            }
            else System.out.println("Unknown ability: " + ability + ". Please enter a valid ability");
        }
    }

    public void choose_random_ability(Entity player_damaged) {

        Random rand = new Random();
        String ability = abilities.get(rand.nextInt(abilities.size()));
        if(ability.equals("Fire") || ability.equals("Ice") || ability.equals("Earth"))
            abilities.remove(ability);
        do_the_ability_opp(ability, player_damaged);
    }

    public void show_how_spells_work(){
        System.out.println("Normal attack deals 10-19 damage without costing mana. 50% chance the damage doubles!");
        System.out.println("Fire adds 31-40 damage with a mana cost of 10-15.");
        System.out.println("Ice adds 21-30 damage with a mana cost of 5-10.");
        System.out.println("Earth adds 26-35 damage with a mana cost of 8-12.");
        System.out.println("Damages can double if you are lucky");
        System.out.println("Both you and your enemy have a 50% chance of blocking the attack!");
    }

    //metoda 3 din entity
    public void do_the_ability_player(String ability, Entity player_damaged) {

        Spell spell = null;
        Random rand = new Random();

        if (ability.equals("Fire") || ability.equals("fire")) {   //fire damage = 31-40 cu mana 10-15
            int random_damage_fire = rand.nextInt(10) + 31;
            int random_mana_cost_fire = rand.nextInt(6) + 10;
            spell = new Fire(random_damage_fire, random_mana_cost_fire);

            if (player_damaged.immune_to_fire) {
                if(currentMana < spell.ability_mana_cost){
                    System.out.println("You do not possess enough mana! Choose a different spell!");
                    abilities.add(ability);
                    choose_abilities(player_damaged);
                    return;
                }
                System.out.println("Enemy is immune to fire!");
                regenerate_mana(currentMana - random_mana_cost_fire);
                return;
            }
        }

        else if (ability.equals("Ice") || ability.equals("ice")) { //ice damage = 21-30 cu mana 5-10
            int random_damage_ice = rand.nextInt(10) + 21;
            int random_mana_cost_ice = rand.nextInt(6) + 5;
            spell = new Ice(random_damage_ice, random_mana_cost_ice);

            if (player_damaged.immune_to_ice) {
                if(currentMana < spell.ability_mana_cost){
                    System.out.println("You do not possess enough mana! Choose a different spell!");
                    abilities.add(ability);
                    choose_abilities(player_damaged);
                    return;
                }
                System.out.println("Enemy is immune to ice!");
                regenerate_mana(currentMana - random_mana_cost_ice);
                return;
            }
            }

            if (ability.equals("Earth") || ability.equals("earth")) { //earth damage = 26-35 cu mana 8-12
                int random_damage_earth = rand.nextInt(10) + 26;
                int random_mana_cost_earth = rand.nextInt(5) + 8;
                spell = new Earth(random_damage_earth, random_mana_cost_earth);

                if (player_damaged.immune_to_earth) {
                    if(currentMana < spell.ability_mana_cost){
                        System.out.println("You do not possess enough mana! Choose a different spell!");
                        abilities.add(ability);
                        choose_abilities(player_damaged);
                        return;
                    }
                    System.out.println("Enemy is immune to earth!");
                    regenerate_mana(currentMana - random_mana_cost_earth);
                    return;
                }
            }

            if(ability.equals("Normal") || ability.equals("normal")) { //normal damage = 10-19 cu mana = 0
                spell = new Normal(0, 0);
            }

            //s-a creat abilitatea
            if(spell != null) {
                if(currentMana < spell.ability_mana_cost){
                    System.out.println("You do not possess enough mana! Choose a different spell!");
                    abilities.add(ability);
                    choose_abilities(player_damaged);
                }
                else {
                    int damage = getDamage(); //damage acum contine damage normal
                    damage = damage + spell.ability_damage;
                    if(spell.ability_damage > 0)
                        System.out.println("Damage after adding the spell is " + damage);
                    regenerate_mana(currentMana - spell.ability_mana_cost);
                    player_damaged.receiveDamage(damage);
                }
            }
        }

    public void do_the_ability_opp(String ability, Entity player_damaged) {

        System.out.println("Ability being done by enemy = " + ability);

        Spell spell = null;
        Random rand = new Random();

        if (ability.equals("Fire")) {   //fire damage = 31-40 cu mana 10-15
            int random_damage_fire = rand.nextInt(10) + 31;
            int random_mana_cost_fire = rand.nextInt(6) + 10;
            spell = new Fire(random_damage_fire, random_mana_cost_fire);

            if (player_damaged.immune_to_fire) {
                if(currentMana < spell.ability_mana_cost){
                    abilities.add(ability);
                    choose_random_ability(player_damaged);
                    return;
                }
                System.out.println("You are immune to fire, enemy did not hit you!");
                regenerate_mana(currentMana - random_mana_cost_fire);
                return;
            }
        }

        else if (ability.equals("Ice")) { //ice damage = 21-30 cu mana 6-10
            int random_damage_ice = rand.nextInt(10) + 21;
            int random_mana_cost_ice = rand.nextInt(5) + 6;
            spell = new Ice(random_damage_ice, random_mana_cost_ice);

            if (player_damaged.immune_to_ice) {
                if(currentMana < spell.ability_mana_cost){
                    abilities.add(ability);
                    choose_random_ability(player_damaged);
                    return;
                }
                System.out.println("You are immune to ice, enemy did not hit you!");
                regenerate_mana(currentMana - random_mana_cost_ice);
                return;
            }
        }

        if (ability.equals("Earth")) { //earth damage = 26-35 cu mana 9-12
            int random_damage_earth = rand.nextInt(10) + 26;
            int random_mana_cost_earth = rand.nextInt(4) + 9;
            spell = new Earth(random_damage_earth, random_mana_cost_earth);

            if (player_damaged.immune_to_earth) {
                if(currentMana < spell.ability_mana_cost){
                    abilities.add(ability);
                    choose_random_ability(player_damaged);
                    return;
                }

                System.out.println("You are immune to earth, enemy did not hit you!");
                regenerate_mana(currentMana - random_mana_cost_earth);
                return;
            }
        }

        if(ability.equals("Normal")) { //normal damage = 10-19 cu mana = 0
            spell = new Normal(0, 0);
        }

        //s-a creat abilitatea
        if(spell != null) {
            if(currentMana < spell.ability_mana_cost){
                abilities.add(ability);
                choose_random_ability(player_damaged);
            }
            else {
                int damage = getDamage();
                damage = damage + spell.ability_damage;
                if(spell.ability_damage > 0)
                    System.out.println("Damage after adding the spell is " + damage);
                regenerate_mana(currentMana - spell.ability_mana_cost);
                if(currentMana < 0)
                    currentMana = 0;
                player_damaged.receiveDamage(damage);
            }
        }
    }
}
