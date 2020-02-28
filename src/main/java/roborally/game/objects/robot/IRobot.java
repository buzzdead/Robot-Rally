package roborally.game.objects.robot;

import com.badlogic.gdx.math.Vector2;
import roborally.tools.BooleanCalculator;

public interface IRobot {
    /**
     *
     * @return the models name.
     */
    String getName();

    /**
     *
     * @return a true\false calculator.
     */
    BooleanCalculator getCalc();

    /**
     *
     * @return the position for this robotCores robot.
     */
    Vector2 getPosition();

    /**
     * Shoots a laser, bound to key "F"
     */
    void fireLaser();

    /**
     * Clears the laser
     */
    void clearLaser();

    /**
     *
     * @param i
     * For setting different textureRegions for AI and Players
     */
    void setTextureRegion(int i);

    /**
     *
     * @param dx - change in x direction
     * @param dy - change in y direction
     * @return
     * Makes move inside the graphical interface for uiRobot.
     */
    boolean move(int dx, int dy);

    /**
     *
     * @return
     */
    boolean moveForward();

    /**
     *
     * @return
     */
    boolean moveBackward();

    /**
     *
     */
    void turnRight();

    /**
     *
     */
    void turnLeft();

    /**
     *
     * @param x
     * @param y
     * Sets position for this robotCores robot.
     */
    void setPos(int x, int y);

    /**
     * Updates the current cell to a WinCell
     */
    void setWinTexture();

    /**
     * Updates the current cell to a LoseCell
     */
    void setLostTexture();

    /**
     *
     * @return
     */
    int getDegrees();

    /**
     *
     * @return
     */
    boolean hasVisitedAllFlags();

    /**
     *
     * @return
     */
    int getNextFlag();

    /**
     *
     */
    void visitNextFlag();

    /**
     *
     * @param nFlags
     */
    void setNumberOfFlags(int nFlags);
}