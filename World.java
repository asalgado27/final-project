import java.awt.Color;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Image;
import javax.imageio.ImageIO;


class World{
    int width;
    int height;
    Person person;

    Platform[] platforms;

    Main main;
    private Image background = null;
    
    public World(Main main, int width, int height, String worldType){
        this.main = main;
        this.width = width;
        this.height = height;

        this.person = main.person;

        if (worldType.equals("Homebase")) {
            createHomebase();
        } else {
            createLavaBiome();
        }
    }

    // Create objects within homebase
    private void createHomebase() {
        try{
            this.background = ImageIO.read(Main.class.getResource("hbBackground.png"));
            background = background.getScaledInstance(width,height, 1);
        } catch (IOException e){
            System.err.println("IOException");
            System.exit(1);
        }
        // Create array of ladder locations
        int[] ladderPos = new int[3]; 
        int[] ladderLength = new int[3];
        // NOTE: THE FUNCTION OF LADDERPOS HAS BEEN CHANGED TO EACH PLATFORM HAS ONE LADDER ONLY

        ladderPos[0] = width - 70;
        ladderPos[1] = 19;
        ladderPos[2] = width - 88;

        ladderLength[0] = 205;
        ladderLength[1] = 185;
        ladderLength[2] = 205;

        platforms = new Platform[4];
        platforms[0] = new Platform(new Pair(0, 190), new Pair(width, 13), ladderPos[0], ladderLength[0]);
        platforms[1] = new Platform(new Pair(0, 388), new Pair(width, 13), ladderPos[1], ladderLength[1]);
        platforms[2] = new Platform(new Pair(0, 570), new Pair(width, 13), ladderPos[2], ladderLength[2]);
        platforms[3] = new Platform(new Pair(0, height), new Pair(width, 13));

        this.person.currentPlatform = platforms[3];

        platforms[0].door = new Door(new Pair((int)(platforms[0].position.x + 30), (int)(platforms[0].position.y - Door.dimensions.y)));
        platforms[1].door = new Door(new Pair((int)(platforms[1].position.x + 335), (int)(platforms[1].position.y - Door.dimensions.y)));
        platforms[2].door = new Door(new Pair((int)(platforms[2].position.x + 88), (int)(platforms[2].position.y - Door.dimensions.y)));
            
        platforms[0].key = new Key(platforms[0], this, 200);
        platforms[1].key = new Key(platforms[1], this, width - 350);
        platforms[2].key = new Key(platforms[2], this, 450);
        platforms[3].key = new Key(platforms[3], this, 200);
    }

    // Create objects within lava biome
    private void createLavaBiome() {
        double stepWidth = this.width * (1.0 / 3.0);
        try{
            this.background = ImageIO.read(Main.class.getResource("lavabackground.jpg"));
            background = background.getScaledInstance(width,height, 1);
        } catch (IOException e){
            System.err.println("IOException");
            System.exit(1);
        }
        // Create platforms of lava biome
        platforms = new Platform[3];

        // CAUTION: platform 0 is the topmost platform, platform 1 is one step lower, and so on
        platforms[0] = new Platform(new Pair(this.width * (2.0 / 3.0), this.height * (1.0 / 3.0)), new Pair(stepWidth, 13));
        platforms[1] = new Platform(new Pair(this.width * (1.0 / 3.0), this.height * (2.0 / 3.0)), new Pair(stepWidth, 13));
        platforms[2] = new Platform(new Pair(this.width * (0.0 / 3.0), this.height * (3.0 / 3.0)), new Pair(this.width, 13));
    
        platforms[0].door = new Door(new Pair((int)(platforms[0].position.x + platforms[0].dimensions.x - 20 - Door.dimensions.x), (int)(platforms[0].position.y - Door.dimensions.y)));
        
        platforms[0].key = new Key(platforms[2], this, 250);
        platforms[1].key = new Key(platforms[1], this);
        platforms[2].key = new Key(platforms[0], this, width - 150);
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
        g.drawImage(background,  (int)0, (int)0, null);

        if (platforms != null) {
            // Draw the platforms in the world
            for (Platform platform : platforms) {
                platform.draw(g);
            }
        }

    }

}
  
