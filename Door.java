import java.awt.Color;
import java.awt.Graphics;

class Door {
    // Fields indicating location and size of the platform
    int xPos;
    int yPos;
    int width;
    int height;

    public Door(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = 100;
        this.height = 125;
    }

    // Draws the platform
    public void draw(Graphics g) {
        g.setColor(new Color(150, 75, 0));
        g.fillRect(xPos, yPos, width, height);
    }
}