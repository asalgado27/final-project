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

    public char c = '0';
    World homebase;

 
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
 
    public Main(){
        homebase = new World(WIDTH, HEIGHT);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addKeyListener(this); 
        this.setFocusable(true); 
        this.requestFocus(); 
        Thread mainThread = new Thread(new Runner());
        mainThread.start();
    }
     
    public static void main(String[] args){
        JFrame frame = new JFrame("GAME ON");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main mainInstance = new Main();
        frame.setContentPane(mainInstance);
        frame.pack();
        frame.setVisible(true);
    }
 
 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        homebase.draw(g);
        homebase.drawPerson(g);
    }
}
