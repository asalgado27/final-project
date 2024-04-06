import java.awt.Color;
import java.awt.Graphics;

class Platform {
    // Fields indicating location and size of the platform
    int xPos;
    int yPos;
    int width;
    int height;

    // Array of ints to keep track of where any ladders on this platform are
    int[] ladderPos;

    // Field to keep track of Person object
    boolean hasPerson;

    // Constructor for default platform
    public Platform(int xPos, int yPos, int width, int height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.hasPerson = false;
    }

    public Platform(int xPos, int yPos, int width, int height, int[] ladderPos) {
        this(xPos, yPos, width, height);

        this.ladderPos = ladderPos;
    }

    // Draws the platform
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(xPos, yPos, width, height);

        // Draw any ladders (if it's not null)
        if (ladderPos != null) {
            for (int x : this.ladderPos) {
                // Draws line indicating ladder's location
                g.setColor(Color.blue);
                g.drawLine(x, this.yPos, x, this.yPos - 30);
            }
        }
    }

    // Create method to change if the person is on the platform or not
}