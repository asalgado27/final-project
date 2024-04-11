import java.awt.Color;
import java.awt.Graphics;

class Platform {
    // Fields indicating location and size of the platform
    Pair position;
    Pair dimensions;

    // Array of ints to keep track of where any ladders on this platform are
    int[] ladderPos;

    // Field to keep track of Person object
    boolean hasPerson;

    // Constructor for default platform
    public Platform(int xPos, int yPos, int width, int height) {
        position = new Pair(xPos, yPos);
        dimensions = new Pair(width, height);
        this.hasPerson = false;
    }

    public Platform(int xPos, int yPos, int width, int height, int[] ladderPos) {
        this(xPos, yPos, width, height);

        this.ladderPos = ladderPos;
    }

    // Draws the platform
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect((int)position.x, (int)position.y, (int)dimensions.x, (int)dimensions.y);

        // Draw any ladders (if it's not null)
        if (ladderPos != null) {
            for (int x : this.ladderPos) {
                // Draws line indicating ladder's location
                g.setColor(Color.blue);
                g.drawLine(x, (int)position.x, x, (int)position.y - 30);
            }
        }
    }

    // Create method to change if the person is on the platform or not
}