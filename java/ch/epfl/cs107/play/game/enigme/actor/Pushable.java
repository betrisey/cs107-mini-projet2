package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;

public interface Pushable {
    boolean push(Orientation direction);
    boolean isBeingPushed();
}
