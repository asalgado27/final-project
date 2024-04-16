import java.awt.Color;
import java.awt.Graphics;

class Door {
    // Fields indicating location and size of the platform
    Pair position;
    static Pair dimensions = new Pair(75, 110);

    public Door(int xPos, int yPos) {
        position = new Pair(xPos, yPos);
    }

    // Draws the platform
    public void draw(Graphics g) {
        g.setColor(new Color(150, 75, 0));
        g.drawRect((int)position.x, (int)position.y, (int)dimensions.x, (int)dimensions.y);
    }
}