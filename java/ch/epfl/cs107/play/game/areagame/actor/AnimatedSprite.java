package ch.epfl.cs107.play.game.areagame.actor;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class AnimatedSprite implements Graphics {
    private Sprite[][] sprites;
    private float timePerSprite;
    private int currentOrientation;
    private int currentIndex;
    private float elapsedTime;
    private final boolean orientable;

    private AnimatedSprite(float timePerSprite, boolean orientable) {
        this.currentIndex = 0;
        this.elapsedTime = 0;
        this.currentOrientation = 0;
        this.timePerSprite = timePerSprite;
        this.orientable = orientable;
    }

    public AnimatedSprite(String name, float width, float height, int imageWidth, int imageHeight, float timePerSprite, boolean orientable, Positionable parent) {
        this(timePerSprite, orientable);
        int orientationCount = orientable ? Orientation.values().length : 1;
        sprites = new Sprite[orientationCount][4];

        Vector anchor = new Vector(0.25f, 0.32f);
        for (int o = 0; o < orientationCount; o++) {
            for (int i = 0; i < 4; i++) {
                sprites[o][i] = new Sprite(name, width, height, parent,
                        new RegionOfInterest(o * imageWidth, i * imageHeight, imageWidth, imageHeight), anchor);
            }
        }
    }

    public AnimatedSprite(float timePerSprite, Positionable parent, String... spriteNames) {
        this(timePerSprite, false);
        sprites = new Sprite[1][spriteNames.length];
        for (int i = 0; i < spriteNames.length; i++) {
            sprites[0][i] = new Sprite(spriteNames[i], 1, 1, parent);
        }
    }

    public void setOrientation(Orientation orientation) {
        if (orientable) this.currentOrientation = orientation.ordinal();
    }

    public void setSpriteIndex(int spriteIndex) {
        this.currentIndex = spriteIndex;
    }

    public void update(float deltaTime) {
        elapsedTime += deltaTime;

        int framesToAdd = (int)(elapsedTime/timePerSprite);
        currentIndex = (currentIndex + framesToAdd) % sprites[currentOrientation].length;

        elapsedTime = elapsedTime % timePerSprite;
    }

    @Override
    public void draw(Canvas canvas) {
        sprites[currentOrientation][currentIndex].draw(canvas);
    }
}
