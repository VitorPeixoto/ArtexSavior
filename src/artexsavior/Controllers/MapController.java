package artexsavior.Controllers;

import artexsavior.Area;
import artexsavior.BackgroundPanel;
import artexsavior.Constants;
import artexsavior.Coordinate;
import artexsavior.enums.Direction;
import java.util.ArrayList;

/**
 *
 * @author Peixoto
 */
public class MapController implements Constants {
    private static volatile MapController singleMap = null;
    
    private final ArrayList<Area> Areas;    
    
    private final BackgroundPanel backgroundPanel;
    private final BackgroundPanel uppergroundPanel;
    
    private final SkillController skillControl;
    private final WallController wallControl;
    
    private final int maxOffsetX, maxOffsetY;
    private int offsetX, offsetY;

    private MapController() {
        Areas = new ArrayList<>();
        Areas.add(new Area(0));
        
        skillControl = SkillController.newSkillController();
        wallControl = WallController.newWallController();
        
        backgroundPanel = new BackgroundPanel(Areas.get(0).getImage('B'), 1000, 600);
        uppergroundPanel = new BackgroundPanel(Areas.get(0).getImage('U'), 1000, 600);
        
        maxOffsetX = IMAGE_WIDTH - SCREEN_WIDTH;
        maxOffsetY = IMAGE_HEIGHT - SCREEN_HEIGHT;
    }

    public static MapController newMapController() {
        if (singleMap == null) {
            singleMap = new MapController();
        }
        return singleMap;
    }

    public void nextArea(Direction passedDirection) {
        /*TODO nextArea
         switch(passedDirection) {
         case NORTH:
         Areas.add(Areas.get(Areas.size()-1).getUpperArea());
         break;
         case WEST:
         Areas.add(Areas.get(Areas.size()-1).getRightArea());
         break;
         case SOUTH:
         Areas.add(Areas.get(Areas.size()-1).getDownArea());
         break;                    
         case EAST:
         Areas.add(Areas.get(Areas.size()-1).getLeftArea());
         break;    
         }*/
    }

    public Area currentArea() {
        return Areas.get((Areas.size() - 1));
    }

    public void previousArea() {
        Areas.remove((Areas.size() - 1));
    }

    public synchronized boolean addOffset(Coordinate toAdd) {
        boolean result = false;

        if ((offsetX + toAdd.getX() <= maxOffsetX) && (offsetX + toAdd.getX() >= 0)) {
            offsetX += toAdd.getX();
            if (toAdd.getX() != 0) {
                result = true;
            }
        }

        if ((offsetY + toAdd.getY() <= maxOffsetY+45) && (offsetY + toAdd.getY() >= 0)) {
            offsetY += toAdd.getY();
            if (toAdd.getY() != 0) {
                result = true;
            }
        }

        if (result) {
            backgroundPanel.setOffsetX(offsetX);
            backgroundPanel.setOffsetY(offsetY);

            uppergroundPanel.setOffsetX(offsetX);
            uppergroundPanel.setOffsetY(offsetY);
            
            skillControl.changeByOffset(offsetX, offsetY);
        }
        return result;        
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }        

    public BackgroundPanel getBackgroundPanel() {
        return backgroundPanel;
    }

    public BackgroundPanel getUppergroundPanel() {
        return uppergroundPanel;
    }
    
    public boolean canWalk(Coordinate entityCoordinate) {
        return wallControl.canWalk(entityCoordinate);
    }
}
