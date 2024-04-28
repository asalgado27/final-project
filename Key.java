import java.awt.Color;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Key extends Item {
	Pair position;
	static int radius = 15;
	Color color;
	Random rand;
	int index;

	Platform platform;
	Person person;

	boolean show = true;
	private Image keyImage = null;
	
	public Key(Platform platform, World world, int index) {
		super(world.main, world.person);
		this.platform = platform;
		this.person = world.person;
		rand = new Random();
		color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
		position = new Pair(platform.position.x + platform.dimensions.x / 2, platform.position.y - 52);
		this.index = index;
		try {
            keyImage = ImageIO.read(Main.class.getResource("key.png"));
            keyImage = keyImage.getScaledInstance((int)50,(int)50, 1);
            
        } catch (IOException e) {
            System.err.println("IOException");
            System.exit(1);
        }
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
			
        	g.drawImage(keyImage,  (int)position.x, (int)position.y, null);
			g.drawString("" + index, (int)position.x, (int)position.y);
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
