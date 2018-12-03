package ch.epfl.cs107.play.game.enigme.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.actor.Door;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class Level1 extends EnigmeArea {
    @Override
    public String getTitle() {
        return "Level1";
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        super.begin(window, fileSystem);

        DiscreteCoordinates destinationCoordinates = new DiscreteCoordinates(1 ,6);
        DiscreteCoordinates doorCoordinates = new DiscreteCoordinates(5, 0);
        Door door = new Door(this, "LevelSelector", destinationCoordinates, Orientation.DOWN,
                doorCoordinates, doorCoordinates);
        registerActor(door);

        return true;
    }

}
