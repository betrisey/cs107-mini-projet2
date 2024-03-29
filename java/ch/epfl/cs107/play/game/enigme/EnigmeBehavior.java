package ch.epfl.cs107.play.game.enigme;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.actor.EnigmePlayer;
import ch.epfl.cs107.play.game.enigme.actor.Portal;
import ch.epfl.cs107.play.game.enigme.actor.Pushable;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class EnigmeBehavior extends AreaBehavior {

    /**
     * @param window   (Window): graphic context, not null
     * @param fileName (String): name of the file containing the behavior image, not null
     */
    public EnigmeBehavior(Window window, String fileName) {
        super(window, fileName);

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                EnigmeCellType cellType = EnigmeCellType.toType(getBehaviorMap().getRGB(getHeight()-1-y, x));
                cells[x][y] = new EnigmeCell(x, y, cellType);
            }
        }
    }

    public EnigmeCellType getCellType(DiscreteCoordinates coordinates) {
        return ((EnigmeCell)cells[coordinates.x][coordinates.y]).getType();
    }

    public enum EnigmeCellType {
        NULL(0),
        WALL(-16777216),  // RGB code of black
        DOOR(-65536),     // RGB code of red
        WATER(-16776961), // RGB code of blue
        INDOOR_WALKABLE(-1),
        OUTDOOR_WALKABLE(-14112955);
        final int type;

        EnigmeCellType(int type) {
            this.type = type;
        }

        public static EnigmeCellType toType(int type) {
            for (EnigmeCellType cellType : values()) {
                if (type == cellType.type) {
                    return cellType;
                }
            }
            return NULL;
        }
    }

    public class EnigmeCell extends Cell {
        private EnigmeCellType type;

        private EnigmeCell(int x, int y, EnigmeCellType type) {
            super(x, y);
            this.type = type;
        }

        @Override
        protected boolean canEnter(Interactable entity) {
        	if (entity.takeCellSpace() || entity.needEmptySpace()) {
        		for (Interactable interactable : interactables) {
        		    // If the player tries to walk on a Pushable and it's not already being pushed, we try to push it
                    // and only block the player if the push didn't succeeded (e.g. the rock cannot move because of a wall)
                    if (entity instanceof EnigmePlayer && interactable instanceof Pushable) {
                        if (!((Pushable)interactable).isBeingPushed() &&
                                !((Pushable)interactable).push(((EnigmePlayer)entity).getOrientation()))
                            return false;
                    } else if (interactable.takeCellSpace()) {
						return false;
					}
				}
        	}

            return !(type == EnigmeCellType.NULL || type == EnigmeCellType.WALL);
        }

        @Override
        public boolean isCellInteractable() {
            return true;
        }

        @Override
        public boolean isViewInteractable() {
            return false;
        }

        @Override
        protected boolean canLeave(Interactable entity) {
            // Player hasn't been teleported yet so we don't want it to leave the cell before the teleportaion triggers
            if (entity instanceof EnigmePlayer && ((EnigmePlayer)entity).getDestination() == null && // the player doesn't have a destination yet
                    interactables.stream().anyMatch(x -> x instanceof Portal && ((Portal) x).linkedPortalPlaced())) // there is a portal which is linked on the cell
                return false;
            return true;
        }

        @Override
        public boolean takeCellSpace() {
            return false;
        }

        public EnigmeCellType getType() {
            return type;
        }

        @Override
        public void acceptInteraction(AreaInteractionVisitor v) {
            ((EnigmeInteractionVisitor)v).interactWith(this);
        }
    }
}

