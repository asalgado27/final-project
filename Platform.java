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

    // Field to keep track of door on platform
    Door door;
    Key key;

    // Constructor for default platform
    public Platform(Pair position, Pair dimensions) {
        this.position = position;
        this.dimensions = dimensions;
    }

    public Platform(Pair position, Pair dimensions, int ladderPos, int ladderLength) {
        this(position, dimensions);
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
            g.drawRect(ladderPos, (int)position.y-2, ladderWidth, ladderLength);
        }

        if (this.key != null) {
            this.key.checkAndCollect();
            this.key.draw(g);
        }

        // Draw the door (if it's not null)
        if (this.door != null) {
            this.door.draw(g);
        }
    }

}
