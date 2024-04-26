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
    World[] worlds;
    private Image background = null;

    // Keep track of what type of world this is
    public String worldType;
    
    public World(Main main, int width, int height, String worldType){
        this.main = main;
        this.width = width;
        this.height = height;

        this.person = main.person;
        this.worldType = worldType;
    }

    // Creates the world
    public void createWorld(World[] worlds) {
        this.worlds = worlds;
        if (this.worldType.equals("Homebase")) {
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

        ladderPos[0] = width - 70;
        ladderPos[1] = 19;
        ladderPos[2] = width - 88;

        ladderLength[0] = 205;
        ladderLength[1] = 185;
        ladderLength[2] = 205;

        platforms = new Platform[4];
        platforms[0] = new Platform(this, new Pair(0, 190), new Pair(width, 13), ladderPos[0], ladderLength[0]);
        platforms[1] = new Platform(this, new Pair(0, 388), new Pair(width, 13), ladderPos[1], ladderLength[1]);
        platforms[2] = new Platform(this, new Pair(0, 570), new Pair(width, 13), ladderPos[2], ladderLength[2]);
        platforms[3] = new Platform(this, new Pair(0, height), new Pair(width, 13));

        this.person.currentPlatform = platforms[3];

        platforms[0].door = new Door(this, worlds[1], new Pair((int)(platforms[0].position.x + 30), (int)(platforms[0].position.y - Door.dimensions.y)));
        platforms[1].door = new Door(this, worlds[1], new Pair((int)(platforms[1].position.x + 335), (int)(platforms[1].position.y - Door.dimensions.y)));
        platforms[2].door = new Door(this, worlds[1], new Pair((int)(platforms[2].position.x + 88), (int)(platforms[2].position.y - Door.dimensions.y)));

        platforms[0].key = new Key(platforms[0], this, 200);
        platforms[1].key = new Key(platforms[1], this, width - 350);
        platforms[2].key = new Key(platforms[2], this, 450);
        platforms[3].key = new Key(platforms[3], this, 200);
    }

    // Create objects within lava biome
    private void createLavaBiome() {
        try{
            this.background = ImageIO.read(Main.class.getResource("lavabackground.jpg"));
            background = background.getScaledInstance(width,height, 1);
        } catch (IOException e){
            System.err.println("IOException");
            System.exit(1);
        }

        // Create platforms of lava biome
        platforms = new Platform[13];
        platforms[0] = new Platform(this, new Pair(0, this.height - 600), new Pair(186, 13));
        platforms[1] = new Platform(this, new Pair(186, this.height - 570), new Pair(93, 13));
        platforms[2] = new Platform(this, new Pair(558, this.height - 550), new Pair(93, 13));
        platforms[3] = new Platform(this, new Pair(837, this.height - 500), new Pair(93, 13));
        platforms[4] = new Platform(this, new Pair(372, this.height - 460), new Pair(93, 13));
        platforms[5] = new Platform(this, new Pair(1302, this.height - 520), new Pair(93, 13));
        platforms[6] = new Platform(this, new Pair(1116, this.height - 370), new Pair(93, 13));
        platforms[7] = new Platform(this, new Pair(930, this.height - 250), new Pair(93, 13));
        platforms[8] = new Platform(this, new Pair(558, this.height - 200), new Pair(93, 13));
        platforms[9] = new Platform(this, new Pair(744, this.height - 100), new Pair(93, 13));
        platforms[10] = new Platform(this, new Pair(372, this.height - 150), new Pair(93, 13));
        platforms[11] = new Platform(this, new Pair(186, this.height - 120), new Pair(93, 13));
        platforms[12] = new Platform(this, new Pair(0, this.height), new Pair(this.width, 13));

        // Add door to final platform
        platforms[0].door = new Door(Color.orange, this, worlds[0], new Pair((int) (platforms[0].position.x + 62), (int)(platforms[0].position.y - Door.dimensions.y)));

        platforms[4].key = new Key(platforms[4], this);
        platforms[8].key = new Key(platforms[8], this);
        platforms[5].key = new Key(platforms[5], this);
    }

    public void drawPerson(Graphics g){
        person.draw(g);
    }

    public void updatePerson(double time){
        person.update(time);
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

    
    // Returns the index of a specific platform in the lists of platform
    // Note that return type is Integer so that we can return null without having a compile-time error 
    public Integer findPlatform(Platform target) {
        for (int i = 0; i < platforms.length; i++) {
            if (platforms[i].equals(target)) {
                return i;
            }
        }

        // If platform does not exist, return null
        return null;
    }
    

}
  
