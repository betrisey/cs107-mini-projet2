package ch.epfl.cs107.play.game.enigme.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.actor.*;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.Signal;
import ch.epfl.cs107.play.signal.logic.Logic;
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
            Signal signal = Logic.FALSE;
            if (i <= 3) {
                destinationName = "Level" + i;
                destinationCoordinates = new DiscreteCoordinates(5 ,1);
                signal = Logic.TRUE;
            }

            DiscreteCoordinates doorCoordinates = new DiscreteCoordinates(i, 7);

            Door door = new SignalDoor(signal, this, destinationName, destinationCoordinates, Orientation.DOWN,
                    doorCoordinates, doorCoordinates);

            registerActor(door);
        }

        return true;
    }
}
