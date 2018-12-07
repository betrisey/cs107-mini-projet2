package ch.epfl.cs107.play.signal.logic;

public class LogicNumber extends LogicSignal{

	private Logic[] signaux;
	private float nb;
	
	public LogicNumber(float nb, Logic... signaux){
		this.signaux=signaux;
		this.nb=nb;
	}
	
	
	@Override
	public boolean isOn() {
		float somme=0;
		if(signaux.length>12||nb<0||nb>Math.pow(2, signaux.length)) {
			return false;
		}
		for(int i=0;i<signaux.length;i++) {
			somme+=(float) (Math.pow(2, i)*signaux[i].getIntensity());
		}
		return somme==nb;
	}

}
