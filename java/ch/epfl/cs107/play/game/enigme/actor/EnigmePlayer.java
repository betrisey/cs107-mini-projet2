package ch.epfl.cs107.play.game.enigme.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;



	public class EnigmePlayer extends MovableAreaEntity implements Interactor{
	    private boolean isPassingDoor;
	    private Sprite ghostSprite;
	    private Door passedDoor;
	    private final EnigmePlayerHandler handler;


	    // Animation duration in frame number
	    private final static int ANIMATION_DURATION = 8;

	    public EnigmePlayer(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
	        super(area, orientation, coordinates);
	        isPassingDoor = false;
	        ghostSprite = new Sprite("ghost.1", 1, 1, this);
	        setOrientation(Orientation.DOWN);
	        handler=new EnigmePlayerHandler();
	    }
	    public void interactWith(Interactable other) { 
	    	other.acceptInteraction(handler);
	    }
	    public void enterArea(Area area, DiscreteCoordinates position) {
	        area.registerActor(this);
	        setOwnerArea(area);

	        setCurrentPosition(position.toVector());

	        resetMotion();
	        isPassingDoor = false;
	    }

	    public void leaveArea(Area area) {
	        area.unregisterActor(this);
	        isPassingDoor = false;
	    }

	    public boolean isPassingDoor() {
	        return isPassingDoor;
	    }

	    @Override
	    public void draw(Canvas canvas) {
	        ghostSprite.draw(canvas);
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
	        return true;
	    }
	    
	    public void setIsPassingDoor(Door door) {
              isPassingDoor=true;
              passedDoor=door;
	    }
	    public Door passedDoor() {
	    	return passedDoor;
	    }
	    
	    @Override
	    public void update(float deltaTime) {
	        super.update(deltaTime);

	        for (Orientation orientation : Orientation.values()) {
	            boolean keyPressed = getOwnerArea().getKeyboard().get(orientation.getKeyCode()).isDown();
	            if (keyPressed) {
	                if (getOrientation() == orientation)
	                    move(ANIMATION_DURATION);
	                else
	                    setOrientation(orientation);
	            }
	        }
	    }

		@Override
		public List<DiscreteCoordinates> getFieldOfViewsCells() {
			// TODO Auto-generated method stub
			return Collections.singletonList(this.getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
		}

		@Override
		public boolean wantsCellInteraction() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean wantsViewInteraction() {
			
			return  getOwnerArea().getKeyboard().get(Keyboard.L).isDown();
		}
		
		private class EnigmePlayerHandler implements EnigmeInteractionVisitor {
			@Override
			public void interactWith(Door door) {
			  setIsPassingDoor(door);
			}
			@Override
			public void interactWith(Apple apple){
			apple.pickUp();
			} }

		@Override
		public void acceptInteraction(AreaInteractionVisitor v) {
			((EnigmeInteractionVisitor)v).interactWith(this);
		}
	}

