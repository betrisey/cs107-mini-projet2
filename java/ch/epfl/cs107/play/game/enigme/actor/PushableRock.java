package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class PushableRock extends MovableAreaEntity implements Pushable, Interactor {
    private Sprite rockSprite;
    private PushableRockHandler handler;

    // Animation duration in frame number
    private final static int ANIMATION_DURATION = 8;

    public PushableRock(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);

        this.rockSprite = new Sprite("rock.3", 1, 1, this);

        handler = new PushableRockHandler();
    }

    @Override
    public void push(Orientation direction) {
        setOrientation(direction);
        move(ANIMATION_DURATION);
    }

    @Override
    public void draw(Canvas canvas) {
        rockSprite.draw(canvas);
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

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((EnigmeInteractionVisitor) v).interactWith((Pushable) this);
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.emptyList();
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);
    }

    /**
     * Specific interaction handler for an EnigmePlayer
     */
    private class PushableRockHandler implements EnigmeInteractionVisitor {
        /*@Override
        public void interactWith(Door door) {
            setIsPassingDoor(door);
        }*/

        @Override
        public void interactWith(Switchable switchable) {
            switchable.switchState();
        }

        /*@Override
        public void interactWith(Portal portal){
            portal.teleport(EnigmePlayer.this);
        }*/
    }
}
