import java.awt.Color;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

class Platform {
    // Fields indicating location and size of the platform
    Pair position;
    Pair dimensions;
    // Keeps track of whether person is here
    boolean personHere;

    // Images for the platform
     Image lavaPlatform = null;
     Image treePlatform = null;
     Image skyPlatform = null;

    boolean visible = true;

    // Array of ints to keep track of where any ladders on this platform are
    int ladderPos = 0; // NOTE: 0 means NO LADDER
    int ladderLength;
    static int ladderWidth = 52;

    // Field to keep track of door on platform
    public Door door;
    Key key;

    // Field to keep track of which world the platform is in
    World world;

    // Constructor for default platform
    public Platform(World world, Pair position, Pair dimensions) {
        this.position = position;
        this.dimensions = dimensions;
        this.world = world;

        try {
            lavaPlatform = ImageIO.read(Main.class.getResource("Lava.png"));
            lavaPlatform = lavaPlatform.getScaledInstance((int)dimensions.x,(int)dimensions.y, 1);
            treePlatform = ImageIO.read(Main.class.getResource("Branch.png"));
            treePlatform = treePlatform.getScaledInstance((int)dimensions.x,(int)dimensions.y, 1);
            skyPlatform = ImageIO.read(Main.class.getResource("Cloud.png"));
            skyPlatform = skyPlatform.getScaledInstance((int)dimensions.x,(int)dimensions.y, 1);
        } catch (IOException e) {
            System.err.println("IOException");
            System.exit(1);
        }
    }

    // Constructor for platforms with ladders
    public Platform(World world, Pair position, Pair dimensions, int ladderPos, int ladderLength) {
        this(world, position, dimensions);
        this.ladderPos = ladderPos;
        this.ladderLength = ladderLength;
    }

    // Draws the platform
    public void draw(Graphics g) {
        
        // Draw image of platform based on which world it is
        if (this.world.worldType.equals("Lava Biome")) {
            g.drawImage(lavaPlatform,  (int)position.x, (int)position.y, null);
        } else if (this instanceof TreePlatform){
            g.drawImage(treePlatform,  (int)position.x, (int)position.y, null);
        } else if (this instanceof SkyPlatform) {
            g.drawImage(skyPlatform,  (int)position.x, (int)position.y, null);
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

    public void update(double time) {}

}
