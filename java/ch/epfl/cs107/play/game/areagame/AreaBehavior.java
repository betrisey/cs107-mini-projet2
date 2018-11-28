package ch.epfl.cs107.play.game.areagame;

import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Image;
import ch.epfl.cs107.play.window.Window;

/**
 * AreaBehavior manages a map of Cells.
 */
public abstract class AreaBehavior
{
    /// The behavior is an Image of size height x width
    private final Image behaviorMap;
    private final int width, height;

    /// We will convert the image into an array of cells
    private final Cell[][] cells ;

    /**
     * Default AreaBehavior Constructor
     * @param window (Window): graphic context, not null
     * @param fileName (String): name of the file containing the behavior image, not null
     */
    public AreaBehavior(Window window, String fileName){
        behaviorMap = window.getImage(ResourcePath.getBehaviors(fileName), null, false);
        width = behaviorMap.getWidth();
        height = behaviorMap.getHeight();
        cells = new Cell[width][height]; // TODO: width lignes et height colonnes ? Inverse
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getBehaviorMap() {
        return behaviorMap;
    }

    public abstract class Cell {
        private DiscreteCoordinates coordinates;

        public Cell(int x, int y) {
            this.coordinates = new DiscreteCoordinates(x, y);
        }
    }
}
