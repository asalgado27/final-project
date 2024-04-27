import java.awt.Color;
import java.util.Random;
import java.awt.Graphics;

public class Key extends Item {
	Pair position;
	static int radius = 15;
	Color color;
	Random rand;
	int index;

	Platform platform;
	Person person;

	boolean show = true;
	
	public Key(Platform platform, World world, int index) {
		super(world.main, world.person);
		this.platform = platform;
		this.person = world.person;
		rand = new Random();
		color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
		position = new Pair(platform.position.x + platform.dimensions.x / 2, platform.position.y - 52);
		this.index = index;
	}

	public Key(Platform platform, World world, int xPos, int index) {
		super(world.main, world.person);
		this.platform = platform;
		this.person = world.person;
		rand = new Random();
		color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
		position = new Pair(xPos, platform.position.y - 52);
	}

	public void draw(Graphics g){
		if (show == true) {
			Color c = g.getColor();
        
        	g.setColor(color);
        	g.fillOval((int) position.x, (int) position.y, radius, radius);
        	g.setColor(c);
		}
    }

    public void checkAndCollect() {
    	super.checkAndCollect();
		boolean sameYvalue = (person.position.y + person.dimensions.y / 2 - position.y < 10) && (person.position.y + person.dimensions.y / 2 - position.y > -10);
    	boolean sameXvalue = (person.position.x + person.dimensions.x / 2 - position.x < 10 && person.position.x + person.dimensions.x / 2 - position.x > -10);
    	if (sameYvalue && sameXvalue) {
    		show = false;
    	}
    }
		
	public void use() {
		// semantically: inventory.pop(this);
	}

}
