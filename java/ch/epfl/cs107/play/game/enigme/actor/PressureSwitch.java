package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class PressureSwitch extends Switchable {
    private Interactor currentInteractor = null;

    public PressureSwitch(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position, "GroundLightOn", "GroundLightOff", false);
    }

    @Override
    public boolean takeCellSpace() {
        return false;
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
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (currentInteractor != null) {
            boolean interactorHasLeft = true;
            for (DiscreteCoordinates cell : currentInteractor.getCurrentCells()) {
                if (getCurrentMainCellCoordinates().equals(cell)) {
                    interactorHasLeft = false;
                }
            }

            if (interactorHasLeft) currentInteractor = null;
        }
    }

    @Override
    protected void rememberSwitch(Interactor interactor) {
        currentInteractor = interactor;
    }

    @Override
    protected boolean canSwitch(Interactor interactor) {
        return currentInteractor == null;
    }
}
