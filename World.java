
import java.awt.Color;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


class World{
    int width;
    int height;
    public Person person;

    Platform[] platforms;
    Door[] doors;

    Main main;
    private BufferedImage hbBackground = null;
    
    public World(Main main, int initWidth, int initHeight){
        this.width = initWidth;
        this.height = initHeight;
        this.main = main;
    }

    // Create objects within homebase
    public void createHomebase() {
        person = new Person(main, this, 50, height);
        try{
            hbBackground = ImageIO.read(Main.class.getResource("hbBackground.png"));
        } catch (IOException e){
            System.err.println("IOException");
            System.exit(1);
        }
        // Create array of ladder locations
        int[] ladderPos = new int[3]; 
        int[] ladderLength = new int[3];
        // NOTE: THE FUNCION OF LADDERPOS HAS BEEN CHANGED TO EACH PLATFORM HAS ONE LADDER ONLY

        ladderPos[0] = width - 60;
        ladderPos[1] = 29;
        ladderPos[2] = width - 78;

        ladderLength[0] = 200;
        ladderLength[1] = 180;
        ladderLength[2] = 200;

        platforms = new Platform[3];
        platforms[0] = new Platform(0, 190, width, 13, ladderPos[0], ladderLength[0]);
        platforms[1] = new Platform(0, 388, width, 13, ladderPos[1], ladderLength[1]);
        platforms[2] = new Platform(0, 570, width, 13, ladderPos[2], ladderLength[2]);

        doors = new Door[3];
        doors[0] = new Door((int)(platforms[0].position.x + 30), (int)(platforms[0].position.y - Door.dimensions.y));
        doors[1] = new Door((int)(platforms[1].position.x + 335), (int)(platforms[1].position.y - Door.dimensions.y));
        doors[2] = new Door((int)(platforms[2].position.x + 88), (int)(platforms[2].position.y - Door.dimensions.y));
    }

    // Create objects within lava biome
    public void createLavaBiome(Person person) {
        this.person = person;
    }

    public void drawPerson(Graphics g){
        person.draw(g);
    }

    public void updatePerson(double time){
        person.update(this, time);
    }

    // Draws the world
    public void draw(Graphics g) {
        // Draw the background of the world
        g.drawImage(hbBackground,  (int)0, (int)0, null);

        if (platforms != null) {
            // Draw the platforms in the world
            for (Platform platform : platforms) {
                platform.draw(g);
            }
        }

        if (doors != null) {
            // Then draw the doors in the world
            for (Door door : doors) {
                door.draw(g);
            }
        }

    }

}
  
