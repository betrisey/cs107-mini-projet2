package ch.epfl.cs107.play.game.areagame;

import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Image;
import ch.epfl.cs107.play.window.Window;

import java.util.Collections;
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
        cells = new Cell[width][height];
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

    public void cellInteractionOf(Interactor interactor) {
        for (DiscreteCoordinates coord : interactor.getCurrentCells()) {
            cells[coord.x][coord.y].cellInteractionOf(interactor);
        }
    }

    public void viewInteractionOf(Interactor interactor) {
        for (DiscreteCoordinates coord : interactor.getFieldOfViewCells()) {
            cells[coord.x][coord.y].viewInteractionOf(interactor);
        }
    }

    public abstract class Cell implements Interactable {
    	protected final DiscreteCoordinates coordinates;
        protected final Set<Interactable> interactables;

        public Cell(int x, int y) {
            this.coordinates = new DiscreteCoordinates(x, y);
            this.interactables = new HashSet<>();
        }

        @Override
        public List<DiscreteCoordinates> getCurrentCells() {
            return Collections.singletonList(coordinates);
        }

        private void enter(Interactable entity) {
            if (canEnter(entity)) {
                System.out.println("enter " + entity + " " + coordinates);
                interactables.add(entity);
            }
        }

        private void leave(Interactable entity) {
            if (canLeave(entity)) {
                System.out.println("leave " + entity + " " + coordinates);
                interactables.remove(entity);
            }
        }

        private void cellInteractionOf(Interactor interactor) {
            for (Interactable interactable : interactables){
                if (interactable.isCellInteractable())
                    interactor.interactWith(interactable);
            }
        }

        private void viewInteractionOf(Interactor interactor) {
            for (Interactable interactable : interactables){
                if (interactable.isViewInteractable())
                    interactor.interactWith(interactable);
            }
        }

        protected abstract boolean canEnter(Interactable entity);

        protected abstract boolean canLeave(Interactable entity);
    }
}
