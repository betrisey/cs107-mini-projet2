package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.Signal;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class SignalDoor extends Door {
    private Signal signal;
    private Sprite openSprite, closeSprite;

    public SignalDoor(Signal signal, Area area, String destinationArea, DiscreteCoordinates destinationCoordinates,
                      Orientation orientation, DiscreteCoordinates position, DiscreteCoordinates... occupiedCoordinates) {
        super(area, destinationArea, destinationCoordinates, orientation, position, occupiedCoordinates);
        this.signal = signal;

        this.openSprite = new Sprite("door.open.1", 1, 1, this);
        this.closeSprite = new Sprite("door.close.1", 1, 1, this);
    }

    @Override
    public boolean isCellInteractable() {
        return isOpen();
    }

    @Override
    public boolean takeCellSpace() {
        return !isOpen();
    }

    @Override
    public void draw(Canvas canvas) {
        if (isOpen())
            openSprite.draw(canvas);
        else
            closeSprite.draw(canvas);
    }

    private boolean isOpen() {
        return signal.is(Logic.TRUE, 0);
    }
}
