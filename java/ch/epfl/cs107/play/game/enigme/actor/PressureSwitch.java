package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class PressureSwitch extends Switchable {
	private boolean canBeSwitched;
	private boolean hasBeenInteractedLastUpdate;

	public PressureSwitch(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position, "GroundLightOn", "GroundLightOff", false);

		canBeSwitched = true;
		hasBeenInteractedLastUpdate = false;
	}

	@Override
	public void switchState() {
		// It can be switched only if it hasn't been during last update
		if (canBeSwitched) {
			super.switchState();
			canBeSwitched = false;
		}
		hasBeenInteractedLastUpdate = true;
	}

	@Override
	public void update(float deltaTime) {
		// It can be switched only if it hasn't been during last update
		if (!hasBeenInteractedLastUpdate) canBeSwitched = true;
		// Reset
		hasBeenInteractedLastUpdate = false;
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
	public void acceptInteraction(AreaInteractionVisitor v) {
		((EnigmeInteractionVisitor) v).interactWith(this);
	}

}
