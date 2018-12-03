package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public abstract class Collectable extends AreaEntity {
    private boolean isCollected; // TODO

    public Collectable(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        isCollected = false;
    }

    public void collect() {
        getOwnerArea().unregisterActor(this);

        isCollected = true;
    }
}
