package ch.epfl.cs107.play.game.enigme.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.actor.*;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.*;
import ch.epfl.cs107.play.window.Window;

public class Level3 extends EnigmeArea {
    @Override
    public String getTitle() {
        return "Level3";
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        super.begin(window, fileSystem);

        Key key = new Key(this, Orientation.DOWN, new DiscreteCoordinates(1, 3));
        registerActor(key);

        Torch torch = new Torch(this, Orientation.DOWN, new DiscreteCoordinates(7, 5), false);
        registerActor(torch);

        PressurePlate pressurePlate = new PressurePlate(this, Orientation.DOWN,
                new DiscreteCoordinates(9, 8), 0.3f);
        registerActor(pressurePlate);

        PressureSwitch[] pressureSwitches = new PressureSwitch[7];
        pressureSwitches[0] = new PressureSwitch(this, Orientation.DOWN, new DiscreteCoordinates(4, 4));
        pressureSwitches[1] = new PressureSwitch(this, Orientation.DOWN, new DiscreteCoordinates(5, 4));
        pressureSwitches[2] = new PressureSwitch(this, Orientation.DOWN, new DiscreteCoordinates(6, 4));
        pressureSwitches[3] = new PressureSwitch(this, Orientation.DOWN, new DiscreteCoordinates(5, 5));
        pressureSwitches[4] = new PressureSwitch(this, Orientation.DOWN, new DiscreteCoordinates(4, 6));
        pressureSwitches[5] = new PressureSwitch(this, Orientation.DOWN, new DiscreteCoordinates(5, 6));
        pressureSwitches[6] = new PressureSwitch(this, Orientation.DOWN, new DiscreteCoordinates(6, 6));
        for (PressureSwitch pressureSwitch : pressureSwitches) {
            registerActor(pressureSwitch);
        }

        Lever[] levers = new Lever[3];
        levers[0] = new Lever(this, Orientation.DOWN, new DiscreteCoordinates(10, 5), false);
        levers[1] = new Lever(this, Orientation.DOWN, new DiscreteCoordinates(9, 5), false);
        levers[2] = new Lever(this, Orientation.DOWN, new DiscreteCoordinates(8, 5), false);
        for (Lever lever : levers) {
            registerActor(lever);
        }

        Logic pressureSwitchesSignal = new MultipleAnd(pressureSwitches);

        SignalDoor signalDoor = new SignalDoor(new And(key, pressureSwitchesSignal), this, "LevelSelector", new DiscreteCoordinates(3,6), Orientation.DOWN, new DiscreteCoordinates(5, 9), new DiscreteCoordinates(5, 9));
        registerActor(signalDoor);

        SignalRock[] signalRocks = new SignalRock[3];
        signalRocks[0] = new SignalRock(pressurePlate, this, Orientation.DOWN, new DiscreteCoordinates(6, 8));
        signalRocks[1] = new SignalRock(pressureSwitchesSignal, this, Orientation.DOWN, new DiscreteCoordinates(5, 8));
        signalRocks[2] = new SignalRock(new Or(new LogicNumber(5, levers), torch), this, Orientation.DOWN, new DiscreteCoordinates(4, 8));
        for (SignalRock signalRock : signalRocks) {
            registerActor(signalRock);
        }

        return true;
    }
}
