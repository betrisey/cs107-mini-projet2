package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class PressurePlate extends Switchable {
    private final float DELAY;
    private float timeLeft;

    public PressurePlate(Area area, Orientation orientation, DiscreteCoordinates position, float delay) {
        super(area, orientation, position, "GroundLightOn", "GroundPlateOff", false);
        this.DELAY = delay;
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
    public boolean isOn() {
        return timeLeft > 0;
    }

    @Override
    public void switchState(Interactor interactor) {
        timeLeft = DELAY;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (timeLeft > 0)
            timeLeft -= deltaTime;

        if (timeLeft < 0)
            timeLeft = 0;
    }
}
