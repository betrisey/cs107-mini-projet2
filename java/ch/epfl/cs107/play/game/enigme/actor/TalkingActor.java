package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AnimatedSprite;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.area.Level4;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class TalkingActor extends AreaEntity implements Interactable {
    private boolean showDialog;
    private String[] texts;
    private int textIndex;

    private AnimatedSprite characterSprite;
    private Dialog dialog;

    /**
     * Default AreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity in the Area. Not null
     * @param position    (DiscreteCoordinate): Initial position of the entity in the Area. Not null
     * @param characterSpriteName (String): Name of a sprite for a character, must have 4 orientations. Not null
     * @param texts (String[]): Texts to display. Not null, not empty
     */
    public TalkingActor(Area area, Orientation orientation, DiscreteCoordinates position, String characterSpriteName, String... texts) {
        super(area, orientation, position);

        showDialog = false;
        this.texts = texts;
        this.textIndex = 0;

        characterSprite = new AnimatedSprite(characterSpriteName, 0.7619f, 1, 16, 21,
                1, 0.3f, true, this, new Vector(0.12f, 0), 90);
        dialog = new Dialog(texts[textIndex], "dialog.1", area);
    }

	public void talk(Orientation orientation) {
        setOrientation(orientation.opposite());

        if (!showDialog) {
            showDialog = true;
        } else {
            if (dialog.push()) {
                showDialog = false;
                textIndex = (textIndex + 1) % texts.length;
                dialog.resetDialog(texts[textIndex]);
            }
        }
    }

    @Override
    protected void setOrientation(Orientation orientation) {
        super.setOrientation(orientation);
        characterSprite.setOrientation(getOrientation());
    }

    @Override
    public void draw(Canvas canvas) {
        characterSprite.draw(canvas);

        if (showDialog)
            dialog.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((EnigmeInteractionVisitor) v).interactWith(this);
    }
}
