package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Apple extends Collectable {

    /**
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity in the Area. Not null
     * @param position    (DiscreteCoordinate): Initial position of the entity in the Area. Not null
     */
    public Apple(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position, "apple.1");
    }

    public void acceptInteraction(AreaInteractionVisitor v) {
        // fait en sorte que le gestionnaire d'interaction du personnage gère l'interation avec Apple
        ((EnigmeInteractionVisitor) v).interactWith(this);
    }
}
