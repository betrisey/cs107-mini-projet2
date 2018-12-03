package ch.epfl.cs107.play.game.enigme.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.EnigmeArea;
import ch.epfl.cs107.play.game.enigme.actor.Door;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class LevelSelector extends EnigmeArea{
private Door[] doors ;
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		// TODO Auto-generated method stub
		super.begin(window, fileSystem);
		doors=new Door[8];
		for(int i=1;i<=doors.length;i++) {
			String destinationArea = "";
			DiscreteCoordinates destinationCoord = new DiscreteCoordinates(5, 5);
			DiscreteCoordinates position = new DiscreteCoordinates(i, 7);
			if (i <= 2) {
				destinationArea = "Level" + i;
				destinationCoord = new DiscreteCoordinates(5, 1);
			}
			doors[i-1] = new Door(this, destinationArea, destinationCoord, Orientation.DOWN, position, position);
			registerActor(doors[i-1]);
		}
		return true;
		
	}
	
	@Override
	public String getTitle() {
		return "LevelSelector";
	}

}
