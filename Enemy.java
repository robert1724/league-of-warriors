//Enemy nu au atribute speciale. doar Warrior, Rogue si Mage au
import java.util.ArrayList;
import java.util.Random;


//Enemy nu are nici atribute secundare, nici principale
public class Enemy extends Entity {
    Random rand = new Random();

    public Enemy(int random_enemy_health, int random_enemy_mana, boolean random_immune_fire, boolean random_immune_ice, boolean random_immune_earth) {
        super(random_enemy_health, random_enemy_mana, random_immune_earth, random_immune_fire, random_immune_ice);
        this.abilities = new ArrayList<>();
        enemy_abilities();
    }

    public void enemy_abilities() {
        ArrayList<String> possible_abilities = new ArrayList<>();
        possible_abilities.add("Fire");
        possible_abilities.add("Ice");
        possible_abilities.add("Earth");

        //adaug o abilitate din fiecare apoi vad daca se mai adauga random si altele
        abilities.add("Normal");
        abilities.add("Fire");
        abilities.add("Ice");
        abilities.add("Earth");

        int number_of_random_abilities = rand.nextInt(4);  //0,1,2,3 abilitati in plus

        while(abilities.size() < number_of_random_abilities + 4) {
            String duplicate_ability = possible_abilities.get(rand.nextInt(possible_abilities.size()));
            abilities.add(duplicate_ability);
        }
    }

    public void receiveDamage(int damage) {
        //daca chance = 0 o sa ia damage, daca chance = 1 nu va lua
        int chance = rand.nextInt(2);
        if(chance == 1) {
            System.out.println("Unlucky! Opponent defended the attack!");
            return;
        }
        if(chance == 0) {
            super.regenerate_health(currentHealth - damage);
            System.out.println("You've dealt " + damage + " damage to your opponent!");
        }
    }

    public int getDamage(){
        Random rand = new Random();
        int normal_damage = rand.nextInt(10) + 10; //normal_damage = intre 10 si 19
        System.out.println("Normal damage enemy got: " + normal_damage);
        int p = 0;
        int damage_doubled = rand.nextInt(2);
        if(damage_doubled == 1) {
            normal_damage *= 2;
            p = 1;
        }
        if(p == 1)
            System.out.println("Enemy's damage doubled! His damage now is " + normal_damage);
        if(p == 0)
            System.out.println("Enemy's damage did not double. His damage remains " + normal_damage);
        return normal_damage;
    }
}
