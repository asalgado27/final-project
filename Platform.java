import java.awt.Color;
import java.awt.Graphics;

class Platform {
    // Fields indicating location and size of the platform
    Pair position;
    Pair dimensions;
    boolean personHere;

    // Array of ints to keep track of where any ladders on this platform are
    int ladderPos = 0; // NOTE: 0 means NO LADDER
    int ladderLength;
    static int ladderWidth = 52;

    // Field to keep track of Person object
    boolean hasPerson = false;

    // Field to keep track of door on platform
    Door door;
    Key key;

    // Field to keep track of which world the platform is in
    World world;

    // Constructor for default platform
    public Platform(World world, Pair position, Pair dimensions) {
        this.position = position;
        this.dimensions = dimensions;
        this.world = world;
    }

    public Platform(World world, Pair position, Pair dimensions, int ladderPos, int ladderLength) {
        this(world, position, dimensions);
        this.ladderPos = ladderPos;
        this.ladderLength = ladderLength;
    }

    // Draws the platform
    public void draw(Graphics g) {
        // Draw color of platform based on which world it is
        if (this.world.worldType.equals("Homebase")) {
            g.setColor(Color.RED);
            g.drawRect((int)position.x, (int)position.y, (int)dimensions.x, (int)dimensions.y);
        } else {
            g.setColor(Color.black);
            g.fillRect((int)position.x, (int)position.y, (int)dimensions.x, (int)dimensions.y);
        }

        // Draw any ladders (if it's not null)
        // Note that ladders are drawn downward from the platform they are established on
        if (ladderPos > 0) {
            g.setColor(Color.blue);
            g.drawRect(ladderPos, (int) position.y - 2, ladderWidth, ladderLength);
        }

        // Draw the key (if it's not null)
        if (this.key != null) {
            this.key.checkAndCollect();
            this.key.draw(g);
        }

        // Draw the door (if it's not null)
        if (this.door != null) {
            this.door.draw(g);
        }
    }
    public void counterPlus(){
        return;
    }

}
