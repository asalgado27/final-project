import java.awt.Color;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Key implements Item {
	Pair position;
	static int radius = 15;
	Color color;
	Main main;
	Random rand;
	int index;
	boolean visible;

	Platform platform;
	Person person;

	boolean show = true;
	private Image keyImage = null;
	
	// Constructs a Key object
	public Key(Platform platform, Main main, World world, int index) {

        main.uncollectedItems.add(this);
		this.platform = platform;
		this.person = world.person;
		this.main = main;
		rand = new Random();
		color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
		position = new Pair(platform.position.x + platform.dimensions.x / 2, platform.position.y - 52);
		this.index = index;
		this.visible = true;
		try {
            keyImage = ImageIO.read(Main.class.getResource("key.png"));
            keyImage = keyImage.getScaledInstance((int)50,(int)50, 1);
            
        } catch (IOException e) {
            System.err.println("IOException");
            System.exit(1);
        }
	}

	// Draws the Key object
	public void draw(Graphics g){
		if (show == true) {
        	g.drawImage(keyImage,  (int)position.x, (int)position.y, null);
		}
    }

	// If the person comes into contact with the key, it will disappear and be stored in the person's keyInventory.
    public void checkAndCollect() {
		boolean sameYvalue = (person.position.y + person.dimensions.y / 2 - position.y < 10) && (person.position.y + person.dimensions.y / 2 - position.y > -10);
    	boolean sameXvalue = (person.position.x + person.dimensions.x / 2 - position.x < 10 && person.position.x + person.dimensions.x / 2 - position.x > -10);
    	if (sameYvalue && sameXvalue) {
    		show = false;
			person.keyInventory.add(this.index);
			main.uncollectedItems.remove(this);
    	}
    }

	// Item disappears (or pops, or does some motion) when the person collects it
    public void use() {
        if (this instanceof Key){
            person.keyInventory.remove(this.index);
        }
    } 
		

}
