package ch.epfl.cs107.play.game.enigme.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.actor.Apple;
import ch.epfl.cs107.play.game.enigme.actor.Door;
import ch.epfl.cs107.play.game.enigme.actor.Key;
import ch.epfl.cs107.play.game.enigme.actor.PressurePlate;
import ch.epfl.cs107.play.game.enigme.actor.PushableRock;
import ch.epfl.cs107.play.game.enigme.actor.SignalRock;
import ch.epfl.cs107.play.game.enigme.actor.TalkingActor;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.io.XMLTexts;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.signal.logic.MultipleAnd;
import ch.epfl.cs107.play.window.Window;

public class Level4 extends EnigmeArea {

	@Override
	public String getTitle() {
		return "Level4";
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);
		
		
		Key key1 =new Key(this,Orientation.DOWN,new DiscreteCoordinates(10,25));
		Key key2 =new Key(this,Orientation.DOWN,new DiscreteCoordinates(19,37));
		Key key3=new Key(this,Orientation.DOWN,new DiscreteCoordinates(23,30));
		registerActor(key1);
		registerActor(key2);
		registerActor(key3);
		
		TalkingActor actorPortal= new TalkingActor(this, Orientation.DOWN, new DiscreteCoordinates(23, 35),"girl.5", XMLTexts.getText("portal_hint"));
		TalkingActor actorKeys= new TalkingActor(this, Orientation.DOWN, new DiscreteCoordinates(9, 7),"boy.1", XMLTexts.getText("keys_hint"));
		TalkingActor actorRock= new TalkingActor(this, Orientation.DOWN, new DiscreteCoordinates(9, 15),"boy.4", XMLTexts.getText("rock_hint"));
		TalkingActor actorPrincess= new TalkingActor(this, Orientation.DOWN, new DiscreteCoordinates(7, 31),"girl.4", XMLTexts.getText("princess_thanks"));
		registerActor(actorPortal);
		registerActor(actorKeys);
		registerActor(actorRock);
		registerActor(actorPrincess);
		
		Logic andKeys=new MultipleAnd(key1,key2,key3);
		SignalRock rock1=  new SignalRock(andKeys,this,Orientation.DOWN,new DiscreteCoordinates(6, 9));
		SignalRock rock2=  new SignalRock(andKeys,this,Orientation.DOWN,new DiscreteCoordinates(7, 9));
		registerActor(rock1);
		registerActor(rock2);
		
		PressurePlate plate = new PressurePlate(this,Orientation.DOWN,new DiscreteCoordinates(12,13),0.5f);
		registerActor(plate);
		SignalRock rock3=  new SignalRock(plate,this,Orientation.DOWN,new DiscreteCoordinates(6, 17));
		SignalRock rock4=  new SignalRock(plate,this,Orientation.DOWN,new DiscreteCoordinates(7, 17));
		registerActor(rock3);
		registerActor(rock4);
		
		PushableRock rock = new PushableRock(this, Orientation.DOWN, new DiscreteCoordinates(22, 36));
	    registerActor(rock);
	    
	    Door doorMariage = new Door(this,"LevelSelector",new DiscreteCoordinates(4,6),Orientation.DOWN,new DiscreteCoordinates(6,32),new DiscreteCoordinates(6,32));
	    registerActor(doorMariage);
		
	    return true;
	}

}
