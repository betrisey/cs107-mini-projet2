package ch.epfl.cs107.play.game.enigme.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Switchable extends AreaEntity implements Logic {
	private boolean isOn;
	private Sprite onSprite,offSprite;

	public Switchable(Area area, Orientation orientation, DiscreteCoordinates position,String nomSpriteOn,String nomSpriteOff,boolean initialState) {
		super(area, orientation, position);
		 onSprite = new Sprite(nomSpriteOn, 1, 1, this);
		 offSprite = new Sprite(nomSpriteOff, 1, 1, this);
	     isOn=initialState;
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
		
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		 ((EnigmeInteractionVisitor) v).interactWith(this);

	}

	@Override
	public void draw(Canvas canvas) {
		if(isOn()) {
			onSprite.draw(canvas);
		}else {
			offSprite.draw(canvas);
		}
	}
	public void switchState() {
		isOn=!isOn;
	}

	@Override
	public boolean isOn() {
		return isOn;
	}

}
