package artexsavior.Controllers;

import artexsavior.Coordinate;
import artexsavior.Wall;
import java.util.ArrayList;

/**
 *
 * @author Peixoto
 */
public class WallController {
    private static volatile WallController singleWall = null;
    private final ArrayList<Wall> areaWalls;
    
    private WallController() {
        areaWalls = new ArrayList<>();
    }
    
    public static WallController newWallController() {
        if(singleWall == null) singleWall = new WallController();
        return singleWall;
    }
    
    public void addWall(Wall toAdd) {
        areaWalls.add(toAdd);
    }
    
    public Wall removeWall(int indexToRemove) {
        return areaWalls.remove(indexToRemove);
    }
    
    public boolean removeWall(Wall wallToRemove) {
        return areaWalls.remove(wallToRemove);
    }    
    
    public boolean canWalk(Coordinate entityCoordinate) {        
        boolean canWalk = true;
        for(int i = 0; i < areaWalls.size(); i++) {            
            if(!areaWalls.get(i).canWalk(entityCoordinate)) canWalk = false;                                
        }            
        return canWalk;
    }
}
