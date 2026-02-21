import java.util.ArrayList;
import java.util.Random;

//DEXTERITY atribut principal
public class Rogue extends Figure {

    Random rand = new Random();

    public Rogue(String name, int experience, int level) {
        super(100, 100, true, false, false, name, experience, level, 1,1,1);
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

//abilitate = ice. fire. earth

    public void receiveDamage(int damage) {

        int chance = rand.nextInt(2);
        if(chance == 1) {
            System.out.println("Lucky! You defended the opp's attack!");
            return;
        }
        if(chance == 0) {
            super.regenerate_health(currentHealth - damage);
            System.out.println("You got hit with " + damage + " damage!");
        }
    }

    public int getDamage() {
        Random rand = new Random();
        int normal_damage = rand.nextInt(10) + 10; //normal_damage = intre 10 si 19
        System.out.println("Normal_damage you got: " + normal_damage);
        int p = 0;
        int damage_doubled = rand.nextInt(2);
        if(damage_doubled == 1) {
            normal_damage *= 2;
            p = 1;
        }
        if(p == 1)
            System.out.println("Your damage doubled! Normal damage now is " + normal_damage);
        if(p == 0)
            System.out.println("Your damage did not double. Normal damage remains " + normal_damage);
        int k = rand.nextInt(3) + 1;
        if(k == 1)
            normal_damage = normal_damage + strength;
        if (k == 2)
            normal_damage = normal_damage + dexterity;
        if(k == 3)
            normal_damage = normal_damage + charisma;
        System.out.println("Final normal damage, after adding level " + level + " attributes = " + normal_damage);
        return normal_damage;
    }

}