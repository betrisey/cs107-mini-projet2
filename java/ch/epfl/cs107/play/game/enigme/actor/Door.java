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
    private String destinationName;
    private DiscreteCoordinates destinationCoordinates;
    private List<DiscreteCoordinates> currentCells;

    public Door(Area area, String destinationName, DiscreteCoordinates destinationCoordinates,
                Orientation orientation, DiscreteCoordinates position, DiscreteCoordinates... occupiedCoordinates) {
        super(area, orientation, position);
        this.destinationName = destinationName;
        this.destinationCoordinates = destinationCoordinates;
        currentCells = Arrays.asList(occupiedCoordinates);
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
        return true;
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
    public void acceptInteraction(AreaInteractionVisitor handler) {
        ((EnigmeInteractionVisitor) handler).interactWith(this);
    }

    public String getDestinationName() {
        return destinationName;
    }

    public DiscreteCoordinates getDestinationCoordinates() {
        return destinationCoordinates;
    }
}
