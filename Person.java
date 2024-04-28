
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;


class Person{
    // Field to determine size of person
    Pair dimensions = new Pair(70, 100);

    // Position variable denotes the top-left corner of the person's image
    public Pair position;
    Pair velocity;
    Pair acceleration;

    double radius;
    int animationCounter;
    boolean horizontalRMotion;
    boolean horizontalLMotion;

    // Fields to keep track of jump velocity
    int upwardVelocity = 300;
    int downwardVelocity = 0;

    Platform currentPlatform;

    // Field to keep track of whether person is on a ladder or on a platform
    boolean onLadder = false;
    boolean canGoUp = true;


    Main main;
    private World currentWorld;
    public ArrayList<Item> inventory;
    public ArrayList<Integer> keyInventory;

    private Image avatar = null;
    private Image walkR1 = null;
    private Image walkR2 = null;
    private Image walkR3 = null;
    private Image walkR4 = null;
    private Image walkL1 = null;
    private Image walkL2 = null;
    private Image walkL3 = null;
    private Image walkL4 = null;

    public Person(Main main, World world) {
        velocity = new Pair(0, 0);
        acceleration = new Pair(0,250);
        radius = 5;
        horizontalRMotion = false;
        horizontalLMotion = false;
        animationCounter = 0;
        inventory = new ArrayList<>();
        keyInventory = new ArrayList<>();
        
        this.main = main;
        this.currentWorld = world;
        
        try {
            avatar = ImageIO.read(Main.class.getResource("avatar.png"));
            avatar = avatar.getScaledInstance((int)dimensions.x,(int)dimensions.y, 1);
            //Image a = avatar.getScaledInstance(100,50, 1);
            //avatar = (BufferedImage)a;
            walkR1 = ImageIO.read(Main.class.getResource("walkR1.png"));
            walkR1 = walkR1.getScaledInstance((int)dimensions.x,(int)dimensions.y, 1);
            walkR2 = ImageIO.read(Main.class.getResource("walkR2.png"));
            walkR2 = walkR2.getScaledInstance((int)dimensions.x,(int)dimensions.y, 1);
            walkR3 = ImageIO.read(Main.class.getResource("walkR3.png"));
            walkR3 = walkR3.getScaledInstance((int)dimensions.x,(int)dimensions.y, 1);
            walkR4 = ImageIO.read(Main.class.getResource("walkR4.png"));
            walkR4 = walkR4.getScaledInstance((int)dimensions.x,(int)dimensions.y, 1);
            walkL1 = ImageIO.read(Main.class.getResource("walkL1.png"));
            walkL1 = walkL1.getScaledInstance((int)dimensions.x,(int)dimensions.y, 1);
            walkL2 = ImageIO.read(Main.class.getResource("walkL2.png"));
            walkL2 = walkL2.getScaledInstance((int)dimensions.x,(int)dimensions.y, 1);
            walkL3 = ImageIO.read(Main.class.getResource("walkL3.png"));
            walkL3 = walkL3.getScaledInstance((int)dimensions.x,(int)dimensions.y, 1);
            walkL4 = ImageIO.read(Main.class.getResource("walkL4.png"));
            walkL4 = walkL4.getScaledInstance((int)dimensions.x,(int)dimensions.y, 1);
        } catch (IOException e) {
            System.err.println("IOException");
            System.exit(1);
        }

    }

    public void update(double time){
        // Continually update person's position
        position = position.add(velocity.times(time));

        // Check if person is within the y-bounds of the world
        if (position.y <= currentWorld.height - dimensions.y && position.y >= 0){
            velocity = velocity.add(acceleration.times(time));
        }

        else if (position.y < 0) {
            setVelocityY(0);
            this.setPosition(new Pair(position.x, 0));
        }

        else if (position.y > currentWorld.height - dimensions.y){
            setVelocityY(0);
            this.setPosition(new Pair(position.x, currentWorld.height - dimensions.y));
        }

        // Check if person is within the x-bounds of the world
        if (position.x <= 0){
            this.setVelocityX(0);
            this.setPosition(new Pair(0, position.y));
            horizontalRMotion = false;
            horizontalLMotion = false;
            animationCounter = 0;
        }
        else if (position.x >= currentWorld.width - dimensions.x){
            this.setVelocityX(0);
            this.setPosition(new Pair(currentWorld.width - dimensions.x, position.y));
            horizontalRMotion = false;
            horizontalLMotion = false;
            animationCounter = 0;
        }

        // Check if the person is touching any items
        main.checkForItems(position);

        checkIfOnLadder();
        checkIfOnPlatform();
    }
    

    public void checkIfOnLadder() {
        // Only check if on ladder if platforms exist in the person's current world
        if (currentWorld.platforms != null) {
            for (Platform platform : currentWorld.platforms) {
                // Check if person is within the x-bounds of ladder
                if (this.position.x + this.dimensions.x / 2 < platform.ladderPos + platform.ladderWidth && this.position.x + this.dimensions.x / 2 > platform.ladderPos) {
                    // Check if person is within the y-bounds of ladder
                    if (this.position.y + this.dimensions.y <= platform.position.y + platform.ladderLength && this.position.y + this.dimensions.y > platform.position.y) {  
                        canGoUp = true;
                        onLadder = true;
                        // No acceleration when on ladder
                        setAcceleration(0);
                        upwardVelocity = 80;
                        downwardVelocity = 80;

                        // Exit the method as we have found the person to be on the ladder
                        return;
                    }
                }
            }
        }

        // If this point is reached, the person is not on the ladder

        onLadder = false;
        // Falls due to gravity
        setAcceleration(250);
        upwardVelocity = 300;
    }
    

    public void checkIfOnPlatform() {
        // Only check if on platform if platforms are nearby
        if (currentWorld.platforms != null) {
            for (Platform platform : currentWorld.platforms) {
                if (position.y + dimensions.y > currentPlatform.position.y + 2 && platform.position.y > currentPlatform.position.y) {
                    currentPlatform = platform;
                    currentPlatform.personHere = true;
                    System.out.println("1");
                    if (platform instanceof TreePlatform){
                        platform.counterPlus();
                        System.out.println("2");
                    }
                }
                else if (position.y + dimensions.y < platform.position.y && platform.position.y < currentPlatform.position.y) {
                    currentPlatform = platform;
                    currentPlatform.personHere = true;
                    if (platform instanceof TreePlatform){
                        platform.counterPlus();
                        System.out.println("4");
                    }
                }
                // Check if the person can stand on the platform (or otherwise will fall)
                if (position.y + dimensions.y >= currentPlatform.position.y) {
                    if (position.x + dimensions.x * 3 / 4 > currentPlatform.position.x && position.x + dimensions.x / 4 < currentPlatform.position.x + currentPlatform.dimensions.x) {
                        this.setPosition(new Pair(position.x, currentPlatform.position.y - this.dimensions.y));
                        canGoUp = true;
                        setVelocityY(0);
                    }
                }
                else if (onLadder == false) {
                    // Person cannot move up if they're not on the platform or the ladder
                    canGoUp = false;
                }
            }
        }
    }
    
    public void setPosition(Pair position){
    	this.position = position;
    }
    public void setVelocityX(int newXVelocity){
    	this.velocity = new Pair(newXVelocity, this.velocity.y);
    }
    public void setVelocityY(int newYVelocity){
    	this.velocity = new Pair(velocity.x, newYVelocity);
    }
    public void setAcceleration(int newAcceler) {
        this.acceleration = new Pair(this.acceleration.x, newAcceler);
    }
    public Pair getPosition(){
    	return this.position;
    }
    public Pair getVelocity(){
    	return this.velocity;
    }
    
    public void draw(Graphics g){
        if (!horizontalRMotion && !horizontalLMotion){
            g.drawImage(avatar,  (int)position.x, (int)position.y, null);
        }
        if (horizontalRMotion){
            animationCounter++;
            //divided just stores an integer that tells the animation to change which one is being drawn every 10 frames
            int divided = animationCounter/10%4;
            if (divided==0){
                g.drawImage(walkR1,  (int)position.x, (int)position.y, null);
            }
            if (divided==1){
                g.drawImage(walkR2,  (int)position.x, (int)position.y, null);
            }
            if (divided==2){
                g.drawImage(walkR3,  (int)position.x, (int)position.y, null);
            }
            if (divided==3){
                g.drawImage(walkR4,  (int)position.x, (int)position.y, null);
            }
        }
        if (horizontalLMotion){
            animationCounter++;
            //divided just stores an integer that tells the animation to change which one is being drawn every 10 frames
            int divided = animationCounter / 10 % 4;
            if (divided==0){
                g.drawImage(walkL1,  (int)position.x, (int)position.y, null);
            }
            if (divided==1){
                g.drawImage(walkL2,  (int)position.x, (int)position.y, null);
            }
            if (divided==2){
                g.drawImage(walkL3,  (int)position.x, (int)position.y, null);
            }
            if (divided==3){
                g.drawImage(walkL4,  (int)position.x, (int)position.y, null);
            }
        }
    }
    
    //when keys are pressed, use this to start moving
    public void movement(char c){
        if (c == 'a'){
            this.setVelocityX(-200);
            horizontalLMotion = true;
        }
        if (c == 'd'){
            this.setVelocityX(200);
            horizontalRMotion = true;
        }
        if (c == 's'){
            if (onLadder == true) {
                this.setVelocityY(downwardVelocity);
            }
            // Don't change the velocity. can make the person duck
        }
        if (c == 'w'){
            // Only jump or climb if canGoUp
            if (canGoUp == true) {
                this.setVelocityY(-1 * upwardVelocity);
            }
            
        }
        if (c == 'o') {
            main.openDoor(this.currentWorld);
        }
        
    }

    //when keys are released, trigger this to stop moving
    public void antimovement(char c){
        if (c == 'a'){
            this.setVelocityX(0);
            horizontalLMotion = false;
            animationCounter = 0;
        }
        if (c == 's'){
            this.setVelocityY(0);
            animationCounter = 0;
        }
        if (c== 'd'){
            this.setVelocityX(0);
            horizontalRMotion = false;
            animationCounter = 0;
        }
        if (c == 'w'){
            this.setVelocityY(0);
            animationCounter = 0;
        }
    }

    // Changes the world the person is in and puts them at a new location
    public void changeWorld(World newWorld) {
        this.currentWorld = newWorld;
        position = new Pair(50, currentWorld.height - this.dimensions.y);
        // Update the person's new platform and give error message if no platforms available
        if (newWorld.platforms == null) {
            System.err.println("ERROR: New world does not have any platforms for person to stand on.");
            System.exit(1);
        }

        this.currentPlatform = newWorld.platforms[newWorld.platforms.length - 1];
    }

} 
