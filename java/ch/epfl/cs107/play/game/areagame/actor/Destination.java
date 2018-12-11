package ch.epfl.cs107.play.game.areagame.actor;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public interface Destination {
    String getDestinationArea();

    DiscreteCoordinates getDestinationCoordinates();

    /**
     * Orientation of the character after the teleportation
     * @return (Orientation): can be null
     */
    default Orientation getDestinationOrientation() {
        return null;
    }
}
