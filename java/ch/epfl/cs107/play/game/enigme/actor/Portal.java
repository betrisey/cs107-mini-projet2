package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.actor.Teleportable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Portal extends AreaEntity {
    private Portal linkedPortal;
    private Sprite sprite;
    private boolean isPlaced;

    /**
     * Portal factory that creates two portals linked together
     * @return Portal[] the first one is orange and the second is blue
     */
    public static Portal[] createPortalPair() {
        Portal p1 = new Portal("portal.orange");
        Portal p2 = new Portal("portal.blue");

        p1.linkedPortal = p2;
        p2.linkedPortal = p1;

        return new Portal[]{p1, p2};
    }

    /**
     * This constructor is private because Portals come in pair
     * so we only want them to be created by the createPortalPair factory method
     * @param spriteName (String): name of the sprite, not null
     */
    private Portal(String spriteName) {
        // The portal hasn't been placed yet
        super(null, Orientation.DOWN, DiscreteCoordinates.ORIGIN);
        isPlaced = false;
        sprite = new Sprite(spriteName, 1, 1, this, null, Vector.ZERO, 1, -100);
    }

    @Override
    public void draw(Canvas canvas) {
        if (isPlaced) sprite.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    /**
     * A Portal can only be placed on a cell without something taking cell space
     * but it isn't taking space itself to allow the player to go through
     */
    @Override
    public boolean needEmptySpace() { return true; }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((EnigmeInteractionVisitor) v).interactWith(this);
    }

    /**
     * Teleports the player using the same mechanism as the Doors
     * @param teleportable teleportable requesting the teleportation
     */
    public void teleport(Teleportable teleportable) {
        if (linkedPortal.isPlaced) {
            //teleportable.beforeTeleport();
            teleportable.setDestination(linkedPortal.getDestinationCoordinates(teleportable));
        } else {
            System.out.println("The linked portal hasn't been placed yet");
            // TODO use dialog
        }
    }

    /**
     * Place a Portal
     * @param area Area where it will be placed
     * @param position Position of the portal
     * @param orientation Direction in which it will push the teleported player out
     * @return false if we have to retry to place it during the next update
     */
    public boolean place(Area area, DiscreteCoordinates position, Orientation orientation) {
        if (!area.getAreaBehavior().canEnter(this, Collections.singletonList(position)))
            return false;

        // If the portal has already been placed, it must be removed before it can be placed somewhere else
        // the cells aren't property left and it may cause errors
        if (isPlaced) {
            // In that case we have to wait for next update
            getOwnerArea().unregisterActor(this);
            isPlaced = false;
            return false;
        } else {
            setOwnerArea(area);
            setOrientation(orientation);
            setCurrentPosition(position.toVector());
            area.registerActor(this);

            isPlaced = true;

            return true;
        }
    }

    /**
     * Destination coordinates to push the player out of the portal
     * @return where the player should be teleported
     */
    private PortalDestination getDestinationCoordinates(Teleportable player) {
        // We first check if the cell in the desired orientation is available
        // otherwise we check for the other surrounding cells
        Orientation destinationOrientation = getOrientation();
        for (int i = 0; i < Orientation.values().length; i++) {
            DiscreteCoordinates destination = getCurrentMainCellCoordinates().jump(destinationOrientation.toVector());
            player.setOrientation(destinationOrientation, true);
            if (getOwnerArea().getAreaBehavior().canEnter(player, Collections.singletonList(destination))) {
                return new PortalDestination(getOwnerArea().getTitle(), destination, destinationOrientation);
            }

            destinationOrientation = destinationOrientation.hisRight();
        }

        // If no available cell is found, return the portal cell and the player will be teleported back
        return new PortalDestination(getOwnerArea().getTitle(), getCurrentMainCellCoordinates(), null);
    }

    /**
     * Class containing information on the destination to be passed to the player
     * in the same was as the Door
     */
    private class PortalDestination implements Destination {
        private String destinationArea;
        private DiscreteCoordinates destinationCoordinates;
        private Orientation destinationOrientation;

        public PortalDestination(String destinationArea, DiscreteCoordinates destinationCoordinates,
                                 Orientation destinationOrientation) {
            this.destinationArea = destinationArea;
            this.destinationCoordinates = destinationCoordinates;
            this.destinationOrientation = destinationOrientation;
        }

        @Override
        public String getDestinationArea() {
            return destinationArea;
        }

        @Override
        public DiscreteCoordinates getDestinationCoordinates() {
            return destinationCoordinates;
        }

        @Override
        public Orientation getDestinationOrientation() {
            return destinationOrientation;
        }
    }
}
