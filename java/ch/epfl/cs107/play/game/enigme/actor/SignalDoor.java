package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class SignalDoor extends Door{
	private Logic signal;
	private Sprite openSprite;
	private Sprite closeSprite;

	public SignalDoor(Logic signal , Area area, String destinationArea, DiscreteCoordinates destinationCoordinates,
			Orientation orientation, DiscreteCoordinates position, DiscreteCoordinates... occupiedCoordinates) {
		super(area, destinationArea, destinationCoordinates, orientation, position, occupiedCoordinates);
	    this.openSprite=new Sprite("door.open.1",1,1,this);
	    this.closeSprite=new Sprite("door.close.1",1,1,this);
	    this.signal=signal;
	}
	
	@Override
	public boolean isCellInteractable() {
		return isOpen();
	}
    @Override
    public boolean takeCellSpace() {
    	return !isOpen();
    }
	private boolean isOpen() {
		return signal.isOn();
	}
	@Override
	public void draw(Canvas canvas) {
		if(isOpen()) {
		openSprite.draw(canvas);
		}else {
	    closeSprite.draw(canvas);
		}
	}
}
