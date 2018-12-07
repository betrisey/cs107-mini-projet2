package ch.epfl.cs107.play.game.enigme.handler;

import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.EnigmeBehavior;
import ch.epfl.cs107.play.game.enigme.actor.Apple;
import ch.epfl.cs107.play.game.enigme.actor.Collectable;
import ch.epfl.cs107.play.game.enigme.actor.Door;
import ch.epfl.cs107.play.game.enigme.actor.EnigmePlayer;
import ch.epfl.cs107.play.game.enigme.actor.Lever;
import ch.epfl.cs107.play.game.enigme.actor.PressurePlate;
import ch.epfl.cs107.play.game.enigme.actor.PressureSwitch;
import ch.epfl.cs107.play.game.enigme.actor.Torch;

public interface EnigmeInteractionVisitor extends AreaInteractionVisitor {
    /**
     * Simulate and interaction between a enigme Interactors and an enigme Apple
     * @param apple (Apple), not null
     */
    default void interactWith(Apple apple){
        // by default the interaction is empty
    }

    default void interactWith(EnigmeBehavior.EnigmeCell cell){
        // by default the interaction is empty
    }

    default void interactWith(Door door){
        // by default the interaction is empty
    }

    default void interactWith(EnigmePlayer player){
        // by default the interaction is empty
    }

	default void interactWith(Collectable collectable) {
	
	}

	default void interactWith(Torch torche) {
	}

	default void interactWith(Lever lever) {
	
	}

	default void interactWith(PressureSwitch bouton) {
		
	}

	default void interactWith(PressurePlate plate) {
		
	}
}
