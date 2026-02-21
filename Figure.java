import java.util.ArrayList;

public abstract class Figure extends Entity {
    public String name; //numele personajului nu tipul
    public int experience;
    public int level;

    //atribute
    public int strength;
    public int charisma;
    public int dexterity;

    //strength, charisma, dexterity influenteaza damage-ul primit si oferit de personaj

    public Figure(int maxHealth, int maxMana, boolean immune_to_earth, boolean immune_to_fire, boolean immune_to_ice, String name, int experience, int level, int strength, int charisma, int dexterity) {
        super(maxHealth, maxMana, immune_to_earth, immune_to_fire, immune_to_ice);
        this.name = name;
        this.experience = experience;
        this.level = level;
        this.strength = strength;
        this.charisma = charisma;
        this.dexterity = dexterity;
    }

    public void check_level_up(Figure player) {
        //daca ajungi la experienta 50 primesti level up
        if(player.experience > 50) {
            while (player.experience > 50) {
                player.experience -= 50;
                levelUp();
            }
        }
    }

    public void levelUp() {
        this.level++;
        this.strength += 1;
        this.charisma += 2;
        this.dexterity += 3;
        System.out.println("Player is now level " + this.level);
        System.out.println("You now possess these statistics:\nStrength = " + this.strength + "\nCharisma = " + this.charisma + "\nDexterity = " + this.dexterity);
    }

}
