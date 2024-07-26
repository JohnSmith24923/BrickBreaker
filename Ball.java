package Simple_Game;
// Rishi Shah; Mr Harwood Period 4 Grade 11 Comp Sci; Brick Breaker Game
import java.awt.Color;
import java.awt.Rectangle;

class Ball extends Rectangle {
    Color colour = Color.CYAN; // Circles start blue
    double vx = 2, vy = 1.5;        //speeds

    Ball(int x, int y){
        this.x = x;
        this.y = y;
        width = height = 20; // All circles will start with the same size
    }
}
