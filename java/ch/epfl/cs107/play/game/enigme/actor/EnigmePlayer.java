package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Collections;
import java.util.List;

public class EnigmePlayer extends MovableAreaEntity implements Interactor {
    private boolean isPassingDoor;
    private Door passedDoor;

    private AnimatedSprite playerSprites;

    private final EnigmePlayerHandler handler;

    // Animation duration in frame number
    private final static int ANIMATION_DURATION = 8;

    public EnigmePlayer(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates);
        isPassingDoor = false;
        playerSprites = new AnimatedSprite("max.new.1", 0.5f, 0.65625f, 16, 21,
                4, 0.3f, true, this, new Vector(0.25f, 0.32f));

        handler = new EnigmePlayerHandler();
    }

    public void enterArea(Area area, DiscreteCoordinates position) {
        area.registerActor(this);
        setOwnerArea(area);

        setCurrentPosition(position.toVector());

        resetMotion();
        isPassingDoor = false;
    }

    public void leaveArea(Area area) {
        area.unregisterActor(this);
        isPassingDoor = false;
    }

    public boolean isPassingDoor() {
        return isPassingDoor;
    }

    private void setIsPassingDoor(Door door) {
        this.passedDoor = door;
        isPassingDoor = true;
    }

    public Door passedDoor() {
        return passedDoor;
    }

    @Override
    public void draw(Canvas canvas) {
        playerSprites.draw(canvas);
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
        return true;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        for (Orientation orientation : Orientation.values()) {
            boolean keyDown = getOwnerArea().getKeyboard().get(orientation.getKeyCode()).isDown();
            if (keyDown) {
                if (getOrientation() == orientation) {
                    move(ANIMATION_DURATION);
                }
                else {
                    setOrientation(orientation);
                    playerSprites.setOrientation(getOrientation());
                }
            }
        }

        if (isMoving()) {
            playerSprites.update(deltaTime);
        } else {
            playerSprites.setSpriteIndex(0);
        }
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
        return getOwnerArea().getKeyboard().get(Keyboard.L).isPressed();
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((EnigmeInteractionVisitor) v).interactWith(this);
    }

    /**
     * Specific interaction handler for an EnigmePlayer
     */
    private class EnigmePlayerHandler implements EnigmeInteractionVisitor {
        @Override
        public void interactWith(Door door) {
            // fait en sorte que la porte soit passée par l'acteur
            setIsPassingDoor(door);
        }

        @Override
        public void interactWith(Collectable collectable){
            collectable.collect();
        }

        @Override
        public void interactWith(Switchable switchable){
            switchable.switchState();
        }
    }
}
