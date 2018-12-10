package ch.epfl.cs107.play.game.enigme;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Teleportable;
import ch.epfl.cs107.play.game.enigme.actor.EnigmePlayer;
import ch.epfl.cs107.play.game.enigme.actor.Destination;
import ch.epfl.cs107.play.game.enigme.area.*;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;


/**
 * Enigme Game is a concept of Game derived for AreaGame. It introduces the notion of Player
 * When initializing the player is added to the current area
 */
public class Enigme extends AreaGame {

    public static final float CAMERA_SCALE_FACTOR = 22;

    /// The player is a concept of RPG games
    private EnigmePlayer player;

    /// Enigme implements Playable

    @Override
    public String getTitle() {
        return "Enigme";
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        super.begin(window, fileSystem);

        EnigmeArea[] areas = new EnigmeArea[]{new LevelSelector(), new Level1(), new Level2(), new Level3()};
        for (EnigmeArea area : areas) {
            addArea(area);
        }

        DiscreteCoordinates startPosition = new DiscreteCoordinates(5 ,5);
        setCurrentArea(areas[0].getTitle(), false);
        player = new EnigmePlayer(areas[0], Orientation.DOWN, startPosition);
        player.enterArea(areas[0], startPosition);

        return true;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        for (Teleportable teleportable : getCurrentArea().getTeleportables()) {
            Destination destination = teleportable.getDestination();
            if (destination != null) {
                Area destinationArea = areas.get(destination.getDestinationArea());
                if (destinationArea != null) {
                    if (teleportable instanceof EnigmePlayer) {
                        EnigmePlayer p = ((EnigmePlayer) teleportable);
                        p.leaveArea(getCurrentArea());
                        setCurrentArea(destination.getDestinationArea(), false); // forceBegin=false to resume
                        p.enterArea(destinationArea, destination.getDestinationCoordinates());
                    } else {
                        if (destination.getDestinationArea().equals(getCurrentArea().getTitle())) {
                            teleportable.setPosition(destination.getDestinationCoordinates());
                        } else {
                            getCurrentArea().unregisterActor(teleportable);

                            // To make sure the destination area has been initialized
                            String startAreaTitle = getCurrentArea().getTitle();
                            setCurrentArea(destination.getDestinationArea(), false); // forceBegin=false to resume

                            teleportable.setPosition(destination.getDestinationCoordinates());
                            destinationArea.registerActor(teleportable);
                            teleportable.setOwnerArea(destinationArea);

                            setCurrentArea(startAreaTitle, false);
                        }
                    }

                    if (destination.getDestinationOrientation() != null)
                        teleportable.setOrientation(destination.getDestinationOrientation(), true);
                }
                teleportable.setDestination(null);
            }
        }
    }

    @Override
    public int getFrameRate() {
        return 24;
    }
}
