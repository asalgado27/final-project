import java.awt.Graphics;
import java.awt.Color;

class World{
    int height;
    int width;
    Person person;

    Platform platform1;
    Platform platform2;
    Door door1;
    
    public World(int initWidth, int initHeight){
        width = initWidth;
        height = initHeight;
        person = new Person(50, height);

        platform1 = new Platform(0, 250, width, 20);
        
        // Create array of ladder locations
        int[] ladderPos = new int[1];
        ladderPos[0] = 100;

        platform2 = new Platform(0, 500, width, 20, ladderPos);
        door1 = new Door((int)(platform2.position.x + Door.dimensions.x), (int)(platform2.position.y - Door.dimensions.y));
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
