package ch.epfl.cs107.play.game.enigme.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Apple extends AreaEntity {

	private Sprite appleSprite;
	private boolean isPickedup;
	
	public Apple(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		appleSprite=  new Sprite("apple.1", 1, 1, this);
		
	}

	public void pickUp() {
		isPickedup=true;
		getOwnerArea().unregisterActor(this);
	}
	
	
	public void acceptInteraction(AreaInteractionVisitor v) { 
		((EnigmeInteractionVisitor)v).interactWith(this);
	}
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {
		
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		
		return true;
	}

	@Override
	public boolean isCellInteractable() {
		return false;
	}

	@Override
	public void draw(Canvas canvas) {
		appleSprite.draw(canvas);
		
	}

}
