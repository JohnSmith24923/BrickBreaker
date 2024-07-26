package Simple_Game;

// Rishi Shah; Mr Harwood Period 4 Grade 11 Comp Sci; Brick Breaker Game

import java.awt.Toolkit;
import hsa2.GraphicsConsole;
import java.awt.Color;
import java.awt.Font;
import java.lang.Math;


public class Brick_breaker {
    public static void main (String[] args){
        new Brick_breaker().run();
    }
    // Make the game full screen 
    static Toolkit tk = Toolkit.getDefaultToolkit();  
    static int SCRW = ((int) tk.getScreenSize().getWidth());  
    static int SCRH = ((int) tk.getScreenSize().getHeight());
    GraphicsConsole gc = new GraphicsConsole(SCRW, SCRH,"Brick Breaker");

    // Calling of the objects
    Paddle p1;
    Brick b1;
    Ball ba1;

    // Global Variables
    int sleepTime = 5;
    int mx=-200,my=-200; //mouse x and y location starts off the screen
    int Num_of_bricks = 105;
    Brick [] bricks = new Brick[Num_of_bricks];
    int brickx = SCRW/2 - 100;
    int score = 0;

    Brick_breaker(){
        setup();
        // Make the objects
        p1 = new Paddle(500,SCRH-100);
        b1 = new Brick(brickx,SCRH/2-100);
        ba1 = new Ball(SCRW/2,400);
    }

    void setup(){
        gc.setLocationRelativeTo(null);
		gc.setAntiAlias(true);

        // Background 
        Color Background = new Color(0,0,0);
        gc.setBackgroundColor(Background);
        gc.clear();

        // Mouse
        gc.enableMouseMotion(); //detect mouse movement
        gc.enableMouse(); //mouse click
    }

    void run() {
        // Runs the begin_game method to show the starting screen
        Begin_game();

        // Creates the bricks which will get drawn later
        make_bricks();

        while (true){
            // First sees if there has been any collisions and then updates the speed and score
            checkBrickCollision();
            speed();
            score();

            //track mouse movement
            mx = gc.getMouseX();
            
            // Moves the paddle and ball
            Move_paddle(p1);
            Move_ball(ba1);

            // Draws everything onto the screen
            drawGraphics();
            gc.sleep(sleepTime);

            // Checks if the ball and paddle hit 
            ball_paddle_collision();

            // This sees if the ball hits the bottom of the screen and then sees the number of bricks left. If the number of bricks is 0, it will leave the while true loop
            if (ba1.y >= SCRH){
                break;
            }
            if (Num_of_bricks == 0){
                break;
            }
        }
        // If the number of bricks is 0, the winner text wil be printed onto the screen
        if (Num_of_bricks == 0){
            winner();
        }
        // if the number of bricks is greater than 0 but the ball has hit the ground, it will print the game over text
        if (Num_of_bricks > 0){
            Game_over();
        }
    }

    void Move_paddle(Paddle p1){
        // Makes the paddle's x values match where the x value of the cursor is which will move the platform according to cursor
        p1.x = mx;
    }

    void Move_ball(Ball ba1){
        // Makes the ball's x values change depending on the speed created by the ball object. 
        ba1.x += ba1.vx;
        ba1.y += ba1.vy;

        // checks boundaries of the screen: If the ball hits the sides of the screen except for the ground, the ball will bounce away
        if (ba1.x >= SCRW){
            ba1.vx *= -1;
        }
        if(ba1.x <=0){
            ba1.vx *= -1;
        }
        if (ba1.y <= 0){
            ba1.vy *= -1;
            ba1.vx ++;
        }
    }
    void make_bricks(){
        // Creates the number of columns for the bricks 
        int columns = 15; 

        // Determines where the bricks will be made relative too
        int top_start = 17;
        int left_start = 10;

        // Space between the bricks
        int spacing = 12; 

        // Creates a brick per number of bricks
        for (int i = 0; i < Num_of_bricks; i++){
            int x = (90 + spacing ) * (i%columns) + top_start;
            int y = (20 + spacing) * (i/columns) + left_start; 
            bricks[i] = new Brick(x,y);
        }
    }

    boolean ball_paddle_collision(){
        // Checks if the ball hits the paddles. if the ball hits, the ball will bounce off the paddle
        if (ba1.intersects(p1)){
            ba1.vy *=-1;
        }
        return false;
    }

    void checkBrickCollision(){
        // References for every single brick
        for (int i = 0; i < bricks.length; i++) {
            
            // Every brick that gets hit, shouldn't be hit already
            if (bricks[i] != null){
                
                // Sees if the ball hits any of the bricks
                if (ba1.intersects(bricks[i])){
                    
                    // Renounces the brick as null now
                    bricks[i] = null; 

                    // Bounces the ball off the brick
                    ba1.vy *= -1; 

                    // Lowers the number of bricks so if the number of bricks reaches 0, the winner method can be called and print that you have won
                    Num_of_bricks--;

                    // Increases the speed to make the game more difficult for every brick that is hit
                    ba1.vx += 0.5;
                    ba1.vy += 0.5; 

                    // This is the scoring method as the longer you play the game, the harder it gets. Speed 15 is a lot harder than speed 1 so speed 15 should reward more points compared to speed 1. Anything above speed 20 gets 15 points
                    if (ba1.vy >= 1) {
                        if (ba1.vy < 5) {
                            score += 1; // add 1 point for speeds between 1 and 5
                        } else if (ba1.vy < 10) {
                            score += 3; // add 3 points for speeds between 5 and 10
                        } else if (ba1.vy < 15) {
                            score += 5; // add 5 points for speeds between 10 and 15
                        } else if (ba1.vy < 20) {
                            score += 7; // add 7 points for speeds between 15 and 20
                        } else {
                            score += 15; // add 10 points for speeds of 20 or higher
                        }
                    }
                }
            }   
        }
    }

    void speed(){
        // This prints the text about speed onto the screen
        gc.setColor(Color.white);
        Font Speed = new Font ( "Arial", Font.BOLD, 40);
        gc.setFont(Speed);
        gc.drawString("Speed: " + Math.abs(ba1.vy), SCRW/2-100, SCRH-40);
    }

    void score(){
        // Prints the score text onto the screen
        gc.setColor(Color.white);
        Font Score = new Font ( "Arial", Font.BOLD, 40);
        gc.setFont(Score);
        gc.drawString("Score: " + score, SCRW/2-400, SCRH-40);
    }
    void drawGraphics(){
        // Makes the game run smoothly
        synchronized(gc){
            // Clears the screen
            gc.clear();
            // Creates the paddle
            gc.setColor(p1.colour);
            gc.fillRect(p1.x,p1.y,p1.width,p1.height);

            // Creates the Bricks
            for (int i = 0; i < bricks.length; i++) {
                if (bricks[i] != null){
                    gc.setColor(bricks[i].colour);
                    gc.fillRect(bricks[i].x,bricks[i].y, bricks[i].width, bricks[i].height);
                }
            }

            // Creates the Ball
            gc.setColor(ba1.colour);
            gc.fillOval(ba1.x,ba1.y, ba1.width, ba1.height);
        }
    }

    boolean Begin_game(){
        // This is the code for the starting screen where you have to press on the screen to load to the main game loop. 
        gc.setColor(new Color(255,255,255));
        Font Startgame = new Font ( "Arial", Font.BOLD, 50);
        gc.setFont(Startgame);
        gc.drawString("Welcome to Brick Breaker!! ", SCRW/2-325, SCRH/2+50);
        Font Caption = new Font ( "Arial", Font.BOLD, 25);
        gc.setFont(Caption);
        gc.drawString("Click the Screen to Start ", SCRW/2-140, SCRH/2+100);
        // The while true loop will keep this text on the screen until the mouse is clicked and then will begin the main loop
        while(true){
            if (gc.getMouseClick() > 0) {
                return true;
            }
            gc.sleep(50);
        }
    }

    void winner(){
        // This text comes up if all the bricks are hit and gone and pronounces you as a winner
        // Extremely hard to acheive :)
        gc.clear(); 
        gc.setColor(Color.WHITE);
        Font Winner = new Font ( "Arial", Font.BOLD, 100);
        gc.setFont(Winner);
        gc.drawString("YOU HAVE WON! ", SCRW/2-325, SCRH/2+50);
        // Keeps the speed text on the screen
        speed();
        // Keeps the score text on the screen
        score();
    }
    void Game_over(){
        // This text comes up if the ball hits the bottom of the screen and goes past the paddle
        gc.clear(); 
        gc.setColor(Color.WHITE);
        Font Gameover = new Font ( "Arial", Font.BOLD, 100);
        gc.setFont(Gameover);
        gc.drawString("GAME OVER ", SCRW/2-325, SCRH/2+50);
        // Keeps the speed text on the screen
        speed();
        // Keeps the score text on the screen
        score();
    }
}
