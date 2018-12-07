package ch.epfl.cs107.play.signal.logic;

public abstract class LogicSignal implements Logic{

	@Override
	
	public final float getIntensity(float t) {
		return getIntensity();
	}

	public final  float getIntensity() {
		if (isOn()) {
			return 1.0f;
		} else {
			return 0.0f;
		}
	}

	
	
	
	
}
