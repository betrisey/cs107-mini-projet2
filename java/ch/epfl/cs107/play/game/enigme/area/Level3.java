package ch.epfl.cs107.play.game.enigme.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.actor.Apple;
import ch.epfl.cs107.play.game.enigme.actor.Door;
import ch.epfl.cs107.play.game.enigme.actor.Key;
import ch.epfl.cs107.play.game.enigme.actor.Torch;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class Level3 extends EnigmeArea {
    @Override
    public String getTitle() {
        return "Level3";
    }
    
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        super.begin(window, fileSystem);
        
        
        Key key=new Key(this,Orientation.DOWN,new DiscreteCoordinates(1, 3));
        registerActor(key);
        
        Torch torch=new Torch(this,Orientation.DOWN,new DiscreteCoordinates(7, 5),false);
        registerActor(torch);

        DiscreteCoordinates destinationCoordinates = new DiscreteCoordinates(2 ,6);
        DiscreteCoordinates doorCoordinates = new DiscreteCoordinates(5, 0);
        Door door = new Door(this, "LevelSelector", destinationCoordinates, Orientation.DOWN,
                doorCoordinates, doorCoordinates);
        registerActor(door);

        Apple apple = new Apple(this, Orientation.DOWN, new DiscreteCoordinates(5, 6));
        registerActor(apple);

        return true;
    }
}
