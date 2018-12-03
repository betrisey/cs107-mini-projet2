package ch.epfl.cs107.play.game.enigme;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public abstract class EnigmeArea extends Area{
	  @Override
	    public float getCameraScaleFactor() {
	        return Enigme.CAMERA_SCALE_FACTOR;
	    }

	    @Override
	    public boolean begin(Window window, FileSystem fileSystem) {
	        super.begin(window, fileSystem);

	        this.setBehavior(new EnigmeBehavior(window, getTitle()));
	        registerActor(new Background(this));

	        return true;
	    }

		@Override
		public abstract String getTitle();
	}
