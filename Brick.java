import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Brick
{
    //uses these to create the rectangle coordinates
    int x;
    int y;
    //creates the brick length and width
    int brickw = 135;
    int brickh = 25;

    Rectangle brick;

    /**
     * Creates the brick size and color
     *
     * @param startX returns the starting x-value for the bricks
     * @param startY returns the starting y-value for the bricks
     */
    public Brick(int startX, int startY)
    {
        x = startX;
        y = startY;
        //creates the bricks
        brick = new Rectangle(x, y, brickw, brickh);
        Random r = new Random();
        //makes a random color that is used by the bricks
        int red = r.nextInt(256);
        int green = r.nextInt(256);
        int blue = r.nextInt(256);

        //uses the random colors to fill teh bricks, all bricks are colored randomly
        brick.setFill(Color.rgb(red, green, blue));
        //makes a black outline around the bricks
        brick.setStroke(Color.BLACK);


    }
}