package roborally.gameview.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.jetbrains.annotations.NotNull;
import roborally.events.Events;
import roborally.game.IGame;
import roborally.game.robot.Robot;
import roborally.utilities.SettingsUtil;
import roborally.utilities.enums.UIElement;

import java.util.ArrayList;

import static roborally.utilities.enums.UIElement.*;

public class UIElements {
    private final static float UI_ELEMENT_SCALE = 2.5f;
    private ArrayList<Image> reboots;
    private ArrayList<Image> damageTokens;

    private ImageButton powerDownButton;
    private boolean hasPowerDownBeenActivated;
    private boolean isPowerDownSetForNextRound;

    private Label.LabelStyle messageStyle;
    private Label messageLabel;

    private Stage stage;
    private ArrayList<Image> flags;
    private ImageButton restartButton;
    private ImageButton exitButton;

    public UIElements() {
        this.reboots = new ArrayList<>();
        this.damageTokens = new ArrayList<>();
        this.flags = new ArrayList<>();
        this.hasPowerDownBeenActivated = false;
    }

    private Image getAndSetUIElement(UIElement uiElement) {
        Image rebootType = new Image(uiElement.getTexture());
        rebootType.setPosition(0, 150);
        rebootType.setSize(rebootType.getPrefWidth() / UI_ELEMENT_SCALE, rebootType.getPrefHeight() / UI_ELEMENT_SCALE);
        return rebootType;
    }

    private void setReboots(int availableReboots) {
        for (int i = 0; i < availableReboots; i++) {
            this.reboots.add(getAndSetUIElement(REBOOT_ACTIVE));
        }

        if (availableReboots < (SettingsUtil.ROBOT_MAX_REBOOTS - 1)) {
            for (int i = 0; i < ((SettingsUtil.ROBOT_MAX_REBOOTS - 1) - availableReboots); i++) {
                this.reboots.add(getAndSetUIElement(REBOOT_INACTIVE));
            }
        }
    }

    public ArrayList<Image> getReboots() {
        return reboots;
    }

    private void clearReboots() {
        this.reboots = new ArrayList<>();
    }

    public void updateReboots(Robot robot) {
        clearReboots();

        setReboots(robot.getLogic().getReboots() - 1);

        float xShift = (stage.getWidth() - SettingsUtil.MAP_WIDTH) / 2f;
        float rebootsListFixedPosX = xShift - getReboots().get(0).getWidth();

        for (Image reboot : getReboots()) {
            reboot.setX(rebootsListFixedPosX += reboot.getWidth());
        }
    }

    private void setDamageTokens(int availableHealth) {
        /*
            TODO: Should be displayed like this, not sure how yet.
            0       : red
            1 - 5   : card_green
            6 - 9   : green
         */
        for (int i = 0; i < availableHealth; i++) {
            this.damageTokens.add(getAndSetUIElement(DAMAGE_TOKEN_GREEN));
        }

        if (availableHealth < SettingsUtil.ROBOT_MAX_HEALTH) {
            for (int i = 0; i < (SettingsUtil.ROBOT_MAX_HEALTH - availableHealth); i++) {
                this.damageTokens.add(getAndSetUIElement(DAMAGE_TOKEN_RED));
            }
        }
    }

    public ArrayList<Image> getDamageTokens() {
        return damageTokens;
    }

    private void clearDamageTokens() {
        this.damageTokens = new ArrayList<>();
    }

    public void updateDamageTokens(Robot robot) {
        clearDamageTokens();
        // UNCOMMENT for debug
        //System.out.println(robot.getName() + "'s health is before updating damage tokens: " + robot.getLogic().getHealth());

        setDamageTokens(Math.max(robot.getLogic().getHealth(), 0));

        float damageTokensWidth = getDamageTokens().size() * (DAMAGE_TOKEN_GREEN.getTexture().getWidth() / UI_ELEMENT_SCALE);
        float damageTokenListFixedPosX = (stage.getWidth() / 2f) - (damageTokensWidth / 2f);

        int index = 0;
        for (Image damageToken : getDamageTokens()) {
            if (index == 0) {
                damageToken.setX(damageTokenListFixedPosX -= damageToken.getWidth());
            }
            damageToken.setX(damageTokenListFixedPosX += damageToken.getWidth());
            index++;
        }
    }

    /**
     * For debugging
     *
     * @param robot The user controlled robot
     */
    public void update(Robot robot) {
        updateReboots(robot);
        updateDamageTokens(robot);
        updateFlags(robot);
    }

    public void setPowerDownButton(@NotNull UIElement powerDownState) {
        powerDownButton = new ImageButton(new TextureRegionDrawable(powerDownState.getTexture()), new TextureRegionDrawable((POWERING_DOWN.getTexture())), new TextureRegionDrawable((POWERING_DOWN.getTexture())));

        float xShift = (stage.getWidth() + SettingsUtil.MAP_WIDTH) / 2f;
        float powerDownButtonFixedPosX = xShift - powerDownButton.getWidth();

        powerDownButton.setPosition(powerDownButtonFixedPosX, 130);

        powerDownButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Activated power down");
                powerDownButton.setChecked(true);
                isPowerDownSetForNextRound = true;
                hasPowerDownBeenActivated = true;
            }
        });
    }

    public ImageButton getPowerDownButton() {
        return powerDownButton;
    }

    // For checking if the button has been pressed
    public boolean hasPowerDownBeenActivated() {
        return hasPowerDownBeenActivated;
    }

    public void hasPowerDownBeenActivated(boolean state) {
        this.hasPowerDownBeenActivated = state;
    }

    public boolean isPowerDownSetForNextRound() {
        return isPowerDownSetForNextRound;
    }

    public void setPowerDownForNextRound(boolean state) {
        this.isPowerDownSetForNextRound = state;
    }

    public void setMessageLabel(String message) {
        this.messageStyle = new Label.LabelStyle();
        this.messageStyle.fontColor = Color.YELLOW;
        this.messageStyle.font = new BitmapFont();
        this.messageLabel = new Label(message, messageStyle);
        this.messageLabel.setFontScale(3);
        float x = (stage.getWidth() / 2f) - (messageLabel.getPrefWidth() / 2f);
        float y = (((stage.getHeight() + SettingsUtil.MAP_HEIGHT) / 2f) + (messageLabel.getPrefHeight() / 2f));
        this.messageLabel.setPosition(x, y);
    }

    public Label getMessageLabel() {
        return messageLabel;
    }

    public void setFlags(int collectedFlags) {
        for (int i = 0; i < collectedFlags; i++) {
            this.flags.add(getAndSetUIElement(FLAG_WHITE));
        }

        float flagsWidth = getFlags().size() * (FLAG_WHITE.getTexture().getWidth() / UI_ELEMENT_SCALE);
        float flagsListFixedPosX = (stage.getWidth() / 3f) - (flagsWidth / 2f);

        int index = 0;
        for (Image flag : getFlags()) {
            flag.setY((stage.getHeight()) - (SettingsUtil.MAP_HEIGHT / 4f) - (flag.getHeight() / 2f));
            if (index == 0) {
                flag.setX(flagsListFixedPosX -= flag.getWidth());
            }
            flag.setX(flagsListFixedPosX += flag.getWidth());
            index++;
        }
    }

    public void updateFlags(Robot robot) {
        clearFlags();
        int collectedFlags = 0;

        for (boolean flag : robot.getLogic().getVisitedFlags()) {
            if (flag) {
                collectedFlags++;
            }
        }

        setFlags(collectedFlags);
    }

    private void clearFlags() {
        this.flags = new ArrayList<>();
    }

    public ArrayList<Image> getFlags() {
        return flags;
    }

    public void setRestartButton(IGame game) {
        this.restartButton = new ImageButton(new TextureRegionDrawable(RESTART_BUTTON.getTexture()), new TextureRegionDrawable((RESTART_BUTTON_PRESSED.getTexture())), new TextureRegionDrawable((RESTART_BUTTON_PRESSED.getTexture())));

        this.restartButton.setY(getExitButton().getY());


        this.restartButton.setX(getExitButton().getX() - getRestartButton().getWidth());

        this.restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Should restart game or something!");
                game.restartGame();
                clearAll();
            }
        });
    }

    public ImageButton getRestartButton() {
        return restartButton;
    }

    public void setExitButton(IGame game, Events events) {
        this.exitButton = new ImageButton(new TextureRegionDrawable(EXIT_BUTTON.getTexture()));
        float y = (((stage.getHeight() + SettingsUtil.MAP_HEIGHT) / 2f) + (exitButton.getHeight() / 2f));
        this.exitButton.setY(y);

        float xShift = (stage.getWidth() + SettingsUtil.MAP_WIDTH) / 2f;
        float quitButtonFixedX = xShift - exitButton.getWidth();
        this.exitButton.setX(quitButtonFixedX);

        this.exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.endGame();
                events.setWonGame(true);
            }
        });
    }

    public ImageButton getExitButton() {
        return exitButton;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void clearAll() {
        clearDamageTokens();
        clearReboots();
        clearFlags();
        getMessageLabel().setText("");
        getExitButton().clear();
        getRestartButton().clear();
    }
}
