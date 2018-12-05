package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.Signal;

public class Key extends Collectable implements Signal {
    public Key(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position, "key.1");
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((EnigmeInteractionVisitor) v).interactWith(this);
    }

    @Override
    public float getIntensity(float t) {
        return isCollected() ? 1.0f : 0.0f;
    }
}
