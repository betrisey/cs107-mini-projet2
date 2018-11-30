package ch.epfl.cs107.play.game.demo1;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.GraphicsEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.demo1.actor.MovingRock;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.awt.*;

public class Demo1 implements Game {
    private Window window;
    private FileSystem fileSystem;

    private Actor actor1;
    private MovingRock movingRock;

    private final float circleRadius = 0.2f;

    @Override
    public int getFrameRate() {
        return 24;
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        this.window = window;
        this.fileSystem = fileSystem;

        actor1 = new GraphicsEntity(Vector.ZERO, new ShapeGraphics(new Circle(circleRadius), null, Color.RED, 0.005f));
        movingRock = new MovingRock(new Vector(0.2f, 0.2f), "Hello, I'm a moving rock!");

        return true;
    }

    @Override
    public void end() {

    }

    @Override
    public String getTitle() {
        return "Demo1";
    }

    @Override
    public void update(float deltaTime) {
        actor1.draw(window);

        // Update movingRock state
        Keyboard keyboard = window.getKeyboard();
        Button downArrow = keyboard.get(Keyboard.DOWN);
        if (downArrow.isDown()) {
            movingRock.update(deltaTime);
        }

        float distance = actor1.getPosition().sub(movingRock.getPosition()).getLength();
        movingRock.setCollision(distance < circleRadius);

        movingRock.draw(window);
    }
}
