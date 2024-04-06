import java.awt.Graphics;
import java.awt.Color;

public class World{
    int height;
    int width;
    int pointsLeft = 0;
    int pointsRight = 0;
    Person person = new Person();
    
    public World(int initWidth, int initHeight){
        width = initWidth;
        height = initHeight;
    }

    public void drawPerson(Graphics g){
        person.draw(g);
    }

    public void updatePerson(double time){
        person.update(this, time);
    }

    // Draws the world
    public void draw(Graphics g) {
        g.setColor(new Color(135, 206, 235));
        g.fillRect(0, 0, width, height);
    }
}
