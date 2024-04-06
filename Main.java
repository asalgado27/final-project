import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

 
public class Main extends JPanel implements KeyListener{
    public static final int WIDTH = 320;
    public static final int HEIGHT = 768;
    public static final int FPS = 60;

    public char c = '0';
    World HB;

 
    class Runner implements Runnable{
        public void run() {
            while(true){
                
                repaint();
            try{
                Thread.sleep(1000/FPS);
                }
            catch(InterruptedException e){}
            }
        }
    }
 
    public void keyPressed(KeyEvent e) {
        c = e.getKeyChar();
    }

    public void keyReleased(KeyEvent e) {
    }
 
    public void keyTyped(KeyEvent e) {
    }
    
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
 
    public Main(){
        HB = new World(WIDTH, HEIGHT);
        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Thread mainThread = new Thread(new Runner());
        mainThread.start();
    }
     
    public static void main(String[] args){
        JFrame frame = new JFrame("World...");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main mainInstance = new Main();
        frame.setContentPane(mainInstance);
        frame.pack();
        frame.setVisible(true);
    }
 
 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
 
        g.setColor(new Color(140, 100, 20));
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }
}
