package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public abstract class DelaySwitchable extends Switchable {
    private final float delayAfterSwitch;
    private float delayLeft;

    public DelaySwitchable(Area area, Orientation orientation, DiscreteCoordinates position,
                           String onSpriteName, String offSpriteName, boolean initialState, float delayAfterSwitch) {
        super(area, orientation, position, onSpriteName, offSpriteName, initialState);

        this.delayAfterSwitch = delayAfterSwitch;
        this.delayLeft = 0;
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
        return false;
    }

    @Override
    protected void rememberSwitch(Interactor interactor) {
        delayLeft = delayAfterSwitch;
    }

    @Override
    protected boolean canSwitch(Interactor interactor) {
        return delayLeft <= 0;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (delayLeft > 0)
            delayLeft -= deltaTime;

        if (delayLeft < 0)
            delayLeft = 0;
    }
}
