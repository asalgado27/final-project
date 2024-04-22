// basic structure. Will work out the details when we need to create objects.

import java.awt.Color;
import java.awt.Graphics;

public abstract class Item {
	Pair position;
	Color color;
    Main m;
    Person p;
    boolean visible;

	public Item(/*Main m, Person p*/) {
        // this.m = m;
        //this.p = p;
        // m.uncollectedItems.add(this);
        visible = true;
    }

	public void draw(Graphics g) {}

	public void checkAndCollect() {
        p.inventory.add(this);
        m.uncollectedItems.remove(this);
        visible = false;
    }
	// item disappears (or pops, or does some motion) when the person collects it

	public void use() {
        p.inventory.remove(this);
        
    } 
	// unleash powers of the item
	// remove from inventory if item is inventory; or, if item is immediate-use, immediately use
}
