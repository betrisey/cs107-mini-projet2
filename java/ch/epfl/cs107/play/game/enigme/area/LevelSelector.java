package ch.epfl.cs107.play.game.enigme.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.actor.Door;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class LevelSelector extends EnigmeArea {
    @Override
    public String getTitle() {
        return "LevelSelector";
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        super.begin(window, fileSystem);

        for (int i = 1; i <= 8; i++) {
            String destinationName = "";
            DiscreteCoordinates destinationCoordinates = new DiscreteCoordinates(5 ,5);
            if (i <= 2) {
                destinationName = "Level" + i;
                destinationCoordinates = new DiscreteCoordinates(5 ,1);
            }

            DiscreteCoordinates doorCoordinates = new DiscreteCoordinates(i, 7);

            Door door = new Door(this, destinationName, destinationCoordinates, Orientation.DOWN,
                    doorCoordinates, doorCoordinates);

            registerActor(door);
        }

        return true;
    }
}
