package ch.epfl.cs107.play.game.enigme.area.demo2;

import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Room1 extends Demo2Area {
    @Override
    public String getTitle() {
        return "Level1";
    }

    @Override
    public DiscreteCoordinates getStartPosition() {
        return new DiscreteCoordinates(5, 2);
    }
}
