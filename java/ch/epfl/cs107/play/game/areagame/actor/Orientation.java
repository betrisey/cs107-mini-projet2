package ch.epfl.cs107.play.game.areagame.actor;

import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Keyboard;


public enum Orientation {

    /// Enumeration elements
    DOWN (new Vector(0.0f, -1.0f), Keyboard.DOWN),
    LEFT (new Vector(-1.0f, 0.0f), Keyboard.LEFT),
    UP (new Vector(0.0f, 1.0f), Keyboard.UP),
    RIGHT ( new Vector(1.0f, 0.0f), Keyboard.RIGHT);


    /// Direction of the Orientation
    private final Vector direction;

    private final int keyCode;
    public int getKeyCode() {
        return keyCode;
    }

    /**
     * Default Orientation constructor
     * @param direction (Vector). Not null
     */
    Orientation(Vector direction, int keyCode){
        this.direction = direction;
        this.keyCode = keyCode;
    }

    /**
     * Return the opposite Orientation
     * @return (Orientation): the opposite orientation Down:Up, Right:Left
     */
    public Orientation opposite(){
        return Orientation.values()[(ordinal()+2)%4];
    }

    /** @return (Orientation): the orientation on the left of this*/
    public Orientation hisLeft(){
        // Be careful, % return the reminder and not the modulus i.e. could be negative
        // It is why we do this trick +4)%4
        return Orientation.values()[(((ordinal()-1)%4)+4)%4];
    }

    /** @return (Orientation): the orientation on the right of this*/
    public Orientation hisRight(){
        return Orientation.values()[(ordinal()+1)%4];
    }

    /**
     * Convert an orientation into vector
     * @return (Vector)
     */
    public Vector toVector(){
        return direction;
    }


    @Override
    public String toString(){
        return super.toString()+direction.toString();
    }
}
