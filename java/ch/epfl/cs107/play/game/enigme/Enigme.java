package ch.epfl.cs107.play.game.enigme;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.actor.Door;
import ch.epfl.cs107.play.game.enigme.actor.EnigmePlayer;
import ch.epfl.cs107.play.game.enigme.area.*;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;


/**
 * Enigme Game is a concept of Game derived for AreaGame. It introduces the notion of Player
 * When initializing the player is added to the current area
 */
public class Enigme extends AreaGame {

    public static final float CAMERA_SCALE_FACTOR = 22;

    /// The player is a concept of RPG games
    // TODO implements me #PROJECT
    private EnigmePlayer player;
    private EnigmeArea[] areas;

    /// Enigme implements Playable

    @Override
    public String getTitle() {
        return "Enigme";
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        super.begin(window, fileSystem);

        areas = new EnigmeArea[]{new LevelSelector(), new Level1(), new Level2(), new Level3()};
        for (EnigmeArea area : areas) {
            addArea(area);
        }

        DiscreteCoordinates startPosition = new DiscreteCoordinates(5 ,5);
        setCurrentArea(areas[0].getTitle(), false);
        player = new EnigmePlayer(areas[0], Orientation.DOWN, startPosition);
        player.enterArea(areas[0], startPosition);
        areas[0].setViewCandidate(player);

        return true;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (player.isPassingDoor()) {
            player.leaveArea(getCurrentArea());

            Door door = player.passedDoor();
            Area area = setCurrentArea(door.getDestinationName(), true);
            player.enterArea(area, door.getDestinationCoordinates());
            area.setViewCandidate(player);
        }
    }

    @Override
    public int getFrameRate() {
        return 24;
    }
}
