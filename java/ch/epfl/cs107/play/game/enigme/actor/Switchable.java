package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public abstract class Switchable extends AreaEntity implements Logic {
    private boolean isOn;
    private Sprite onSprite, offSprite;

    public Switchable(Area area, Orientation orientation, DiscreteCoordinates position,
                      String onSpriteName, String offSpriteName, boolean initialState) {
        super(area, orientation, position);

        this.onSprite = new Sprite(onSpriteName, 1, 1, this);
        this.offSprite = new Sprite(offSpriteName, 1, 1, this);

        this.isOn = initialState;
    }

    public void switchState() {
        this.isOn = !this.isOn;
    }

    @Override
    public void draw(Canvas canvas) {
        if (isOn())
            onSprite.draw(canvas);
        else
            offSprite.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean isOn() {
        return isOn;
    }
}
