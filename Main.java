
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

 
public class Main extends JPanel implements KeyListener{
    public static final int FPS = 60;

    public static final int openerWidth = 501;
    public static final int openerHeight = 768;

    public static final int HBWidth = 500;
    public static final int HBHeight = 768;

    public static final int lavaWidth = 1400;
    public static final int lavaHeight = 768;

    public static final int treeWidth = 1400;
    public static final int treeHeight = 768;

    public static final int skyWidth = 1400;
    public static final int skyHeight = 768;

    public char c = '0';

    World currentWorld;

    World[] worlds;

    public Person person;

    JFrame frame;

    public ArrayList<Item> uncollectedItems = new ArrayList<>();

    public Main(JFrame frame){
        this.frame = frame;

        // Create person
        this.person = new Person(this, null);

        // Establish array of worlds (0 is opener, 1 is homebase, 2 is lava biome, 3 is tree biome, 4 is sky biome)
        worlds = new World[5];
        worlds[0] = new World(this, openerWidth, openerHeight, "Opener");
        worlds[1] = new World(this, HBWidth, HBHeight, "Homebase");
        worlds[2] = new World(this, lavaWidth, lavaHeight, "Lava Biome");
        worlds[3] = new World(this, treeWidth, treeHeight, "Tree Biome");
        worlds[4] = new World(this, skyWidth, skyHeight, "Sky Biome");

        // Determine the world the person will begin in
        World startWorld = worlds[0];

        // Finish creating each world now that the array has been created
        for (World world : worlds) {
            world.createWorld(worlds);
        }

        // Put the person in the opener
        person.changeWorld(startWorld);

        // Establish opener as the initial current world
        this.currentWorld = startWorld;

        this.setPreferredSize(new Dimension(startWorld.width, startWorld.height));
        this.addKeyListener(this); 
        this.setFocusable(true); 
        this.requestFocus(); 
        Thread mainThread = new Thread(new Runner());
        mainThread.start();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Welcome to Earth Escape!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main mainInstance = new Main(frame);
        frame.setContentPane(mainInstance);
        frame.pack();
        frame.setVisible(true);
    }
 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        currentWorld.draw(g);
        currentWorld.drawPerson(g);
    }
 
    class Runner implements Runnable{
        public void run() {
            while(true){
                currentWorld.updatePerson((double)1.0/FPS);
                currentWorld.updateSkyPlatforms((double)1.0/FPS);
                repaint();
            try{
                Thread.sleep(1000/FPS);
                }
            catch(InterruptedException e){}
            }
        }
    }
 
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        person.movement(c);
    }
    

    public void keyReleased(KeyEvent e) {
        char c = e.getKeyChar();
        person.antimovement(c);
    }
 
 
    public void keyTyped(KeyEvent e) {
    	char c = e.getKeyChar();
        person.movement(c);
        
    }
    
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    // Open the door the person is at
    public void openDoor() {

        // Determine what world the door is opening to
        // Check if person is near door (assuming a door exists)
        if (person.currentPlatform.door != null) {
            if (person.position.x + person.dimensions.x / 3 > person.currentPlatform.door.position.x && person.position.x + person.dimensions.x / 3 < person.currentPlatform.door.position.x + person.currentPlatform.door.dimensions.x && person.currentPlatform.door.canOpen()) {

                World nextWorld = person.currentPlatform.door.nextWorld;
                // Move person to new world
                this.currentWorld = nextWorld;
                person.changeWorld(nextWorld);

                changeDimensions(nextWorld.worldType);
            }
        }
        

    }

    // Change dimensions to those of the specified world
    public void changeDimensions(String worldType) {
        if (worldType.equals("Opener")) {
            this.setPreferredSize(new Dimension(openerWidth, openerHeight));
            frame.setTitle("Welcome to Earth Escape!");
        } else if (worldType.equals("Homebase")) {
            this.setPreferredSize(new Dimension(HBWidth, HBHeight));
            frame.setTitle("Homebase");
        } else if (worldType.equals("Lava Biome")) {
            this.setPreferredSize(new Dimension(lavaWidth, lavaHeight));
            frame.setTitle("Lava Biome");
        } else if (worldType.equals("Tree Biome")) {
            this.setPreferredSize(new Dimension(treeWidth, treeHeight));
            frame.setTitle("Tree Biome");
        } else if (worldType.equals("Sky Biome")) {
            this.setPreferredSize(new Dimension(skyWidth, skyHeight));
            frame.setTitle("Sky Biome");
        } else {
            // An invalid world attempted to be resized
            System.err.println("ERROR: Cannot resize an invalid world.");
            System.exit(1);
        }

        // Don't forget to pack the frame after these changes!
        frame.pack();
    }

    public void checkForItems(Pair personPosition){
        for (int i = 0; i < uncollectedItems.size(); i++) {
            Item item = uncollectedItems.get(i);
            item.checkAndCollect();
        }
    }

    // Returns the index of the platform in the hombease that has the door back to old world
    // If newWorld is not homebase, it returns null
    public Integer findPlatform(World oldWorld, World newWorld) {
        // If there's no door back to the old world (or if the new world does not exist), return null
        if (oldWorld == null || newWorld == null) {
            return null;
        }

        // If the newWorld is not the homebase, the user must start at the bottom platform
        if (!newWorld.worldType.equals("Homebase")) {
            return null;
        }

        // Loop through the homebase's platforms searching for the proper door
        for (int i = 0; i < newWorld.platforms.length; i++) {
            // Only check if a door exists on this platform
            if (newWorld.platforms[i].door != null) {
                if (newWorld.platforms[i].door.nextWorld.equals(oldWorld)) {
                    return i;
                }
            }
        }

        // At this point, no door back has been found (which should be impossible as this is for the homebase)
        return null;
    }
}
