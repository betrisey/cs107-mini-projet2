package ch.epfl.cs107.play.game.enigme.actor.demo2;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.enigme.Demo2Behavior;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Demo2Player extends MovableAreaEntity {
    private boolean isPassingDoor;
    private Sprite ghostSprite;

    // Animation duration in frame number
    private final static int ANIMATION_DURATION = 8;

    public Demo2Player(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates);
        isPassingDoor = false;
        ghostSprite = new Sprite("ghost.1", 1, 1, this);
        setOrientation(Orientation.DOWN);
    }

    public void enterArea(Area area, DiscreteCoordinates position) {
        area.registerActor(this);
        setOwnerArea(area);

        setCurrentPosition(position.toVector());

        resetMotion();
        isPassingDoor = false;
    }

    public void leaveArea(Area area) {
        area.unregisterActor(this);
        isPassingDoor = false;
    }

    public boolean isPassingDoor() {
        return isPassingDoor;
    }

    @Override
    public void draw(Canvas canvas) {
        ghostSprite.draw(canvas);
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
        return true;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        for (Orientation orientation : Orientation.values()) {
            boolean keyPressed = getOwnerArea().getKeyboard().get(orientation.getKeyCode()).isDown();
            if (keyPressed) {
                if (getOrientation() == orientation)
                    move(ANIMATION_DURATION);
                else
                    setOrientation(orientation);
            }
        }
    }

    @Override
    protected boolean move(int framesForMove) {
        isPassingDoor = false;
        for (DiscreteCoordinates coordinates : getEnteringCells()) {
            Demo2Behavior.Demo2CellType cellType = ((Demo2Behavior)getOwnerArea().getAreaBehavior()).getCellType(coordinates);
            if (cellType == Demo2Behavior.Demo2CellType.DOOR) {
                isPassingDoor = true;
                break;
            }
        }
        return super.move(framesForMove);
    }
}
