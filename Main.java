import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

 
public class Main extends JPanel implements KeyListener{
    public static final int WIDTH = 500;
    public static final int HEIGHT = 768;
    public static final int FPS = 60;

    public static final int lavaWidth = 1200;
    public static final int lavaHeight = 768;

    public char c = '0';
    World homebase;
    World lavaBiome;

    World currentWorld;

    public Main(){
        homebase = new World(this, WIDTH, HEIGHT);
        homebase.createHomebase();

        lavaBiome = new World(this, lavaWidth, lavaHeight);
        lavaBiome.createLavaBiome(homebase.person);

        // Establish homebase as the current world
        this.currentWorld = homebase;

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addKeyListener(this); 
        this.setFocusable(true); 
        this.requestFocus(); 
        Thread mainThread = new Thread(new Runner());
        mainThread.start();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Homebase");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main mainInstance = new Main();
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
                homebase.updatePerson((double)1.0/FPS);
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
        homebase.person.movement(c);
    }
    

    public void keyReleased(KeyEvent e) {
        char c = e.getKeyChar();
        homebase.person.antimovement(c);
    }
 
 
    public void keyTyped(KeyEvent e) {
    	char c = e.getKeyChar();
        
        
    }
    
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    // Open the door the person is at
    public void openDoor() {
        // Check if person is near door
        if (homebase.person.position.x > homebase.doors[0].position.x && homebase.person.position.x < homebase.doors[0].position.x + homebase.doors[0].dimensions.x) {
            // Move person to new world
            this.currentWorld = lavaBiome;
            dimLava();
        }
    }

    // Change dimensions to those of Lava Biome
    public void dimLava() {
        this.setPreferredSize(new Dimension(lavaWidth, lavaHeight));
    }
}
