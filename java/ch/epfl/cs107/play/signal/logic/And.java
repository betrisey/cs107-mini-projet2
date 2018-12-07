package ch.epfl.cs107.play.signal.logic;

public class And extends LogicSignal{
    private Logic signal1;
    private Logic signal2;
	
	public And(Logic signal1, Logic signal2) {
		this.signal1=signal1;
		this.signal2=signal2;
	}
	@Override
	public boolean isOn() {
		if(signal1!=null&&signal2!=null&&signal1.isOn()&&signal2.isOn()) {
			return true;
		}
		return false;
	}
    
}
