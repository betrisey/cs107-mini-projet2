package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Bomb extends AreaEntity implements Interactor {
    private boolean hasExploded;
    private Sprite sprite;
    private final int scope;
    private BombHandler handler;

    public Bomb(Area area, Orientation orientation, DiscreteCoordinates position, int scope) {
        super(area, orientation, position);
        hasExploded = false;
        sprite = new Sprite("bomb", 1, 1, this);
        this.scope = scope;
        this.handler = new BombHandler();
    }

    public void explode() {
        hasExploded = true;
    }

    public boolean hasExploded() {
        return hasExploded;
    }

    @Override
    public void draw(Canvas canvas) {
        if (!hasExploded)
            sprite.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        // TODO: Return surrounding cells (using scope)
        /*for (int i = 0; i <  ; i++) {
            
        }*/
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        return false;
    }

    @Override
    public boolean wantsViewInteraction() {
        return hasExploded;
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);
    }

    @Override
    public boolean takeCellSpace() {
        return !hasExploded;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((EnigmeInteractionVisitor) v).interactWith(this);
    }

    @Override
    public void update(float deltaTime) {
        if (hasExploded) {
            getOwnerArea().unregisterActor(this);
        }
    }

    /**
     * Specific interaction handler for an PushableRock
     */
    private class BombHandler implements EnigmeInteractionVisitor {
        @Override
        public void interactWith(BreakableWall wall){
            wall.breakIt();
        }
    }
}
