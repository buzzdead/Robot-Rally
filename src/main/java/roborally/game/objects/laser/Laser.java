package roborally.game.objects.laser;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.GridPoint2;
import roborally.tools.AssetManagerUtil;
import roborally.tools.BooleanCalculator;
import roborally.ui.ILayers;
import roborally.ui.Layers;

import java.util.ArrayList;
import java.util.HashMap;
public class Laser {
    private HashMap<Integer, String> laserType;
    private int laserDegree;
    private ILayers layers;
    private GridPoint2 robotsOrigin;
    private ArrayList<GridPoint2> storedCoordsCoords;
    private TiledMapTileLayer.Cell storedLaserCell;
    private TiledMapTileLayer.Cell crossLaser;
    private TiledMapTileLayer.Cell horizontalLaser;
    private TiledMapTileLayer.Cell verticalLaser;
    private BooleanCalculator booleanCalculator;
    private int cannonId;
    private boolean removeLaser;

    /**
     * Constructs a laser with a map of directions the lasers are going, as well as for the direction the cannons are going.
     * @param laserDegree Horizontal or Vertical laser.
     */
    public Laser(int laserDegree) {
        layers = new Layers();
        laserType = new HashMap<>();
        laserType.put(39, "HORIZONTAL");
        laserType.put(47, "VERTICAL");
        laserType.put(40, "BOTH");
        laserType.put(46, "LeftCannon");
        laserType.put(38, "RightCannon");
        laserType.put(37, "UpCannon");
        laserType.put(45, "DownCannon");
        this.laserDegree = laserDegree;
        storedCoordsCoords = new ArrayList<>(); // Stores coordinates of laser-cells that are active.
        this.booleanCalculator = new BooleanCalculator();
        removeLaser = false;
    }

    public void clearLaser() {
        removeLaser();
        update();
        clearStoredCoordinates();
    }

    /**
     * Shoots a laser until it hits a wall or a robot. Stores the cells for clearing them after.
     * @param robotsPos The position of the robot that is shooting the laser.
     * @param direction The direction the robot is looking.
     */
    public void fireLaser(GridPoint2 robotsPos, int direction) {
        if (!getCoords().isEmpty()) {
            clearLaser();
            return;
        }
        Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.SHOOT_LASER);
        sound.play((float) 0.5);
        storedCoordsCoords.clear();
        storedLaserCell = getLaser(direction);
        int[] dir = setDirection(direction);
        int i = robotsPos.x + dir[0];
        int j = robotsPos.y + dir[1];

        // Makes sure there's not a wall blocking the laser.
        if (booleanCalculator.checkForWall(robotsPos.x, robotsPos.y, dir[0], dir[1]))
            return;
        while (i >= 0 && i < layers.getWidth() && j >= 0 && j < layers.getHeight()) {
            // Makes sure it doesnt stack laser on top of other laser cells.
            if (!layers.assertLaserNotNull(i, j) || layers.assertRobotNotNull(i, j)) {
                layers.setLaserCell(i, j, storedLaserCell);
                storedCoordsCoords.add(new GridPoint2(i, j));
                if (booleanCalculator.checkForWall(i, j, dir[0], dir[1]) || layers.assertRobotNotNull(i, j)) {
                    break;
                }
            }
            //Creates a crossing laser-cell as a combination of a vertical and horizontal laser.
            else if (storedLaserCell.getTile().getId() != layers.getLaserID(i, j)) {
                layers.setLaserCell(i, j, crossLaser);
                storedCoordsCoords.add(new GridPoint2(i, j));
            }
            i = i + dir[0];
            j = j + dir[1];
        }
    }

    /**
     * @param direction The direction the robot is facing
     * @return an array with values that determines which direction the laser is being fired.
     */
    public int[] setDirection(int direction) {
        int dx = 0;
        int dy = 0;
        if (direction == 0)
            dy = 1;
        else if (direction == 1)
            dx = -1;
        else if (direction == 2)
            dy = -1;
        else if (direction == 3)
            dx = 1;
        return new int[]{dx, dy};
    }

    /**
     * Finds the laser cannon and adds the coordinates of its projected laser.
     * @param robotsOrigin The position the robot is or was currently in.
     * @return the id of the cannon.
     */
    public int findLaser(GridPoint2 robotsOrigin) {
        int cannonId = 0;
        this.robotsOrigin = robotsOrigin;
        if (laserType.get(laserDegree).equals("HORIZONTAL")) {
            storedLaserCell = getLaser(1);
            cannonId = findHorizontal();
        }
        else if (laserType.get(laserDegree).equals("VERTICAL")) {
            storedLaserCell = getLaser(2);
            cannonId = findVertical();
        }
        storedCoordsCoords.add(robotsOrigin);
        this.cannonId = cannonId;
        return cannonId;
    }

    /**
     * Finds the cannon projecting a horizontal laser, and stores the cells of the laser.
     * @return cannonId
     */
    public int findHorizontal() {
        int i = robotsOrigin.x + 1;
        int j = robotsOrigin.x - 1;
        int k = robotsOrigin.y;
        while (i < layers.getWidth() && layers.assertLaserNotNull(i, k)) i++;
        while (j >= 0 && layers.assertLaserNotNull(j, k)) j--;
        cannonId = findCannon(i, j, k);
        if (cannonId != 0) {
            int dx;
            if(laserType.get(cannonId).equals("LeftCannon"))
                dx = -1;
            else {
                dx = 1;
                i = j;
            }
            do {
                storedCoordsCoords.add(new GridPoint2(i+=dx, k));
            } while (!booleanCalculator.checkForWall(i, k, dx, 0));
        }
        return cannonId;
    }

    /**
     * Finds the cannon projecting a vertical laser, and stores laser cells.
     * @return cannonId
     */
    public int findVertical() {
        int i = robotsOrigin.y + 1;
        int j = robotsOrigin.y - 1;
        int k = robotsOrigin.x;
        while (i < layers.getHeight() && layers.assertLaserNotNull(k, i)) i++;
        while (j >= 0 && layers.assertLaserNotNull(k, j)) j--;
        cannonId = findCannon(i, j, k);
        if (cannonId != 0) {
            int dy;
            if(laserType.get(cannonId).equals("UpCannon"))
                dy = 1;
            else {
                dy = -1;
                j = i;
            }
            do {
                storedCoordsCoords.add(new GridPoint2(k, j += dy));
            } while (!booleanCalculator.checkForWall(k, j, 0, dy));
        }
        return cannonId;
    }

    /**
     * Updates the cannon when there is activity, and continues to do so until it is registered in Lasers
     * that no robot is no longer active in the laser.
     */
    public void update() {
        for (GridPoint2 pos : storedCoordsCoords) {
            if(!identifyLaser(pos.x, pos.y))
                layers.setLaserCell(pos.x, pos.y, null);
        }
        if (removeLaser)
            return;
        for (GridPoint2 pos : storedCoordsCoords) {
            layers.setLaserCell(pos.x, pos.y, this.storedLaserCell);
            if (layers.assertRobotNotNull(pos.x, pos.y))
                break;
        }
    }

    /**
     * Calculates where the laser is coming from.
     * @param i The negative sides endpoint (down or left) which the laser might be coming from.
     * @param j The positive sides endpoint (up or right) which the laser might be coming from.
     * @param k The static x or y coordinate.
     * @return cannonId
     */

    private int findCannon(int i, int j, int k) {
        if (laserType.get(this.laserDegree).equals("VERTICAL")) {
            if (layers.assertLaserCannonNotNull(k, i - 1))
                return layers.getLaserCannonID(k, i - 1);
            if (layers.assertLaserCannonNotNull(k, j + 1))
                return layers.getLaserCannonID(k, j + 1);
        } else {
            if (layers.assertLaserCannonNotNull(i - 1, k))
                return layers.getLaserCannonID(i - 1, k);
            if (layers.assertLaserCannonNotNull(j + 1, k))
                return layers.getLaserCannonID(j + 1, k);
        }
        return 0;
    }

    // Checks if the robot is in a laser instance.
    public boolean gotPos(GridPoint2 pos) {
        return storedCoordsCoords.contains(pos);
    }

    // Clears all stored coordinates.
    public void clearStoredCoordinates() {
        storedCoordsCoords.clear();
    }

    // When robots shoot it is registered in the update method that it shall not put laser cells after clearing them.
    public void removeLaser() {
        this.removeLaser = true;
    }

    // The coordinates of the laser cells.
    public ArrayList<GridPoint2> getCoords() {
        return this.storedCoordsCoords;
    }

    // Identifies when to different laser cells intersect, creates a cross laser.
    public boolean identifyLaser(int i, int j) {
        if (layers.assertLaserNotNull(i, j)) {
            if (layers.getLaserID(i, j) == 40) {
                if (laserType.get(storedLaserCell.getTile().getId()).equals("VERTICAL"))
                    layers.setLaserCell(i, j, horizontalLaser);
                else
                    layers.setLaserCell(i, j, verticalLaser);
                return true;
            }
        }
        return false;
    }

    // At first use it creates the laser tiles for the laser class, else it returns the specific laser tile to use.
    public TiledMapTileLayer.Cell getLaser(int direction) {
        if(storedLaserCell==null) {
            horizontalLaser = new TiledMapTileLayer.Cell();
            horizontalLaser.setTile(AssetManagerUtil.getTileSets().getTile(39));

            verticalLaser = new TiledMapTileLayer.Cell();
            verticalLaser.setTile(AssetManagerUtil.getTileSets().getTile(47));

            crossLaser = new TiledMapTileLayer.Cell();
            crossLaser.setTile(AssetManagerUtil.getTileSets().getTile(40));
        }
        if (direction == 0 || direction == 2)
            return verticalLaser;
        else
            return horizontalLaser;
    }
}