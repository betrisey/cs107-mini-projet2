package ch.epfl.cs107.play.game.enigme;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class EnigmeBehavior extends AreaBehavior {

	public EnigmeBehavior(Window window, String fileName) {
        super(window, fileName);

        for (int x = 0; x < getHeight(); x++) {
            for (int y = 0; y < getWidth(); y++) {
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
            return true;
        }

        @Override
        public boolean takeCellSpace() {
            return false; // TODO
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
