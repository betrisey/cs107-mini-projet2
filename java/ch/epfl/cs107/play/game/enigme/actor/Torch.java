package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Torch extends DelaySwitchable {
    public Torch(Area area, Orientation orientation, DiscreteCoordinates position, boolean initialState) {
        super(area, orientation, position, "torch.ground.on.1", "torch.ground.off",
                initialState, 0.25f);
    }
}
