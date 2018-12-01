package ch.epfl.cs107.play.game.areagame;

import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Image;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * AreaBehavior manages a map of Cells.
 */
public abstract class AreaBehavior
{
    /// The behavior is an Image of size height x width
    private final Image behaviorMap;
    private final int width, height;

    /// We will convert the image into an array of cells
    protected final Cell[][] cells; // x, y

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

    public boolean canLeave(Interactable entity, List<DiscreteCoordinates> coordinates) {
        for (DiscreteCoordinates coord : coordinates) {
            if (!cells[coord.x][coord.y].canLeave(entity)) {
                return false;
            }
        }
        return true;
    }

    public boolean canEnter(Interactable entity, List<DiscreteCoordinates> coordinates) {
        for (DiscreteCoordinates coord : coordinates) {
            if (coord.x < 0 || coord.x >= getWidth() || coord.y < 0 || coord.y >= getHeight()
                    || !cells[coord.x][coord.y].canEnter(entity)) {
                return false;
            }
        }
        return true;
    }

    protected void leave(Interactable entity, List<DiscreteCoordinates> coordinates) {
        for (DiscreteCoordinates coord : coordinates) {
            cells[coord.x][coord.y].leave(entity);
        }
    }

    protected void enter(Interactable entity, List<DiscreteCoordinates> coordinates) {
        for (DiscreteCoordinates coord : coordinates) {
            cells[coord.x][coord.y].enter(entity);
        }
    }

    public abstract class Cell implements Interactable {
        private DiscreteCoordinates coordinates;
        private Set<Interactable> content;

        public Cell(int x, int y) {
            this.coordinates = new DiscreteCoordinates(x, y);
            this.content = new HashSet<>();
        }

        @Override
        public List<DiscreteCoordinates> getCurrentCells() {
            List<DiscreteCoordinates> cells = new ArrayList<>();
            cells.add(coordinates);
            return cells;
        }

        private void enter(Interactable entity) {
            if (canEnter(entity)) {
                content.add(entity);
            }
        }

        private void leave(Interactable entity) {
            if (canLeave(entity)) {
                content.remove(entity);
            }
        }

        protected abstract boolean canEnter(Interactable entity);

        protected abstract boolean canLeave(Interactable entity);
    }
}
