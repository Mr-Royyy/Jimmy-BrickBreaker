import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class brickpaddle
{

    //keep track of where the rectangle is.
    int recx = 280;
    int recy = 550;

    //keep track of the direction the rectangle is moving don't need a y because the paddle doesn't move up or down
    int recdeltaX = 0;
    //creates the paddle's width and height
    final int recwidth = 150;
    final int rechight = 10;

    Rectangle rectangle;

    /**
     * creates the paddle and starts the timer and mover
     * @param startX sets the paddle x-value
     * @param startY sets the paddle y-value
     */

    public brickpaddle(int startX, int startY)
    {
        recx = startX;
        recy = startY;

        rectangle = new Rectangle(recx, recy, recwidth, rechight);//makes the paddle

        rectangle.setFill(Color.BLACK);//fills in the rectangle black

        mover.start();
        timer.start();

    }


    AnimationTimer timer = new AnimationTimer() //creates the timer

    {
        /**
         * checks if the paddle leaves the max width and teleports it back
         * @param now
         *            The timestamp of the current frame given in nanoseconds. This
         *            value will be the same for all {@code AnimationTimers} called
         *            during one frame.
         */
        @Override
        public void handle(long now)
        {
            recx += recdeltaX;

            if(recx + recwidth >= BrickBreaker.MAX_WIDTH)
            {
                recx = BrickBreaker.MAX_WIDTH - recwidth;
            }
            if(recx <= 0)
            {
                recx = 0 ;
            }
            //update the coordinates of the shape itself.
            rectangle.setX(recx);
        }
    };



    EventHandler<KeyEvent> keyPressedEventHandler = event -> {
        /**
         * checks if the user presses wasd or arrow keys and moves accordingly
         * @param event the event which occurred
         */

        //KeyCode is an enumerator and works really well with a switch statement
        switch (event.getCode()) //only have to call getCode() once
        {
            case A: //don't have to type out KeyCode at all.
            case LEFT:
                recdeltaX = -7;
                break;
            case D:
            case RIGHT:
                recdeltaX = 7;
                break;
        }
    };

    EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>()
    {
        /**
         * checks if the user releases wasd or arrow keys and moves accordingly
         * @param event the event which occurred
         */
        @Override
        public void handle(KeyEvent event)
        {

            //when we release the key we stop moving in that direction
            switch (event.getCode())
            {
                case A:
                case LEFT:
                case D:
                case RIGHT:
                    recdeltaX = 0;
                    break;


            }
        }
    };

    AnimationTimer mover;

    {
        mover = new AnimationTimer() //creates the mover
        {
            /**
             * makes the rectangle/paddle move
             * @param now
             *            The timestamp of the current frame given in nanoseconds. This
             *            value will be the same for all {@code AnimationTimers} called
             *            during one frame.
             */
            @Override
            public void handle(long now)
            {
                recx += recdeltaX; //makes the paddle move
                //update the coordinates of the shape itself.
                rectangle.setX(recx);
            }
        };
    }
}


