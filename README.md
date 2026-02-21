# league-of-warriors
A text-based adventure RPG game built in Java, featuring turn-based combat, character progression, and procedurally generated maps.

Overview
  - The game takes place on a randomly generated n×m grid where the player navigates through cells containing enemies, sanctuaries, portals, and empty desert tiles. The player logs in with their credentials, picks a character, and explores the map trying to find the portal to advance to the next level.
  - Each character has unique immunities and attributes that influence combat. As the player defeats enemies and completes levels, they gain experience points and level up, improving their stats over time.

Authentication
  - The game reads account data from a JSON file. The player enters their email and password, then selects one of three characters tied to their account: "Warrior", "Mage", or "Rogue"
  - To start the game use an account created already in the JSON file, or add your own!

Map
  - The grid is generated randomly each level with at least: 4 enemies, 2 sanctuaries, 1 portal and 1 player cell
  - Cells are marked "N" until visited. Once explored, they show as "V". The player's position is marked with "P"

Movement
  - The player moves using "W" (north), "A" (west), "S" (south), "D" (east). Attempting to move outside the grid throws an "ImpossibleMove" exception with a descriptive message.

Combat
  - Fights are turn-based. Each turn the player can choose: "Normal attack" — free, costs no mana or "Spell attack" (Fire / Ice / Earth) — deals extra damage but costs mana, consumed after use
  - After the player attacks, the enemy picks a random ability from its list
  - Both sides have a 50% chance to dodge incoming damage. Damage also has a 50% chance to double depending on attributes.
  - If the enemy is immune to the chosen element, the spell has no effect but mana is still spent.
  - After winning, the player's HP doubles (capped at max) and mana is fully restored. Abilities are regenerated for the next fight.

Sanctuaries
  - Landing on a sanctuary restores a random amount of HP and mana.

Portal
  - Finding the portal completes the level, and a new map is generated with random dimensions.

Death
  - If the player's HP reaches 0, they are sent back to the login screen to pick a new character. The level resets.

Spells
  - Normal deals 10–19 damage costing 0 mana
  - Fire deals 31–40 damage costing 10–15 mana
  - Ice deals 21–30 damage costing 5–10 mana
  - Earth deals 26–35 damage costing 8–12 mana
  - Spells are randomly generated at the start of each fight (3–6 total, at least one of each type). After a spell is used, it's removed from the list.

How to Run
  - javac -cp ".:json-simple-1.1.jar" *.java
  - java -cp ".:json-simple-1.1.jar" Test
