//trebuie generat aleator damage-ul si costul manei dat de abilitati

public class Earth extends Spell{

    public Earth(int random_damage_earth, int random_mana_cost_earth){
        super(random_damage_earth, random_mana_cost_earth);
    }

    @Override
    public String toString() {
        return "Ability chosen : Earth\n" + "Damage dealt: " + this.ability_damage + "\n" + "Mana cost: " + this.ability_mana_cost + "\n";
    }
}
