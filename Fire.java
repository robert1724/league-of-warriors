//trebuie generat aleator damage-ul si costul manei dat de abilitati

public class Fire extends Spell{

    public Fire(int random_damage_fire, int random_mana_cost_fire){
        super(random_damage_fire, random_mana_cost_fire);
    }

    @Override
    public String toString() {
        return "Ability chosen : Fire\n" + "Damage dealt: " + this.ability_damage + "\n" + "Mana cost: " + this.ability_mana_cost + "\n";
    }
}
