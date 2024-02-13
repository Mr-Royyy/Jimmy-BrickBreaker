import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BrickBreaker extends Application
{
    //creates variables for max width and height that can be used at any time
    static final int MAX_WIDTH = 800;
    final int MAX_HEIGHT = 600;

    //links code from paddle class to main
    brickpaddle Rectangle;

    //x and y coordinates of our ball.
    int ballx = 450;
    int bally = 540;

    //x and y speeds of the ball
    double deltaX = 2;
    double deltaY = 6;

    //by using a variable, I only have to change this one number to resize the ball.
    final int BALL_RADIUS = 10;

    //creates ball, so it can be printed later, uses the variables above
    Circle ball = new Circle(ballx, bally, BALL_RADIUS);

    //
    AnimationTimer timer;
    //Creates the brick array and is used later to create the bricks
    Brick[][] bricks = new Brick[5][10];

//    Brick[][] bricks2 = new Brick[3][6];
//    Brick[][] bricks3 = new Brick[4][4];

    // creates a start rectangle that can be placed over the screen before the game begins
    Rectangle gameStartRect = new Rectangle(0, 0, MAX_WIDTH, MAX_HEIGHT);
    //creates text that can be placed over the screen before the game begins
    Text gameStartText = new Text(250, 250, "Break Breaker");
    //creates text that can be placed over the screen before the game begins
    Text Start = new Text(185, 350, "Press Enter to Start ");


    // creates an over start rectangle that can be placed over the screen after the user loses the game
    Rectangle gameOverRect = new Rectangle(0, 0, MAX_WIDTH, MAX_HEIGHT);
    //creates text that can be placed over the screen after the user loses
    Text gameOverText = new Text(255, 250, "Game Over");
    //creates text that can be placed over the screen after the user loses that tells then that enter restarts the game
    Text restart = new Text(155, 350, "Press Enter to Restart ");

    /**
     * checks if the ball intercepts with the bricks, and if the ball intercepts with the paddle
     * if it intercepts with a brick the brick disappears and bounces back, if it intercepts with the paddle it bounces back
     *
     */
    private void checkForCollision()
    {
        for (int i = 0; i < bricks.length; i++)
        {
            for (int j = 0; j < bricks[i].length; j++)
            {
                if (bricks[i][j].brick.isVisible() && ball.intersects(bricks[i][j].brick.getBoundsInLocal()))
                {
                    deltaY = -deltaY;
                    bricks[i][j].brick.setVisible(false);
                }
            }
        }

        if (ball.intersects(Rectangle.rectangle.getBoundsInLocal()))
        {
            deltaY = -deltaY;
            bally = Rectangle.recy - BALL_RADIUS;
            ball.setCenterY(bally);
        }
    }

   
    @Override
    public void start(Stage primaryStage)
    {
        Group root = new Group();
        Scene scene = new Scene(root, MAX_WIDTH, MAX_HEIGHT);//creates the scene using the max width and max height variables


        Rectangle = new brickpaddle( 350, 550);//creates the paddle using the rectangle we got from the paddle class
        root.getChildren().add(Rectangle.rectangle);//adds the paddle


        ball.setFill(Color.RED);//makes the ball color red
        ball.setStroke(Color.BLACK);//makes the ball outline black
        root.getChildren().add(ball);//adds ball

        gameStartRect.setFill(Color.WHITE);//makes the rectangle on top of the screen white
        gameStartText.setFont(new Font(50));//sets the font size to 50
        Start.setFont(new Font(50));//sets the font size to 50

        gameStartRect.setVisible(true);//makes the start rectangle on top  of the screen visible
        gameStartText.setVisible(true);//makes the start text on the screen visible
        Start.setVisible(true);//makes the start text on the screen visible

        root.getChildren().add(gameStartRect);//adds the rectangle
        root.getChildren().add(gameStartText);//adds the start text
        root.getChildren().add(Start);//adds the start text

        gameOverRect.setFill(Color.WHITE);//makes the rectangle on top of the screen white
        gameOverText.setFont(new Font(50));//sets the font size to 50
        restart.setFont(new Font(50));//sets the font size to 50


        gameOverRect.setVisible(false);//makes the game over rectangle on top  of the screen not visible, this will be visible once the game is over
        gameOverText.setVisible(false);//makes the game over text on the screen visible, this will be visible once the game is over
        restart.setVisible(false);//makes the restart text on the screen visible, this will be visible once the game is over

        root.getChildren().add(gameOverRect);//adds the rectangle
        root.getChildren().add(gameOverText);//adds the start text
        root.getChildren().add(restart);//adds the start text

        //makes the bricks and spaces them out how you like
        for (int i = 0; i < bricks.length; i++)
        {
            for (int j = 0; j < bricks[i].length; j++)
            {
                bricks[i][j] = new Brick(i*170, j * 40);
                root.getChildren().add(bricks[i][j].brick);
            }
        }

        /**
         * Makes the code check collision by using the coalition detection code
         */
        AnimationTimer timer1 = new AnimationTimer()//creates the timer

        {
            @Override
            public void handle(long now)
            {
                checkForCollision();
            }
        };
        timer1.start();//starts the timer


        /**
         * checks if the user has pressed enter, if so game starts or ends. It also teleports the ball and paddle to it original spots
         * @param KeyEvent checks if enter is pressed and either starts the game or restarts it
         */
        EventHandler<KeyEvent> gameOverHandler = new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {

                if(event.getCode() == KeyCode.ENTER)
                {
                    scene.setOnKeyPressed(Rectangle.keyPressedEventHandler);
                    timer1.start();//starts timer1 again
                    timer.start();//starts timer again


                    gameOverRect.setVisible(false);
                    gameOverText.setVisible(false);
                    restart.setVisible(false);

                    gameStartRect.setVisible(false);
                    gameStartText.setVisible(false);
                    Start.setVisible(false);

                    //updates the ball coordinates to the original ones
                    ballx = 450;
                    bally = 540;

                    //updates the rectangle coordinates to the original ones
                    Rectangle.recx = 350;
                    Rectangle.recy = 550;


                }

            }
        };

        //to make the movement smooth we use an AnimationTimer to constantly update the
        //x and y coordinates of the rectangle.
        /**
         * @param now
         */
        timer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {


                //update the x and y coordinates based on current speed.
                ballx += deltaX;
                bally += deltaY;


                //update the coordinates of the shape itself.
                ball.setCenterX(ballx);
                ball.setCenterY(bally);


                //check if we need to bounce left-right
                if(ballx + BALL_RADIUS >= MAX_WIDTH || ballx - BALL_RADIUS <= 0)
                {
                    //negative becomes positive and vice-versa.
                    deltaX *= -1;
                }
                //check if we need to bounce up
                if(bally + BALL_RADIUS >= MAX_HEIGHT)
                {

                    for (int i = 0; i < bricks.length; i++)
                    {
                        for (int j = 0; j < bricks[i].length; j++)
                        {
                            bricks[i][j].brick.setVisible(true);
                        }
                    }
                    deltaY = -deltaY;

                    timer1.stop();
                    gameOverRect.setVisible(true);
                    gameOverRect.toFront();
                    gameOverText.setVisible(true);
                    gameOverText.toFront();
                    restart.setVisible(true);
                    restart.toFront();

                    scene.setOnKeyPressed(gameOverHandler);
                }


                //check if we need to bounce down
                if(bally - BALL_RADIUS <= 0)
                {
                    deltaY = -deltaY;
                }
            }
        };
        //the scene handles keyboard input
        scene.setOnKeyPressed(gameOverHandler);
        scene.setOnKeyReleased(Rectangle.keyReleasedEventHandler);


        primaryStage.setTitle("Brick Breaker");
        primaryStage.setScene(scene);
        primaryStage.show();

        //brings gamestartrect to the front, so it can be seen in the front
        gameStartRect.toFront();
        gameStartText.toFront();
        Start.toFront();

    }
}

