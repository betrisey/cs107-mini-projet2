package ch.epfl.cs107.play.game.areagame.actor;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public interface Teleportable extends Interactable, Actor {
    Destination getDestination();
    void setDestination(Destination destination);

    Orientation getOrientation();
    void setOrientation(Orientation orientation, boolean force);

    void setPosition(DiscreteCoordinates coordinates);

    void setOwnerArea(Area ownerArea);

    default void afterTeleport() {}
}
