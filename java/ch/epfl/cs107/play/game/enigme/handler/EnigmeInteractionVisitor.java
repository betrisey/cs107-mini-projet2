package ch.epfl.cs107.play.game.enigme.handler;

import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.EnigmeBehavior;
import ch.epfl.cs107.play.game.enigme.actor.*;

public interface EnigmeInteractionVisitor extends AreaInteractionVisitor {

    default void interactWith(EnigmeBehavior.EnigmeCell cell) { }

    default void interactWith(Door door) { }

    default void interactWith(EnigmePlayer player) { }

    default void interactWith(Collectable collectable) { }

    default void interactWith(Switchable switchable) { }

    default void interactWith(Portal portal) { }

    default void interactWith(Pushable pushable) { }
}
