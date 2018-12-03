package ch.epfl.cs107.play.game.areagame.actor;

import java.util.List;

import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Models objects asking for interaction (i.e. can interact with some Interactable)
 * @see Interactable
 * This interface makes sense only in the "Area Context" with Actor contained into Area Cell
 */
public interface Interactor {
	 List<DiscreteCoordinates>getCurrentCells();

List<DiscreteCoordinates> getFieldOfViewsCells();
boolean wantsCellInteraction();
boolean wantsViewInteraction();
void interactWith(Interactable other);


}