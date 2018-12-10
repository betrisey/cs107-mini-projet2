package ch.epfl.cs107.play.game.areagame.actor;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.enigme.actor.Destination;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public interface Teleportable extends Interactable, Actor {
    Destination getDestination();
    void setDestination(Destination destination);
    void setOrientation(Orientation orientation, boolean force);
    void setPosition(DiscreteCoordinates coordinates);
    Orientation getOrientation();
    void setOwnerArea(Area ownerArea);
    default void afterTeleport() {}
}
