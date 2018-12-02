package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Apple extends Collectable {
    private Sprite appleSprite;

    /**
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity in the Area. Not null
     * @param position    (DiscreteCoordinate): Initial position of the entity in the Area. Not null
     */
    public Apple(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);

        appleSprite = new Sprite("apple.1", 1, 1, this);
    }

    @Override
    public void draw(Canvas canvas) {
        appleSprite.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    public void acceptInteraction(AreaInteractionVisitor v) {
        // fait en sorte que le gestionnaire d'interaction du personnage gère l'interation avec Apple
        ((EnigmeInteractionVisitor) v).interactWith(this) ;
    }
}
