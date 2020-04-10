package roborally.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.GridPoint2;
import org.jetbrains.annotations.NotNull;
import roborally.game.cards.IProgramCards;
import roborally.game.cards.ProgramCards;
import roborally.game.gameboard.GameBoard;
import roborally.game.gameboard.IGameBoard;
import roborally.game.gameboard.objects.IFlag;
import roborally.game.gameboard.objects.laser.LaserRegister;
import roborally.game.gameboard.objects.robot.AIControl;
import roborally.game.gameboard.objects.robot.Robot;
import roborally.ui.ILayers;
import roborally.ui.Layers;
import roborally.ui.ProgramCardsView;
import roborally.ui.gdx.events.Events;
import roborally.utilities.AssetManagerUtil;
import roborally.utilities.SettingsUtil;

import java.util.ArrayList;

public class Game implements IGame {
	//region Game Objects
	private IGameBoard gameBoard;
	private ILayers layers;
	private ArrayList<Robot> robots;
	private ArrayList<IFlag> flags;
	private IProgramCards deckOfProgramCards;
	private LaserRegister laserRegister;
	private Robot userRobot;
	//endregion

	//private Robot winner;
	private int currentRobotID;
	private Events events;
	private GameOptions gameOptions;
	private IRound round;

	private boolean funMode;
	private int robotPlayedCounter;
	private int currentPhaseIndex;

	//private HashMap<IProgramCards.CardType, Runnable> cardTypeMethod;

	public Game(Events events) {
		currentRobotID = 0;
		deckOfProgramCards = new ProgramCards();
		this.events = events;
		this.gameOptions = new GameOptions();
	}

	@Override
	public void startUp() {
		endGame();
		this.gameBoard = new GameBoard(AssetManagerUtil.manager.getAssetFileName(AssetManagerUtil.getLoadedMap()));
		this.flags = gameBoard.findAllFlags();
		this.layers = new Layers();
		this.laserRegister = new LaserRegister(layers);
		this.robots = gameOptions.makeRobots(layers, laserRegister, flags);
		this.round = new Round(events, robots, gameBoard);
		this.userRobot = robots.get(0);

	}

	@Override
	public void funMode() {
		endGame();
		startUp();
		this.robots = gameOptions.funMode(layers, flags, laserRegister);
		this.events.setGameSpeed("fastest");
		this.round = new Round(events, robots, gameBoard);
		this.funMode = true;
		this.userRobot = robots.get(0);
	}

	@Override
	public ILayers getLayers() {
		return this.layers;
	}

	//region Robots
	@Override
	public Robot getFirstRobot() {
		if (this.currentRobotID == robots.size()) {
			this.currentRobotID = 0;
		}
		events.checkForDestroyedRobots(this.robots);
		userRobot.backToArchiveMarker();
		return userRobot;
	}

	@Override
	public ArrayList<Robot> getRobots() {
		return this.robots;
	}

	private void setRobots(ArrayList<Robot> newRobots) {
		this.robots = newRobots;
		round = new Round(events, robots, gameBoard);
	}
	//endregion

	@Override
	public void restartGame() {
		if (events.hasWaitEvent())
			return;
		System.out.println("Restarting game...");
		for (Robot robot : robots) {
			events.removeFromUI(robot);
		}
		setRobots(gameOptions.makeRobots(layers, laserRegister, flags));
		userRobot = robots.get(0);
	}

	@Override
	public GameOptions getGameOptions() {
		return this.gameOptions;
	}

	@Override
	public void manuallyFireOneLaser() {
		// This method is only for bugtesting...
		Sound sound = AssetManagerUtil.manager.get(AssetManagerUtil.SHOOT_LASER);
		sound.play((float) 0.08 * AssetManagerUtil.volume);
		userRobot.fireLaser();
		ArrayList<GridPoint2> coords = userRobot.getLaser().getCoords();
		if (!coords.isEmpty())
			events.createNewLaserEvent(userRobot.getPosition(), coords.get(coords.size() - 1));
	}

	private void removeDeadRobots() {
		ArrayList<Robot> aliveRobots = new ArrayList<>();
		for (Robot robot : getRobots()) {
			if (isNotInGraveyard(robot))
				aliveRobots.add(robot);
		}
		setRobots(aliveRobots);
		returnToMenuIfOnlyOneRobotLeft();
	}

	private void returnToMenuIfOnlyOneRobotLeft() {
		if (getRobots().size() < 2) {
			System.out.println("Entering menu");
			gameOptions.enterMenu();
		}
	}

	//region Cards
	@Override
	public ProgramCardsView dealCards() {
		if (funMode)
			removeDeadRobots();
		deckOfProgramCards.shuffleCards();
		AIControl aiControl = new AIControl(gameBoard);
		for (Robot currentRobot : getRobots()) {
			deckOfProgramCards = currentRobot.getLogic().drawCards(deckOfProgramCards);
			if (!currentRobot.equals(userRobot)) {
				aiControl.controlRobot(currentRobot.getLogic());
				currentRobot.getLogic().arrangeCardsInHand(aiControl.getOrder());
			}
		}
		return makeProgramCardsView(userRobot);
	}

	@NotNull
	private ProgramCardsView makeProgramCardsView(@NotNull Robot robot) {
		ProgramCardsView programCardsView = new ProgramCardsView();
		for (IProgramCards.Card card : robot.getLogic().getCardsInHand()) {
			programCardsView.makeCard(card);
		}
		return programCardsView;
	}
	//endregion

	@Override
	public void shuffleTheRobotsCards(int[] order) {
		userRobot.getLogic().arrangeCardsInHand(order);
		userRobot.getLogic().setHasSelectedCards(true);
	}

	@Override
	public boolean hasAllPlayersChosenCards() {
		if (userRobot != null && userRobot.getLogic().isCardsSelected()) {
			userRobot.getLogic().setHasSelectedCards(false);
			return true;
		}
		return false;
	}

	@Override
	public void endGame() {
		events.setWaitMoveEvent(false);
		if(robots == null)
			return;
		for (Robot robot : robots) {
			layers.setRobotTexture(robot.getPosition(), null);
			events.removeFromUI(robot);
		}
		robots.clear();
		gameOptions.enterMenu(true);
	}

	@Override
	public void exitGame() {
		Gdx.app.exit();
	}

	@Override
	public IRound getRound() {
		return this.round;
	}

	@Override
	public float continueGameLoop(float dt, double gameSpeed) {
		float deltaTime = dt;
		if (deltaTime >= gameSpeed) {
			getRound().getPhase().playNextRegisterCard();
			deltaTime = 0f;
			this.robotPlayedCounter++;
		}
		if (this.robotPlayedCounter == getRobots().size()) {
			getRound().getPhase().run(getLayers());
			this.currentPhaseIndex++;
			this.robotPlayedCounter = 0;
		}
		if (this.currentPhaseIndex == SettingsUtil.NUMBER_OF_PHASES) {
			this.currentPhaseIndex = 0;
			this.events.setWaitMoveEvent(false);
			getRound().run(getLayers());
		}
		return deltaTime;
	}

	private boolean isNotInGraveyard(@NotNull Robot robot) {
		return !robot.getPosition().equals(SettingsUtil.GRAVEYARD);
	}
}
