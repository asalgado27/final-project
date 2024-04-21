import java.awt.Color;
import java.awt.Graphics;

class Door {
    Pair position;
    static Pair dimensions = new Pair(75, 110);

    public Door(Pair position) {
        this.position = position;
    }

    // Draws the platform
    public void draw(Graphics g) {
        g.setColor(new Color(150, 75, 0));
        g.drawRect((int)position.x, (int)position.y, (int)dimensions.x, (int)dimensions.y);
    }
}
