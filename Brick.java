package Simple_Game;
// Rishi Shah; Mr Harwood Period 4 Grade 11 Comp Sci; Brick Breaker Game
import java.awt.Color;
import java.awt.Rectangle;

class Brick extends Rectangle {
    Color colour = Color.ORANGE; // brick color

    Brick(int x, int y){
        this.x = x;
        this.y = y;
        width = 90; // height and width of each brick
        height = 20;
    }
}
