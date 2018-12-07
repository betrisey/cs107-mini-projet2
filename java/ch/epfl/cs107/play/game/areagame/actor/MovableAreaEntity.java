package ch.epfl.cs107.play.game.areagame.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

import java.util.Collections;
import java.util.List;


/**
 * MovableAreaEntity are AreaEntity able to move on a grid
 */
public abstract class MovableAreaEntity extends AreaEntity {

    // Indicate if the actor is currently moving
    private boolean isMoving;
    // Indicate how many frames the current move is supposed to take
    private int framesForCurrentMove;
    // The target cell (i.e. where the mainCell will be after the motion)
    private DiscreteCoordinates targetMainCellCoordinates;

    /**
     * Default MovableAreaEntity constructor
     * @param area (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     */
    public MovableAreaEntity(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        resetMotion();
    }

    /**
     * Initialize or reset the current motion information
     */
    protected void resetMotion(){
        isMoving = false;
        framesForCurrentMove = 0;
        targetMainCellCoordinates = getCurrentMainCellCoordinates();
    }

    /**
     * 
     * @param framesForMove (int): number of frames used for simulating motion
     * @return (boolean): returns true if motion can occur
     */
  
    protected  boolean move(int framesForMove){
        if (!isMoving || getCurrentMainCellCoordinates().equals(targetMainCellCoordinates)) {
            // TODO : check before calling leave/enter? 4.7.4
            if (getOwnerArea().leaveAreaCells(this, getLeavingCells()) &&
                    getOwnerArea().enterAreaCells(this, getEnteringCells())) {
                framesForCurrentMove = Math.max(framesForMove, 1);

                Vector orientation = getOrientation().toVector();
                targetMainCellCoordinates = getCurrentMainCellCoordinates().jump(orientation);

                isMoving = true;
                return true;
            }
        }
        return false;
    }


    /// MovableAreaEntity implements Actor

    @Override
    public void update(float deltaTime) {
        if (isMoving && !getCurrentMainCellCoordinates().equals(targetMainCellCoordinates)) {
            Vector distance = getOrientation().toVector();
            distance = distance.mul(1.0f / framesForCurrentMove);
            setCurrentPosition(getPosition().add(distance));
        } else {
            resetMotion();
        }
    }

    /// Implements Positionable

    @Override
    public Vector getVelocity() {
        // the velocity must be computed as the orientation vector (getOrientation().toVector() mutiplied by 
    	// framesForCurrentMove
        return getOrientation().toVector().mul(framesForCurrentMove);
    }

    // TODO : Ces implémentations simples peuvent s’avérer inadaptées lorsque plusieurs acteurs voisins se déplacent
    // (chevauchement des cellules quittées/investies). Libre à vous par la suite d’affiner ces méthodes
    // pour mieux répondre à vos besoins.
    protected final List<DiscreteCoordinates> getLeavingCells() {
        return getCurrentCells();
    }

    protected final List<DiscreteCoordinates> getEnteringCells() {
        return Collections.singletonList(this.getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    protected void setOrientation(Orientation orientation) {
        if (!isMoving) {
            super.setOrientation(orientation);
        }
    }
    
    protected boolean isMoving() {
    	return isMoving;
    }
    
}
