package ch.epfl.cs107.play.game.enigme;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.window.Window;

public class Demo2Behavior extends AreaBehavior {
    /**
     * Default AreaBehavior Constructor
     *
     * @param window   (Window): graphic context, not null
     * @param fileName (String): name of the file containing the behavior image, not null
     */
    public Demo2Behavior(Window window, String fileName) {
        super(window, fileName);

        Demo2Cell[][] cells = new Demo2Cell[getWidth()][getHeight()];
        for (int y = 0; y < getWidth(); y++) {
            for (int x = 0; x < getHeight(); x++) {
                Demo2CellType cellType = Demo2CellType.toType(getBehaviorMap().getRGB(getHeight()-1-y, x));
                cells[y][x] = new Demo2Cell(x, y, cellType);
            }
        }
    }

    public enum Demo2CellType {
        NULL(0),
        WALL(-16777216),  // RGB code of black
        DOOR(-65536),     // RGB code of red
        WATER(-16776961), // RGB code of blue
        INDOOR_WALKABLE(-1),
        OUTDOOR_WALKABLE(-14112955);
        final int type;

        Demo2CellType(int type) {
            this.type = type;
        }

        public static Demo2CellType toType(int type) {
            if (type == WALL.type) return WALL;
            if (type == DOOR.type) return DOOR;
            if (type == WATER.type) return WATER;
            if (type == INDOOR_WALKABLE.type) return INDOOR_WALKABLE;
            if (type == OUTDOOR_WALKABLE.type) return OUTDOOR_WALKABLE;
            return NULL;
        }
    }

    public class Demo2Cell extends Cell {
        private Demo2CellType type;

        public Demo2Cell(int x, int y, Demo2CellType type) {
            super(x, y);
            this.type = type;
        }
    }
}
