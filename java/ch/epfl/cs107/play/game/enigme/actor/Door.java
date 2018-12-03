package ch.epfl.cs107.play.game.enigme.actor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Door extends AreaEntity{
	private String destinationArea;
	private DiscreteCoordinates destinationCoord;
	public DiscreteCoordinates getDestinationCoord() {
		return destinationCoord;
	}

	private List<DiscreteCoordinates> currentCells;

	public Door(Area area, String destinationArea, DiscreteCoordinates destinationCoord,Orientation orientation, DiscreteCoordinates position, DiscreteCoordinates...coordinates) {
		super(area, orientation, position);
		this.destinationArea=destinationArea;
		this.destinationCoord=destinationCoord;
		this.currentCells=Arrays.asList(coordinates);
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		 return currentCells;
	}

	@Override
	public boolean takeCellSpace() {
		
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		
		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		
		
	}

	public String getDestinationArea() {
		return destinationArea;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((EnigmeInteractionVisitor)v).interactWith(this);
	}



}
