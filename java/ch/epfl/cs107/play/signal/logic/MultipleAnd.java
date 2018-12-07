package ch.epfl.cs107.play.signal.logic;

public class MultipleAnd extends LogicSignal{
     
	private Logic[] signaux;
	
	public MultipleAnd(Logic... signaux) {
		this.signaux=signaux;
	}
	@Override
	public boolean isOn() {
		if(signaux==null) {
			return false;
		}
		for(Logic signal : signaux ) {
			if(signal==null||!signal.isOn()) {
				return false;
			}
		}
		return true;
	}

}
