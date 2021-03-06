/* * * * * * * * * * * * * * * * * *
* PROGRAMMER: CHARLES FAHSELT
*
* COURSE: CINF 4388 SENIOR PROJECT 2015
*
* PURPOSE: Shows an indicator which presents roll and pitch information.
*
 * * * * * * * * * * * * * * * * * */
package edu.uhcl.team_drone.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.drone.Drone;

// This class is responsible for updating and displaying the HUD's 
// attitude indicator, which displays Pitch and Roll information
public class AttitudeIndicator {

    private static final Vector2 INDICATOR_SIZE = new Vector2(190, 190);

    // portion of the indicator that moves
    private Image indicatorMoving;

    // keeps track of the initial position for the moving indicator
    private float indicatorHomePos;

    public AttitudeIndicator(Table table) {

        // container that holds two objects on top of each other
        Stack stack = new Stack();

        // Background for the indicator : add it to stack
        Image indicatorStaticBlueBG = new Image(
                Assets.manager.get("2d/hud/attitudeIndicator/background.png", Texture.class));
        stack.add(indicatorStaticBlueBG);

        // moving portion of the attitude indicator, add to stack
        indicatorMoving = new Image(
                Assets.manager.get("2d/hud/attitudeIndicator/inner-lines.png", Texture.class));
        stack.add(indicatorMoving);

        // static portion of indicator, add to stack
        Image indicatorStatic = new Image(
                Assets.manager.get("2d/hud/attitudeIndicator/outer.png", Texture.class));
        indicatorStatic.setOrigin(indicatorStatic.getWidth() / 2, indicatorStatic.getHeight() / 2);
        stack.add(indicatorStatic);

        // position the stack into the table
        table.add(stack).size(INDICATOR_SIZE.x, INDICATOR_SIZE.y);

        indicatorMoving.setOrigin(INDICATOR_SIZE.x / 2, INDICATOR_SIZE.y / 2);
        table.right().bottom();
        indicatorHomePos = indicatorMoving.getY();
    }

    protected void update(Drone droneIn) {
        // update rotation
        float rotation = droneIn.gyroCmpnt.getCurrentRoll() * 45;
        indicatorMoving.setRotation(-rotation);

        // update pitch
        float pitch = droneIn.gyroCmpnt.getCurrentPitch();
        indicatorMoving.setPosition(
                indicatorMoving.getX(),
                indicatorHomePos + (pitch * 45));
    }
}
