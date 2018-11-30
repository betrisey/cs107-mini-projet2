package ch.epfl.cs107.play.game.areagame;

import ch.epfl.cs107.play.game.Playable;
import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;

import java.util.LinkedList;
import java.util.List;


/**
 * Area is a "Part" of the AreaGame. It is characterized by its AreaBehavior and a List of Actors
 */
public abstract class Area implements Playable {

    // Context objects
	private Window window;
	private FileSystem fileSystem;
	
    // TODO implements me #PROJECT #TUTO
	private List<Actor> actors;
	private List<Actor> registeredActors;
	private List<Actor> unregisteredActors;

	/** @return (float): camera scale factor, assume it is the same in x and y direction */
    public abstract float getCameraScaleFactor();
    
    /**
     * Add an actor to the actors list
     * @param a (Actor): the actor to add, not null
     * @param forced (Boolean): if true, the method ends
     */
    private void addActor(Actor a, boolean forced) {
       //Here decisions on the area to decide if an actor is added or not 
    	boolean errorOccured = !actors.add(a);
    	
    	if(errorOccured && !forced) {
    		System.out.println("Actor"+a+"cannot be completely added so remove it from where it was ");
    		removeActor(a,true);
    	}
    }

    /**
     * Remove an actor form the actor list
     * @param a (Actor): the actor to remove, not null
     * @param forced (Boolean): if true, the method ends
     */
    private void removeActor(Actor a, boolean forced){
    boolean errorOccured = !actors.remove(a);
    	
    	if(errorOccured && !forced) {
    		System.out.println("Actor"+a+"cannot be completely removed so add it from where it was ");
    		addActor(a,true);
    	}
    }

    /**
     * Register an actor : will be added at next update
     * @param a (Actor): the actor to register, not null
     * @return (boolean): true if the actor is correctly registered
     */
    public final boolean registerActor(Actor a){
        registeredActors.add(a);
        return false;
    }

    /**
     * Unregister an actor : will be removed at next update
     * @param a (Actor): the actor to unregister, not null
     * @return (boolean): true if the actor is correctly unregistered
     */
    public final boolean unregisterActor(Actor a){
        unregisteredActors.add(a);
        return false;
    }

    /**
     * Getter for the area width
     * @return (int) : the width in number of cols
     */
    public final int getWidth(){
        // TODO implements me #PROJECT #TUTO
        return 0;
    }

    /**
     * Getter for the area height
     * @return (int) : the height in number of rows
     */
    public final int getHeight(){
        // TODO implements me #PROJECT #TUTO
        return 0;
    }

    /** @return the Window Keyboard for inputs */
    public final Keyboard getKeyboard () {
        // TODO implements me #PROJECT #TUTO
        return null;
    }

    /// Area implements Playable

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        this.window=window;
        this.fileSystem=fileSystem;
        actors=new LinkedList<>();
        return true;
    }

    /**
     * Resume method: Can be overridden
     * @param window (Window): display context, not null
     * @param fileSystem (FileSystem): given file system, not null
     * @return (boolean) : if the resume succeed, true by default
     */
    public boolean resume(Window window, FileSystem fileSystem){
        return true;
    }

    @Override
    public void update(float deltaTime) {
        ( (Actor) actors).draw(window);
    }


    private void updateCamera () {
        
    }

    /**
     * Suspend method: Can be overridden, called before resume other
     */
    public void suspend(){
        // Do nothing by default
    }


    @Override
    public void end() {
        // TODO save the AreaState somewhere
    }

}
