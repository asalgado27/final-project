import java.awt.Color;
import java.awt.Graphics;

class Platform {
    // Fields indicating location and size of the platform
    Pair position;
    Pair dimensions;

    // Array of ints to keep track of where any ladders on this platform are
    int ladderPos = 0; // NOTE: 0 means NO LADDER
    int ladderLength;
    static int ladderWidth = 52;

    // Field to keep track of Person object
    boolean hasPerson = false;

    // Constructor for default platform
    public Platform(int xPos, int yPos, int width, int height) {
        position = new Pair(xPos, yPos);
        dimensions = new Pair(width, height);
    }

    public Platform(int xPos, int yPos, int width, int height, int ladderPos, int ladderLength) {
        this(xPos, yPos, width, height);
        this.ladderPos = ladderPos;
        this.ladderLength = ladderLength;
    }

    // Draws the platform
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect((int)position.x, (int)position.y, (int)dimensions.x, (int)dimensions.y);

        // Draw any ladders (if it's not null)
        if (ladderPos > 0) {
            g.setColor(Color.blue);
            g.drawRect(ladderPos, (int)position.y, ladderWidth, ladderLength);
        }
    }

    // Create method to change if the person is on the platform or not
}