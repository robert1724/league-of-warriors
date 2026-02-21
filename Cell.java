public class Cell {
    public int ox;
    public int oy;
    public boolean visited;
    public CellEntityType entity_type;
    public Enemy enemy;

    public Cell(int x, int y, CellEntityType type, boolean visited) {
        this.ox = x;
        this.oy = y;
        this.entity_type = type;
        this.visited = visited;
        this.enemy = null;
    }
    public int getX() {
        return ox;
    }

    public void setX(int x) {
        this.ox = x;
    }

    public int getY() {
        return oy;
    }
    public void setY(int y) {
        this.oy = y;
    }

    public CellEntityType getType() {
        return entity_type;
    }

    public void setType(CellEntityType type) {
        this.entity_type = type;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }
}
