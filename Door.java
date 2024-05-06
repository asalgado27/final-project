import java.awt.Color;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

class Door {
    Pair position;
    static Pair dimensions = new Pair(75, 110);
    Color doorColor;

    // Keeps track of what keys are needed to open the door
    int neededKeys[];

    Image doorGraphic = null;

    // Fields to keep track of world door is located in and world it leads to
    World currentWorld;
    public World nextWorld;

    // Default constructor, used for homebase
    public Door(World currentWorld, World nextWorld, Pair position, int neededKeys[]) {
        this.position = position;
        this.currentWorld = currentWorld;
        this.nextWorld = nextWorld;
        this.neededKeys = neededKeys;
    }

    // Constructor for world doors
    public Door(Color doorColor, World currentWorld, World nextWorld, Pair position, int neededKeys[]) {
        this(currentWorld, nextWorld, position, neededKeys);
        this.doorColor = doorColor;
        try {
            doorGraphic = ImageIO.read(Main.class.getResource("Door.png"));
            doorGraphic = doorGraphic.getScaledInstance((int)dimensions.x,(int)dimensions.y, 1);
        } catch (IOException e) {
            System.err.println("IOException");
            System.exit(1);
        }
    }

    // Checks if the needed keys are in the inventory
    public boolean canOpen(){

        if (neededKeys.length==0){
            return true;
        }
        for (int i = 0; i<neededKeys.length; i++){
            //if neededKeys are not contained in the inventory, return false
            if (!currentWorld.person.keyInventory.contains(neededKeys[i])){
                return false;
            } 
        }
        return true;
        
    }

    // Draws the platform
    public void draw(Graphics g) {
        // If door has been given a color (in other words, if we've taken a look at it)
        if (doorColor != null) {
            g.drawImage(doorGraphic, (int) position.x, (int) position.y, null);
        }
    }
}
