package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AnimatedSprite;
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
    private AnimatedSprite onAnimSprite, offAnimSprite;

    public Switchable(Area area, Orientation orientation, DiscreteCoordinates position,
                      String[] onSpriteNames, String[] offSpriteNames, boolean initialState) {
        super(area, orientation, position);

        this.onAnimSprite = new AnimatedSprite(0.3f, this, onSpriteNames);
        this.offAnimSprite = new AnimatedSprite(0.3f, this, offSpriteNames);

        this.isOn = initialState;
    }

    public Switchable(Area area, Orientation orientation, DiscreteCoordinates position,
                      String onSpriteName, String offSpriteName, boolean initialState) {
        this(area, orientation, position, new String[]{onSpriteName}, new String[]{offSpriteName}, initialState);
    }

    public void switchState() {
        this.isOn = !this.isOn;
    }

    @Override
    public void draw(Canvas canvas) {
        if (isOn())
            onAnimSprite.draw(canvas);
        else
            offAnimSprite.draw(canvas);
    }

    @Override
    public void update(float deltaTime) {
        onAnimSprite.update(deltaTime);
        offAnimSprite.update(deltaTime);
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
