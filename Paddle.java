package Simple_Game;
// Rishi Shah; Mr Harwood Period 4 Grade 11 Comp Sci; Brick Breaker Game
import java.awt.Color;
import java.awt.Rectangle;

class Paddle extends Rectangle {
    Color colour  = Color.WHITE; // Paddle is white

    Paddle(int x, int y){
        this.x = x;
        this.y = y;
        width = 150; // width and height of the paddle
        height = 15;
    }
}
