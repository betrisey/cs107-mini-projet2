package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Lever extends DelaySwitchable {
    public Lever(Area area, Orientation orientation, DiscreteCoordinates position, boolean initialState) {
        super(area, orientation, position, "lever.big.left", "lever.big.right",
                false, 0.25f);
    }
}
