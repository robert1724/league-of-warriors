public abstract class Spell {
    int ability_damage;
    int ability_mana_cost;

    public Spell(int ability_damage, int ability_mana_cost) {
        this.ability_damage = ability_damage;
        this.ability_mana_cost = ability_mana_cost;
    }

    public abstract String toString();
}
