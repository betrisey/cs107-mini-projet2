package ch.epfl.cs107.play.game.areagame.actor;


import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.List;

/**
 * Models objects receptive to interaction (i.e. Interactor can interact with them)
 * @see Interactor
 * This interface makes sense only in the "AreaGame" context with Actor contained into Area Cell
 */
public interface Interactable {
    List<DiscreteCoordinates> getCurrentCells();

    boolean takeCellSpace();

    // We have to do the distinction between takeCellSpace and needEmptySpace for the Portal
    // is doesn't take cell space but it cannot be placed on an occupied cell
    default boolean needEmptySpace() {
        return takeCellSpace();
    }

    boolean isViewInteractable();
    boolean isCellInteractable();

    void acceptInteraction(AreaInteractionVisitor v);
}
