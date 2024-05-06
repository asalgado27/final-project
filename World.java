import java.awt.Color;
import java.awt.Graphics;
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
        if (this.worldType.equals("Opener")) {
            createOpener();
        } else if (this.worldType.equals("Homebase")) {
            createHomebase();
        } else if (this.worldType.equals("Lava Biome")){
            createLavaBiome();
        } else if (this.worldType.equals("Tree Biome")){
            createTreeBiome();
        } else if (this.worldType.equals("Sky Biome")) {
            createSkyBiome();
        } else if (this.worldType.equals("Win Page")) {
            createWinPage();
        } else {
            // If an invalid world type is attempted to be created
            System.err.println("ERROR: Invalid world type.");
            System.exit(1);
        }
    }

    // Creates the opening page of the game
    private void createOpener() {
        try{
            this.background = ImageIO.read(Main.class.getResource("openerbackground.png"));
            background = background.getScaledInstance(width,height, 1);
        } catch (IOException e){
            System.err.println("IOException");
            System.exit(1);
        }

        // Create platform of opener world
        platforms = new Platform[1];
        platforms[0] = new Platform(this, new Pair(0, height), new Pair(width, 13));

        this.person.currentPlatform = platforms[0];
        // Opener door opens without keys
        platforms[0].door = new Door(this, worlds[1], new Pair((int)(platforms[0].position.x + 360), (int)(platforms[0].position.y - Door.dimensions.y)), new int[]{});
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
        
        platforms[0].door = new Door(this, worlds[4], new Pair((int)(platforms[0].position.x + 30), (int)(platforms[0].position.y - Door.dimensions.y)), new int[]{});
        platforms[1].door = new Door(this, worlds[3], new Pair((int)(platforms[1].position.x + 335), (int)(platforms[1].position.y - Door.dimensions.y)), new int[]{});
        platforms[2].door = new Door(this, worlds[2], new Pair((int)(platforms[2].position.x + 88), (int)(platforms[2].position.y - Door.dimensions.y)), new int[]{});

        platforms[0].key = new Key(platforms[0], this, 3);
        platforms[1].key = new Key(platforms[1], this, 2);
        platforms[2].key = new Key(platforms[2], this, 1);
        platforms[3].key = new Key(platforms[3], this, 0);
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
        platforms[0] = new Platform(this, new Pair(0, this.height - 600), new Pair(186, 20));
        platforms[1] = new Platform(this, new Pair(186, this.height - 570), new Pair(93, 20));
        platforms[2] = new Platform(this, new Pair(558, this.height - 550), new Pair(93, 20));
        platforms[3] = new Platform(this, new Pair(837, this.height - 500), new Pair(93, 20));
        platforms[4] = new Platform(this, new Pair(372, this.height - 460), new Pair(93, 20));
        platforms[5] = new Platform(this, new Pair(1302, this.height - 520), new Pair(93, 20));
        platforms[6] = new Platform(this, new Pair(1116, this.height - 370), new Pair(93, 20));
        platforms[7] = new Platform(this, new Pair(930, this.height - 250), new Pair(93, 20));
        platforms[8] = new Platform(this, new Pair(558, this.height - 200), new Pair(93, 20));
        platforms[9] = new Platform(this, new Pair(744, this.height - 100), new Pair(93, 20));
        platforms[10] = new Platform(this, new Pair(372, this.height - 150), new Pair(93, 20));
        platforms[11] = new Platform(this, new Pair(186, this.height - 120), new Pair(93, 20));
        platforms[12] = new Platform(this, new Pair(0, this.height), new Pair(this.width, 20));

        // Add door to final platform - this door needs keys 4, 5, 6
        platforms[0].door = new Door(Color.orange, this, worlds[1], new Pair((int) (platforms[0].position.x + 62), (int)(platforms[0].position.y - Door.dimensions.y)), new int[]{4,5,6});

        // Note that the lava biome contains keys 4, 5, 6
        platforms[4].key = new Key(platforms[4], this, 4);
        platforms[8].key = new Key(platforms[8], this, 5);
        platforms[5].key = new Key(platforms[5], this, 6);
    }

    // Create objects within tree biome
    private void createTreeBiome() {
        try{
            this.background = ImageIO.read(Main.class.getResource("treebackground.png"));
            background = background.getScaledInstance(width,height, 1);
        } catch (IOException e){
            System.err.println("IOException");
            System.exit(1);
        }

        // Create platforms of tree biome
        platforms = new TreePlatform[13];
        platforms[0] = new TreePlatform(this, new Pair(0, this.height - 600), new Pair(186, 20));
        platforms[1] = new TreePlatform(this, new Pair(186, this.height - 570), new Pair(93, 20));
        platforms[2] = new TreePlatform(this, new Pair(558, this.height - 550), new Pair(93, 20));
        platforms[3] = new TreePlatform(this, new Pair(837, this.height - 500), new Pair(93, 20));
        platforms[4] = new TreePlatform(this, new Pair(372, this.height - 460), new Pair(93, 20));
        platforms[5] = new TreePlatform(this, new Pair(1302, this.height - 520), new Pair(93, 20));
        platforms[6] = new TreePlatform(this, new Pair(1116, this.height - 370), new Pair(93, 20));
        platforms[7] = new TreePlatform(this, new Pair(930, this.height - 250), new Pair(93, 20));
        platforms[8] = new TreePlatform(this, new Pair(558, this.height - 200), new Pair(93, 20));
        platforms[9] = new TreePlatform(this, new Pair(744, this.height - 100), new Pair(93, 20));
        platforms[10] = new TreePlatform(this, new Pair(372, this.height - 150), new Pair(93, 20));
        platforms[11] = new TreePlatform(this, new Pair(186, this.height - 120), new Pair(93, 20));
        platforms[12] = new TreePlatform(this, new Pair(0, this.height), new Pair(this.width, 20));

        // Add door to final platform - this door needs keys 7, 8, 9
        platforms[0].door = new Door(Color.pink, this, worlds[1], new Pair((int) (platforms[0].position.x + 62), (int)(platforms[0].position.y - Door.dimensions.y)), new int[]{7,8,9});
        
        //the tree biome needs keys 7, 8, 9
        platforms[4].key = new Key(platforms[4], this, 7);
        platforms[8].key = new Key(platforms[8], this, 8);
        platforms[5].key = new Key(platforms[5], this, 9);
    }
    
    // Create objects within sky biome
    private void createSkyBiome() {
        try{
            this.background = ImageIO.read(Main.class.getResource("skybackground.png"));
            background = background.getScaledInstance(width,height, 1);
        } catch (IOException e){
            System.err.println("IOException");
            System.exit(1);
        }

        // Create platforms of sky biome
        platforms = new SkyPlatform[10];
        platforms[0] = new SkyPlatform(this, new Pair(1214, this.height - 450), new Pair(186, 20));
        platforms[1] = new SkyPlatform(this, new Pair(270, this.height - 240), new Pair(93, 20));
        platforms[2] = new SkyPlatform(this, new Pair(790, this.height - 510), new Pair(93, 20));
        platforms[3] = new SkyPlatform(this, new Pair(400, this.height - 420), new Pair(93, 20));
        platforms[4] = new SkyPlatform(this, new Pair(660, this.height - 380), new Pair(93, 20));
        platforms[5] = new SkyPlatform(this, new Pair(1050, this.height - 280), new Pair(93, 20));
        platforms[6] = new SkyPlatform(this, new Pair(530, this.height - 270), new Pair(93, 20));
        platforms[7] = new SkyPlatform(this, new Pair(920, this.height - 220), new Pair(93, 20));
        platforms[8] = new SkyPlatform(this, new Pair(130, this.height - 80), new Pair(93, 20));
        platforms[9] = new SkyPlatform(this, new Pair(0, this.height), new Pair(this.width, 20));

        // Add door to win page - this door needs keys 10, 11, 12
        platforms[0].door = new Door(Color.blue, this, worlds[5], new Pair((int) (platforms[0].position.x + 62), (int)(platforms[0].position.y - Door.dimensions.y)), new int[]{10,11,12});

        // Sky biome contains keys 10, 11, 12
        platforms[4].key = new Key(platforms[4], this, 10);
        platforms[8].key = new Key(platforms[8], this, 11);
        platforms[5].key = new Key(platforms[5], this, 12);
    }

    // Create background and platform within the Win screen
    private void createWinPage() {
        try{
            this.background = ImageIO.read(Main.class.getResource("winbackground.jpg"));
            background = background.getScaledInstance(width,height, 1);
        } catch (IOException e){
            System.err.println("IOException");
            System.exit(1);
        }

        platforms = new Platform[1];
        platforms[0] = new Platform(this, new Pair(0, this.height), new Pair(this.width, 13));

    }

    public void drawPerson(Graphics g){
        person.draw(g);
    }

    public void updatePerson(double time){
        person.update(time);
    }

    public void updateSkyPlatforms(double time) {
        if (this.worldType.equals("Sky Biome")) {
            platforms[1].update(time);
            platforms[2].update(time);
            platforms[3].update(time);
            platforms[6].update(time);
            platforms[7].update(time);
        } 
    }

    // Draws the world
    public void draw(Graphics g) {
        // Draw the background of the world
        g.drawImage(background, (int) 0, (int) 0, null);

        if (platforms != null) {
            // Draw the platforms in the world
            for (Platform platform : platforms) {
                platform.draw(g);
            }
        }

    }

}
