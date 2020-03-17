package roborally.game;

import roborally.game.objects.gameboard.IGameBoard;
import roborally.game.objects.robot.AI;
import roborally.game.objects.robot.Robot;
import roborally.ui.ILayers;
import roborally.ui.gdx.MakeCards;
import roborally.utilities.enums.PhaseStep;
import roborally.utilities.enums.RoundStep;

public interface IGame {

    /**
     * Serves ONLY feed the keyUp method..
     *
     * @return the layers of the gameboard
     */
    ILayers getLayers();

    /**
     * Serves ONLY feed the keyUp method..
     * @deprecated serves no purpose at the moment
     */
    @Deprecated
    AI[] getAIRobots();

    /**
     * Exists only for debugging.
     * @return The first of the robots
     */
    Robot getRobots();

    void funMode();

    void startUp();

    /**
     * Fire the laser of the first robot. Only used for debugging.
     */
    void fireLaser();

    void restartGame();

    void startGame();

    void checkForDestroyedRobots();

    void startNewRound();

    boolean isRunning();

    GameOptions getGameOptions();

    RoundStep currentRoundStep();

    PhaseStep currentPhaseStep();

    void announcePowerDown();

    void dealCards();

    void programRobots();

    void revealProgramCards();

    void moveRobots();

    void moveAllConveyorBelts();

    void moveExpressConveyorBelts();

    void moveCogs();

    void fireLasers();

    void allowMovingBackupPoints();

    void registerFlagPositions();

    boolean checkIfSomeoneWon();

    Robot getWinner();

    MakeCards getCards();

    void shuffleTheRobotsCards(int[] order);

    void playNextCard();

    void endGame();

    void exitGame();
}
