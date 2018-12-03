package ch.epfl.cs107.play.game.enigme;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.actor.Door;
import ch.epfl.cs107.play.game.enigme.actor.EnigmePlayer;
import ch.epfl.cs107.play.game.enigme.area.Level1;
import ch.epfl.cs107.play.game.enigme.area.Level2;
import ch.epfl.cs107.play.game.enigme.area.Level3;
import ch.epfl.cs107.play.game.enigme.area.LevelSelector;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;


/**
 * Enigme Game is a concept of Game derived for AreaGame. It introduces the notion of Player
 * When initializing the player is added to the current area
 */
public class Enigme extends AreaGame {
	public static final int CAMERA_SCALE_FACTOR = 22;

   
    private EnigmePlayer player;
    private EnigmeArea levelSelector, level1,level2,level3;

    @Override
    public int getFrameRate() {
        return 24;
    }

    @Override
    public String getTitle() {
        return "Enigme";
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        super.begin(window, fileSystem);

        levelSelector= new LevelSelector();
        level1 = new Level1();
        level2=new Level2();
        level3=new Level3();
        addArea(levelSelector);
        addArea(level1);
        addArea(level2);
        addArea(level3);

        setCurrentArea(levelSelector.getTitle(), false);
        DiscreteCoordinates startPosition = new DiscreteCoordinates(5, 5);
        player = new EnigmePlayer(levelSelector, Orientation.DOWN, startPosition);
        player.enterArea(levelSelector, startPosition);
        levelSelector.setViewCandidate(player);

        
        return true;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(player.isPassingDoor()) {
        	Door currentDoor= player.passedDoor();
        	player.leaveArea(getCurrentArea());
        	
        	setCurrentArea(currentDoor.getDestinationArea(), false);
            player.enterArea(getCurrentArea(), currentDoor.getDestinationCoord());
            getCurrentArea().setViewCandidate(player);

        }

       
    }
}

	
