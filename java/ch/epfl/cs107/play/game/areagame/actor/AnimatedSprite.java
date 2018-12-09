package ch.epfl.cs107.play.game.areagame.actor;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

/**
 * Extension: animations
 *
 */
public class AnimatedSprite implements Graphics {
    private Sprite[][] sprites; // first index is orientation, second the index of the animation
    private float timePerSprite; // how long we'll show a sprite
    private int currentOrientation;
    private int currentIndex;
    private float elapsedTime; // keeping track of the elapsedTime since the last change of sprite
    private final boolean orientable;

    /**
     * Private constructor to initialize what's common to the other constructors
     * @param timePerSprite (float): how long each frame should last
     * @param orientable (boolean): whether it is orientable or only has a single orientation
     */
    private AnimatedSprite(float timePerSprite, boolean orientable) {
        this.currentIndex = 0;
        this.elapsedTime = 0;
        this.currentOrientation = 0;
        this.timePerSprite = timePerSprite;
        this.orientable = orientable;
    }

    /**
     * Create a new AnimatedSprite from a single file that contains all the images
     * a column must contain {@spritePerOrientation} images, each image must be of dimension {@pixelWidth}x{@pixelHeight}
     * If it is {@orientable} it must contains 4 columns each where column is an orientation. The order is the same
     * as in the enum {@see Orientation}
     * @param name (String): image name, not null
     * @param width (float): actual image width, before transformation
     * @param height (float): actual image height, before transformation
     * @param pixelWidth (int): width of each image
     * @param pixelHeight (int): height of each image
     * @param spritePerOrientation (int): number of sprites used by the animation
     * @param timePerSprite (float): how long each frame should last
     * @param orientable (boolean): whether it is orientable or only has a single orientation
     * @param parent (Positionable): parent of this, not null
     * @param anchor (Vector): image anchor, not null
     */
    public AnimatedSprite(String name, float width, float height, int pixelWidth, int pixelHeight, int spritePerOrientation,
                          float timePerSprite, boolean orientable, Positionable parent, Vector anchor, float depthCorrection) {
        this(timePerSprite, orientable);
        int orientationCount = orientable ? Orientation.values().length : 1;
        sprites = new Sprite[orientationCount][spritePerOrientation];

        for (int o = 0; o < orientationCount; o++) {
            for (int i = 0; i < spritePerOrientation; i++) {
                sprites[o][i] = new Sprite(name, width, height, parent,
                        new RegionOfInterest(o * pixelWidth, i * pixelHeight, pixelWidth, pixelHeight),
                        anchor, 1.0f, depthCorrection);
            }
        }
    }

    /**
     * Create a new AnimatedSprite from multiple files that each contain a single image
     * a column must contain {@spritePerOrientation} images, each image must be of dimension {@pixelWidth}x{@pixelHeight}
     * If it is {@orientable} it must contains 4 columns each where column is an orientation. The order is the same
     * as in the enum {@see Orientation}
     * @param timePerSprite (float): how long each frame should last
     * @param parent (Positionable): parent of this, not null
     * @param spriteNames (String[]): name of all the sprites composing the animation, not null and not empty
     */
    public AnimatedSprite(float timePerSprite, Positionable parent, String... spriteNames) {
        this(timePerSprite, false);

        sprites = new Sprite[1][spriteNames.length];
        for (int i = 0; i < spriteNames.length; i++) {
            sprites[0][i] = new Sprite(spriteNames[i], 1, 1, parent);
        }
    }

    /**
     * Must be called before draw when the associated Actor changes orientation
     * @param orientation the new orientation
     */
    public void setOrientation(Orientation orientation) {
        if (orientable) this.currentOrientation = orientation.ordinal();
    }

    /**
     * Set a specific image of the orientation
     * for example when a character stops moving, we may want to set an image where he's standing still
     * @param spriteIndex index of the image
     */
    public void setSpriteIndex(int spriteIndex) {
        if (spriteIndex >= 0 && spriteIndex < sprites[currentOrientation].length)
            this.currentIndex = spriteIndex;
    }

    /**
     * Must be called periodically when we want to animate the sprite
     * @param deltaTime elapsed time since the last call to this method
     */
    public void update(float deltaTime) {
        elapsedTime += deltaTime;

        int framesToAdd = (int)(elapsedTime/timePerSprite);
        currentIndex = (currentIndex + framesToAdd) % sprites[currentOrientation].length;

        elapsedTime = elapsedTime % timePerSprite;
    }

    /**
     * Draws on the canvas the appropriate sprite
     * @param canvas target, not null
     */
    @Override
    public void draw(Canvas canvas) {
        sprites[currentOrientation][currentIndex].draw(canvas);
    }
}
