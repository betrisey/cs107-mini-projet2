package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class PushableRock extends MovableAreaEntity implements Pushable, Interactor, Teleportable {
    private Sprite rockSprite;
    private PushableRockHandler handler;

    private Destination destination;

    // Animation duration in frame number
    private final static int ANIMATION_DURATION = 8;

    public PushableRock(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);

        float depthCorrection = 50;
        this.rockSprite = new Sprite("rock.3", 1, 1, this, null, Vector.ZERO, 1, depthCorrection);
        handler = new PushableRockHandler();
    }

    @Override
    public boolean push(Orientation direction) {
        if (destination == null) {
            setOrientation(direction, true);
            return move(ANIMATION_DURATION);
        } else {
            return false;
        }
    }

    @Override
    public boolean isBeingPushed() {
        return super.isMoving() && !getTargetMainCellCoordinates().equals(getCurrentMainCellCoordinates());
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
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
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

    @Override
    public Destination getDestination() {
        return destination;
    }

    @Override
    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    @Override
    public void setPosition(DiscreteCoordinates coordinates) {
        resetMotion();
        getOwnerArea().leaveAreaCells(this, getCurrentCells());
        super.setCurrentPosition(coordinates.toVector());
        getOwnerArea().enterAreaCells(this, getCurrentCells());
    }

    @Override
    public Orientation getOrientation() {
        return super.getOrientation();
    }

    /**
     * Specific interaction handler for an PushableRock
     */
    private class PushableRockHandler implements EnigmeInteractionVisitor {
        @Override
        public void interactWith(Door door) {
            setDestination(door);
        }

        @Override
        public void interactWith(Switchable switchable) {
            switchable.switchState();
        }

        @Override
        public void interactWith(Portal portal){
            portal.teleport(PushableRock.this);
        }
    }
}
