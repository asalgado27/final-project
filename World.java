import java.awt.Graphics;
import java.awt.Color;

class World{
    int height;
    int width;
    int pointsLeft = 0;
    int pointsRight = 0;
    Person person = new Person();

    Platform platform1;
    Platform platform2;
    Door door1;
    
    public World(int initWidth, int initHeight){
        width = initWidth;
        height = initHeight;

        this.platform1 = new Platform(50, 250, 300, 20);
        
        // Create array of ladder locations
        int[] ladderPos = new int[1];
        ladderPos[0] = 100;

        this.platform2 = new Platform(50, 500, 300, 20, ladderPos);
        this.door1 = new Door(300, 300);
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

        // Draw the platforms in the world as well
        platform1.draw(g);
        platform2.draw(g);
        door1.draw(g);
    }
}
