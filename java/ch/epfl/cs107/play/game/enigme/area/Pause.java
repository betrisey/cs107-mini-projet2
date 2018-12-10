package ch.epfl.cs107.play.game.enigme.area;

import ch.epfl.cs107.play.game.actor.GraphicsEntity;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.enigme.Enigme;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

import java.awt.*;

public class Pause extends Area {
    private Window window;

    @Override
    public float getCameraScaleFactor() {
        return Enigme.CAMERA_SCALE_FACTOR;
    }

    @Override
    public String getTitle() {
        return "Pause";
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        super.begin(window, fileSystem);

        this.window = window;

        GraphicsEntity pauseGraphics = new GraphicsEntity(new Vector(-7, 3), new TextGraphics("PAUSE", 3f, Color.WHITE));
        registerActor(pauseGraphics);

        GraphicsEntity continueGraphics = new GraphicsEntity(new Vector(-7, -2), new TextGraphics("press P to continue", 1.2f, Color.WHITE));
        registerActor(continueGraphics);

        return true;
    }
}
