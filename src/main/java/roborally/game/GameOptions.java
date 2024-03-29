package roborally.game;

import com.badlogic.gdx.math.GridPoint2;
import roborally.game.gameboard.objects.flag.IFlag;
import roborally.game.gameboard.objects.laser.LaserRegister;
import roborally.game.robot.Robot;
import roborally.gameview.layout.ILayers;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.enums.LayerName;

import java.util.ArrayList;

public class GameOptions {
    private boolean inMenu;

    public GameOptions() {
        this.inMenu = false;
    }

    public void enterMenu() {
        this.inMenu = !this.inMenu;
    }

    public void enterMenu(boolean value) {
        this.inMenu = value;
    }

    public boolean inMenu() {
        return inMenu;
    }

    public ArrayList<Robot> makeRobots(ILayers layers, LaserRegister laserRegister, ArrayList<IFlag> flags) {
        AssetManagerUtil.makeRobotNames();
        ArrayList<Robot> robots = new ArrayList<>();
        int cell = 0;
        for (int i = 0; i < layers.getWidth(); i++) {
            for (int j = 0; j < layers.getHeight(); j++) {
                if (layers.layerNotNull(LayerName.START_POSITIONS, new GridPoint2(i, j))) {
                    Robot robot = new Robot(new GridPoint2(i, j), cell % 8, laserRegister);
                    robot.getLogic().setNumberOfFlags(flags.size());
                    robots.add(robot);
                    cell++;
                }
            }
        }
        AssetManagerUtil.setRobots(robots);
        return robots;
    }
}
