import java.awt.Color;
import java.awt.Graphics;

class Door {
    Pair position;
    static Pair dimensions = new Pair(75, 110);
    Color doorColor;
    int neededKeys[];

    // Fields to keep track of world door is located in and world it leads to
    World currentWorld;
    public World nextWorld;

    // Default constructor
    public Door(World currentWorld, World nextWorld, Pair position, int neededKeys[]) {
        this.position = position;
        this.currentWorld = currentWorld;
        this.nextWorld = nextWorld;
        this.neededKeys = neededKeys;
    }

    // Constructor if we want the door to have a special look
    public Door(Color doorColor, World currentWorld, World nextWorld, Pair position, int neededKeys[]) {
        this(currentWorld, nextWorld, position, neededKeys);
        this.doorColor = doorColor;
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
        if (doorColor == null) {
            g.setColor(new Color(150, 75, 0));
            g.drawRect((int) position.x, (int) position.y, (int) dimensions.x, (int) dimensions.y);
        } else {
            g.setColor(doorColor);
            g.fillRect((int) position.x, (int) position.y, (int) dimensions.x, (int) dimensions.y);
        }
    }
}
