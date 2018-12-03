package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Arrays;
import java.util.List;

public class Door extends AreaEntity {
    private String destinationArea;
    private DiscreteCoordinates destinationCoordinates;
    private List<DiscreteCoordinates> currentCells;

    public Door(Area area, String destinationArea, DiscreteCoordinates destinationCoordinates,
                Orientation orientation, DiscreteCoordinates position, DiscreteCoordinates... occupiedCoordinates) {
        super(area, orientation, position);
        this.destinationArea = destinationArea;
        this.destinationCoordinates = destinationCoordinates;
        this.currentCells = Arrays.asList(occupiedCoordinates);
    }

    public String getDestinationArea() {
        return destinationArea;
    }

    public DiscreteCoordinates getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return currentCells;
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((EnigmeInteractionVisitor) v).interactWith(this);
    }
}
