import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


class Person{
    Pair dimensions = new Pair(70, 100); // size of person

    public Pair position;
    Pair velocity;
    Pair acceleration;

    double radius;
    int animationCounter;
    boolean horizontalRMotion;
    boolean horizontalLMotion;

    int upwardVelocity = 500; // jump velocity
    int downwardVelocity = 0;

    Platform currentPlatform;

    // keeps track of whether the person is on a ladder or platform
    boolean onLadder = false;
    boolean canGoUp = true;

    Main main; // allows person to access aspects of main e.g. to check for key presses
    private World currentWorld; // keep track of what world the person is in; same person for any world
    public  ArrayList<Item> inventory; // contains the items the person has collected

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
        //position = new Pair(x, y - dimensions.y);
        velocity = new Pair(0, 0);
        acceleration = new Pair(0,250);
        radius = 5;
        horizontalRMotion = false;
        horizontalLMotion = false;
        animationCounter = 0;
        inventory = new ArrayList<>();
        
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
        } 
        catch (IOException e) {
            System.err.println("IOException");
            System.exit(1);
        }

    }
    
    public void update(World w, double time){
        position = position.add(velocity.times(time)); // continually updates person's position

        // checking whether person is in the y-bounds of world; keeps person within y-bounds of world
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

        // checking whether person is in x-bounds of world; keeps person within x-bounds of world
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

        main.checkForItems(position); // checks whether person is touching items

        checkIfOnLadder();
        checkIfOnPlatform();
            
    }

    public void checkIfOnLadder() {
        // Only check if on ladder if platforms are nearby
        if (currentWorld.platforms != null) {
            for (Platform p : currentWorld.platforms) {
                if (this.position.y + this.dimensions.y > p.position.y && this.position.y + this.dimensions.y <= p.position.y + p.ladderLength) {  
                // check if person within x-bounds of ladder
                    if (this.position.x + this.dimensions.x / 2 < p.ladderPos + p.ladderWidth && this.position.x + this.dimensions.x / 2 > p.ladderPos) {
                    // check if person within y-bounds of ladder  
                        canGoUp = true; // can go up the ladder
                        onLadder = true; // yes on ladder
                        setAcceleration(0); // no acceleration when on ladder
                        upwardVelocity = 80; // climb up with velocity 80
                        downwardVelocity = 80; // climb down with velocity 80
                    }
                    else {
                        onLadder = false; // not on ladder
                        setAcceleration(250); // falls due to gravity
                        upwardVelocity = 500; // jumps with velocity 500
                    }
                }
            }
        }
    }

    public void checkIfOnPlatform() {
        // Only check if on platform if platforms are nearby
        if (currentWorld.platforms != null) {
            for (Platform p : currentWorld.platforms) {
                if (position.y + dimensions.y > currentPlatform.position.y + 2 && p.position.y > currentPlatform.position.y) {
                    currentPlatform = p; // keep track of current platform the person is within y-bounds of
                }
                else if (position.y + dimensions.y < p.position.y && p.position.y < currentPlatform.position.y) {
                    currentPlatform = p; // keep track of current platform the person is within y-bounds of
                }
                // check if within platform's standing dimentions (otherwise needs to fall)
                if (position.y + dimensions.y >= currentPlatform.position.y) {
                    if (position.x + dimensions.x * 3 / 4 > currentPlatform.position.x && position.x + dimensions.x / 4 < currentPlatform.position.x + currentPlatform.dimensions.x) {
                        this.setPosition(new Pair(position.x, currentPlatform.position.y - this.dimensions.y));
                        canGoUp = true;
                        setVelocityY(0);
                    }
                }
                else if (onLadder == false) {
                    canGoUp = false; // can't move up if neither on platform nor on ladder
                }
            }
        }
    }

    public void setPosition(Pair p){
    	position = p;
    }
    public void setVelocityX(int v){
    	velocity = new Pair(v, velocity.y);
    }
    public void setVelocityY(int v){
    	velocity = new Pair(velocity.x, v);
    }
    public void setAcceleration(int a) {
        acceleration = new Pair(acceleration.x, a);
    }
    public Pair getPosition(){
    	return position;
    }
    public Pair getVelocity(){
    	return velocity;
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
            int divided = animationCounter/10%4;
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
            //don't change the velocity. can make the person duck
        }
        if (c == 'w'){
            if (canGoUp == true) { // only jump or climb if canGoUp
                this.setVelocityY(-1 * upwardVelocity);
            }
            
        }
        if (c == 'o') {
            main.openDoor();
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
