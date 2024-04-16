import java.awt.Color;
import java.util.Random;
import java.awt.Graphics;

public class Key extends Item {
	Pair position;
	static int radius = 10;
	Color color;

	boolean show = true;
	
	public Key(Platform platform, World world) {
		super(world.main, world.person);
		Random rand = new Random();
		Color color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
		position = new Pair(world.width/2, platform.position.y - 50);
	}

	public void draw(Graphics g){
		if (show == true) {
			Color c = g.getColor();
        
        	g.setColor(color);
        	g.drawOval((int) position.x, (int) position.y, radius, radius);
        	g.setColor(c);
		}
    }

    public void collect() {
    	// item disappears when person collects it
    	// how should we make it disappear?
    		// hide? or set position = new Pair() ?
    }
		
	public void use() {
		// semantically: inventory.pop(this);
	}

}
