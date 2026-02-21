//trebuie generat aleator damage-ul si costul manei dat de abilitati

public class Normal extends Spell{

    public Normal(int normal_damage, int normal_mana_cost){
        super(normal_damage, normal_mana_cost);
    }

    @Override
    public String toString() {
        return "Ability chosen : Normal attack\n" + "Damage dealt: " + this.ability_damage + "\n" + "Mana cost: " + this.ability_mana_cost + "\n";
    }
}
