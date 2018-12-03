package ch.epfl.cs107.play.game.areagame.handler;

import ch.epfl.cs107.play.game.enigme.EnigmeBehavior;
import ch.epfl.cs107.play.game.enigme.actor.Apple;
import ch.epfl.cs107.play.game.enigme.actor.Door;
import ch.epfl.cs107.play.game.enigme.actor.EnigmePlayer;

public interface EnigmeInteractionVisitor extends AreaInteractionVisitor {
	/**
	 * Simulate and interaction between a enigme Interactors and an enigme Apple
	 * 
	 * @param apple (Apple), not null
	 */
	default void interactWith(Apple apple) {
		// by default the interaction is empty
	}

	default void interactWith(EnigmeBehavior.EnigmeCell cell) { // by default the interaction is empty
	}

	default void interactWith(Door door) {
		// by default the interaction is empty
	}

	default void interactWith(EnigmePlayer player) {
		// by default the interaction is empty
	}
}
