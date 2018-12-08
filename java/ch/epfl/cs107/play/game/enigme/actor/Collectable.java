package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public abstract class Collectable extends AreaEntity {
    private boolean isCollected;
    private Sprite sprite;

    public Collectable(Area area, Orientation orientation, DiscreteCoordinates position, String spriteName) {
        super(area, orientation, position);
        isCollected = false;
        sprite = new Sprite(spriteName, 1, 1, this);
    }

    public void collect() {
        getOwnerArea().unregisterActor(this); // TODO should we keep it and just hide it?

        isCollected = true;
    }

    public boolean isCollected() {
        return isCollected;
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
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
}
