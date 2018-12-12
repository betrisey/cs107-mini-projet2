package ch.epfl.cs107.play.game.enigme.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.actor.*;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.io.XMLTexts;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
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
            Logic signal = Logic.FALSE;
            if (i <= 4) {
                destinationName = "Level" + i;
                destinationCoordinates = new DiscreteCoordinates(5, 1);
                signal = Logic.TRUE;
            }

            DiscreteCoordinates doorCoordinates = new DiscreteCoordinates(i, 7);

            Door door = new SignalDoor(signal, this, destinationName, destinationCoordinates, Orientation.DOWN,
                    doorCoordinates, doorCoordinates);

            registerActor(door);
        }
        
        TalkingActor talkingActor = new TalkingActor(this, Orientation.DOWN, new DiscreteCoordinates(7, 5), "girl.1", XMLTexts.getText("welcome_text"));
        registerActor(talkingActor);

        return true;
    }
}
