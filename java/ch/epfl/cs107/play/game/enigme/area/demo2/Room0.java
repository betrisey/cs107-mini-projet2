package ch.epfl.cs107.play.game.enigme.area.demo2;

import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Room0 extends Demo2Area {
    @Override
    public String getTitle() {
        return "LevelSelector";
    }

    @Override
    public DiscreteCoordinates getStartPosition() {
        return new DiscreteCoordinates(5, 5);
    }
}
