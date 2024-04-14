// basic structure. Will work out the details when we need to create objects.

import java.awt.Color;
import java.awt.Graphics;

public abstract class Item {
	Pair position;
	Color color;

	public Item() {}

	public void draw(Graphics g) {}

	public void collect() {}
	// item disappears (or pops, or does some motion) when the person collects it

	public Color use() {return null;} 
	// unleash powers of the item
	// remove from inventory if item is inventory; or, if item is immediate-use, immediately use
}

