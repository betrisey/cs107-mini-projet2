package ch.epfl.cs107.play.game.areagame;

import ch.epfl.cs107.play.game.Playable;
import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Area is a "Part" of the AreaGame. It is characterized by its AreaBehavior and a List of Actors
 */
public abstract class Area implements Playable {
    // Context objects
    private Window window;
    private FileSystem fileSystem;

    private AreaBehavior areaBehavior;

    private List<Actor> actors;
    private List<Interactor> interactors;

    private List<Actor> registeredActors;
    private List<Actor> unregisteredActors;

    private Map<Interactable, List<DiscreteCoordinates>> interactablesToEnter;
    private Map<Interactable, List<DiscreteCoordinates>> interactablesToLeave;

    // Camera Parameter
    // actor on which the view is centered
    private Actor viewCandidate;
    // effective center of the view
    private Vector viewCenter;

    private boolean hasBegun = false;

	/** @return (float): camera scale factor, assume it is the same in x and y direction */
    public abstract float getCameraScaleFactor();

    /**
     * Add an actor to the actors list
     * @param a (Actor): the actor to add, not null
     * @param forced (Boolean): if true, the method ends
     */
    private void addActor(Actor a, boolean forced) {
        // Here decisions at the area level to decide if an actor
        // must be added or not
        boolean errorOccured = !actors.add(a);
        if(a instanceof Interactable)
            errorOccured = errorOccured || !enterAreaCells(((Interactable) a), ((Interactable) a).getCurrentCells());
        if(a instanceof Interactor)
            errorOccured = errorOccured || !interactors.add((Interactor) a);
        if(errorOccured && !forced) {
            System.out.println("Actor " + a + " cannot be completely added, so remove it from where it was");
            removeActor(a, true);
        }
    }

    /**
     * Remove an actor form the actor list
     * @param a (Actor): the actor to remove, not null
     * @param forced (Boolean): if true, the method ends
     */
    private void removeActor(Actor a, boolean forced){
        boolean errorOccured = !actors.remove(a) ;
        if(a instanceof Interactable)
            errorOccured = errorOccured || !leaveAreaCells(((Interactable) a), ((Interactable) a).getCurrentCells());
        if(a instanceof Interactor)
            errorOccured = errorOccured || !interactors.remove(a);
        if(errorOccured && !forced) {
            System.out.println("Actor " + a + " cannot be completely removed, so add it where it was");
            addActor(a, true); // TODO : check if we should add it back
        }
    }

    /**
     * Register an actor : will be added at next update
     * @param a (Actor): the actor to register, not null
     * @return (boolean): true if the actor is correctly registered
     */
    public final boolean registerActor(Actor a) {
        return registeredActors.add(a);
    }

    /**
     * Unregister an actor : will be removed at next update
     * @param a (Actor): the actor to unregister, not null
     * @return (boolean): true if the actor is correctly unregistered
     */
    public final boolean unregisterActor(Actor a) {
        return unregisteredActors.add(a);
    }

    /**
     * Getter for the area width
     * @return (int) : the width in number of cols
     */
    public final int getWidth(){
        return areaBehavior.getWidth();
    }

    /**
     * Getter for the area height
     * @return (int) : the height in number of rows
     */
    public final int getHeight(){
        return areaBehavior.getHeight();
    }

    /** @return the Window Keyboard for inputs */
    public final Keyboard getKeyboard () {
        return window.getKeyboard();
    }

    /// Area implements Playable

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        this.window = window;
        this.fileSystem = fileSystem;

        actors = new LinkedList<>();
        interactors=new LinkedList<>();

        registeredActors = new LinkedList<>();
        unregisteredActors = new LinkedList<>();

        interactablesToEnter = new HashMap<>();
        interactablesToLeave = new HashMap<>();

        viewCandidate = null;
        viewCenter = Vector.ZERO;

        hasBegun = true;

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
        purgeRegistration();

        for (Actor actor : actors) {
            actor.update(deltaTime);
        }

        for (Interactor interactor : interactors) {
            if (interactor.wantsCellInteraction()) {
                // demander au gestionnaire de la grille (AreaBehavior)
                //de mettre en place les interactions de contact
                areaBehavior.cellInteractionOf(interactor);
            }
            if (interactor.wantsViewInteraction()) {
                // demander au gestionnaire de la grille de mettre en place
                // les interactions distantes
                areaBehavior.viewInteractionOf(interactor);
            }
        }

        updateCamera();

        for (Actor actor : actors) {
            actor.draw(window);
        }
    }

    private final void purgeRegistration() {
        for (Actor actor : registeredActors)
            addActor(actor, false);
        for (Actor actor : unregisteredActors)
            removeActor(actor, false);

        registeredActors.clear();
        unregisteredActors.clear();

        for (Map.Entry<Interactable, List<DiscreteCoordinates>> entry : interactablesToEnter.entrySet())
            areaBehavior.enter(entry.getKey(), entry.getValue());
        for (Map.Entry<Interactable, List<DiscreteCoordinates>> entry : interactablesToLeave.entrySet())
            areaBehavior.leave(entry.getKey(), entry.getValue());

        interactablesToEnter.clear();
        interactablesToLeave.clear();
    }


    private void updateCamera () {
        if (viewCandidate != null) viewCenter = viewCandidate.getPosition();
        // Compute new viewport
        Transform viewTransform = Transform.I.scaled(getCameraScaleFactor()).translated(viewCenter);
        window.setRelativeTransform(viewTransform);
    }

    /**
     * Suspend method: Can be overridden, called before resume other
     */
    public void suspend(){
        purgeRegistration();
        // Do nothing by default
    }


    @Override
    public void end() {
        // TODO save the AreaState somewhere
    }

    public final void setViewCandidate(Actor a) {
        this.viewCandidate = a;
    }

    protected final void setBehavior(AreaBehavior ab) {
        this.areaBehavior = ab;
    }

    public final AreaBehavior getAreaBehavior() {
        return areaBehavior;
    }

    public boolean hasBegun() {
        return hasBegun;
    }

    public final boolean leaveAreaCells(Interactable entity, List<DiscreteCoordinates> coordinates) {
        if (areaBehavior.canLeave(entity, coordinates)) {
            interactablesToLeave.put(entity, coordinates);
            return true;
        }
        return false;
    }

    public final boolean enterAreaCells(Interactable entity, List<DiscreteCoordinates> coordinates) {
        if (areaBehavior.canEnter(entity, coordinates)) {
            interactablesToEnter.put(entity, coordinates);
            return true;
        }
        return false;
    }
}
