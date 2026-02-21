import java.util.Random;
import java.util.ArrayList;

public class Grid extends ArrayList<ArrayList<Cell>> {
    public static Grid instance;
    public int grid_length;
    public int grid_width;
    public Figure currentCharacter;
    public Cell currentCell;

    //constructor private
    private Grid(int grid_length, int grid_width) {}

    public static Grid getInstance(int grid_length, int grid_width) {
        if (instance == null) {
            instance = new Grid(grid_length, grid_width);
        }
        return instance;
    }

    //try catch exception in main
    //generatorul de harta
    public static ArrayList<ArrayList<Cell>> map_generator(int grid_length, int grid_width) {
        //initializez harta cu VOID-uri
        ArrayList<ArrayList<Cell>> grid = new ArrayList<>();
        for(int i = 0; i < grid_length; i++) {
            grid.add(new ArrayList<Cell>());
            for(int j = 0; j < grid_width; j++) {
                //fiecare Cell reprezinta o casuta din matrice
                grid.get(i).add(new Cell(i, j, CellEntityType.VOID, false));
            }
        }

        Random rand = new Random();

        //valori legale
        int total_cells = grid_length * grid_width;

        //asez jucatorul si portalul in grid
        RandomlyPlace_Entities(grid, grid_length, grid_width, CellEntityType.PORTAL);
        RandomlyPlace_Entities(grid, grid_length, grid_width, CellEntityType.PLAYER);

        int sanctuary_or_enemy_cells = total_cells - 2;
        int min_enemy_cells = 4;
        int min_sanctuary_cells = 2;

        //am un numar random de sanctuare si de inamici
        int random_enemy_number = rand.nextInt(sanctuary_or_enemy_cells - min_enemy_cells- min_sanctuary_cells + 1) + 4;
        int random_sanctuary_number = rand.nextInt(sanctuary_or_enemy_cells - min_sanctuary_cells - random_enemy_number + 1) + 2;

        for(int i = 0; i < random_enemy_number; i++) {
            RandomlyPlace_Entities(grid, grid_length, grid_width, CellEntityType.ENEMY);
        }

        for(int i = 0; i < random_sanctuary_number; i++) {
            RandomlyPlace_Entities(grid, grid_length, grid_width, CellEntityType.SANCTUARY);
        }

        return grid;
    }

    public static ArrayList<ArrayList<Cell>> map_generator_hardcoded(int grid_length, int grid_width) {
        //initializez harta cu VOID-uri
        ArrayList<ArrayList<Cell>> grid = new ArrayList<>();
        for(int i = 0; i < grid_length; i++) {
            grid.add(new ArrayList<Cell>());
            for(int j = 0; j < grid_width; j++) {
                //fiecare Cell reprezinta o casuta din matrice
                grid.get(i).add(new Cell(i, j, CellEntityType.VOID, false));
            }
        }

        Random rand = new Random();

        Cell cell_player = grid.get(0).get(0);
        Cell cell_portal = grid.get(4).get(4);
        Cell cell_sanctuary_1 = grid.get(0).get(3);
        Cell cell_sanctuary_2 = grid.get(1).get(3);
        Cell cell_sanctuary_3 = grid.get(2).get(0);
        Cell cell_sanctuary_4 = grid.get(4).get(3);
        Cell cell_enemy_1 = grid.get(3).get(4);

        cell_player.setType(CellEntityType.PLAYER);
        cell_player.setVisited(true);

        cell_portal.setType(CellEntityType.PORTAL);

        cell_sanctuary_1.setType(CellEntityType.SANCTUARY);
        cell_sanctuary_2.setType(CellEntityType.SANCTUARY);
        cell_sanctuary_3.setType(CellEntityType.SANCTUARY);
        cell_sanctuary_4.setType(CellEntityType.SANCTUARY);

        cell_enemy_1.setType(CellEntityType.ENEMY);
        int random_enemy_health = rand.nextInt(30) + 71;
        int random_enemy_mana = rand.nextInt(40) + 61;
        boolean random_immune_fire = rand.nextBoolean();
        boolean random_immune_ice = rand.nextBoolean();
        boolean random_immune_earth = rand.nextBoolean();
        Enemy enemy = new Enemy(random_enemy_health, random_enemy_mana, random_immune_earth, random_immune_fire, random_immune_ice);
        cell_enemy_1.setEnemy(enemy);

        return grid;
    }

    public static void RandomlyPlace_Entities(ArrayList<ArrayList<Cell>> filling_grid, int grid_length, int grid_width, CellEntityType types){
        Random rand = new Random();
        int random_length;
        int random_width;

        //pe un loc random din grid. cauta pana gaseste celula VOID.
        do {
            random_length = rand.nextInt(grid_length);
            random_width = rand.nextInt(grid_width);
        }while (filling_grid.get(random_length).get(random_width).getType() != CellEntityType.VOID);

        Cell cell = filling_grid.get(random_length).get(random_width);
        cell.setX(random_length);
        cell.setY(random_width);
        cell.setType(types);
        cell.setVisited(false);

        //toate sunt setate dinainte cu false. player trebuie setat cu true
        //doar player viziteaza. aici marchez daca ce mi se cere sa asez in harta este Player
        if (types == CellEntityType.PLAYER)
            filling_grid.get(random_length).get(random_width).setVisited(true);
        if(types == CellEntityType.ENEMY){
            int random_enemy_health = rand.nextInt(30) + 71;
            int random_enemy_mana = rand.nextInt(40) + 61;
            boolean random_immune_fire = rand.nextBoolean();
            boolean random_immune_ice = rand.nextBoolean();
            boolean random_immune_earth = rand.nextBoolean();
            Enemy enemy = new Enemy(random_enemy_health, random_enemy_mana, random_immune_earth, random_immune_fire, random_immune_ice);
            cell.setEnemy(enemy);
        }

}

    public void goNorth(ArrayList<ArrayList<Cell>> grid) {
        try {

            int player_i = currentCell.getX();
            int player_j = currentCell.getY();

            //celula unde era player o fac Void, Visited
            grid.get(player_i).get(player_j).setType(CellEntityType.VOID);
            grid.get(player_i).get(player_j).setVisited(true);

            if (player_i == 0) {
                throw new ImpossibleMove("You reached the furthest North you can go");
            }

            player_i--;
            currentCell = grid.get(player_i).get(player_j);

        }catch(ImpossibleMove e){
            System.out.println(e.getMessage());
        }
    }

    public void goSouth(ArrayList<ArrayList<Cell>> grid){
        try {
            int player_i = currentCell.getX();
            int player_j = currentCell.getY();

            int lower_bound = grid.size() - 1;

            grid.get(player_i).get(player_j).setType(CellEntityType.VOID);
            grid.get(player_i).get(player_j).setVisited(true);

            if (player_i == lower_bound) {
                throw new ImpossibleMove("You reached the furthest South you can go");
            }

            player_i++;

            currentCell = grid.get(player_i).get(player_j);
        }catch(ImpossibleMove e) {
            System.out.println(e.getMessage());
        }
    }

    public void goWest(ArrayList<ArrayList<Cell>> grid){
        try {
            int player_i = currentCell.getX();
            int player_j = currentCell.getY();

            grid.get(player_i).get(player_j).setType(CellEntityType.VOID);
            grid.get(player_i).get(player_j).setVisited(true);

            if (player_j == 0) {
                throw new ImpossibleMove("You reached the furthest West you can go");
            }

            player_j--;

            currentCell = grid.get(player_i).get(player_j);
        }catch(ImpossibleMove e){
            System.out.println(e.getMessage());
        }
    }

    public void goEast(ArrayList<ArrayList<Cell>> grid){
        try {
            int player_i = currentCell.getX();
            int player_j = currentCell.getY();

            int right_bound = grid.get(0).size() - 1;

            grid.get(player_i).get(player_j).setType(CellEntityType.VOID);
            grid.get(player_i).get(player_j).setVisited(true);

            if(player_j == right_bound) {
                throw new ImpossibleMove("You reached the furthest East you can go");
            }

            player_j++;

            currentCell = grid.get(player_i).get(player_j);
        }catch(ImpossibleMove e) {
            System.out.println(e.getMessage());
        }
    }
}
