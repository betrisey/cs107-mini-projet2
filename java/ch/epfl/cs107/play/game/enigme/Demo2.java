package ch.epfl.cs107.play.game.enigme;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.actor.demo2.Demo2Player;
import ch.epfl.cs107.play.game.enigme.area.demo2.Demo2Area;
import ch.epfl.cs107.play.game.enigme.area.demo2.Room0;
import ch.epfl.cs107.play.game.enigme.area.demo2.Room1;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;

public class Demo2 extends AreaGame {
    public static final int CAMERA_SCALE_FACTOR = 22;

    private Demo2Player player;
    private Demo2Area room0, room1;

    @Override
    public int getFrameRate() {
        return 24;
    }

    @Override
    public String getTitle() {
        return "Demo2";
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        super.begin(window, fileSystem);

        room0 = new Room0();
        room1 = new Room1();
        addArea(room0);
        addArea(room1);

        setCurrentArea(room0.getTitle(), false);
        player = new Demo2Player(room0, Orientation.DOWN, room0.getStartPosition());
        player.enterArea(room0, room0.getStartPosition());
        room0.setViewCandidate(player);

        return true;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (player.isPassingDoor()) {
            Area prevArea = getCurrentArea();
            Demo2Area nextArea = (prevArea == room0) ? room1 : room0;

            player.leaveArea(prevArea);

            setCurrentArea(nextArea.getTitle(), true);
            player.enterArea(nextArea, nextArea.getStartPosition());
            nextArea.setViewCandidate(player);
        }
    }
}
