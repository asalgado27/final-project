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
    public static final int HBWidth = 500;
    public static final int HBHeight = 768;
    public static final int FPS = 60;

    public static final int lavaWidth = 1200;
    public static final int lavaHeight = 500;

    public char c = '0';
    World homebase;
    World lavaBiome;
    World currentWorld;

    public Person person;

    JFrame frame;

    public ArrayList<Item> uncollectedItems;

    public Main(JFrame frame){
        this.frame = frame;
        this.person = new Person(this, null);

        homebase = new World(this, HBWidth, HBHeight, "Homebase");

        // Put the person in the homebase
        person.changeWorld(homebase);

        lavaBiome = new World(this, lavaWidth, lavaHeight, "Lava Biome");

        // Establish homebase as the initial current world
        this.currentWorld = homebase;
        
        uncollectedItems = new ArrayList<>();

        this.setPreferredSize(new Dimension(HBWidth, HBHeight));
        this.addKeyListener(this); 
        this.setFocusable(true); 
        this.requestFocus(); 
        Thread mainThread = new Thread(new Runner());
        mainThread.start();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Homebase");
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
        // Check if person is near door (assuming a door exists)
        if (person.currentPlatform.door != null) {
            if (person.position.x + person.dimensions.x / 3 > person.currentPlatform.door.position.x && person.position.x + person.dimensions.x / 3 < person.currentPlatform.door.position.x + person.currentPlatform.door.dimensions.x) {
                // Move person to new world
                if (currentWorld == homebase) {
                    this.currentWorld = lavaBiome;
                    person.changeWorld(lavaBiome);
                    changeDimensions("Lava Biome");
                }
                else if (currentWorld == lavaBiome) {
                    this.currentWorld = homebase;
                    person.changeWorld(homebase);
                    changeDimensions("Homebase");
                    person.position = new Pair(homebase.platforms[2].door.position.x, homebase.platforms[2].door.position.y);
                }

            }
        }
        

    }

    // Change dimensions to those of the specified world
    public void changeDimensions(String worldType) {
        if (worldType.equals("Homebase")) {
            this.setPreferredSize(new Dimension(HBWidth, HBHeight));
            frame.setTitle("Homebase");
            frame.pack();
        } else {
            this.setPreferredSize(new Dimension(lavaWidth, lavaHeight));
            frame.setTitle("Lava Biome");
            frame.pack();
        }
    }

    public void checkForItems(Pair personPosition){
        for (int i = 0; i < uncollectedItems.size(); i++) {
            Item item = uncollectedItems.get(i);
            //i'll change this later to be a range --Alicia
            item.checkAndCollect();
        }
    }
}
