import java.awt.Color;
import java.util.Random;

public class Key extends Item {
	Pair position;
	static int radius = 10;
	Color color;
	
	public Key(int x, int y) {
		Random rand = new Random();
		Color color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
		position = new Pair(x, y);
	}

	public void draw(Graphics g){
        Color c = g.getColor();
        
        g.setColor(color);
        g.drawOval(position.x, position.y, radius, radius);
        g.setColor(c);
    }

    public void collect() {
    	// item disappears when person collects it
    	// how should we make it disappear?
    		// hide? or set position = new Pair() ?
    }
		
	public void use() {
		// semantically: inventory.pop(this);
		// person.velocity increases or something like that
		// item needs to control person? otherwise return
		// what the person should do
		return color; // myKey.use() returns blue and then we
		// can do myPerson.color = myKey.use() turning person into blue
	}

}