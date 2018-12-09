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
    private Destination passedDoor;

    private AnimatedSprite playerSprites;

    private Portal orangePortal, bluePortal;
    private boolean placeingOrange, placingBlue;

    private final EnigmePlayerHandler handler;

    // Animation duration in frame number
    private final static int ANIMATION_DURATION = 8;

    public EnigmePlayer(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates);
        isPassingDoor = false;
        // depth correction to make sure the player is always displayed on top
        float depthCorrection = 100;
        playerSprites = new AnimatedSprite("max.new.1", 0.5f, 0.65625f, 16, 21,
                4, 0.3f, true, this, new Vector(0.25f, 0.32f), depthCorrection);

        Portal[] portals = Portal.createPortalPair();
        orangePortal = portals[0];
        bluePortal = portals[1];

        handler = new EnigmePlayerHandler();
    }

    public void enterArea(Area area, DiscreteCoordinates position) {
        area.registerActor(this);
        setOwnerArea(area);

        setCurrentPosition(position.toVector());

        area.setViewCandidate(this);

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

    public void setIsPassingDoor(Destination door) {
        this.passedDoor = door;
        isPassingDoor = true;
    }

    public Destination passedDoor() {
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
                }
            }
        }

        if (isMoving()) {
            playerSprites.update(deltaTime);
        } else {
            playerSprites.setSpriteIndex(0);
        }

        // Place the portal if the J key has been pressed or if we tried to place it during the last update
        // and it didn't succeeded
        if (getOwnerArea().getKeyboard().get(Keyboard.J).isPressed() || placeingOrange) {
            if (orangePortal.place(getOwnerArea(), getFieldOfViewCells().get(0), getOrientation().opposite())) {
                placeingOrange = false;
            } else {
                placeingOrange = true;
            }
        }
        if (getOwnerArea().getKeyboard().get(Keyboard.K).isPressed() || placingBlue) {
            if (bluePortal.place(getOwnerArea(), getFieldOfViewCells().get(0), getOrientation().opposite())) {
                placingBlue = false;
            } else {
                placingBlue = true;
            }
        }
    }

    @Override
    public void setOrientation(Orientation orientation) {
        super.setOrientation(orientation);
        playerSprites.setOrientation(getOrientation());
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

        @Override
        public void interactWith(Portal portal){
            portal.teleport(EnigmePlayer.this);
        }
    }
}
