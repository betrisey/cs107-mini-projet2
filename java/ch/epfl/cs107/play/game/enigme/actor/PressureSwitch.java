package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class PressureSwitch extends Switchable {

	public PressureSwitch(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position, "GroundLightOn", "GroundLightOff", false);

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

}
