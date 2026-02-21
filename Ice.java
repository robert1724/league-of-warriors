//trebuie generat aleator damage-ul si costul manei dat de abilitati

public class Ice extends Spell{

    public Ice(int random_damage_ice, int random_mana_cost_ice){
        super(random_damage_ice, random_mana_cost_ice);
    }

    @Override
    public String toString() {
        return "Ability chosen : Ice\n" + "Damage dealt: " + this.ability_damage + "\n" + "Mana cost: " + this.ability_mana_cost + "\n";
    }
}
