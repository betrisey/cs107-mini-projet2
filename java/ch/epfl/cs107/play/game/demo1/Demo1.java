package ch.epfl.cs107.play.game.demo1;

import java.awt.Color;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.GraphicsEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class Demo1 implements Game {
	private Actor actor1;
	private MovingRock movingRock;
	private Window window;
	private FileSystem fileSystem;
	private final float radius=0.2f;
	

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		float radius = 0.2f;
		this.window=window;
		this.fileSystem=fileSystem;
	    actor1 =new GraphicsEntity(Vector.ZERO,
				new ShapeGraphics(new Circle(radius), null,
				Color.RED, 0.005f));
	    movingRock=new MovingRock(new Vector(0f,0.1f),"Hello I'm movingRock");
	   
		return true;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTitle() {
		
		return "Demo1";
	}

	@Override
	public void update(float deltaTime) {
		
		// ici donner un peu de vie au premier acteur si nécessaire
		actor1.draw(window);
		movingRock.draw(window);

        Keyboard keyboard = window.getKeyboard();
        Button downArrow = keyboard.get(Keyboard.DOWN);
        if(downArrow.isDown()) {
        	movingRock.update(deltaTime);
        	System.out.println("Boom");
        		}
       
        float deltaPosition=((movingRock.getPosition().sub(actor1.getPosition())).getLength());
        if(deltaPosition<radius) {
            System.out.println("Boom");
            movingRock.setCollision(true);
          }else {
        	  movingRock.setCollision(false);
          }
        	
        //text.setParent(movingRock);		
	}     
        		
	
	
	

	@Override
	public int getFrameRate() {
	
		return 24;
	}
  
}
