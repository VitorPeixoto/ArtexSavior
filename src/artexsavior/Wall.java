package artexsavior;

/**
 *
 * @author Peixoto
 */

public class Wall {
    private Coordinate init;
    private Coordinate end;
    
    public Wall() {
        init = new Coordinate(0, 0);
        end = new Coordinate(0, 0);
    }

    public Wall(Coordinate initX, Coordinate endX) {
        this.init = new Coordinate(initX.getX(), initX.getY());
        this.end = new Coordinate(endX.getX(), endX.getY());
    }

    public Wall(int initX, int initY, int endX, int endY) {
        this.init = new Coordinate(initX, initY);
        this.end = new Coordinate(endX, endY);
    }

    public void setWall(Coordinate init, Coordinate end) {
        this.init = new Coordinate(init.getX(), init.getY());
        this.end = new Coordinate(end.getX(), end.getY());
    }

    public void setWall(int initX, int initY, int endX, int endY) {
        this.init.set(initX, initY);
        this.end.set(endX, endY);
    }

    public boolean canWalk(Coordinate entityCoordinate) {
        return canWalk(entityCoordinate.getX(), entityCoordinate.getY());
    }
    
    public boolean canWalk(int x, int y) {
        return (x < init.getX() || y < init.getY() || x > end.getX() || y > end.getY());
    }
}

