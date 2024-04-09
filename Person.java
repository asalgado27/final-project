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
import java.io.File;
import java.io.IOException;


class Person{
    Pair position;
    Pair velocity;
    Pair acceleration;
    double radius;
    int animationCounter;
    boolean horizontalMotion;
    private BufferedImage avatar = null;
    private BufferedImage walk1 = null;
    private BufferedImage walk2 = null;
    private BufferedImage walk3 = null;
    private BufferedImage walk4 = null;

    public Person() {
        position = new Pair(262.0, 384.0);
        velocity = new Pair((double)(0), (double)(0));
        acceleration = new Pair(0,300);
        radius = 5;
        horizontalMotion = false;
        animationCounter = 0;
        
        try{
            avatar = ImageIO.read(Main.class.getResource("avatar.png"));
            walk1 = ImageIO.read(Main.class.getResource("walk1.png"));
            walk2 = ImageIO.read(Main.class.getResource("walk2.png"));
            walk3 = ImageIO.read(Main.class.getResource("walk3.png"));
            walk4 = ImageIO.read(Main.class.getResource("walk4.png"));
        } catch (IOException e){
            System.err.println("IOException");
            System.exit(1);
        }
        
    }
    
    public void update(World w, double time){
        position = position.add(velocity.times(time));
        if (position.y>=0){
            velocity = velocity.add(acceleration.times(time));
        }
        if (position.y>=786-170){
            setVelocityY(0);
        }
        if (position.x <=-10){
            this.setVelocityX(0);
            this.setPosition(new Pair(-9, position.y));
            horizontalMotion = false;
            animationCounter = 0;
        }
        if (position.x>= 1024-100){
            this.setVelocityX(0);
            this.setPosition(new Pair(1024-100-1, position.y));
            horizontalMotion = false;
            animationCounter = 0;
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
    public Pair getPosition(){
    	return position;
    }
    public Pair getVelocity(){
    	return velocity;
    }
    
    public void draw(Graphics g){
        if (!horizontalMotion){
            g.drawImage(avatar,  (int)position.x, (int)position.y, null);
        }
        if (horizontalMotion){
            animationCounter++;
            //divided just stores an integer that tells the animation to change which one is being drawn every 10 frames
            int divided = animationCounter/10%4;
            if (divided==0){
                g.drawImage(walk1,  (int)position.x, (int)position.y, null);
            }
            if (divided==1){
                g.drawImage(walk2,  (int)position.x, (int)position.y, null);
            }
            if (divided==2){
                g.drawImage(walk3,  (int)position.x, (int)position.y, null);
            }
            if (divided==3){
                g.drawImage(walk4,  (int)position.x, (int)position.y, null);
            }
        }
    }

    
    //when keys are pressed, use this to start moving
    public void movement(char c){
        
        if (c == 'a'){
            this.setVelocityX(-200);
            horizontalMotion = true;
        }
        if (c == 'd'){
            this.setVelocityX(200);
            horizontalMotion = true;
        }
        if (c == 's'){
            this.setVelocityY(200);
        }
        if (c == 'w'){
            this.setVelocityY(-200);
        }
        
    }
    //when keys are released, trigger this to stop moving
    public void antimovement(char c){
        if (c == 'a' || c=='d'){
            this.setVelocityX(0);
            horizontalMotion = false;
            animationCounter = 0;
        }
        if (c == 's' || c == 'w'){
            this.setVelocityY(0);
            horizontalMotion = false;
            animationCounter = 0;
        }
    }

} 
