package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class BreakableWall extends AreaEntity {
    private boolean isBroken;
    private Sprite sprite;
    private Sprite brokenSprite;

    public BreakableWall(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        isBroken = false;
        sprite = new Sprite("wall", 1, 1, this);
        brokenSprite = new Sprite("wall.broken", 1, 1, this);
    }

    public void breakIt() {
        isBroken = true;
    }

    public boolean isBroken() {
        return isBroken;
    }

    @Override
    public void draw(Canvas canvas) {
        if (isBroken)
            brokenSprite.draw(canvas);
        else
            sprite.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
        return !isBroken;
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
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((EnigmeInteractionVisitor) v).interactWith(this);
    }
}
